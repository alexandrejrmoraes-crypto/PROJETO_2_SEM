
package br.com.threearquitetura.service;

import br.com.threearquitetura.model.Produto;
import br.com.threearquitetura.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // Cadastrar produto
    public Produto cadastrar(Produto produto) {
        return repository.save(produto);
    }

    // Listar todos os produtos
    public List<Produto> listar() {
        return repository.findAll();
    }

    // Buscar produto pelo ID
    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Produto não encontrado"));
    }

    // Atualizar produto
    public Produto atualizar(Long id, Produto dados) {
        Produto produto = buscarPorId(id);

        produto.setNome(dados.getNome());
        produto.setQuantidade(dados.getQuantidade());
        produto.setPreco(dados.getPreco());

        return repository.save(produto);
    }

    // Excluir produto
    public void excluir(Long id) {
        Produto produto = buscarPorId(id);
        repository.delete(produto);
    }
}
