package FoodSave.Service;

import FoodSave.Domain.*;
import FoodSave.Repository.CustomerRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerService {
    public void orderService(String today, CustomerRepository customerRepository){
        Scanner scStr = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        Food clamp = new Clamp();
        Food hamburger = new Hamburger();
        Food pizza = new Pizza();
        Food porkbelly = new Porkbelly();
        Food chicken = new Chicken();
        KindOfFood foods;
        ArrayList<OrderMenu> orderMenus = new ArrayList<>();
        String menu, nickname;
        int many, selectMenu;

        System.out.printf("주문할때 사용할 닉네임을 입력하세요. : ");
        nickname = scStr.nextLine();

        while(true){
            System.out.printf("1.치킨, 2.피자, 3.햄버거, 4.조개, 5.삼겹살, 6.주문완료 => ");
            selectMenu = sc.nextInt();

            if(selectMenu == 1){
                foods = new KindOfFood(chicken);

                System.out.printf("주문할 수량을 입력하세요. : ");
                many = sc.nextInt();

                orderMenus.add(new OrderMenu(foods.foodName(), many));
            }else if(selectMenu == 2){
                foods = new KindOfFood(pizza);

                System.out.printf("주문할 수량을 입력하세요. : ");
                many = sc.nextInt();

                orderMenus.add(new OrderMenu(foods.foodName(), many));
            }else if(selectMenu == 3){
                foods = new KindOfFood(hamburger);

                System.out.printf("주문할 수량을 입력하세요. : ");
                many = sc.nextInt();

                orderMenus.add(new OrderMenu(foods.foodName(), many));
            }else if(selectMenu == 4){
                foods = new KindOfFood(clamp);

                System.out.printf("주문할 수량을 입력하세요. : ");
                many = sc.nextInt();

                orderMenus.add(new OrderMenu(foods.foodName(), many));
            }else if(selectMenu == 5){
                foods = new KindOfFood(porkbelly);

                System.out.printf("주문할 수량을 입력하세요. : ");
                many = sc.nextInt();

                orderMenus.add(new OrderMenu(foods.foodName(), many));
            }else if(selectMenu == 6){
                break;
            }

        }

        for(int i=0; i<orderMenus.size(); i++){
            customerRepository.insertSaleTable(nickname, today, orderMenus.get(i).getMenuName(), orderMenus.get(i).getOrderMany());
        }

    }

    public void inputStartsAndFeelings(CustomerRepository customerRepository){//별점, 후기 남기기
        Scanner scStr = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        float stars;
        String feelings;


        while (true){
            System.out.printf("별점을 남겨주세요.(1.5,3,5 형태로 입력, 5점이하로 입력) : ");
            stars = sc.nextFloat();

            if(stars < 0.0 || stars > 5.0){
                System.out.println("다시 입력해주세요.");
            }else{
                break;
            }
        }

        System.out.printf("후기를 남겨주세요. : ");
        feelings = scStr.nextLine();

        customerRepository.insertStartsAndFeeling(stars, feelings);
    }


}
