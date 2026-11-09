
package br.com.threearquitetura.controller;

import br.com.threearquitetura.model.Produto;
import br.com.threearquitetura.service.ProdutoService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    // CREATE - Cadastrar produto
    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return service.cadastrar(produto);
    }

    // READ - Listar produtos
    @GetMapping
    public List<Produto> listar() {
        return service.listar();
    }

    // READ - Buscar produto pelo ID
    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // UPDATE - Atualizar produto
    @PutMapping("/{id}")
    public Produto atualizar(
            @PathVariable Long id,
            @RequestBody Produto produto) {
        return service.atualizar(id, produto);
    }

    // DELETE - Excluir produto
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
