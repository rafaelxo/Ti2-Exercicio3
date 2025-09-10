package ti2_spark;

import java.util.List;

public class Infos {
    private List<Atividade> atividades;
    private List<Feedback> feedbacks;

    public Infos() {}

    public Infos(List<Atividade> atividades, List<Feedback> feedbacks) {
        setAtividades(atividades);
        setFeedbacks(feedbacks);
    }

    public List<Atividade> getAtividades() { return atividades; }
    public void setAtividades(List<Atividade> atividades) { this.atividades = atividades; }

    public List<Feedback> getFeedbacks() { return feedbacks; }
    public void setFeedbacks(List<Feedback> feedbacks) { this.feedbacks = feedbacks; }
}
