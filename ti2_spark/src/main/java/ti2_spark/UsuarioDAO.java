package ti2_spark;

import java.sql.*;

public class UsuarioDAO extends DAO {

    // Método para mostrar os usuários
    public Usuario[] listarUsuarios() {
        Usuario[] usuarios = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM usuario");
            if (rs.next()) {
                rs.last();
                usuarios = new Usuario[rs.getRow()];
                rs.beforeFirst();
                for (int i = 0; rs.next(); i++) usuarios[i] = new Usuario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
            }
            st.close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return usuarios;
    }

    // Método para inserir usuário
    public boolean inserirUsuario(Usuario usuario) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO usuario (codigo, login, senha, sexo) VALUES (" + usuario.getCodigo() + ", '" + usuario.getLogin() + "', '" + usuario.getSenha() + "', '" + usuario.getSexo() + "')");
            st.close();
            status = true;
        } catch (SQLException u) { throw new RuntimeException(u); }
        return status;
    }

    // Método para atualizar usuário
    public boolean atualizarUsuario(Usuario usuario) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("UPDATE usuario SET login = '" + usuario.getLogin() + "', senha = '" + usuario.getSenha() + "', sexo = '" + usuario.getSexo() + "' WHERE codigo = " + usuario.getCodigo());
            st.close();
            status = true;
        } catch (SQLException u) { throw new RuntimeException(u); }
        return status;
    }

    // Método para excluir um usuário
    public boolean excluirUsuario(int codigo) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM usuario WHERE codigo = " + codigo);
            st.close();
            status = true;
        } catch (SQLException u) { throw new RuntimeException(u); }
        return status;
    }
}