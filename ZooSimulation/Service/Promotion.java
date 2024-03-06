package springstart.ZooSimulation.Service;

import java.util.Scanner;

public class Promotion {

    public String factors(int select){

        String str = "";
        if(select == 1){

            str = "입장권 할인";
        }else if(select == 2){

            str = "특별 공연";
        }else if(select == 3){

            str = "스탬프 모으기";
        }

        return str;
    }

    public void onSale(){
        System.out.println("입장권 50% 할인 합니다.");
    }

    public void onConcert(){
        System.out.println("오후 4시에 특별 공연 시작합니다.");
    }

    public void collectStamp(){
        System.out.println("스탬프 도장 10개 찍으시면 선물을 드립니다.");
    }
}
