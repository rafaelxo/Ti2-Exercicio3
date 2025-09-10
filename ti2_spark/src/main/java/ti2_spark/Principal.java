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

        if (!usuarioDAO.conectar()) {
            System.out.println("Falha ao conectar no banco!");
            stop();
            return;
        }

        final java.util.function.BiFunction<Request,String,String> param = (req, name) -> {
            String v = req.queryParams(name);
            if (v != null) return v;
            String body = req.body();
            if (body == null || body.isEmpty()) return null;
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                    if (k.equals(name)) { return URLDecoder.decode(kv[1], StandardCharsets.UTF_8); }
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
                        .append("\"id\":").append(u.getId()).append(",")
                        .append("\"login\":\"").append(escapeJson(u.getLogin())).append("\",")
                        .append("\"senha\":\"").append(escapeJson(u.getSenha())).append("\",")
                        .append("\"nome\":\"").append(escapeJson(u.getNome())).append("\",")
                        .append("\"email\":\"").append(escapeJson(u.getEmail())).append("\"");

                    if (u.getInfos() != null) {
                        json.append(",\"atividades\":").append(u.getInfos().getAtividades().size())
                            .append(",\"feedbacks\":").append(u.getInfos().getFeedbacks().size());
                    }

                    json.append("}");
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

            Usuario novo = new Usuario();
            novo.setLogin(param.apply(req, "login"));
            novo.setSenha(DAO.getMD5(param.apply(req, "senha")));
            novo.setNome(param.apply(req, "nome"));
            novo.setEmail(param.apply(req, "email"));
            novo.setFoto(param.apply(req, "foto"));
            novo.setTelefone(param.apply(req, "telefone"));
            novo.setDataNasc(param.apply(req, "dataNasc"));
            novo.setLocalizacao(param.apply(req, "localizacao"));
            novo.setProfissao(param.apply(req, "profissao"));
            novo.setFormacao(param.apply(req, "formacao"));
            novo.setEmpresa(param.apply(req, "empresa"));
            novo.setHabilidades(param.apply(req, "habilidades"));
            novo.setIdioma(param.apply(req, "idioma"));

            boolean ok = usuarioDAO.inserirUsuario(novo);
            res.status(ok ? 201 : 500);
            return "{\"success\":" + ok + "}";
        });

        // Atualizar usuário
        put("/usuarios/:id", (req, res) -> {
            res.type("application/json; charset=UTF-8");
            int id = Integer.parseInt(req.params(":id"));

            Usuario u = new Usuario();
            u.setId(id);
            u.setLogin(param.apply(req, "login"));
            u.setSenha(DAO.getMD5(param.apply(req, "senha")));
            u.setNome(param.apply(req, "nome"));
            u.setEmail(param.apply(req, "email"));
            u.setFoto(param.apply(req, "foto"));
            u.setTelefone(param.apply(req, "telefone"));
            u.setDataNasc(param.apply(req, "dataNasc"));
            u.setLocalizacao(param.apply(req, "localizacao"));
            u.setProfissao(param.apply(req, "profissao"));
            u.setFormacao(param.apply(req, "formacao"));
            u.setEmpresa(param.apply(req, "empresa"));
            u.setHabilidades(param.apply(req, "habilidades"));
            u.setIdioma(param.apply(req, "idioma"));

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
