package CoporationWork.Domain;
import lombok.Data;

@Data
public class Customer {
    int id;
    String customerID;

    public Customer(String customerID){
        this.customerID = customerID;
    }
}
