package springstart.TravelAgency.Domain;
import lombok.Data;

@Data
public class User {
    int id;
    String userID;

    public User(String userID){
        this.userID = userID;
    }
}
