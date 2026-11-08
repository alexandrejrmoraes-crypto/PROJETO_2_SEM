# Three Arquitetura

Projeto acadêmico desenvolvido em Java com Spring Boot e MySQL.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Maven
- MySQL 8.4
- Docker
- GitHub Codespaces

## Como executar

### 1. Abrir o Codespaces

No GitHub, acesse o repositório e clique em:

Code > Codespaces > Create codespace on main

### 2. Configurar as senhas

Configure os seguintes Codespaces Secrets:

- DB_PASSWORD
- DB_ROOT_PASSWORD

Cada integrante deve utilizar suas próprias senhas.

Após configurar os secrets, reinicie o Codespaces para disponibilizar as variáveis.

### 3. Verificar o MySQL

No terminal, execute:

docker compose -f .devcontainer/docker-compose.yml ps

O MySQL deve aparecer com status healthy.

### 4. Executar o Spring Boot

Entre na pasta do projeto:

cd three-arquitetura

Execute:

./mvnw spring-boot:run

### 5. Acessar o sistema

Abra a aba PORTAS (PORTS) do Codespaces.

Localize a porta 8080 e abra o endereço disponibilizado.

## Banco de dados

Banco: three_arquitetura

Usuário: three_user

O Docker Compose configura o MySQL automaticamente na primeira inicialização com um volume vazio.

## Observações

- Nunca publique senhas no GitHub.
- O arquivo application.properties é gerado a partir de application.properties.example.
- Os dados do MySQL são preservados em um volume Docker.
