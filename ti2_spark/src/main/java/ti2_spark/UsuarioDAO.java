package ti2_spark;

import java.sql.*;

public class UsuarioDAO extends DAO {

    public UsuarioDAO() {
        super();
    }

    public Usuario[] listarUsuarios() {
        Usuario[] usuarios = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM usuario");

            if (rs.next()) {
                rs.last();
                usuarios = new Usuario[rs.getRow()];
                rs.beforeFirst();

                int i = 0;
                while (rs.next()) {
                    usuarios[i++] = new Usuario(
                        rs.getInt("codigo"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getString("sexo").charAt(0)
                    );
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return usuarios;
    }

    public boolean inserirUsuario(Usuario u) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO usuario (codigo, login, senha, sexo) "
                    + "VALUES (" + u.getCodigo() + ", '" + u.getLogin() + "', '" + u.getSenha() + "', '" + u.getSexo() + "');");
            st.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean atualizarUsuario(Usuario u) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("UPDATE usuario SET login = '" + u.getLogin() + "', senha = '" + u.getSenha() +
                    "', sexo = '" + u.getSexo() + "' WHERE codigo = " + u.getCodigo() + ";");
            st.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean excluirUsuario(int codigo) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM usuario WHERE codigo = " + codigo + ";");
            st.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }
}
