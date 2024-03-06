package springstart.ZooSimulation.Repository;

public interface VisitorInterface {
    void insertNewFeedback(double score1, double score2, double score3, String text);
    void insertNewZooExhibition(String topic, int opendays, String factor);
    String selectZooExhibitionFactor(String topic);
}
