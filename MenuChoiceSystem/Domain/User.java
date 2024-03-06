package MenuChoiceSystem.Domain;
import lombok.Data;

@Data
public class User {
    int id;
    String userID;
    int beforePocketMoney;
    int presentPocketMoney;
    int lendMoney = 0; //빌린 금액
    int borrowMoney = 0; //빌려준 금액
    boolean dbinfoFlag = false; //정보들 db에 이미 넣었는지 아직 아닌지 확인용

    public User(int beforePocketMoney){
        this.beforePocketMoney = beforePocketMoney;
    }
}
