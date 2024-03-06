package Lotto.Service;

import Lotto.Domain.LottoInterface;
import Lotto.Domain.MyLotto;
import Lotto.Repository.LottoRepo;
import Lotto.Repository.LottoRepoInterface;
import Lotto.Repository.LottoRepository;
import Lotto.Repository.UpdateDeleteRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class LottoService {

    private static LottoRepo lottoRepo;

    public LottoService(LottoRepo repo){
       lottoRepo = repo;
    }

    public void saveService(){
        Scanner scStr = new Scanner(System.in);
        String name, id, pwd;
        int findID;
        System.out.printf("이름을 입력하세요 : ");
        name = scStr.nextLine();

        System.out.printf("사용할 아이디를 입력하세요. : ");
        id = scStr.nextLine();

        System.out.printf("사용할 비밀번호를 입력하세요. : ");
        pwd = scStr.nextLine();

        lottoRepo.save(name, id, pwd);
        findID = findMemberTableID(id);

        if(findID != 0){
            inputZeroPoint(findID);
            System.out.println("회원이 되신 기념으로 로또 번호 추천은 처음엔 무료입니다.");
            System.out.println("2번째부터는 포인트를 충전하셔서 사용해야 합니다.");
        }
    }

    public boolean loginService(String id, String pwd){
        return lottoRepo.canLogin(id, pwd);
    }

    public void buyLottoService(MyLotto myLotto, int papersMany){
        lottoRepo.insertLottonums(myLotto, papersMany);
    }

    public void lottoUpdate(MyLotto myLotto){
        lottoRepo.updateLottoNums(myLotto);
    }

    public void deleteService(String memberID){
        Scanner scStr = new Scanner(System.in);
        String lottoNumStr;

        System.out.printf("입력했던 로또 번호를 입력하세요. (ex.23 10 5 42 3 19 -> 2310542319): ");
        lottoNumStr = scStr.nextLine();

        lottoRepo.deleteMyLottoNum(lottoNumStr, memberID);

    }

    public void inputZeroPoint(int table_id){
        lottoRepo.insertNewPoint(table_id);
    }

    public int findMemberTableID(String id){
        return lottoRepo.findMemberTableID(id);
    }

    public int findMemberPoint(int table_id){
        return lottoRepo.findMemberPoint(table_id);
    }

    public void updatePoint(int point, int table_id){
        lottoRepo.updatePoint(point, table_id);
    }

    public boolean isYouRecommendFirst(String id){
        return lottoRepo.isYouRecommendFirst(id);
    }

    public int[] recommendLottoNum(){
        return lottoRepo.recommendLottoNum();
    }

    public void changeRecommendFirst(String id){
        lottoRepo.changeRecommendFirst(id);
    }

    public void whileSelect(){
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        int papersMany = 0; //종이 장수
        int inputNumber; //로또 번호 쓸 숫자 갯수
        int[] lottoFirst = new int[6]; //1등 로또 번호
        int[] recommendLottonums;
        int sameNumbers1; //1등 맞힌 개수
        int sameNumbers2; //2등 맞힌 개수
        int sameNumbers3; //3등 맞힌 개수
        int profit;
        int select;
        int inputPoint, nowPoint, memberTableID;
        MyLotto myLotto = null;
        boolean islogin = false;
        boolean isbuy = false; //구매여부 판단
        boolean isRecommendFirst;
        LocalDateTime buyLottoTime; //로또 구매한 시간
        LocalDateTime limitRemakeLottoTime = null; //로또 수정 가능한 시간제한
        String inputID, inputPwd;
        String nowloginMemberID = "", nowloginMemberPwd = "";

        lottoFirst[0] = random.nextInt(50)+1; //1등 번호 램덤으로 지정
        lottoFirst[1] = random.nextInt(50)+1;
        lottoFirst[2] = random.nextInt(50)+1;
        lottoFirst[3] = random.nextInt(50)+1;
        lottoFirst[4] = random.nextInt(50)+1;
        lottoFirst[5] = random.nextInt(50)+1;

        while (true){

            if(isbuy && LocalDateTime.now().isAfter(limitRemakeLottoTime)){
                isbuy = false;
            }

            System.out.printf("1.회원 가입, 2.로그인, 3.로또 구매, 4.로그아웃, 5.로또 번호 수정, 6.로또번호삭제, 7.포인트 충전, 8.로또 번호 추천, 9.종료 => ");
            select = sc.nextInt();

            if(select == 1){

                saveService();

            }else if(select == 2){

                System.out.printf("아이디를 입력하세요. : ");
                inputID = scStr.nextLine();

                System.out.printf("비밀번호를 입력하세요. : ");
                inputPwd = scStr.nextLine();

                islogin = loginService(inputID, inputPwd);

                if(islogin){
                    nowloginMemberID = inputID;
                    nowloginMemberPwd = inputPwd;

                    System.out.println("현재 포인트 : "+findMemberPoint(findMemberTableID(nowloginMemberID)));
                }else{
                    System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
                }

            }else if(select == 3) {

                if (islogin) {
                    while (true) {
                        System.out.printf("로또 종이 몇 장을 구매하시겠습니까?(최대 100장까지 구매 가능합니다.) : ");
                        papersMany = sc.nextInt();

                        if (papersMany < 100) {
                            break;
                        }
                    }

                    isbuy = true;
                    buyLottoTime = LocalDateTime.now();
                    limitRemakeLottoTime = buyLottoTime.plusMinutes(3); //3분안에 수정 가능함
                    myLotto = new MyLotto(papersMany);
                    myLotto.setMypay(papersMany * 5000);
                    myLotto.setPapersMany(papersMany);

                    memberTableID = findMemberTableID(nowloginMemberID);
                    nowPoint = findMemberPoint(memberTableID);

                    if(nowPoint - myLotto.getMypay() >= 0){ //포인트로 구매 가능한 경우

                        updatePoint(nowPoint - myLotto.getMypay(), memberTableID); //포인트 감소

                        System.out.println("로또 번호 당 숫자 몇개를 입력하시겠습니까? : ");
                        inputNumber = sc.nextInt();

                        if (inputNumber == 6) { //수동
                            myLotto.passiveLotto();

                        } else if (inputNumber == 0) { //자동
                            myLotto.autoLotto();
                        } else { //반자동
                            myLotto.semiautoLotto(inputNumber);
                        }

                        myLotto.setId(nowloginMemberID);
                        myLotto.setPassword(nowloginMemberPwd);

                        buyLottoService(myLotto, papersMany); //db에 구매한 로또 번호 넣기
                        myLotto.printMyLottoNumbers();

                        sameNumbers1 = findsameNumbersFirst(lottoFirst, myLotto.mylottoNumbers, papersMany);
                        sameNumbers2 = findsameNumbersSecond(lottoFirst, myLotto.mylottoNumbers, papersMany);
                        sameNumbers3 = findsameNumbersThird(lottoFirst, myLotto.mylottoNumbers, papersMany);

                        profit = sameNumbers1 * LottoInterface.firstMoney + sameNumbers2 * LottoInterface.secondMoney + sameNumbers3 * LottoInterface.thirdMoney;

                        System.out.println("지불한 금액 : " + myLotto.getMypay() + "원");
                        System.out.println("지금 부터 3분안에 수정 가능합니다.");

                        if (profit > 0) {
                            System.out.println("차익 : " + (profit - myLotto.getMypay()) + "원");
                        } else {
                            System.out.println("당첨 되지 않았습니다.");
                        }

                    }else{
                        System.out.println("포인트가 부족합니다. 충전해주세요.");
                    }

                    lottoRepo.severalLottonums();//역대 로또 번호 아무거나 막 채워넣음
                } else {
                    System.out.println("로그인을 먼저 해주세요.");
                }

            }else if(select == 4){
                islogin = false;
                System.out.println("로그아웃 되었습니다.");

            }else if(select == 5){

                if(isbuy && islogin){

                    if(LocalDateTime.now().isBefore(limitRemakeLottoTime)){ //수정 가능 시간에 수정할시

                        System.out.println("로또 번호 당 숫자 몇개를 입력하시겠습니까? : ");
                        inputNumber = sc.nextInt();

                        if (inputNumber == 6) { //수동
                            myLotto.passiveLotto();

                        } else if (inputNumber == 0) { //자동
                            myLotto.autoLotto();
                        } else { //반자동
                            myLotto.semiautoLotto(inputNumber);
                        }

                        myLotto.setId(nowloginMemberID);
                        myLotto.setPassword(nowloginMemberPwd);

                        //업데이트
                        lottoUpdate(myLotto);

                        myLotto.printMyLottoNumbers();

                        sameNumbers1 = findsameNumbersFirst(lottoFirst, myLotto.mylottoNumbers, papersMany);
                        sameNumbers2 = findsameNumbersSecond(lottoFirst, myLotto.mylottoNumbers, papersMany);
                        sameNumbers3 = findsameNumbersThird(lottoFirst, myLotto.mylottoNumbers, papersMany);

                        profit = sameNumbers1 * LottoInterface.firstMoney + sameNumbers2 * LottoInterface.secondMoney + sameNumbers3 * LottoInterface.thirdMoney;


                        if (profit > 0) {
                            System.out.println("차익 : " + (profit - myLotto.getMypay()) + "원");
                        } else {
                            System.out.println("당첨 되지 않았습니다.");
                        }

                    }else{
                        System.out.println("시간이 지나서 수정이 안 됩니다.");
                    }

                }else{
                    System.out.println("로그인이 안 되어있거나 아직 구매전입니다.");
                }

            }else if(select == 6){
                System.out.println("삭제할 입력한 번호가 여러 개일 경우 모두 삭제 됩니다.");
                deleteService(nowloginMemberID);
            }
            else if(select == 7){

                System.out.printf("얼만큼의 포인트를 충전하시겠습니까?(1000원당 1000point) : ");
                inputPoint = sc.nextInt();

                memberTableID = findMemberTableID(nowloginMemberID);
                nowPoint = findMemberPoint(memberTableID);

                updatePoint(nowPoint+inputPoint, memberTableID);

                System.out.println("포인트가 충전되었습니다.");
                System.out.println("현재 포인트 : "+findMemberPoint(memberTableID));

            }else if(select == 8){

                isRecommendFirst = isYouRecommendFirst(nowloginMemberID);
                recommendLottonums = recommendLottoNum();

            if(islogin){
                if(!isRecommendFirst){ //처음이라면 무료
                    printNums(recommendLottonums);
                    changeRecommendFirst(nowloginMemberID);

                }else{ //포인트 감소

                    memberTableID = findMemberTableID(nowloginMemberID);
                    nowPoint = findMemberPoint(memberTableID);

                    System.out.println("포인트 1000p를 사용합니다.");

                    if(nowPoint - 1000 >= 0){

                        updatePoint(nowPoint - 1000, memberTableID);
                        printNums(recommendLottonums);

                    }else{
                        System.out.println("포인트가 부족합니다.");
                    }
                }

            }else{
                System.out.println("로그인을 해야 가능합니다.");
            }

            }else if(select == 9){
                break;
            }
            else{
                System.out.println("다시 선택하세요.");
            }

            System.out.println();
        }
    }

    public int findsameNumbersFirst(int[] lottofirst, int[][][] mylottoNumbers, int papersMany){
        int first = 0;
        int[] lottofirstcopy = lottofirst;
        int[] mylottocopy;
        Arrays.sort(lottofirstcopy);

        for(int i=0; i<papersMany; i++){
            for(int j=0; j<5; j++){
                mylottocopy = mylottoNumbers[i][j];

                Arrays.sort(mylottocopy);

                if(Arrays.toString(mylottocopy).equals(Arrays.toString(lottofirstcopy))){
                    first++;
                }
            }
        }

        return first;
    }

    public int findsameNumbersSecond(int[] lottofirst, int[][][] mylottoNumbers, int papersMany){
        int same = 0;
        int second = 0;
        int[] mylottocopy;
        Integer[] boxedLotto;
        Integer[] boxedfirstlottocopy = Arrays.stream(lottofirst).boxed().toArray(Integer[]::new);;
        List<Integer> boxedLottoList;
        List<Integer> boxedfirstlottocopyList = new ArrayList<>(Arrays.asList(boxedfirstlottocopy));

        for(int i=0; i<papersMany; i++){
            for(int j=0; j<5; j++){
                mylottocopy = mylottoNumbers[i][j];

                boxedLotto = Arrays.stream(mylottocopy).boxed().toArray(Integer[]::new);
                boxedLottoList = new ArrayList<>(Arrays.asList(boxedLotto));
                for(int idx=0; idx<6; idx++){
                    if(boxedLottoList.contains(boxedfirstlottocopyList.get(idx))){
                        same++;
                    }
                }

                if(same == 5){
                    second++;
                }
                same = 0;
            }
        }

        return second;
    }

    public int findsameNumbersThird(int[] lottofirst, int[][][] mylottoNumbers, int papersMany){
        int same = 0;
        int third = 0;
        int[] mylottocopy;
        Integer[] boxedLotto;
        Integer[] boxedfirstlottocopy = Arrays.stream(lottofirst).boxed().toArray(Integer[]::new);
        List<Integer> boxedLottoList;
        List<Integer> boxedfirstlottocopyList = new ArrayList<>(Arrays.asList(boxedfirstlottocopy));

        for(int i=0; i<papersMany; i++){
            for(int j=0; j<5; j++){
                mylottocopy = mylottoNumbers[i][j];

                boxedLotto = Arrays.stream(mylottocopy).boxed().toArray(Integer[]::new);
                boxedLottoList = new ArrayList<>(Arrays.asList(boxedLotto));
                for(int idx=0; idx<6; idx++){
                    if(boxedLottoList.contains(boxedfirstlottocopyList.get(idx))){
                        same++;
                    }
                }

                if(same == 4){
                    third++;
                }
                same = 0;
            }
        }

        return third;
    }

    private void printNums(int[] nums){
        for(int i=0; i<nums.length; i++){
            System.out.printf("%d ",nums[i]);
        }
        System.out.println();
    }
}
