package ti2_spark;

import static spark.Spark.*;

public class Principal {
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static void main(String[] args) {
        port(6789);
        staticFiles.location("/public"); // pasta resources/public para arquivos estáticos (HTML, CSS, JS)

        // Conexão com banco
        if (!usuarioDAO.conectar()) {
            System.out.println("Falha ao conectar no banco!");
            stop();
            return;
        }

        // Listar usuários
        get("/usuarios", (req, res) -> {
            res.type("application/json");
            Usuario[] usuarios = usuarioDAO.listarUsuarios();
            StringBuilder json = new StringBuilder("[");
            if (usuarios != null) {
                for (int i = 0; i < usuarios.length; i++) {
                    Usuario u = usuarios[i];
                    json.append("{")
                        .append("\"codigo\":").append(u.getCodigo()).append(",")
                        .append("\"login\":\"").append(u.getLogin()).append("\",")
                        .append("\"senha\":\"").append(u.getSenha()).append("\",")
                        .append("\"sexo\":\"").append(u.getSexo()).append("\"")
                        .append("}");
                    if (i < usuarios.length - 1) json.append(",");
                }
            }
            json.append("]");
            return json.toString();
        });

        // Inserir usuário
        post("/usuarios", (req, res) -> {
            res.type("application/json");
            String login = req.queryParams("login");
            String senha = req.queryParams("senha");
            char sexo = req.queryParams("sexo").charAt(0);

            Usuario[] usuarios = usuarioDAO.listarUsuarios();
            int cod = (usuarios != null && usuarios.length > 0) ? usuarios[usuarios.length - 1].getCodigo() + 1 : 1;

            Usuario novo = new Usuario(cod, login, DAO.getMD5(senha), sexo);
            boolean ok = usuarioDAO.inserirUsuario(novo);
            return "{\"success\":" + ok + "}";
        });

        // Atualizar usuário
        put("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            String login = req.queryParams("login");
            String senha = req.queryParams("senha");
            char sexo = req.queryParams("sexo").charAt(0);

            Usuario u = new Usuario(id, login, DAO.getMD5(senha), sexo);
            boolean ok = usuarioDAO.atualizarUsuario(u);
            return "{\"success\":" + ok + "}";
        });

        // Excluir usuário
        delete("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            boolean ok = usuarioDAO.excluirUsuario(id);
            return "{\"success\":" + ok + "}";
        });
    }
}
