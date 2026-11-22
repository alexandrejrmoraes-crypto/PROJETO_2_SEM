package br.com.threearquitetura;

import jakarta.persistence.*;

@Entity
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String email;
    public String telefone;
    public String tipoServico;
    @Column(length = 3000)
    public String mensagem;

    // Guardamos o hash do código, nunca o código secreto original.
    @Column(nullable = false, unique = true)
    public String codigoHash;
}
