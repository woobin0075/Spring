package springstart.PetToyShop.Domain;
import lombok.Data;

@Data
public class User {
    int id;
    String userID;

    public User(int id, String userID){
        this.id = id;
        this.userID = userID;
    }
}
