package springstart.ZooSimulation.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisitorRepo {

    VisitorInterface visitor;

    @Autowired
    public VisitorRepo(VisitorRepository repository){
        visitor = repository;
    }

    public void insertNewFeedback(double score1, double score2, double score3, String text){
        visitor.insertNewFeedback(score1, score2, score3, text);
    }

    public void insertNewZooExhibition(String topic, int opendays, String factor){
        visitor.insertNewZooExhibition(topic, opendays, factor);
    }

    public String selectZooExhibitionFactor(String topic){
        return visitor.selectZooExhibitionFactor(topic);
    }
}
