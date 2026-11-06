# Three Arquitetura

Base do site institucional e futuro sistema administrativo do escritório Three Arquitetura.

## Tecnologias
Java 21, Spring Boot, Maven Wrapper, Thymeleaf e MySQL.

## Executar no Windows (VS Code)
1. Instale Java 21 e MySQL.
2. Crie o banco de dados: `CREATE DATABASE three_arquitetura;`
3. Configure `DB_PASSWORD` no ambiente com a senha local do MySQL (e `DB_USER`/`DB_URL` se necessário).
4. Na pasta que contém `pom.xml`, execute `./mvnw.cmd spring-boot:run` no PowerShell.
5. Acesse http://localhost:8080

## Estado atual
A página inicial e a estrutura Spring Boot estão adaptadas. Ainda **não** foram implementados CRUD, autenticação, portfólio dinâmico ou APIs; essas são as próximas etapas.

## Segurança
Não coloque senhas reais em `application.properties` nem envie arquivos com credenciais ao GitHub. O ZIP foi criado sem o histórico `.git` e sem a pasta de compilação `target`.
