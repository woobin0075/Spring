package ResutuantOrguugdanOrCalclation.Domain;
import lombok.Data;

@Data
public class NumberCheck {
    String name;
    private int numberCheck_calc = 0; //계산기 방문 횟수
    private int numberCheck_gugudan = 0; //구구단 방문 횟수
    private int numberCheck_Restaurant = 0; //식당 방문 횟수

    public NumberCheck(String name){
        this.name = name;
    }

    public void visitCalc(){
        numberCheck_calc++;
        System.out.println("계산기 방문 횟수 : "+numberCheck_calc);
    }

    public void visitGugudan(){
        numberCheck_gugudan++;
        System.out.println("구구단 방문 횟수 : "+numberCheck_gugudan);
    }

    public void visitRestaurant(){
        numberCheck_Restaurant++;
        System.out.println("중식당 방문 횟수 : "+numberCheck_Restaurant);
    }
}
