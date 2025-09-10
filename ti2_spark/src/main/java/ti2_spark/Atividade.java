package ti2_spark;

import java.time.LocalDateTime;

public class Atividade {
    private String nomeAtividade;
    private String classe;
    private int horasGastas;
    private int metaHoras;
    private boolean metaCumprida;
    private String prioridade;
    private LocalDateTime dataHora;

    public Atividade() {}

    public Atividade(String nomeAtividade, String classe, int horasGastas, int metaHoras, boolean metaCumprida, String prioridade, LocalDateTime dataHora) {
        setNomeAtividade(nomeAtividade);
        setClasse(classe);
        setHorasGastas(horasGastas);
        setMetaHoras(metaHoras);
        setMetaCumprida(metaCumprida);
        setPrioridade(prioridade);
        setDataHora(dataHora);
    }

    public String getNomeAtividade() { return nomeAtividade; }
    public void setNomeAtividade(String nomeAtividade) { this.nomeAtividade = nomeAtividade; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }

    public int getHorasGastas() { return horasGastas; }
    public void setHorasGastas(int horasGastas) { this.horasGastas = horasGastas; }

    public int getMetaHoras() { return metaHoras; }
    public void setMetaHoras(int metaHoras) { this.metaHoras = metaHoras; }

    public boolean isMetaCumprida() { return metaCumprida; }
    public void setMetaCumprida(boolean metaCumprida) { this.metaCumprida = metaCumprida; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
