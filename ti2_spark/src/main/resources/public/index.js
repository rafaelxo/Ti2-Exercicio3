const API = "/usuarios";
    
    async function listarUsuarios() {
      const res = await fetch(API);
      const usuarios = await res.json();
      const tbody = document.querySelector("#tabelaUsuarios tbody");
      tbody.innerHTML = "";
      usuarios.forEach(u => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${u.codigo}</td>
          <td>${u.login}</td>
          <td>${u.senha}</td>
          <td>${u.sexo}</td>
        `;
        tbody.appendChild(tr);
      });
    }

    async function inserirUsuario(event) {
      event.preventDefault();
      const login = document.getElementById("novoLogin").value;
      const senha = document.getElementById("novaSenha").value;
      const sexo = document.getElementById("novoSexo").value;

      await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `login=${encodeURIComponent(login)}&senha=${encodeURIComponent(senha)}&sexo=${encodeURIComponent(sexo)}`
      });
      listarUsuarios();
      event.target.reset();
    }

    async function atualizarUsuario(event) {
      event.preventDefault();
      const id = document.getElementById("atualizarId").value;
      const login = document.getElementById("atualizarLogin").value;
      const senha = document.getElementById("atualizarSenha").value;
      const sexo = document.getElementById("atualizarSexo").value;

      await fetch(`${API}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `login=${encodeURIComponent(login)}&senha=${encodeURIComponent(senha)}&sexo=${encodeURIComponent(sexo)}`
      });
      listarUsuarios();
      event.target.reset();
    }

    async function excluirUsuario(event) {
      event.preventDefault();
      const id = document.getElementById("excluirId").value;

      await fetch(`${API}/${id}`, { method: "DELETE" });
      listarUsuarios();
      event.target.reset();
    }

    listarUsuarios();