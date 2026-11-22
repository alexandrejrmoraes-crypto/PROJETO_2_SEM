package br.com.threearquitetura;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://127.0.0.1:5501", "http://localhost:5501", "https://hitallooliveira.github.io"})
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {
    private final SolicitacaoRepository banco;
    private final NotificacaoEmailService notificacaoEmail;
    private final SecureRandom sorteador = new SecureRandom();

    public SolicitacaoController(
        SolicitacaoRepository banco,
        NotificacaoEmailService notificacaoEmail) {

    this.banco = banco;
    this.notificacaoEmail = notificacaoEmail;
}

    public record Dados(String nome, String email, String telefone, String tipoServico, String mensagem) {}
    public record Codigo(String codigo) {}

    private void preencher(Solicitacao s, Dados d) {
        if (d == null || vazio(d.nome()) || vazio(d.email()) || vazio(d.telefone()) || vazio(d.tipoServico()) || vazio(d.mensagem())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preencha todos os campos");
        }
        if (d.nome().length() > 120 || d.email().length() > 180 || d.telefone().length() > 20 || d.tipoServico().length() > 80 || d.mensagem().length() > 3000 || !d.email().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados invalidos");
        }
        s.nome = d.nome().trim();
        s.email = d.email().trim();
        s.telefone = d.telefone() == null ? "" : d.telefone().trim();
        s.tipoServico = d.tipoServico().trim();
        s.mensagem = d.mensagem().trim();
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }

    private String gerarCodigo() {
        byte[] bytes = new byte[32];
        sorteador.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String codigo) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(codigo.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao proteger o codigo", e);
        }
    }

    private Solicitacao localizar(String codigo) {
        if (vazio(codigo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo obrigatorio");
        }
        return banco.findByCodigoHash(hash(codigo))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitacao nao encontrada"));
    }


    private Map<String, Object> resposta(Solicitacao s) {
    return Map.of(
        "id", s.id,
        "nome", s.nome,
        "email", s.email,
        "telefone", s.telefone == null ? "" : s.telefone,
        "tipoServico", s.tipoServico,
        "mensagem", s.mensagem
        );
    }

    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> criar(@RequestBody Dados dados) {
        Solicitacao s = new Solicitacao();
        preencher(s, dados);

        String codigo = gerarCodigo();
        s.codigoHash = hash(codigo);

        banco.save(s);

     
        notificacaoEmail.avisarNovoOrcamento(s);

        return Map.of(
            "id", s.id,
            "codigoSecreto", codigo
        );
    }


    public Map<String, Object> consultar(@RequestBody Codigo pedido) {
        return resposta(localizar(pedido.codigo()));
    }


    @PutMapping
    public Map<String, Object> atualizar(@RequestHeader("X-Codigo-Secreto") String codigo,
                                         @RequestBody Dados dados) {
        Solicitacao s = localizar(codigo);
        preencher(s, dados);
        return resposta(banco.save(s));
    }


    @PostMapping("/cancelar")
    public Map<String, String> cancelar(@RequestBody Codigo pedido) {
        banco.delete(localizar(pedido.codigo()));
        return Map.of("mensagem", "Solicitacao cancelada");
    }
}
