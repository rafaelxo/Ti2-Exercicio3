package ti2_spark;

public class Feedback {
    private String dataInicio;
    private String adversidades;
    private String dificuldades;
    private String beneficios;
    private int duracao;
    private int satisfacao;
    private String observacoesFeedback;
    private String metas;

    public Feedback() {}

    public Feedback(String dataInicio, String adversidades, String dificuldades, String beneficios, int duracao, int satisfacao, String observacoesFeedback, String metas) {
        setDataInicio(dataInicio);
        setAdversidades(adversidades);
        setDificuldades(dificuldades);
        setBeneficios(beneficios);
        setDuracao(duracao);
        setSatisfacao(satisfacao);
        setObservacoesFeedback(observacoesFeedback);
        setMetas(metas);
    }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getAdversidades() { return adversidades; }
    public void setAdversidades(String adversidades) { this.adversidades = adversidades; }

    public String getDificuldades() { return dificuldades; }
    public void setDificuldades(String dificuldades) { this.dificuldades = dificuldades; }

    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public int getSatisfacao() { return satisfacao; }
    public void setSatisfacao(int satisfacao) { this.satisfacao = satisfacao; }

    public String getObservacoesFeedback() { return observacoesFeedback; }
    public void setObservacoesFeedback(String observacoesFeedback) { this.observacoesFeedback = observacoesFeedback; }

    public String getMetas() { return metas; }
    public void setMetas(String metas) { this.metas = metas; }
}
