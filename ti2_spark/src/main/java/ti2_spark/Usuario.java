package ti2_spark;

public class Usuario {

    // Atributos do usuário
    private int codigo;
    private String login;
    private String senha;
    private char sexo;

    // Construtor padrão
    public Usuario() {
        this.codigo = 1;
        this.login = "nenhum";
        this.senha = "nenhuma";
        this.sexo = 'A';
    }

    // Construtor com parâmetros
    public Usuario(int codigo, String login, String senha, char sexo) {
        this.codigo = codigo;
        this.login = login;
        this.senha = senha;
        this.sexo = sexo;
    }

    // Métodos get e set da classe
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }

    // Método para transformar a mensagem em frase
    @Override
    public String toString() {
        return "Usuario [Codigo: " + codigo + ", Login: " + login + ", Senha: " + senha + ", Sexo: " + sexo + "]";
    }
}