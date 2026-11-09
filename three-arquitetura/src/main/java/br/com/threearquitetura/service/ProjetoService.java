package br.com.threearquitetura.service;

import br.com.threearquitetura.model.Projeto;
import br.com.threearquitetura.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository repository;

    public ProjetoService(ProjetoRepository repository) {
        this.repository = repository;
    }

    // CREATE - Cadastrar projeto
    public Projeto cadastrar(Projeto projeto) {
        return repository.save(projeto);
    }

    // READ - Listar projetos
    public List<Projeto> listar() {
        return repository.findAll();
    }

    // READ - Buscar por ID
    public Projeto buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE - Atualizar projeto
    public Projeto atualizar(Long id, Projeto dados) {
        Projeto projeto = buscarPorId(id);

        if (projeto == null) {
            return null;
        }

        projeto.setTitulo(dados.getTitulo());
        projeto.setDescricao(dados.getDescricao());
        projeto.setCategoria(dados.getCategoria());
        projeto.setImagemUrl(dados.getImagemUrl());

        return repository.save(projeto);
    }

    // DELETE - Excluir projeto
    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}
