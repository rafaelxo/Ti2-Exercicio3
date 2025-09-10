package ti2_spark;

public class Usuario {
    private int id;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private String foto;
    private String telefone;
    private String dataNasc;
    private String localizacao;
    private String profissao;
    private String formacao;
    private String empresa;
    private String habilidades;
    private String idioma;
    private Infos infos;

    public Usuario() {}
    public Usuario(int id, String nome, String login, String senha, String email, Infos infos) {
        setId(id);
        setNome(nome);
        setLogin(login);
        setSenha(senha);
        setEmail(email);
        setInfos(infos);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getDataNasc() { return dataNasc; }
    public void setDataNasc(String dataNasc) { this.dataNasc = dataNasc; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public String getFormacao() { return formacao; }
    public void setFormacao(String formacao) { this.formacao = formacao; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Infos getInfos() { return infos; }
    public void setInfos(Infos infos) { this.infos = infos; }
}
