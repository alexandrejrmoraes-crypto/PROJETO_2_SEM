package br.com.threearquitetura.controller;

import br.com.threearquitetura.model.Projeto;
import br.com.threearquitetura.service.ProjetoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    // READ - Listar projetos
    @GetMapping
    public List<Projeto> listar() {
        return service.listar();
    }

    // READ - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscar(@PathVariable Long id) {
        Projeto projeto = service.buscarPorId(id);

        if (projeto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(projeto);
    }

    // CREATE - Cadastrar projeto
    @PostMapping
    public Projeto cadastrar(@RequestBody Projeto projeto) {
        return service.cadastrar(projeto);
    }

    // UPDATE - Atualizar projeto
    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizar(
            @PathVariable Long id,
            @RequestBody Projeto dados) {

        Projeto projeto = service.atualizar(id, dados);

        if (projeto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(projeto);
    }

    // DELETE - Excluir projeto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
