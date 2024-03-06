package FoodSave.Service;


import FoodSave.Repository.CustomerRepository;
import FoodSave.Repository.MasterRepository;

import java.util.Scanner;

public class MainService {

    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        int select, selectCustomer, selectMaster;
        int todayYear = 2023, todayMonth = 1, todayDay = 3; //오늘날짜 임시 지정
        MasterService masterService = new MasterService();
        CustomerService customerService = new CustomerService();
        MasterRepository masterRepository = new MasterRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        String dateStr;

        System.out.println("장사의 신 가게 배달 전문점에 오신 것을 환영합니다.");
        while (true){
            System.out.println(todayYear+"년 "+todayMonth+"월 "+todayDay+"일");
            System.out.println();
            masterService.printAllmenus(masterRepository); //메뉴 보여주기

            System.out.printf("1.손님, 2.사장 => ");
            select = sc.nextInt();

            if(select == 1){

                System.out.printf("1.메뉴 주문, 2.후기 작성 =>");
                selectCustomer = sc.nextInt();

                if(selectCustomer == 1){
                    dateStr = todayToString(todayYear, todayMonth, todayDay);
                    customerService.orderService(dateStr, customerRepository);

                }else if(selectCustomer == 2){
                    customerService.inputStartsAndFeelings(customerRepository);

                }else{
                    System.out.println("다시 선택해주세요.");
                }
                System.out.println();

            }else if(select == 2){

                System.out.printf("1.메뉴 가격 수정, 2.역대 매출 출력, 3.다음 날 => ");
                selectMaster = sc.nextInt();

                if(selectMaster == 1){
                    masterService.updateMenuPrice(masterRepository);

                }else if(selectMaster == 2){

                    masterService.printAllSalesList(masterRepository);

                }else if(selectMaster == 3){

                    todayDay++;
                    if(todayDay == 31){
                        todayMonth++;
                        todayDay = 1;
                    }

                    if(todayMonth == 13){
                        todayYear++;
                        todayMonth = 1;
                    }

                }
                else{
                    System.out.println("다시 선택해주세요.");
                }
                System.out.println();
            }else{
                System.out.println("다시 선택해주세요.");
            }
            System.out.println();
        }
    }

    private String todayToString(int year, int month, int day){ //날짜를 숫자문자열로 전환
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);

        return stringBuilder.toString();
    }
}
