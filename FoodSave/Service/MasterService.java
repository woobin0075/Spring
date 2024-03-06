package FoodSave.Service;

import FoodSave.Domain.*;
import FoodSave.Repository.MasterRepository;

import java.util.Scanner;

public class MasterService {
    public void printAllmenus(MasterRepository masterRepository){

        masterRepository.selectAllMenus();
    }

    public void updateMenuPrice(MasterRepository masterRepository){
        Scanner scStr = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        Food clamp = new Clamp();
        Food hamburger = new Hamburger();
        Food pizza = new Pizza();
        Food porkbelly = new Porkbelly();
        Food chicken = new Chicken();
        KindOfFood foods = null;
        int select;
        int price = 0;

        System.out.println("가격 수정을 하고 싶은 메뉴를 선택하세요.");
        System.out.printf("1.치킨, 2.피자, 3.햄버거, 4.조개, 5.삼겹살, => ");
        select = sc.nextInt();

        if(select == 1){
            foods = new KindOfFood(chicken);

        }else if(select == 2){
            foods = new KindOfFood(pizza);

        }else if(select == 3){
            foods = new KindOfFood(hamburger);

        }else if(select == 4){
            foods = new KindOfFood(clamp);

        }else if(select == 5){
            foods = new KindOfFood(porkbelly);

        }else{
            System.out.println("넘어가기");
        }

        if(select >=1 && select <= 5){
            System.out.printf("수정하고 싶은 가격을 입력하세요. : ");
            price = sc.nextInt();
        }

        masterRepository.updateMenuPrice(foods.foodName(), price);
    }

    public void printAllSalesList(MasterRepository masterRepository){
        masterRepository.selectAllSales();
    }
}
