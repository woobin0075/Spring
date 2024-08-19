package work.worklist.domain.work;
import lombok.Data;

@Data
public class Account {

    int id;
    String personalNum;
    String handPhoneNum;
    String cardSecretNum;
    int money;

    public Account(String personalNum, String handPhoneNum, String cardSecretNum){
        this.personalNum = personalNum;
        this.handPhoneNum = handPhoneNum;
        this.cardSecretNum = cardSecretNum;
    }
}
