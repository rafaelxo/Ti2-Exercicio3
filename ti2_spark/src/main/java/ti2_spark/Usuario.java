package ti2_spark;

public class Usuario {
    private int codigo;
    private String login;
    private String senha;
    private char sexo;

    public Usuario() {}

    public Usuario(int codigo, String login, String senha, char sexo) {
        this.codigo = codigo;
        this.login = login;
        this.senha = senha;
        this.sexo = sexo;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        if (login == null || login.length() <= 3) { throw new IllegalArgumentException("O login deve ter mais de 3 caracteres."); }
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        if (senha == null || senha.length() <= 5) { throw new IllegalArgumentException("A senha deve ter mais de 5 caracteres."); }
        this.senha = senha;
    }

    public char getSexo() {
        return sexo;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
}