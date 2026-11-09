package br.com.threearquitetura.repository;

import br.com.threearquitetura.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
