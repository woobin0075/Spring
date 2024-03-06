package OrderIngredients.Service;

import OrderIngredients.Repository.FactoryRepo;
import OrderIngredients.Repository.MasterRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

@Component
public class Service {
    FactoryRepo factoryRepo;
    MasterRepo masterRepo;

    public Service(FactoryRepo factoryRepo, MasterRepo masterRepo){
        this.factoryRepo = factoryRepo;
        this.masterRepo = masterRepo;
    }

    public void makeAccountMaster(String name, String pwd, String phoneNumber){
        masterRepo.insertNewMaster(name, pwd, phoneNumber);
    }

    public boolean loginForMaster(String name, String pwd, String phoneNumber){
        return masterRepo.selectMaster(name, pwd, phoneNumber);
    }

    public void addNewFood(String food, int price){ //발주 가능 음식 추가
        factoryRepo.insertNewFood(food, price);
    }

    private int selectFoodID(String food, int price){
        return factoryRepo.selectFoodID(food, price);
    }

    public void deleteOriginFood(int id){ //기존 음식 제거
        factoryRepo.deleteFood(id);
    }

    public void changeFoodPrice(int id, int price){ //발주 금액 변경
        factoryRepo.updateFoodPrice(id, price);
    }

    public void orderFoodToFactory(String date, int factoryid, int masterid){ //발주
        masterRepo.insertNewOrder(date, factoryid, masterid);
    }

    public void cancelOrder(String date, int id, int masterid){ //발주 취소
        masterRepo.deleteMyOrder(date, id, masterid);
    }

    public void printMyTodayAllOrders(String todayDate, int id){ //발주 목록 프린트
        masterRepo.selectMyOrders(todayDate, id);
    }

    public void printMyBeforeAllOrders(String todayDate, int id){
        masterRepo.selectBeforeMyOrders(todayDate, id);
    }

    public int loginOrAccountForMaster(){
        Scanner sc = new Scanner(System.in);
        int select;
        String inputName, inputPwd, inputPhoneNumber;
        int id;

        while (true){
            System.out.printf("1.회원가입, 2.로그인 => ");
            select = sc.nextInt();

            if(select == 1){
                System.out.printf("이름을 입력하세요. : ");
                inputName = sc.next();
                System.out.printf("사용할 비밀번호를 입력하세요. : ");
                inputPwd = sc.next();
                System.out.printf("핸드폰 번호를 입력하세요. : ");
                inputPhoneNumber = sc.next();

                makeAccountMaster(inputName, inputPwd, inputPhoneNumber);

            }else if(select == 2){
                System.out.printf("이름을 입력하세요. : ");
                inputName = sc.next();
                System.out.printf("비밀번호를 입력하세요. : ");
                inputPwd = sc.next();
                System.out.printf("핸드폰 번호를 입력하세요. : ");
                inputPhoneNumber = sc.next();

                if(loginForMaster(inputName, inputPwd, inputPhoneNumber)){
                    System.out.println("로그인에 성공하셨습니다.");
                    System.out.println(masterRepo);
                    id = masterRepo.selectMasterID(inputName, inputPwd, inputPhoneNumber);
                    return id;
                }else{
                    System.out.println("이름 또는 비번 또는 핸드폰 번호가 잘못되었습니다.");
                }
            }
        }
    }

    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        int select, masterID;

        while (true){
            System.out.printf("1.사장님 클릭, 2.공장장 클릭 => ");
            select = sc.nextInt();

            if(select == 1){

                //로그인, 회원가입
                masterID = loginOrAccountForMaster();
                whileMasterSelect(masterID);

            }else if(select == 2){

                whileFactorySelect();

            }else{
                System.out.println("다시 선택해주세요.");
            }
            System.out.println();
        }
    }

    public void whileMasterSelect(int masterID){
        Scanner sc = new Scanner(System.in);
        int selectMaster;
        int todayYear = 2024, todayMonth = 1, todayDay = 1;//오늘 날짜를 2024년 1월 1일이라고 가정
        String todayStr;
        int inputPrice, id;
        String inputFoodName;

        while (true){
            todayStr = dateToString(todayYear, todayMonth, todayDay);
            System.out.println("오늘 날짜 : "+todayStr);
            System.out.printf("1.오늘 발주, 2.발주 취소(오늘 발주한 음식만 삭제가능), 3.오늘 발주 목록, 4.다음 날, 5.이전 발주 목록, 6.뒤로 => ");
            selectMaster = sc.nextInt();

            if(selectMaster == 1){

                System.out.printf("발주하고 음식 이름을 입력하세요. : ");
                inputFoodName = sc.next();
                System.out.printf("음식 가격을 입력하세요. : ");
                inputPrice = sc.nextInt();

                id = selectFoodID(inputFoodName, inputPrice);
                orderFoodToFactory(todayStr, id, masterID);

            }else if(selectMaster == 2){

                 //5시 이전이면 취소 가능 -> 문제 컨셉 바꿔서 당일 발주한건 얼마든지 취소 가능한걸로 바꿈
                    //오늘 주문한거 오늘 삭제하기 버전
                System.out.printf("취소하고 싶은 음식 이름을 입력하세요. : ");
                inputFoodName = sc.next();
                System.out.printf("음식 가격을 입력하세요. : ");
                inputPrice = sc.nextInt();

                id = selectFoodID(inputFoodName, inputPrice);
                cancelOrder(todayStr, id, masterID);


            }else if(selectMaster == 3){

                printMyTodayAllOrders(todayStr, masterID);

            }else if(selectMaster == 4){

                todayDay++;
                if(todayDay == 31){
                    todayMonth++;
                    todayDay = 1;
                }

            }else if(selectMaster == 5){

                printMyBeforeAllOrders(todayStr, masterID);

            }else if(selectMaster == 6){
                break;
            }
            System.out.println();
        }

    }

    public void whileFactorySelect(){
        Scanner sc = new Scanner(System.in);
        int selectFactory;
        int inputPrice, beforePrice;
        int id;
        String inputFoodName;

        System.out.println("주소 : "+this.factoryRepo);
        while (true){
            System.out.printf("1.발주 가능 음식 추가, 2.발주 가능 음식 삭제, 3.발주 금액 변경, 4.이전으로 => ");
            selectFactory = sc.nextInt();

            if(selectFactory == 1){
                System.out.printf("발주되는 음식 이름을 입력하세요. : ");
                inputFoodName = sc.next();
                System.out.printf("음식 가격을 입력하세요. : ");
                inputPrice = sc.nextInt();

                addNewFood(inputFoodName, inputPrice);

            }else if(selectFactory == 2){
                System.out.printf("삭제하고 싶은 음식 이름을 입력하세요. : ");
                inputFoodName = sc.next();
                System.out.printf("음식 가격을 입력하세요. : ");
                inputPrice = sc.nextInt();

                id = selectFoodID(inputFoodName, inputPrice);
                deleteOriginFood(id);

            }else if(selectFactory == 3){
                System.out.printf("발주 금액을 변경하고 싶은 음식 이름을 입력하세요. : ");
                inputFoodName = sc.next();
                System.out.printf("현재 가격을 입력하세요. : ");
                beforePrice = sc.nextInt();
                System.out.printf("변경할 가격을 입력하세요. : ");
                inputPrice = sc.nextInt();

                id = selectFoodID(inputFoodName, beforePrice);
                changeFoodPrice(id, inputPrice);

            }else if(selectFactory == 4){
                break;

            }else{
                System.out.println("다시 입력해주세요.");
            }
            System.out.println();
        }
    }

    private String dateToString(int year, int month, int day){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);

        return stringBuilder.toString();
    }
}
