# Three Arquitetura 🏛️

Site institucional e portfólio digital da **Three Arquitetura**, com formulário de solicitação de orçamento e gerenciamento de solicitações. Projeto acadêmico.

## Tecnologias

- **Frontend:** HTML5, CSS3 e JavaScript (responsivo)
- **Backend:** Java 21, Spring Boot **3.5.7**, Spring Web e Spring Data JPA
- **Banco de dados:** MySQL 8.4
- **Build e publicação:** Maven, Docker, GitHub Codespaces e Render

## Funcionalidades

- Apresentação do escritório, serviços, portfólio, contato e perguntas frequentes
- Layout com logo, menu adaptado para celulares e links de WhatsApp
- Solicitação de orçamento com armazenamento no MySQL
- Código secreto individual para **consultar, editar ou cancelar** uma solicitação (CRUD)

> **Importante:** o formulário salva as solicitações no banco de dados. **Envio automático de e-mail ainda não foi implementado.**

## Estrutura principal

```text
PROJETO_2_SEM/
├── .devcontainer/                    # Codespaces e MySQL local
├── README.md
└── three-arquitetura/
    ├── Dockerfile                   # Imagem Java 21 para o Render
    ├── pom.xml
    └── src/main/
        ├── java/br/com/threearquitetura/
        └── resources/
            ├── application.properties
            └── static/             # index.html, style.css, script.js e logos
```

## Executar localmente (Codespaces)

1. Abra o repositório no GitHub Codespaces.
2. Inicie/verifique o MySQL configurado em `.devcontainer/docker-compose.yml`.
3. Configure as variáveis `DB_USER` e `DB_PASSWORD` conforme seu ambiente.
4. Na pasta `three-arquitetura`, execute:

```bash
cd three-arquitetura
bash mvnw spring-boot:run
```

5. Abra a porta **8080** no painel **PORTS** do Codespaces.

A aplicação usa `jdbc:mysql://localhost:3306/three_arquitetura?createDatabaseIfNotExist=true` por padrão. Ajuste as credenciais conforme a configuração local.

## Variáveis de ambiente

| Variável | Função | Padrão |
|---|---|---|
| `DB_URL` | URL JDBC do MySQL | MySQL local na porta 3306 |
| `DB_USER` | Usuário do MySQL | `root` |
| `DB_PASSWORD` | Senha do MySQL | Vazia (somente para ambiente local configurado assim) |
| `PORT` | Porta HTTP | `8080` |

**Nunca publique credenciais ou senhas no repositório.** Em hospedagem, configure-as como variáveis privadas do serviço.

## Docker e hospedagem

A imagem pode ser construída na raiz do repositório com:

```bash
docker build -t three-arquitetura ./three-arquitetura
```

No Render, use o repositório GitHub, branch `main`, linguagem **Docker** e diretório raiz `three-arquitetura/`. Configure `DB_URL`, `DB_USER` e `DB_PASSWORD` no painel do serviço. O banco externo precisa estar acessível a partir do Render.

O plano gratuito do Render pode suspender o serviço por inatividade. O banco de testes Aiven utilizado durante o projeto pode ter **créditos com validade limitada**, portanto confirme seu status antes de apresentações.

## API de solicitações

| Operação | Método e rota | Autorização |
|---|---|---|
| Criar | `POST /api/solicitacoes` | Pública |
| Consultar | `POST /api/solicitacoes/consultar` | Código secreto no JSON |
| Atualizar | `PUT /api/solicitacoes` | Header `X-Codigo-Secreto` |
| Cancelar | `POST /api/solicitacoes/cancelar` | Código secreto no JSON |

O código secreto é apresentado após o cadastro. **Guarde-o**, pois é necessário para gerenciar a solicitação.

## Atualização do visual

Esta versão integra elementos de `att html.zip` ao projeto Spring Boot existente, incluindo **logo do escritório, menu para celulares e perguntas frequentes**, preservando o formulário funcional e seu CRUD. As imagens do portfólio ainda são ilustrativas e podem ser substituídas por imagens autorizadas.

## Cuidados

- Não inclua dados pessoais reais em testes.
- Faça testes locais antes de enviar alterações para a branch `main`, que aciona a publicação automática no Render.
- O envio de e-mails é uma melhoria futura e requer serviço de e-mail e credenciais próprias.
