package Delivery.Service;

import Delivery.Domain.Master;
import Delivery.Domain.Menu;
import Delivery.Domain.User;
import Delivery.Repository.MasterRepo;
import Delivery.Repository.UserRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Component
public class Service {
    MasterRepo masterRepo;
    UserRepo userRepo;

    public Service(MasterRepo masterRepo, UserRepo userRepo){
        this.masterRepo = masterRepo;
        this.userRepo = userRepo;
    }

    public void makeAccount(String name, String pwd, String phoneNumber, String userID){
        userRepo.insertAccount(name, pwd, phoneNumber, userID);
        System.out.println("회원가입이 완료되었습니다.");
    }

    public boolean inspectSameID(String userID){
        return userRepo.inspectSameID(userID);
    }

    public boolean loginUser(String id, String pwd){
        return userRepo.selectAccount(id, pwd);
    }

    private int findUserTableID(String name, String pwd){
        return userRepo.findTableID(name, pwd);
    }

    private int findRestaurantID(String name){ //식당 주인 로그인용으로도 같이 씀
        return masterRepo.findMasterID(name);
    }

    public ArrayList<Menu> printMenus(int id){
        return masterRepo.selectMenusByID(id);
    }

    public void orderFood(String date, int userID, int masterID, String food, int price){
        userRepo.insertOrder(date, userID, masterID, food, price);
    }

    public void payforRestaurant(String date, int userID, int totalCost, String pay){
        userRepo.insertPay(date, userID, totalCost, pay);
    }

    public void restaurantSatisfaction(int masterID, int userID, int stars){
        userRepo.insertSatisfaction(masterID, userID, stars);
    }

    public void makeMasterAccount(String restaurantName, String pwd){
        masterRepo.insertMaster(restaurantName, pwd);
        System.out.println("회원가입이 완료되었습니다.");
    }

    public void printAllUsers(){
        masterRepo.printAllUsers();
    }

    public void zeroAssests(int id){
        masterRepo.insertZeroAssests(id);
    }

    public int restaurantAssets(int id){
        return masterRepo.selectAssests(id);
    }

    public void updateAssets(int id, int money){
        masterRepo.updateAssests(id, money);
    }

    public boolean masterLogin(String restaurantName, String pwd){
        return masterRepo.masterLogin(restaurantName, pwd);
    }

    public void addNewFoods(int masterID, String food, int price){
        masterRepo.insertFoodsAndDrinks(masterID, food, price);
        System.out.println("추가되었습니다.");
    }

    public void deleteMenu(String food){
        masterRepo.deleteMenu(food);
        System.out.println("삭제되었습니다.");
    }

    public void addNewBranch(int masterID, String branchname, int chefnum, int deliverymannum){
        masterRepo.insertNewBranch(masterID, branchname, chefnum, deliverymannum);
    }

    public ArrayList<Menu> randomOrders(int masterID){
        return masterRepo.selectRandomOrderMenu(masterID);
    }

    public int branchTotalSale(int branchID){
        return masterRepo.selectBranchTotalSale(branchID);
    }

    public void uploadBranchSale(int branchID, int total){
        masterRepo.updateBranchTotalSale(branchID, total);
    }

    public int selectTodayDeliveryOrdersNum(String todaydate, int masterID){
        return masterRepo.selectTodayDeliveryOrdersNum(todaydate, masterID);
    }

    public void whileSelect(){
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        LocalDateTime orderToday = null; //주문한 날
        final int cardFee = 3000; //카드 수수료 3000원 가정
        final int deliveryFee = 3000; //배달 수수료 지정
        final int advertisementCost = 50000; //홍보 비용 5만원 지정
        String inputName, inputPwd, inputPhoneNumber, inputRestaurantName, inputFoodName, inputUserID;
        int selectUser, selectUserOrVisor, selectVisor, orderSelect, selectCashOrCard;
        int selectMaster, inputPrice;
        int restaurantTableID; //식당 db id
        User user = null;
        Master master = null;
        boolean isLogin = false;
        boolean isMasterLogin = false;
        String cash = "현금";
        String card = "카드";
        int totalOrderCost;//음식 주문 비용
        int deliveringTime;//배달하는데 걸리는 시간
        int userPatientTime;//손님이 배달 받는데 좋은 리뷰 남길 수 있을 때까지 버틸 시간
        int stars = 100;//만족도
        ArrayList<Menu> menuList = null;
        String date;
        int assets;

        while (true){
            System.out.printf("1.손님용, 2.관리자용 => ");
            selectUserOrVisor = sc.nextInt();

            if(selectUserOrVisor == 1){

                while (true){ //로그인 전 단계
                    System.out.printf("1.회원 가입, 2.로그인, 3.이전으로 => ");
                    selectUser = sc.nextInt();

                    if(selectUser == 1){

                        System.out.printf("이름을 입력하세요. : ");
                        inputName = sc.next();
                        System.out.printf("사용할 아이디를 입력하세요. : ");
                        inputUserID = sc.next();

                        //아이디 중복 검사
                        if(inspectSameID(inputUserID)){
                            System.out.println("이미 사용되고 있는 아이디입니다.");
                            System.out.println();
                            continue;
                        }

                        System.out.printf("사용할 비밀번호를 입력하세요. : ");
                        inputPwd = sc.next();
                        System.out.printf("핸드폰 번호를 입력하세요. : ");
                        inputPhoneNumber = sc.next();

                        makeAccount(inputName, inputPwd, inputPhoneNumber, inputUserID); //회원 가입

                    }else if(selectUser == 2){

                        System.out.printf("아이디를 입력하세요. : ");
                        inputUserID = sc.next();
                        System.out.printf("비밀번호를 입력하세요. : ");
                        inputPwd = sc.next();

                        if(loginUser(inputUserID, inputPwd)){

                            user = new User(inputUserID, inputPwd);
                            user.setTableID(findUserTableID(inputUserID, inputPwd));
                            isLogin = true; //로그인 중으로 설정
                            System.out.println("로그인에 성공하셨습니다.");
                            break;

                        }else{
                            System.out.println("이름 혹은 비밀번호가 틀렸습니다.");
                        }

                    }else if(selectUser == 3){

                        break;
                    }else{
                        System.out.println("다시 선택해주세요.");
                    }

                }
                System.out.println();
                if(isLogin){ //로그인 성공 이후 단계

                    while(true){
                        System.out.printf("1.배달, 2.포장, 3.로그아웃 => ");
                        selectUser = sc.nextInt();

                        if(selectUser == 3){
                            user = null;
                            System.out.println("로그아웃 되었습니다.");
                            break;
                        }

                        System.out.printf("가게 이름을 입력해주세요. : ");
                        inputRestaurantName = sc.next();

                        orderToday = LocalDateTime.now();
                        date = dateToString(orderToday.getYear(), orderToday.getMonthValue(), orderToday.getDayOfMonth());

                        restaurantTableID = findRestaurantID(inputRestaurantName);
                        menuList = printMenus(restaurantTableID); //메뉴판 보여주기
                        totalOrderCost = 0;

                        while(true){
                            assets = 0;
                            System.out.printf("1. 음식 이름 입력, 2.주문 완료 => ");
                            orderSelect = sc.nextInt();

                            if(orderSelect == 1){
                                System.out.printf("주문 음식 : ");
                                inputFoodName = sc.next();

                                if(foodPriceReturn(menuList, inputFoodName) != 0){

                                    //주문해서 db에 넣기
                                    orderFood(date, user.getTableID(), restaurantTableID, inputFoodName, foodPriceReturn(menuList, inputFoodName));
                                    totalOrderCost += foodPriceReturn(menuList, inputFoodName);
                                }
                                System.out.println("주문 비용 : "+totalOrderCost+"원"); //주문해서 누적된 값 보여주기

                            }else if(orderSelect == 2){
                                break;
                            }
                            System.out.println();
                        }

                        if(selectUser == 1){ //배달이면 배달료 계산
                            stars = 100;//처음엔 100점에서 시작 뒤로 갈 수록 떨어지는 걸로
                            userPatientTime = 20 + random.nextInt(41); //20분~60분
                            deliveringTime = 10 + random.nextInt(51);//10분~60분

                            System.out.println("배달 시간 : "+deliveringTime+"분");

                            if(userPatientTime < deliveringTime){ //손님이 참을 수 있는 시간보다 배달이 더 늦게 온다면
                                stars -= (deliveringTime - userPatientTime);
                            }

                            if(deliveringTime/15*deliveryFee != 0){
                                System.out.println("배달 수수료 : "+deliveringTime/15*deliveryFee+"원");
                            }

                            totalOrderCost += (deliveringTime/15*deliveryFee);//15분당 배달 수수료
                            restaurantSatisfaction(restaurantTableID, user.getTableID(), stars);//배달할때만 만족도 추가하는 걸로 지정
                        }

                        assets += (totalOrderCost + restaurantAssets(restaurantTableID));

                        System.out.println();
                        System.out.println("결제할때 쓸 수단을 선택해주세요. "); //현금, 카드 선택
                        System.out.printf("1.현금, 2.카드 => ");
                        selectCashOrCard = sc.nextInt();

                        if(selectCashOrCard == 1){ //현금
                            totalOrderCost = totalOrderCost - (totalOrderCost*5/100); //5% 할인
                            System.out.println("현금을 선택하셔서 5%할인이 적용됩니다.");
                            payforRestaurant(date, user.getTableID(), totalOrderCost, cash);

                        }else if(selectCashOrCard == 2){ //카드
                            totalOrderCost += cardFee;
                            System.out.println("카드 수수료 "+cardFee+"원이 부과됩니다.");
                            payforRestaurant(date, user.getTableID(), totalOrderCost, card);
                        }

                        updateAssets(restaurantTableID, assets); //판매해서 식당 자산 증가
                    }

                    isLogin = false;

                }

            }else if(selectUserOrVisor == 2){

                System.out.printf("1.식당 등록, 2.사장님 로그인, 3.가입된 사용자 전체 조회 => ");
                selectVisor = sc.nextInt();

                if(selectVisor == 1){
                    System.out.printf("식당 이름 입력 : ");
                    inputRestaurantName = sc.next();
                    System.out.printf("사용할 비밀번호 입력 : ");
                    inputPwd = sc.next();

                    makeMasterAccount(inputRestaurantName, inputPwd);
                    zeroAssests(findRestaurantID(inputRestaurantName));

                }else if(selectVisor == 2){
                    System.out.printf("식당 이름 입력 : ");
                    inputRestaurantName = sc.next();
                    System.out.printf("사용할 비밀번호 입력 : ");
                    inputPwd = sc.next();

                    if(masterLogin(inputRestaurantName, inputPwd)){
                        isMasterLogin = true;
                        master = new Master();
                        master.setTableID(findRestaurantID(inputRestaurantName));
                        master.restaurant_name = inputRestaurantName;
                        System.out.println("로그인 성공.");

                    }else{
                        System.out.println("이름 혹은 비밀번호가 잘못되었습니다.");
                    }
                    System.out.println();

                }else if(selectVisor == 3){
                    printAllUsers();
                }

                if(isMasterLogin){
                    while (true){

                        /*
                            주문 랜덤으로 알아서 들어왔다 치고 알아서 돈 올려버리기
                            지점별 매출 알아서 올리기
                         */

                        ArrayList<Menu> randOrders = randomOrders(master.getTableID());
                        int branch = masterRepo.selectRandomBranch(master.getTableID());

                        if(branch != 0){
                            int nowTotalSale = branchTotalSale(branch);

                            int sum = 0;
                            for(int i = 0; i < randOrders.size(); i++){
                                sum += randOrders.get(i).price;
                            }

                            uploadBranchSale(branch, nowTotalSale + sum);
                        }

                        System.out.printf("1.메뉴 추가, 2.메뉴 삭제, 3.이벤트, 4.자산 조회, 5.로그아웃, 6.지점 늘리기, 7.급여 송금 => ");
                        selectMaster = sc.nextInt();

                        if(selectMaster == 1){

                            System.out.printf("메뉴 이름을 입력하세요. : ");
                            inputFoodName = sc.next();
                            System.out.printf("가격을 지정하세요. : ");
                            inputPrice = sc.nextInt();

                            addNewFoods(master.getTableID(), inputFoodName, inputPrice);

                        }else if(selectMaster == 2){

                            System.out.printf("지우고 싶은 메뉴명을 입력하세요. : ");
                            inputFoodName = sc.next();

                            deleteMenu(inputFoodName);

                        }else if(selectMaster == 3){
                            assets = restaurantAssets(master.getTableID()) - advertisementCost;
                            System.out.println("이벤트에 쓴 비용땜에 자산이 줄어듭니다.");
                            if(assets < 0){
                                assets = 0;
                            }
                            updateAssets(master.getTableID(), assets);

                        }else if(selectMaster == 4){
                            assets = restaurantAssets(master.getTableID());
                            System.out.println("현재 자산 : "+assets+"원");

                        }else if(selectMaster == 5){
                            isMasterLogin = false;
                            System.out.println("로그아웃 되었습니다.");
                            break;
                        }else if(selectMaster == 6){

                            System.out.printf("추가할 지점의 이름을 입력하세요. : ");
                            String branchname = scStr.nextLine();
                            System.out.printf("주방에 일할 인원을 입력하세요. : ");
                            int inputChefNum = sc.nextInt();
                            System.out.printf("배달할 인원을 입력하세요. : ");
                            int inputDeliveryManNum = sc.nextInt();

                            addNewBranch(master.getTableID(), branchname, inputChefNum, inputDeliveryManNum);
                            int branchid = masterRepo.selectBranchID(branchname);
                            String today = dateToString(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
                            masterRepo.insertNewBranchSale(branchid, today);


                        }else if(selectMaster == 7){

                            //오늘 날짜 기준 배달 수 보고 성과금 500원 지급 여부 결정
                            String today = dateToString(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
                            int deliveryOrders = selectTodayDeliveryOrdersNum(today, master.getTableID());

                            System.out.printf("직원 이름 : ");
                            String name = sc.next();

                            System.out.printf("직원 핸드폰 번호 : ");
                            String phoneNum = sc.next();

                            System.out.printf("입금할 급여 : ");
                            int pay = sc.nextInt();

                            if(deliveryOrders >= 5){
                                System.out.println("5번 이상 배달해서 보너스 500원이 주어집니다.");
                                pay += 500;
                            }

                            System.out.println("급여 "+pay+"원이 입금되었습니다.");
                        }
                        else{
                            System.out.println("다시 입력하세요.");
                        }
                        System.out.println();
                    }

                }

            }else{
                System.out.println("다시 입력하세요.");
            }
            System.out.println();
        }

    }

    private int foodPriceReturn(ArrayList<Menu> menulist, String food){

        for(Menu menu : menulist){
            if(menu.foodName.equals(food)){
                return menu.price;
            }
        }
        return 0;//존재하지 않으면 0원 리턴
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
