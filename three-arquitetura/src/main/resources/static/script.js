const API = '/api/solicitacoes'; // API no mesmo domínio do site, inclusive no Render.

const formulario = document.getElementById('form-orcamento');
const resultadoEnvio = document.getElementById('resultado-envio');
const consulta = document.getElementById('form-consulta');
const resultadoConsulta = document.getElementById('resultado-consulta');
const edicao = document.getElementById('form-edicao');

function dadosDoFormulario(form) {
  return {
    nome: form.querySelector('[name="nome"]').value.trim(),
    email: form.querySelector('[name="email"]').value.trim(),
    telefone: form.querySelector('[name="telefone"]').value.trim(),
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
    document.getElementById('editar-telefone').value = dados.telefone || '';
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
    telefone: document.getElementById('editar-telefone').value.trim(),
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

// Menu responsivo (versão atualizada do frontend).
const botaoMenu = document.getElementById('menu-toggle');
const menuPrincipal = document.getElementById('menu-principal');
if (botaoMenu && menuPrincipal) {
  botaoMenu.addEventListener('click', () => {
    const aberto = menuPrincipal.classList.toggle('aberto');
    botaoMenu.setAttribute('aria-expanded', String(aberto));
  });
  menuPrincipal.querySelectorAll('a').forEach(link => link.addEventListener('click', () => {
    menuPrincipal.classList.remove('aberto');
    botaoMenu.setAttribute('aria-expanded', 'false');
  }));
}

;(() => {

    const fotosComerciais = document.querySelectorAll(".fotos-comerciais img");
const fotoAmpliada = document.getElementById("foto-ampliada");
const imagemGrande = document.getElementById("imagem-grande");
const fecharFoto = document.getElementById("fechar-foto");

fotosComerciais.forEach(function(foto) {
    foto.addEventListener("click", function() {
        imagemGrande.src = foto.src;
        fotoAmpliada.classList.add("aberta");
    });
});

fecharFoto.addEventListener("click", function() {
    fotoAmpliada.classList.remove("aberta");
});
    const projetoComercial = document.getElementById("projeto-comercial");
const galeriaComercial = document.getElementById("galeria-comercial");
const fecharGaleria = document.getElementById("fechar-galeria");

projetoComercial.addEventListener("click", function() {
    galeriaComercial.classList.add("aberta");

    galeriaComercial.scrollIntoView({
        behavior: "smooth"
    });
});

fecharGaleria.addEventListener("click", function() {
    galeriaComercial.classList.remove("aberta");
});
    const galeria = document.getElementById("galeria");
const anterior = document.getElementById("anterior");
const proximo = document.getElementById("proximo");

let indiceAtual = 0;
const totalProjetos = galeria.children.length;

function atualizarCarrossel() {
    galeria.style.transform =
        "translateX(-" + (indiceAtual * 100) + "%)";
}
anterior.addEventListener("click", function() {
    indiceAtual--;

    if (indiceAtual < 0) {
        indiceAtual = totalProjetos - 1;
    }

    atualizarCarrossel();
});

proximo.addEventListener("click", function() {
    indiceAtual++;

    if (indiceAtual >= totalProjetos) {
        indiceAtual = 0;
    }

    atualizarCarrossel();
});

setInterval(function() {
    indiceAtual++;

    if (indiceAtual >= totalProjetos) {
        indiceAtual = 0;
    }

    atualizarCarrossel();
}, 4000);
    const projetoResidencial = document.getElementById("projeto-residencial");
const galeriaResidencial = document.getElementById("galeria-residencial");
const fecharGaleriaResidencial = document.getElementById("fechar-galeria-residencial");

projetoResidencial.addEventListener("click", function() {
    galeriaResidencial.classList.add("aberta");

    galeriaResidencial.scrollIntoView({
        behavior: "smooth"
    });
});

fecharGaleriaResidencial.addEventListener("click", function() {
    galeriaResidencial.classList.remove("aberta");
});
    const projetoInteriores = document.getElementById("projeto-interiores");
const galeriaInteriores = document.getElementById("galeria-interiores");
const fecharGaleriaInteriores = document.getElementById("fechar-galeria-interiores");

projetoInteriores.addEventListener("click", function() {
    galeriaInteriores.classList.add("aberta");

    galeriaInteriores.scrollIntoView({
        behavior: "smooth"
    });
});

fecharGaleriaInteriores.addEventListener("click", function() {
    galeriaInteriores.classList.remove("aberta");
});

})();
