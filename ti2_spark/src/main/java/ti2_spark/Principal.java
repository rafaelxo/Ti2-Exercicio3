package ti2_spark;

import static spark.Spark.*;
import spark.Request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Principal {
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static void main(String[] args) {
        port(6789);
        staticFiles.location("/public");

        // Conexão com banco
        if (!usuarioDAO.conectar()) {
            System.out.println("Falha ao conectar no banco!");
            stop();
            return;
        }

        // Helper para extrair params de POST/PUT
        final java.util.function.BiFunction<Request,String,String> param = (req, name) -> {
            String v = req.queryParams(name);
            if (v != null) return v;
            String body = req.body();
            if (body == null || body.isEmpty()) return null;
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                    if (k.equals(name)) {
                        return URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                    }
                }
            }
            return null;
        };

        // Listar usuários
        get("/usuarios", (req, res) -> {
            res.type("application/json; charset=UTF-8");
            Usuario[] usuarios = usuarioDAO.listarUsuarios();
            StringBuilder json = new StringBuilder("[");
            if (usuarios != null) {
                for (int i = 0; i < usuarios.length; i++) {
                    Usuario u = usuarios[i];
                    json.append("{")
                        .append("\"codigo\":").append(u.getCodigo()).append(",")
                        .append("\"login\":\"").append(escapeJson(u.getLogin())).append("\",")
                        .append("\"senha\":\"").append(escapeJson(u.getSenha())).append("\",")
                        .append("\"sexo\":\"").append(u.getSexo()).append("\"")
                        .append("}");
                    if (i < usuarios.length - 1) json.append(",");
                }
            }
            json.append("]");
            res.status(200);
            return json.toString();
        });

        // Inserir usuário
        post("/usuarios", (req, res) -> {
            res.type("application/json; charset=UTF-8");
            String login = param.apply(req, "login");
            String senha = param.apply(req, "senha");
            String sexoStr = param.apply(req, "sexo");
            if (login == null || senha == null || sexoStr == null || sexoStr.length() == 0) {
                res.status(400);
                return "{\"success\":false, \"error\":\"Parâmetros inválidos\"}";
            }
            char sexo = sexoStr.charAt(0);
            Usuario novo = new Usuario();
            try {
                novo.setLogin(login);
                novo.setSenha(DAO.getMD5(senha));
                novo.setSexo(sexo);
            } catch (IllegalArgumentException ex) {
                res.status(400);
                return "{\"success\":false, \"error\":\"" + escapeJson(ex.getMessage()) + "\"}";
            }
            boolean ok = usuarioDAO.inserirUsuario(novo);
            res.status(ok ? 201 : 500);
            return "{\"success\":" + ok + "}";
        });

        // Atualizar usuário
        put("/usuarios/:id", (req, res) -> {
            res.type("application/json; charset=UTF-8");
            int id = Integer.parseInt(req.params(":id"));
            String login = param.apply(req, "login");
            String senha = param.apply(req, "senha");
            String sexoStr = param.apply(req, "sexo");
            if (login == null || senha == null || sexoStr == null || sexoStr.length() == 0) {
                res.status(400);
                return "{\"success\":false, \"error\":\"Parâmetros inválidos\"}";
            }
            char sexo = sexoStr.charAt(0);
            Usuario u = new Usuario(id, login, DAO.getMD5(senha), sexo);
            boolean ok = usuarioDAO.atualizarUsuario(u);
            res.status(ok ? 200 : 404);
            return "{\"success\":" + ok + "}";
        });

        // Excluir usuário
        delete("/usuarios/:id", (req, res) -> {
            res.type("application/json; charset=UTF-8");
            int id = Integer.parseInt(req.params(":id"));
            boolean ok = usuarioDAO.excluirUsuario(id);
            res.status(ok ? 200 : 404);
            return "{\"success\":" + ok + "}";
        });
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}