package ResutuantOrguugdanOrCalclation.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NumberCheckRepo {
    NumberCheckInterface numbercheckRepo;

    @Autowired
    public NumberCheckRepo(){
        numbercheckRepo = new NumberCheckRepository();
    }

    public void insert(Object numberCheck, int idx){
        numbercheckRepo.insert(numberCheck, idx);
    }

    public void update(String beforeName, String afterName){
        numbercheckRepo.update(beforeName, afterName);
    }
}
