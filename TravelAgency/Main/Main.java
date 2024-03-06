package springstart.TravelAgency.Main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springstart.TravelAgency.AppConfig.AppConfig;
import springstart.TravelAgency.Domain.TravelPackage;
import springstart.TravelAgency.Domain.User;
import springstart.TravelAgency.Service.TravelAgency;
import springstart.TravelAgency.Service.TravelCalculator;
import springstart.TravelAgency.Service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = ac.getBean("userService", UserService.class);
        TravelAgency travelAgency = ac.getBean("travelAgencyService", TravelAgency.class);

        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        User user;
        int selectCustomerOrAgency;
        int selectAgency, selectCustomer;
        int selectSort;
        String inputID, inputPwd, inputName, inputPhoneNumber, inputEmail, inputPackageName;
        String inputRegion, inputaccommodation, inputGuideName, inputSendWay;
        int inputPrice, inputTravelDistance, inputTravelDay;
        int inputScore;
        String inputPostScript;
        TravelCalculator travelCalculator;
        ArrayList<TravelPackage> packages;
        int totalPrice = 0;

        while(true){
            System.out.printf("1.손님, 2.여행사 => ");
            selectCustomerOrAgency = sc.nextInt();

            if(selectCustomerOrAgency == 1){

                System.out.printf("1.회원 가입, 2.로그인 => ");
                selectCustomer = sc.nextInt();

                if(selectCustomer == 1){

                    System.out.printf("이름을 입력하세요. : ");
                    inputName = sc.next();
                    System.out.printf("사용할 아이디를 입력하세요. : ");
                    inputID = sc.next();
                    System.out.printf("사용할 비밀번호를 입력하세요. : ");
                    inputPwd = sc.next();
                    System.out.printf("핸드폰 번호를 입력하세요. : ");
                    inputPhoneNumber = sc.next();
                    System.out.printf("이메일을 입력하세요. : ");
                    inputEmail = sc.next();

                    userService.makeNewAccount(inputID, inputPwd, inputName, inputPhoneNumber, inputEmail);
                    System.out.println();

                }else if(selectCustomer == 2){

                    System.out.printf("아이디를 입력하세요. : ");
                    inputID = sc.next();
                    System.out.printf("비밀번호를 입력하세요. : ");
                    inputPwd = sc.next();

                    if(userService.canbeLogin(inputID, inputPwd)){

                        user = new User(inputID);
                        user.setId(userService.bringUserDBid(inputID));

                        while(true){
                            System.out.printf("1.검색, 2.정렬, 3.구매, 4.후기 작성, 5.예산 내 추천, 6.뒤로 => ");
                            selectCustomer = sc.nextInt();

                            if(selectCustomer == 1){

                                System.out.printf("패키지 이름을 입력하세요. : ");
                                inputPackageName = scStr.nextLine();

                                travelAgency.searchPackageByName(inputPackageName);

                            }else if(selectCustomer == 2){

                                packages = travelAgency.bringAllPackages();
                                System.out.println("정렬 기준을 입력하세요.");
                                System.out.printf("1.가격 기준, 2.패키지 이름 기준, 3.여행 기간 기준, 4.평점 기준 : ");
                                selectSort = sc.nextInt();

                                switch (selectSort){
                                    case 1:
                                        Collections.sort(packages, new Comparator<TravelPackage>() {
                                            @Override
                                            public int compare(TravelPackage o1, TravelPackage o2) {

                                                if(o1.getPrice() - o2.getPrice() > 0){
                                                    return 1;
                                                }else if(o1.getPrice() - o2.getPrice() < 0){
                                                    return -1;
                                                }else{
                                                    return 0;
                                                }
                                            }
                                        });
                                        break;
                                    case 2:
                                        Collections.sort(packages, new Comparator<TravelPackage>() {
                                            @Override
                                            public int compare(TravelPackage o1, TravelPackage o2) {

                                               if(o1.getPackageName().compareTo(o2.getPackageName()) > 0){
                                                   return 1;
                                               }else if(o1.getPackageName().compareTo(o2.getPackageName()) < 0){
                                                   return -1;
                                               }else{
                                                   return 0;
                                               }
                                            }
                                        });

                                        break;
                                    case 3:
                                        Collections.sort(packages, new Comparator<TravelPackage>() {
                                            @Override
                                            public int compare(TravelPackage o1, TravelPackage o2) {

                                                if(o1.getTravelDays() > o2.getTravelDays()){
                                                    return 1;
                                                }else if(o1.getTravelDays() < o2.getTravelDays()){
                                                    return -1;
                                                }else{
                                                    return 0;
                                                }
                                            }
                                        });
                                        break;
                                    case 4:
                                        Collections.sort(packages, new Comparator<TravelPackage>() {
                                            @Override
                                            public int compare(TravelPackage o1, TravelPackage o2) {

                                                return Double.compare(o1.getAvgScore(), o2.getAvgScore());
                                            }
                                        });
                                }

                                for(int i=0; i< packages.size(); i++){
                                    System.out.println("패키지 이름 : "+packages.get(i).getPackageName());
                                    System.out.println("가격 : "+packages.get(i).getPrice());
                                    System.out.println("여행 기간 : "+packages.get(i).getTravelDays());
                                    System.out.println();
                                }

                            }else if(selectCustomer == 3){

                                System.out.printf("구매할 패키지 이름을 입력하세요. : ");
                                inputPackageName = scStr.nextLine();
                                int price = travelAgency.bringPackagePrice(inputPackageName);

                                if(price > 0){

                                    travelCalculator = new TravelCalculator(price);
                                    travelCalculator.addAdditionalFare();

                                    totalPrice = travelCalculator.getPrice();

                                    System.out.println("총 결제 금액은 "+totalPrice+"원 입니다.");

                                }else{
                                    System.out.println("해당하는 패키지가 없습니다.");
                                }

                            }else if(selectCustomer == 4){
                                System.out.printf("패키지 이름을 입력하세요. : ");
                                inputPackageName = scStr.nextLine();
                                int packageid = travelAgency.bringPackageDBid(inputPackageName);

                                if(packageid > 0){

                                    System.out.printf("점수를 입력하세요.(1~10까지) : ");
                                    inputScore = sc.nextInt();

                                    if(inputScore < 1 || inputScore > 10){
                                        System.out.println("다시 입력하세요.");
                                        continue;
                                    }

                                    System.out.printf("후기를 입력하세요. : ");
                                    inputPostScript = scStr.nextLine();

                                    travelAgency.addNewPostScript(packageid, user.getId(), inputScore, inputPostScript);

                                    //평점 구하기
                                    int totalScore = travelAgency.calcTotalScore(packageid);
                                    int totalnum = travelAgency.calcPostScriptPacackageNum(packageid);
                                    double avgscore = (double) totalScore / (double) totalnum;

                                    travelAgency.inputAvgScore(packageid, avgscore);

                                }else{
                                    System.out.println("해당하는 패키지가 없습니다.");
                                }

                            }else if(selectCustomer == 5){

                                System.out.printf("예산을 입력하세요. : ");
                                inputPrice = sc.nextInt();

                                travelAgency.printRecommendPackages(inputPrice);

                            }else if(selectCustomer == 6){
                                break;
                            }
                        }

                    }else{
                        System.out.println("아이디 또는 비빌번호가 틀렸습니다.");
                    }

                }

            }else if(selectCustomerOrAgency == 2){

                System.out.printf("1.새 상품 추가, 2.검색 = >");
                selectAgency = sc.nextInt();

                if(selectAgency == 1){

                    System.out.printf("1.상품 이름을 입력하세요. : ");
                    inputPackageName = scStr.nextLine();
                    System.out.printf("2.상품 가격을 입력하세요. : ");
                    inputPrice = sc.nextInt();
                    System.out.printf("3.여행 지역을 입력하세요. : ");
                    inputRegion = sc.next();
                    System.out.printf("4.여행 총 거리를 입력하세요. : ");
                    inputTravelDistance = sc.nextInt();
                    System.out.printf("5.숙박 시설 종류를 입력하세요. : ");
                    inputaccommodation = sc.next();
                    System.out.printf("6.총 여행 일수를 입력하세요. : ");
                    inputTravelDay = sc.nextInt();
                    System.out.printf("7.동행할 가이드 이름을 입력하세요. : ");
                    inputGuideName = sc.next();
                    System.out.printf("8.상품 정보 전송 수단을 입력하세요. :");
                    inputSendWay = sc.next();

                    TravelPackage travelPackage = new TravelPackage(inputPackageName, inputPrice, inputRegion, inputTravelDistance,
                            inputaccommodation, inputTravelDay, inputGuideName, inputSendWay);

                    travelAgency.addNewPackage(travelPackage);

                }else if(selectAgency == 2){

                    System.out.printf("패키지 이름을 입력하세요. : ");
                    inputPackageName = scStr.nextLine();

                    travelAgency.searchPackageByName(inputPackageName);
                }

            }else{
                System.out.println("다시 입력하세요.");
            }

        }
    }
}
