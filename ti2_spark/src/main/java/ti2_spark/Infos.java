package ti2_spark;

import java.util.List;

public class Infos {
    private List<Atividade> atividades;
    private List<Feedback> feedback;

    public Infos() {}
    public Infos(List<Atividade> atividades, List<Feedback> feedback) {
        this.atividades = atividades;
        this.feedback = feedback;
    }

    public List<Atividade> getAtividades() { return atividades; }
    public void setAtividades(List<Atividade> atividades) { this.atividades = atividades; }

    public List<Feedback> getFeedback() { return feedback; }
    public void setFeedback(List<Feedback> feedback) { this.feedback = feedback; }
}
