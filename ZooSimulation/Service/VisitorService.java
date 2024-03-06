package springstart.ZooSimulation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springstart.ZooSimulation.Repository.VisitorRepo;

@Component
public class VisitorService {
    VisitorRepo visitorRepo;

    @Autowired
    public VisitorService(VisitorRepo visitorRepo){
        this.visitorRepo = visitorRepo;
    }

    public void addNewFeedback(double score1, double score2, double score3, String text){
        visitorRepo.insertNewFeedback(score1, score2, score3, text);
    }

    public void addNewExhibition(String topic, int opendays, String factor){
        visitorRepo.insertNewZooExhibition(topic, opendays, factor);
    }

    public String bringFactor(String topic){
        return visitorRepo.selectZooExhibitionFactor(topic);
    }
}
