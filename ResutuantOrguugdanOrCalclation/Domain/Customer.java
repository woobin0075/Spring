package ResutuantOrguugdanOrCalclation.Domain;
import lombok.Data;

@Data
public class Customer {
    String name;
    private int purchase = 0;
    private int jajangmyeonNum = 0;
    private int fried_RiceNum = 0;
    private int tangsuyuk_smallNum = 0;
    private int tangsuyuk_mediumNum = 0;
    private int tangsuyuk_largeNum = 0;

    public Customer(){

    }

    public Customer(String name){
        this.name = name;
    }

    public void buyjajangmyeon(){
        jajangmyeonNum++;
    }

    public void buyfried_Rice(){
        fried_RiceNum++;
    }

    public void buytangsuyuksmall(){
        tangsuyuk_smallNum++;

    }

    public void buytangsuyukmedium(){
        tangsuyuk_mediumNum++;
    }

    public void buytangsuyuklarge(){
        tangsuyuk_largeNum++;
    }

    public void buyFood(int price){

        purchase += price;
        System.out.println("누적 금액 : "+purchase);
    }
}
