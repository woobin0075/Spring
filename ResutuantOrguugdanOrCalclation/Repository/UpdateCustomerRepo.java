package ResutuantOrguugdanOrCalclation.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCustomerRepo {
    UpdateCustomerInterface updatecustomerRepo;

    @Autowired
    public UpdateCustomerRepo(){
        updatecustomerRepo = new UpdateCustomerRepository();
    }

    public void refundChineseRestaurant(String name){
        updatecustomerRepo.refundChineseRestaurant(name);
    }

    public void update(String beforeName, String afterName){
        updatecustomerRepo.update(beforeName, afterName);
    }
}
