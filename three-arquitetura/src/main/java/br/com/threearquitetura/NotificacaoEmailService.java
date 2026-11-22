
package br.com.threearquitetura;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificacaoEmailService {

    private final RestClient cliente = RestClient.create();

    @Value("${BREVO_API_KEY:}")
    private String chaveApi;

    private static final String EMAIL =
            "alexandrejrmoraes@uni9.edu.br";

    public void avisarNovoOrcamento(Solicitacao solicitacao) {

        if (chaveApi == null || chaveApi.isBlank()) {
            System.err.println(
                "Aviso de orçamento não enviado: BREVO_API_KEY ausente."
            );
            return;
        }

        String mensagem = """
                Novo orçamento recebido - Three Arquitetura

                Número do pedido: %s
                Nome: %s
                E-mail: %s
                Telefone/WhatsApp: %s
                Tipo de serviço: %s

                Mensagem do cliente:
                %s
                """.formatted(
                    solicitacao.id,
                    solicitacao.nome,
                    solicitacao.email,
                    solicitacao.telefone,
                    solicitacao.tipoServico,
                    solicitacao.mensagem
                );

        Map<String, Object> corpo = Map.of(
            "sender", Map.of(
                "name", "Three Arquitetura",
                "email", EMAIL
            ),
            "to", List.of(
                Map.of("email", EMAIL)
            ),
            "subject", "Novo pedido de orçamento - Three Arquitetura",
            "textContent", mensagem
        );

        try {
            cliente.post()
                .uri("https://api.brevo.com/v3/smtp/email")
                .contentType(MediaType.APPLICATION_JSON)
                .header("api-key", chaveApi)
                .body(corpo)
                .retrieve()
                .toBodilessEntity();

            System.out.println("Notificação de orçamento enviada ao Brevo.");

        } catch (Exception e) {
            System.err.println(
                "Não foi possível enviar a notificação ao Brevo."
            );
        }
    }
}
