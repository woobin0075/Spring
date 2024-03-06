package springstart.TravelAgency.Service;

import java.util.Scanner;
import lombok.Data;

@Data
public class TravelCalculator {
    int price;

    public TravelCalculator(int price){
        this.price = price;
    }

    public void addAdditionalFare(){ //선택제 추가요금 계산
        Scanner sc = new Scanner(System.in);
        int select;
        final int taxi = 60000;
        final int meals = 200000;

        System.out.printf("전용 택시를 이용하실꺼면 1번을 아니면 그 외 다른 숫자를 입력하세요. : ");
        select = sc.nextInt();

        if(select == 1){
            price += taxi;
            System.out.println(taxi+"원이 추가됩니다.");
        }

        System.out.printf("여행 기간 모두 식사 포함 하실거면 1번을 아니면 그 외 다른 숫자를 입력하세요. : ");
        select = sc.nextInt();

        if(select == 1){
            price += meals;
            System.out.println(meals+"원이 추가됩니다.");
        }
    }
}
