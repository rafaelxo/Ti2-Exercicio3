package ti2_spark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {

    public UsuarioDAO() { super(); }

    public Usuario[] listarUsuarios() {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT codigo, login, senha, sexo FROM usuario ORDER BY codigo";
        try (PreparedStatement pst = conexao.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("codigo"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    rs.getString("sexo") != null && rs.getString("sexo").length() > 0 ? rs.getString("sexo").charAt(0) : '\0'
                );
                list.add(u);
            }
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return list.toArray(new Usuario[0]);
    }

    // Insere sem enviar explicitamente o codigo (assumindo SERIAL). Retorna true se inseriu.
    public boolean inserirUsuario(Usuario u) {
        boolean status = false;
        String sql = "INSERT INTO usuario (login, senha, sexo) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, u.getLogin());
            pst.setString(2, u.getSenha());
            pst.setString(3, String.valueOf(u.getSexo()));
            int affected = pst.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) u.setCodigo(keys.getInt(1));
                }
                status = true;
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }

    public boolean atualizarUsuario(Usuario u) {
        boolean status = false;
        String sql = "UPDATE usuario SET login = ?, senha = ?, sexo = ? WHERE codigo = ?";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, u.getLogin());
            pst.setString(2, u.getSenha());
            pst.setString(3, String.valueOf(u.getSexo()));
            pst.setInt(4, u.getCodigo());
            int affected = pst.executeUpdate();
            status = (affected > 0);
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }

    public boolean excluirUsuario(int codigo) {
        boolean status = false;
        String sql = "DELETE FROM usuario WHERE codigo = ?";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, codigo);
            int affected = pst.executeUpdate();
            status = (affected > 0);
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }
}