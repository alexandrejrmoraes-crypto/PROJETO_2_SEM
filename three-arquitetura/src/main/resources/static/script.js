// ENDEREÇO DO SEU BACK-END (mude quando publicar o Spring Boot)
const API = '/api/solicitacoes'; // Para testes locais. Ao publicar, troque pela URL HTTPS do servidor.

// Estas rotas são um exemplo. Confira os caminhos reais no seu Controller Java.
const formulario = document.getElementById('form-orcamento');
const resultadoEnvio = document.getElementById('resultado-envio');
const consulta = document.getElementById('form-consulta');
const resultadoConsulta = document.getElementById('resultado-consulta');
const edicao = document.getElementById('form-edicao');

function dadosDoFormulario(form) {
  return {
    nome: form.querySelector('[name="nome"]').value.trim(),
    email: form.querySelector('[name="email"]').value.trim(),
    tipoServico: form.querySelector('[name="tipoServico"]').value,
    mensagem: form.querySelector('[name="mensagem"]').value.trim()
  };
}

// Enviar orçamento (CREATE)
formulario.addEventListener('submit', async function(evento) {
  evento.preventDefault();
  resultadoEnvio.textContent = 'Enviando...';
  try {
    const resposta = await fetch(API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(dadosDoFormulario(formulario))
    });
    if (!resposta.ok) throw new Error('Não foi possível enviar a solicitação.');
    const dados = await resposta.json();
    // O back-end deve devolver { codigo: "..." } após cadastrar.
    if (!dados.codigoSecreto) throw new Error('O servidor não devolveu o código de consulta.');
    resultadoEnvio.textContent = 'Solicitação enviada! Guarde seu código secreto: ' + dados.codigoSecreto;
    formulario.reset();
  } catch (erro) {
    resultadoEnvio.textContent = erro.message + ' Confira se o Spring Boot está ligado.';
  }
});

// Consultar orçamento (READ)
consulta.addEventListener('submit', async function(evento) {
  evento.preventDefault();
  edicao.classList.add('escondido');
  resultadoConsulta.textContent = 'Consultando...';
  const codigo = document.getElementById('codigo').value.trim();
  try {
    // O código vai no cabeçalho, não na URL.
    const resposta = await fetch(API + '/consultar', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ codigo }) });
    if (!resposta.ok) throw new Error('Solicitação não encontrada ou código inválido.');
    const dados = await resposta.json();
    document.getElementById('editar-nome').value = dados.nome || '';
    document.getElementById('editar-email').value = dados.email || '';
    document.getElementById('editar-servico').value = dados.tipoServico || 'Consultoria';
    document.getElementById('editar-mensagem').value = dados.mensagem || '';
    resultadoConsulta.textContent = 'Solicitação encontrada! Você pode editar ou cancelar abaixo.';
    edicao.classList.remove('escondido');
  } catch (erro) { resultadoConsulta.textContent = erro.message; }
});

// Editar orçamento (UPDATE)
edicao.addEventListener('submit', async function(evento) {
  evento.preventDefault();
  const codigo = document.getElementById('codigo').value.trim();
  const dados = {
    nome: document.getElementById('editar-nome').value.trim(),
    email: document.getElementById('editar-email').value.trim(),
    tipoServico: document.getElementById('editar-servico').value,
    mensagem: document.getElementById('editar-mensagem').value.trim()
  };
  try {
    const resposta = await fetch(API, {
      method: 'PUT', headers: { 'Content-Type':'application/json', 'X-Codigo-Secreto':codigo },
      body: JSON.stringify(dados)
    });
    if (!resposta.ok) throw new Error('Não foi possível salvar.');
    resultadoConsulta.textContent = 'Alterações salvas com sucesso!';
  } catch (erro) { resultadoConsulta.textContent = erro.message; }
});

// Cancelar orçamento (DELETE)
document.getElementById('botao-cancelar').addEventListener('click', async function() {
  if (!confirm('Tem certeza de que deseja cancelar sua solicitação?')) return;
  const codigo = document.getElementById('codigo').value.trim();
  try {
    const resposta = await fetch(API + '/cancelar', { method:'POST', headers:{ 'Content-Type':'application/json' }, body: JSON.stringify({ codigo }) });
    if (!resposta.ok) throw new Error('Não foi possível cancelar.');
    resultadoConsulta.textContent = 'Solicitação cancelada.';
    edicao.classList.add('escondido');
    consulta.reset();
  } catch (erro) { resultadoConsulta.textContent = erro.message; }
});
