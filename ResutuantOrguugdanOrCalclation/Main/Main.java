package ResutuantOrguugdanOrCalclation.Main;

import ResutuantOrguugdanOrCalclation.Domain.Customer;
import ResutuantOrguugdanOrCalclation.Domain.NumberCheck;
import ResutuantOrguugdanOrCalclation.Service.CustomerService;
import ResutuantOrguugdanOrCalclation.Service.SelectMenuService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ResutuantOrguugdanOrCalclation.AutoConfig.AutoWebConfig;

import java.util.*;


public class Main {
    public static void main(String[] args){

        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoWebConfig.class);
        CustomerService customerService = ac.getBean(CustomerService.class);
        SelectMenuService selectMenuService = ac.getBean(SelectMenuService.class);
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        //SelectMenuService selectMenuService = SelectMenuService.getService(); //싱글톤 형식으로 바꿈
        //CustomerService customerService = CustomerService.getService();
        Customer[] customers = new Customer[10];
        NumberCheck[] numberChecks = new NumberCheck[10];
        String inputName;
        int select;
        int i;

        customerService.priceSave();
        i = 0;

        System.out.printf("이름 입력 : ");
        inputName = scStr.nextLine();

        customers[i] = new Customer(inputName);
        numberChecks[i] = new NumberCheck(inputName);

        while (i< customers.length){

            System.out.printf("1.구구단, 2.중식당, 3.계산기, 4.다음 사람, 5.고객 조회 기능, 6.이름 바꾸기(회원 정보 수정), 7.중식당 환불, 8.회원 탈퇴, 9.종료 : ");
            select = sc.nextInt();

            if(select == 1){

                selectMenuService.gugudanServie(numberChecks[i], customerService);

            }else if(select == 2){

                selectMenuService.chineseRestaurant(customerService, numberChecks[i], customers[i]);

            }else if(select == 3){

                selectMenuService.CalculatorService(customerService, numberChecks[i]);

            }else if(select == 4){

                customerService.saveService(customers[i], numberChecks[i], i);

                i++;

                if(i == customers.length){
                    return;
                }

                System.out.printf("이름 입력 : ");
                inputName = scStr.nextLine();

                customers[i] = new Customer(inputName);
                numberChecks[i] = new NumberCheck(inputName);

            }else if(select == 5){

                selectMenuService.customerSelectService(customerService);

            }else if(select == 6){

                selectMenuService.changeNameService(customerService);

            }else if(select == 7){

                selectMenuService.refund();

            }else if(select == 8){
                System.out.printf("이름 입력 : ");
                inputName = scStr.nextLine();
                customerService.deleteService(inputName);
            }
            else if(select == 9){

                customerService.saveService(customers[i], numberChecks[i], i);
                return;
            }
            else{
                System.out.println("다시 입력하세요.");
            }
            System.out.println();
        }
    }
}
