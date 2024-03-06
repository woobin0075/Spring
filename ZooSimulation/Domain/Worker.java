package springstart.ZooSimulation.Domain;
import lombok.Data;

@Data
public class Worker {
    int dbid;
    String workerID;

    public Worker(String workerID){
        this.workerID = workerID;
    }
}
