# Three Arquitetura — Front-end + Back-end no mesmo projeto

## Estrutura
- `src/main/resources/static/index.html`: página do site
- `src/main/resources/static/style.css`: visual
- `src/main/resources/static/script.js`: formulários e chamadas da API
- `src/main/java/br/com/threearquitetura/`: quatro classes Java do Spring Boot
- `src/main/resources/application.properties`: conexão MySQL

## Executar no Windows com VS Code
1. Tenha Java JDK 21 e MySQL instalados e iniciados.
2. Crie o banco `three_arquitetura` (veja `database.sql`) e ajuste usuário e senha no MySQL.
3. Configure as variáveis de ambiente `DB_USER` e `DB_PASSWORD` (ou ajuste `application.properties` SOMENTE no seu computador, sem enviar senha ao GitHub).
4. Abra esta pasta no VS Code. No terminal, execute `./mvnw.cmd spring-boot:run` (PowerShell).
5. Abra `http://localhost:8080/` no navegador. O site e a API rodam no mesmo servidor.
6. Envie uma solicitação com dados fictícios e anote o código. Depois consulte, edite e cancele.

## Integração realizada
O JavaScript agora usa exatamente as rotas do Controller:
- `POST /api/solicitacoes` — cria e recebe `codigoSecreto`
- `POST /api/solicitacoes/consultar` — consulta enviando `{ "codigo": "..." }`
- `PUT /api/solicitacoes` — edita com cabeçalho `X-Codigo-Secreto`
- `POST /api/solicitacoes/cancelar` — cancela enviando `{ "codigo": "..." }`

## GitHub Pages e hospedagem
GitHub Pages publica somente arquivos estáticos. O Spring Boot e MySQL precisam de hospedagem separada. Para publicar o site no GitHub Pages, copie os três arquivos da pasta `static` para a raiz do repositório do site, hospede o Java e mude a constante `API` no `script.js` para a URL HTTPS real. Não deixe `localhost` na versão pública.

## Ainda falta
- Configurar e testar o MySQL no seu computador.
- Testar o Spring Boot com Java 21 (não foi executado aqui).
- Recuperação por e-mail e notificações ao escritório não estão implementadas.
- Segurança adicional (limitação de tentativas, HTTPS, revisão de CORS e validação) antes de uso com clientes reais.
- O site contém imagens ilustrativas; substitua por fotos autorizadas.

Não publique senhas do banco no GitHub. Não use dados reais de clientes nos testes.
