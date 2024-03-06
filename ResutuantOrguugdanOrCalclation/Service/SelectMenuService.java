package ResutuantOrguugdanOrCalclation.Service;

import ResutuantOrguugdanOrCalclation.Domain.Calculator;
import ResutuantOrguugdanOrCalclation.Domain.Customer;
import ResutuantOrguugdanOrCalclation.Domain.NumberCheck;
import ResutuantOrguugdanOrCalclation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class SelectMenuService {

    private static SelectMenuService service;
    NumberCheckRepo numberCheckRepo;
    UpdateCustomerRepo updateCustomerRepo;

    private SelectMenuService(){
     numberCheckRepo = new NumberCheckRepo();
     updateCustomerRepo = new UpdateCustomerRepo();
    }

    public static SelectMenuService getService(){
        if(service == null){
            service = new SelectMenuService();
        }
        return service;
    }

    public void gugudanServie(NumberCheck numberCheck, CustomerService customerService){
        Scanner sc = new Scanner(System.in);
        int inputdan;

        numberCheck.visitGugudan();
        System.out.printf("구구단 몇 단을 출력하고 싶습니까? : ");
        inputdan = sc.nextInt();

        customerService.printgugudan(inputdan);
    }

    public void chineseRestaurant(CustomerService customerService, NumberCheck numberCheck, Customer customer){
        int selectMenu;
        Scanner sc = new Scanner(System.in);

        System.out.printf("1.짜장면, 2.볶음밥, 3.탕수육 소, 4.탕수육 중, 5. 탕수육 대 : ");
        selectMenu = sc.nextInt();

        customerService.restaurantServie(selectMenu, numberCheck, customer);
    }

    public void CalculatorService(CustomerService customerService, NumberCheck numberCheck){
        Scanner sc = new Scanner(System.in);
        int selectCalc;
        float inputnum1, inputnum2;

        System.out.printf("1.더하기, 2.빼기, 3.곱하기, 4.나누기 : ");
        selectCalc = sc.nextInt();

        System.out.printf("숫자1을 입력하세요 : ");
        inputnum1 = sc.nextFloat();
        System.out.printf("숫자2를 입력하세요 : ");
        inputnum2 = sc.nextFloat();

        Calculator calculator = new Calculator(inputnum1, inputnum2);

        customerService.calcService(selectCalc, numberCheck, calculator);
    }

    public void customerSelectService(CustomerService customerService){

        Scanner scStr = new Scanner(System.in);
        String inputName;

        System.out.printf("조회할 손님 이름을 입력하세요 : ");
        inputName = scStr.nextLine();

        customerService.findNameService(inputName);
    }

    public void changeNameService(CustomerService customerService){
        Scanner scStr = new Scanner(System.in);
        String beforeName, inputName;

        System.out.printf("기존에 등록되어있는 이름을 입력하세요. : ");
        beforeName = scStr.nextLine();

        System.out.printf("바꿀 이름을 입력하세요. : ");
        inputName = scStr.nextLine();

        customerService.updateNameService(beforeName, inputName);
    }

    public void refund(){

        Scanner scStr = new Scanner(System.in);
        String inputName;

        System.out.printf("중국집에서 사용했던 이름을 입력하세요. : ");
        inputName = scStr.nextLine();

        updateCustomerRepo.refundChineseRestaurant(inputName);
    }
}
