package CoporationWork.Domain;
import lombok.Data;

@Data
public class Advisor {
    int id; //db id
    String advisorID;
    String pwd;


    public Advisor(String advisorID, String pwd){
        this.advisorID = advisorID;
        this.pwd = pwd;
    }
}
