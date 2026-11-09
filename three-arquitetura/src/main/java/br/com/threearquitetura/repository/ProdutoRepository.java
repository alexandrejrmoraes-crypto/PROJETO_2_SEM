
package br.com.threearquitetura.repository;

import br.com.threearquitetura.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}

