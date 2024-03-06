package ResutuantOrguugdanOrCalclation.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepo {
    CustomerInterface customerRepo;

    @Autowired
    public CustomerRepo(){
        customerRepo = new CustomerRepository();
    }

    public void priceSave(){
        customerRepo.priceSave();
    }

    public void insert(Object customer, int idx){
        customerRepo.insert(customer, idx);
    }

    public void find(String customerName){
        customerRepo.find(customerName);
    }

    public void delete(String name){
        customerRepo.delete(name);
    }
}
