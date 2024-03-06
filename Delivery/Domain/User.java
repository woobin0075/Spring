package Delivery.Domain;
import lombok.Data;

@Data
public class User {
    int tableID;
    String name;
    String password;
    String phone_number;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
}
