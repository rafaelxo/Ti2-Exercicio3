package ti2_spark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {

    public UsuarioDAO() { super(); }

    public Usuario[] listarUsuarios() {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY id";
        try (PreparedStatement pst = conexao.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setEmail(rs.getString("email"));
                u.setFoto(rs.getString("foto"));
                u.setTelefone(rs.getString("telefone"));
                u.setDataNasc(rs.getString("dataNasc"));
                u.setLocalizacao(rs.getString("localizacao"));
                u.setProfissao(rs.getString("profissao"));
                u.setFormacao(rs.getString("formacao"));
                u.setEmpresa(rs.getString("empresa"));
                u.setHabilidades(rs.getString("habilidades"));
                u.setIdioma(rs.getString("idioma"));
                List<Atividade> atividades = listarAtividades(u.getId());
                List<Feedback> feedbacks = listarFeedbacks(u.getId());
                Infos infos = new Infos(atividades, feedbacks);
                u.setInfos(infos);
                list.add(u);
            }
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return list.toArray(new Usuario[0]);
    }

    public boolean inserirUsuario(Usuario u) {
        boolean status = false;
        String sql = "INSERT INTO usuario (nome, login, senha, email, foto, telefone, dataNasc, localizacao, profissao, formacao, empresa, habilidades, idioma) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, u.getNome());
            pst.setString(2, u.getLogin());
            pst.setString(3, u.getSenha());
            pst.setString(4, u.getEmail());
            pst.setString(5, u.getFoto());
            pst.setString(6, u.getTelefone());
            pst.setString(7, u.getDataNasc());
            pst.setString(8, u.getLocalizacao());
            pst.setString(9, u.getProfissao());
            pst.setString(10, u.getFormacao());
            pst.setString(11, u.getEmpresa());
            pst.setString(12, u.getHabilidades());
            pst.setString(13, u.getIdioma());

            int affected = pst.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) u.setId(keys.getInt(1));
                }
                status = true;
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }

    public boolean atualizarUsuario(Usuario u) {
        boolean status = false;
        String sql = "UPDATE usuario SET nome=?, login=?, senha=?, email=?, foto=?, telefone=?, dataNasc=?, localizacao=?, profissao=?, formacao=?, empresa=?, habilidades=?, idioma=? WHERE id=?";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, u.getNome());
            pst.setString(2, u.getLogin());
            pst.setString(3, u.getSenha());
            pst.setString(4, u.getEmail());
            pst.setString(5, u.getFoto());
            pst.setString(6, u.getTelefone());
            pst.setString(7, u.getDataNasc());
            pst.setString(8, u.getLocalizacao());
            pst.setString(9, u.getProfissao());
            pst.setString(10, u.getFormacao());
            pst.setString(11, u.getEmpresa());
            pst.setString(12, u.getHabilidades());
            pst.setString(13, u.getIdioma());
            pst.setInt(14, u.getId());

            int affected = pst.executeUpdate();
            status = (affected > 0);
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }

    public boolean excluirUsuario(int id) {
        boolean status = false;
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, id);
            int affected = pst.executeUpdate();
            status = (affected > 0);
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }

    public List<Atividade> listarAtividades(int usuarioId) {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM atividade WHERE usuario_id = ? ORDER BY dataHora DESC";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, usuarioId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Atividade a = new Atividade();
                    a.setNomeAtividade(rs.getString("nomeAtividade"));
                    a.setClasse(rs.getString("classe"));
                    a.setHorasGastas(rs.getInt("horasGastas"));
                    a.setMetaHoras(rs.getInt("metaHoras"));
                    a.setMetaCumprida(rs.getBoolean("metaCumprida"));
                    a.setPrioridade(rs.getString("prioridade"));
                    a.setDataHora(rs.getTimestamp("dataHora").toLocalDateTime());
                    atividades.add(a);
                }
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return atividades;
    }

    public boolean inserirAtividade(int usuarioId, Atividade a) {
        String sql = "INSERT INTO atividade (usuario_id, nomeAtividade, classe, horasGastas, metaHoras, metaCumprida, prioridade, dataHora) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, usuarioId);
            pst.setString(2, a.getNomeAtividade());
            pst.setString(3, a.getClasse());
            pst.setInt(4, a.getHorasGastas());
            pst.setInt(5, a.getMetaHoras());
            pst.setBoolean(6, a.isMetaCumprida());
            pst.setString(7, a.getPrioridade());
            pst.setTimestamp(8, Timestamp.valueOf(a.getDataHora()));
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return false;
    }

    public List<Feedback> listarFeedbacks(int usuarioId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM feedback WHERE usuario_id = ? ORDER BY dataInicio DESC";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, usuarioId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Feedback f = new Feedback();
                    f.setDataInicio(rs.getDate("dataInicio").toLocalDate());
                    f.setAdversidades(rs.getString("adversidades"));
                    f.setDificuldades(rs.getString("dificuldades"));
                    f.setBeneficios(rs.getString("beneficios"));
                    f.setDuracao(rs.getInt("duracao"));
                    f.setSatisfacao(rs.getInt("satisfacao"));
                    f.setObservacoesFeedback(rs.getString("observacoesFeedback"));
                    f.setMetas(rs.getString("metas"));
                    feedbacks.add(f);
                }
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return feedbacks;
    }

    public boolean inserirFeedback(int usuarioId, Feedback f) {
        String sql = "INSERT INTO feedback (usuario_id, dataInicio, adversidades, dificuldades, beneficios, duracao, satisfacao, observacoesFeedback, metas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, usuarioId);
            pst.setDate(2, Date.valueOf(f.getDataInicio()));
            pst.setString(3, f.getAdversidades());
            pst.setString(4, f.getDificuldades());
            pst.setString(5, f.getBeneficios());
            pst.setInt(6, f.getDuracao());
            pst.setInt(7, f.getSatisfacao());
            pst.setString(8, f.getObservacoesFeedback());
            pst.setString(9, f.getMetas());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return false;
    }
}
