package MenuChoiceSystem.Service;

import MenuChoiceSystem.Domain.DebtRelation;
import MenuChoiceSystem.Domain.User;
import MenuChoiceSystem.Repository.UserRepo;

import java.util.*;

public class Service {

    private UserRepo userRepo;

    public Service(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    private void makeNewAccount(String id, String pwd, String name){ //회원가입
        userRepo.insertNewUser(id, pwd, name);
    }

    private void addBehaveInfo(int userdbid, int beforePocketMoney, int afterPocketMoney, int pizza, int hamburger,
                               int chicken, int pasta, int curry, int lendmoney, int borrowmoney){
        userRepo.insertBehaveInfo(userdbid, beforePocketMoney, afterPocketMoney, pizza, hamburger,
                chicken, pasta, curry, lendmoney, borrowmoney);
    } //구매 금전 행동 기록

    private void updateBehaveInfo(int userdbid, int afterPocketMoney, int pizza, int hamburger,
                                  int chicken, int pasta, int curry, int lendmoney, int borrowmoney){
        userRepo.updateBehaveInfo(userdbid, afterPocketMoney, pizza, hamburger, chicken, pasta, curry, lendmoney, borrowmoney);
    }

    private void printUsersBehave(){
        userRepo.selectUsersBehave();;
    }
    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        User[] users = new User[15];
        final int pizzaPrice = 5000;
        final int hamburgerPrice = 4000;
        final int chickentPrice = 5500;
        final int pastaPrice = 3500;
        final int curryPrice = 4500;
        int inputMoney;
        int inputSelect, inputRefundSelect, inputRefundMany;
        String inputUserID, inputPwd, inputName;
        int pizzaMany = 0, hamburgerMany = 0, chickenMany = 0, pastaMany = 0, curryMany = 0;
        int presentMoney = 0;
        boolean userRegisterFlag = false;
        boolean beforeFlag = false;
        Map<Integer, ArrayList<DebtRelation>> debptMap = new HashMap<>(); //채무관계

        for(int i=0; i< users.length; i++){

            if(i<=4){
                users[i] = new User(20000);
                users[i].setPresentPocketMoney(20000);
            }else if(i<10){
                users[i] = new User(10000);
                users[i].setPresentPocketMoney(10000);
            }else{
                System.out.printf("%d 번째 손님 용돈 입력 : ", i);
                inputMoney = sc.nextInt();

                users[i] = new User(inputMoney);
                users[i].setPresentPocketMoney(inputMoney);
            }
        }

        int idx = 0;
        while(idx < users.length){

            if(!beforeFlag){
                System.out.println(idx+"번째 손님");

                System.out.printf("사용할 아이디를 입력하세요. : ");
                inputUserID = sc.next();
                System.out.printf("사용할 비밀번호를 입력하세요. : ");
                inputPwd = sc.next();
                System.out.printf("이름을 입력하세요. : ");
                inputName = sc.next();

                users[idx].setUserID(inputUserID);
                makeNewAccount(inputUserID, inputPwd, inputName);
                users[idx].setId(userRepo.selectUserDBid(inputUserID));
            }

            while(true){
                menuPrint(userRegisterFlag);
                inputSelect = sc.nextInt();

                if(!canBuyAnyFood(users[idx].getPresentPocketMoney()) && inputSelect <= 5){
                    System.out.println("소지금이 부족합니다.");

                    lend(idx, users, users[idx], debptMap);
                    printCanBuyFood(users[idx].getPresentPocketMoney(), pizzaPrice, hamburgerPrice, chickentPrice, pastaPrice, curryPrice);

                    continue;
                }

                if(inputSelect == 1){

                    if(users[idx].getPresentPocketMoney() - pizzaPrice >= 0){
                        presentMoney = users[idx].getPresentPocketMoney() - pizzaPrice;
                        pizzaMany++;
                        users[idx].setPresentPocketMoney(presentMoney);
                        System.out.println("피자 한 판 구매완료했습니다.");
                    }else{
                        System.out.println("금액이 부족합니다.");
                        //돈 빌리기
                        lend(idx, users, users[idx], debptMap);
                        printCanBuyFood(users[idx].getPresentPocketMoney(), pizzaPrice, hamburgerPrice, chickentPrice, pastaPrice, curryPrice);
                    }

                }else if(inputSelect == 2){

                    if(users[idx].getPresentPocketMoney() - hamburgerPrice >= 0){
                        presentMoney = users[idx].getPresentPocketMoney() - hamburgerPrice;
                        hamburgerMany++;
                        users[idx].setPresentPocketMoney(presentMoney);
                        System.out.println("햄버거 하나 구매완료했습니다.");
                    }else{
                        System.out.println("금액이 부족합니다.");
                        lend(idx, users, users[idx], debptMap);
                        printCanBuyFood(users[idx].getPresentPocketMoney(), pizzaPrice, hamburgerPrice, chickentPrice, pastaPrice, curryPrice);
                    }

                }else if(inputSelect == 3){

                    if(users[idx].getPresentPocketMoney() - chickentPrice >= 0){
                        presentMoney = users[idx].getPresentPocketMoney() - chickentPrice;
                        chickenMany++;
                        users[idx].setPresentPocketMoney(presentMoney);
                        System.out.println("치킨 한 마리 구매완료했습니다.");
                    }else{
                        System.out.println("금액이 부족합니다.");
                        lend(idx, users, users[idx], debptMap);
                        printCanBuyFood(users[idx].getPresentPocketMoney(), pizzaPrice, hamburgerPrice, chickentPrice, pastaPrice, curryPrice);
                    }

                }else if(inputSelect == 4){

                    if(users[idx].getPresentPocketMoney() - pastaPrice >= 0){
                        presentMoney = users[idx].getPresentPocketMoney() - pastaPrice;
                        pastaMany++;
                        users[idx].setPresentPocketMoney(presentMoney);
                        System.out.println("파스타 하나 구매완료했습니다.");
                    }else{
                        System.out.println("금액이 부족합니다.");
                        lend(idx, users, users[idx], debptMap);
                        printCanBuyFood(users[idx].getPresentPocketMoney(), pizzaPrice, hamburgerPrice, chickentPrice, pastaPrice, curryPrice);
                    }

                }else if(inputSelect == 5){

                    if(users[idx].getPresentPocketMoney() - curryPrice >= 0){
                        presentMoney = users[idx].getPresentPocketMoney() - curryPrice;
                        curryMany++;
                        users[idx].setPresentPocketMoney(presentMoney);
                        System.out.println("커리 하나 구매완료했습니다.");
                    }else{
                        System.out.println("금액이 부족합니다.");
                        lend(idx, users, users[idx], debptMap);
                        printCanBuyFood(users[idx].getPresentPocketMoney(), pizzaPrice, hamburgerPrice, chickentPrice, pastaPrice, curryPrice);
                    }

                }else if(inputSelect == 6){

                    System.out.println("환불할 음식을 선택하세요.");
                    System.out.printf("1.피자, 2.햄버거, 3.치킨, 4.파스타, 5.커리 => ");
                    inputRefundSelect = sc.nextInt();

                    if(inputRefundSelect == 1 && pizzaMany > 0){
                        System.out.printf("피자 몇 판을 환불하시겠습니까? : ");
                        inputRefundMany = sc.nextInt();

                        if(pizzaMany - inputRefundMany >= 0){
                            pizzaMany -= inputRefundMany;
                            users[idx].setPresentPocketMoney(inputRefundMany * pizzaPrice);
                            System.out.println(inputRefundMany+"만큼 환불되었습니다.");
                        }else{
                            System.out.println("구매한 값보다 높습니다. 다시 입력해주세요.");
                        }

                    }else if(inputRefundSelect == 2 && hamburgerMany > 0){
                        System.out.printf("햄버거 몇 개를 환불하시겠습니까? : ");
                        inputRefundMany = sc.nextInt();

                        if(hamburgerMany - inputRefundMany >= 0){
                            hamburgerMany -= inputRefundMany;
                            users[idx].setPresentPocketMoney(inputRefundMany * hamburgerPrice);
                            System.out.println(inputRefundMany+"만큼 환불되었습니다.");
                        }else{
                            System.out.println("구매한 값보다 높습니다. 다시 입력해주세요.");
                        }

                    }else if(inputRefundSelect == 3 && chickenMany > 0){
                        System.out.printf("치킨 몇 마리를 환불하시겠습니까? : ");
                        inputRefundMany = sc.nextInt();

                        if(chickenMany - inputRefundMany >= 0){
                            chickenMany -= inputRefundMany;
                            users[idx].setPresentPocketMoney(inputRefundMany * chickentPrice);
                            System.out.println(inputRefundMany+"만큼 환불되었습니다.");
                        }else{
                            System.out.println("구매한 값보다 높습니다. 다시 입력해주세요.");
                        }

                    }else if(inputRefundSelect == 4 && pastaMany > 0){
                        System.out.printf("파스타 몇 개를 환불하시겠습니까? : ");
                        inputRefundMany = sc.nextInt();

                        if(pastaMany - inputRefundMany >= 0){
                            pastaMany -= inputRefundMany;
                            users[idx].setPresentPocketMoney(inputRefundMany * pastaPrice);
                            System.out.println(inputRefundMany+"만큼 환불되었습니다.");
                        }else{
                            System.out.println("구매한 값보다 높습니다. 다시 입력해주세요.");
                        }

                    }else if(inputRefundSelect == 5 && curryMany > 0){
                        System.out.printf("카레 몇 개를 환불하시겠습니까? : ");
                        inputRefundMany = sc.nextInt();

                        if(curryMany - inputRefundMany >= 0){
                            curryMany -= inputRefundMany;
                            users[idx].setPresentPocketMoney(inputRefundMany * curryPrice);
                            System.out.println(inputRefundMany+"만큼 환불되었습니다.");
                        }else{
                            System.out.println("구매한 값보다 높습니다. 다시 입력해주세요.");
                        }
                    }

                }else if(inputSelect == 7){
                    //돈 갚기
                    if(!debptMap.containsKey(idx)){
                        System.out.println("돈을 안 빌렸거나 갚았습니다.");
                        continue;
                    }

                    ArrayList<DebtRelation> borrowPeople = debptMap.get(idx);
                    int inputDebt = 0;
                    int inputIndex = 0;
                    int j = 0;

                    if(borrowPeople.size() == 0){
                        System.out.println("돈을 안 빌렸거나 갚았습니다.");
                        continue;
                    }

                    System.out.println("돈 빌려준 사람들 목록");
                    for(int i=0; i<borrowPeople.size(); i++){
                        System.out.println(borrowPeople.get(i).index+"번째 손님, 빌려준 금액 : "+borrowPeople.get(i).money);
                    }

                    System.out.printf("누구에게 돈을 갚으시겠습니까? : ");
                    inputIndex = sc.nextInt();

                    for(j=0; j<borrowPeople.size(); j++){
                        if(inputIndex == borrowPeople.get(j).index){
                            break;
                        }
                    }

                    if(j < borrowPeople.size()){
                        System.out.printf("얼마를 갚으시겠습니까? : ");
                        inputDebt = sc.nextInt();

                        if(inputDebt > 0 && inputDebt >= borrowPeople.get(j).money){

                            users[idx].setLendMoney(users[idx].getLendMoney() - borrowPeople.get(j).money);
                            users[inputIndex].setBorrowMoney(users[inputIndex].getBorrowMoney() - borrowPeople.get(j).money);
                            borrowPeople.remove(j);
                            System.out.println("다 갚았습니다.");

                        }else if(inputDebt > 0 && inputDebt < borrowPeople.get(j).money){
                            users[idx].setLendMoney(users[idx].getLendMoney() - inputDebt);
                            users[inputIndex].setBorrowMoney(users[inputIndex].getBorrowMoney() - inputDebt);
                            borrowPeople.get(j).money -= inputDebt;
                        }

                        debptMap.put(idx, borrowPeople);
                        if(borrowPeople.size() == 0){
                            debptMap.remove(idx);
                        }

                    }else{
                        System.out.println("해당되는 사람이 없습니다.");
                    }

                }else if(inputSelect == 8){

                    if(userRegisterFlag){
                        System.out.println("이전 손님으로 밖에 돌아갈 수 없습니다.");
                    }else{

                        //db에 구매한거 빌린 거 정보 넣기
                        if(!users[idx].isDbinfoFlag()){
                            addBehaveInfo(users[idx].getId(), users[idx].getBeforePocketMoney(), users[idx].getPresentPocketMoney(), pizzaMany,
                                    hamburgerMany, chickenMany, pastaMany, curryMany, users[idx].getLendMoney(), users[idx].getBorrowMoney());
                            users[idx].setDbinfoFlag(true);
                        }else{
                            updateBehaveInfo(users[idx].getId(), users[idx].getPresentPocketMoney(), pizzaMany, hamburgerMany, chickenMany,
                                    pastaMany, curryMany, users[idx].getLendMoney(), users[idx].getBorrowMoney());
                        }

                        idx++;
                        beforeFlag = false;
                        break;
                    }

                }else if(inputSelect == 9){
                    userRegisterFlag = true;

                }else if(inputSelect == 10){
                    //전체 영수증 출력
                    printUsersBehave();
                    return;
                }

                if(userRegisterFlag && idx > 0 && inputSelect == 11){
                    System.out.println("이전 손님으로 돌아갑니다.");
                    idx--;
                    userRegisterFlag = false;
                    beforeFlag = true;
                    break;
                }
                System.out.println();

            }
        }

        //전체 영수증 출력
        printUsersBehave();
    }

    private void printCanBuyFood(int money, int pizzaprice, int hamburgerprice, int chickenprice, int pastaprice, int curryprice){
        int buy = 0;

        if(money >= pizzaprice){
            System.out.printf("피자를 ");
            buy++;
        }

        if(money >= hamburgerprice){
            System.out.printf("햄버거를 ");
            buy++;
        }

        if(money >= chickenprice){
            System.out.printf("치킨을 ");
            buy++;
        }

        if(money >= pastaprice){
            System.out.printf("파스타를 ");
            buy++;
        }

        if(money >= curryprice){
            System.out.printf("커리를 ");
            buy++;
        }

        if(buy > 0){
            System.out.println("살 수 있습니다.");
        }

    }

    private void lend(int idx, User[] users, User user, Map<Integer, ArrayList<DebtRelation>> debptMap){
        Scanner sc = new Scanner(System.in);
        if(idx == users.length - 1){
            System.out.println("마지막 사람은 돈을 빌릴 수 없습니다.");
            return;
        }
        int j=1;
        int lendMoneyMany = 0;
        int select;
        while(idx + j < users.length){

            System.out.printf("얼만큼의 돈을 빌리시겠습니까? : ");
            lendMoneyMany = sc.nextInt();

            if(lendMoneyMany < users[idx+j].getPresentPocketMoney()){
                user.setLendMoney(user.getLendMoney() + lendMoneyMany);
                user.setPresentPocketMoney(user.getPresentPocketMoney() + lendMoneyMany);

                users[idx+j].setBorrowMoney(users[idx+j].getBorrowMoney() + lendMoneyMany);
                users[idx+j].setPresentPocketMoney(users[idx+j].getPresentPocketMoney() - lendMoneyMany);

                ArrayList<DebtRelation> list;
                if(debptMap.containsKey(idx)){
                    list = debptMap.get(idx);

                }else{
                    list = new ArrayList<>();
                }
                list.add(new DebtRelation(idx+j, lendMoneyMany));
                debptMap.put(idx, list);

            }else{
                System.out.println("상대가 가진 돈 보다 많습니다. 다시 입력해주세요.");
                continue;
            }

            System.out.printf("돈을 더 빌릴거면 1번을 아니면 그외 다른 숫자를 입력하세요. : ");
            select = sc.nextInt();

            if(select == 1) {
                j++;

            }else{
                break;
            }
        }
    }

    private void menuPrint(boolean flag){
        if(flag){
            System.out.printf("1.피자, 2.햄버거, 3.치킨, 4.파스타, 5.커리, 6.환불, 7.돈 갚기, 8.다음 손님 버튼, 9.손님 제어 버튼, 10.강제 종료, 11.이전 손님으로 돌아가기 => ");
        }else{
            System.out.printf("1.피자, 2.햄버거, 3.치킨, 4.파스타, 5.커리, 6.환불, 7.돈 갚기, 8.다음 손님 버튼, 9.손님 제어 버튼, 10.강제 종료 => ");
        }
    }

    private boolean canBuyAnyFood(int pocketMoney){ //모든 음식을 살 수 있는지
        if(pocketMoney < 5000){
            return false;
        }
        return true;
    }
}
