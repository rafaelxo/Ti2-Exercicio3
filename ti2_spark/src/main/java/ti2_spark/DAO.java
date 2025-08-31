package ti2_spark;

import java.sql.*;
import java.security.*;

public class DAO {

    // Atribuição da conexão DAO
    protected Connection conexao;
    public DAO() { conexao = null; }

    // Método para conectar com o banco
    public boolean conectar() {
        String driverName = "org.postgresql.Driver";
        String serverName = "localhost";
        String mydatabase = "teste";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "rafaelxo";
        String password = "Rafael123";

        boolean status = false;
        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) { System.err.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) { System.err.println("Erro ao conectar: " + e.getMessage()); }
        return status;
    }

    // Método para fechar a conexão
    public boolean close() {
        boolean status = false;
        try {
            conexao.close();
            status = true;
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return status;
    }

    // Método utilitário para gerar hash MD5
    public static String getMD5(String senha) {
        String sen = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes());
            byte[] digest = md.digest();
            for (byte b : digest) {
                sen += String.format("%02x", b & 0xff);
            }
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return sen;
    }
}