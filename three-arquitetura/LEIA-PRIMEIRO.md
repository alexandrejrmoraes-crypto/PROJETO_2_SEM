# Three Arquitetura — versão simplificada

Esta versão foi simplificada do ZIP anterior para **4 arquivos Java**.

## O que funciona no código
- CREATE: POST /api/solicitacoes (JSON: nome, email, tipoServico, mensagem) — devolve codigoSecreto.
- READ: POST /api/solicitacoes/consultar (JSON: codigo).
- UPDATE: PUT /api/solicitacoes (mesmo JSON de criação; cabeçalho X-Codigo-Secreto).
- DELETE: POST /api/solicitacoes/cancelar (JSON: codigo).
- Os códigos são aleatórios e apenas seu hash é salvo no MySQL.
- Não há endpoint público para listar todas as solicitações.

## O que foi adiado de propósito
- Recuperação do código por e-mail.
- Notificação automática ao escritório.
- Integração com o site do colega, CORS e hospedagem.
- Proteção contra muitas tentativas, validação robusta de e-mail, testes automatizados e ajustes de produção.

**Não publique esta API com dados reais de clientes antes de implementar proteção contra tentativas repetidas, HTTPS e revisão de segurança.**

## Para executar no Windows com VS Code
1. Tenha Java JDK 21 e MySQL em execução.
2. No MySQL Workbench, execute o conteúdo de `database.sql`.
3. No terminal do VS Code, configure `DB_USER` e `DB_PASSWORD` como variáveis de ambiente (não envie senhas ao GitHub).
4. No terminal PowerShell: `./mvnw.cmd spring-boot:run` (se tiver Maven instalado, pode usar `mvn spring-boot:run`).
5. A API estará em http://localhost:8080. Para testar, use Postman ou a extensão REST Client.

Exemplo para criar solicitação (POST /api/solicitacoes):
```json
{"nome":"Ana","email":"ana@example.com","tipoServico":"Reforma","mensagem":"Quero reformar minha casa"}
```
Guarde o `codigoSecreto` retornado. Para consultar/cancelar, envie `{"codigo":"SEU_CODIGO"}`.

## Explicação dos 4 arquivos
- `ThreeApplication.java`: liga o Spring Boot.
- `Solicitacao.java`: define os campos gravados no MySQL.
- `SolicitacaoRepository.java`: dá acesso ao banco sem SQL manual.
- `SolicitacaoController.java`: recebe pedidos, confere o código e executa o CRUD.

Esta versão é didática: depois podemos separar Controller e Service quando você entender a lógica.
