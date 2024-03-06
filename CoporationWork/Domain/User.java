package CoporationWork.Domain;
import lombok.Data;

@Data
public class User {
    int id; //db id
    String userID;
    String pwd;

    public User(String userID, String pwd){
        this.userID = userID;
        this.pwd = pwd;
    }


}
