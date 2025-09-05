package ti2_spark;

import java.sql.*;
import java.security.MessageDigest;

public class DAO {
    protected Connection conexao;

    public DAO() { conexao = null; }

    public boolean conectar() {
        boolean status = false;
        try {
            String driverName = "org.postgresql.Driver";
            String serverName = "localhost";
            String mydatabase = "teste";
            int porta = 5432;
            String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
            String username = "postgres";
            String password = "rafael123";
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            if (status) System.out.println("Conexão efetuada com o postgres!");
            else System.err.println("Conexão retornou nula.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro conexão: " + e.getMessage());
        }
        return status;
    }

    public boolean close() {
        boolean status = false;
        try {
            if (conexao != null && !conexao.isClosed()) conexao.close();
            status = true;
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }

    public static String getMD5(String input) {
        String md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) sb.append(String.format("%02x", b & 0xff));
            md5 = sb.toString();
        } catch (Exception e) { System.err.println("Erro gerando MD5: " + e.getMessage()); }
        return md5;
    }
}