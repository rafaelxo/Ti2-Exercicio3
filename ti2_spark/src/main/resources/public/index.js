const API = "/usuarios";

// Read
async function listarUsuarios() {
  try {
    const res = await fetch(API);
    if (!res.ok) throw new Error("Erro ao buscar usuários");
    const usuarios = await res.json();
	
    const tbody = document.querySelector("#tabelaUsuarios tbody");
    tbody.innerHTML = "";

    usuarios.forEach(u => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${u.id}</td>
        <td>${u.nome}</td>
        <td>${u.login}</td>
        <td>${u.senha}</td>
        <td>${u.email}</td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error(err);
    alert("Erro ao listar usuários");
  }
}

// Create
async function inserirUsuario(event) {
  event.preventDefault();
  const nome = document.getElementById("novoNome").value;
  const login = document.getElementById("novoLogin").value;
  const senha = document.getElementById("novaSenha").value;
  const email = document.getElementById("novoEmail").value;

  try {
    const res = await fetch(API, {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: `nome=${encodeURIComponent(nome)}&login=${encodeURIComponent(login)}&senha=${encodeURIComponent(senha)}&email=${encodeURIComponent(email)}`
    });
    if (!res.ok) throw new Error("Erro ao inserir usuário");
    listarUsuarios();
    event.target.reset();
  } catch (err) {
    console.error(err);
    alert("Erro ao inserir usuário");
  }
}

// Update
async function atualizarUsuario(event) {
  event.preventDefault();
  const id = document.getElementById("atualizarId").value;
  const nome = document.getElementById("atualizarNome").value;
  const login = document.getElementById("atualizarLogin").value;
  const senha = document.getElementById("atualizarSenha").value;
  const email = document.getElementById("atualizarEmail").value;

  try {
    const res = await fetch(`${API}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: `nome=${encodeURIComponent(nome)}&login=${encodeURIComponent(login)}&senha=${encodeURIComponent(senha)}&email=${encodeURIComponent(email)}`
    });
    if (!res.ok) throw new Error("Erro ao atualizar usuário");
    listarUsuarios();
    event.target.reset();
  } catch (err) {
    console.error(err);
    alert("Erro ao atualizar usuário");
  }
}

// Delete
async function excluirUsuario(event) {
  event.preventDefault();
  const id = document.getElementById("excluirId").value;

  try {
    const res = await fetch(`${API}/${id}`, { method: "DELETE" });
    if (!res.ok) throw new Error("Erro ao excluir usuário");
    listarUsuarios();
    event.target.reset();
  } catch (err) {
    console.error(err);
    alert("Erro ao excluir usuário");
  }
}

// Carrega a lista logo no início
listarUsuarios();
