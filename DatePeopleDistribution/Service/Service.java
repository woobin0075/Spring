package DatePeopleDistribution.Service;

import DatePeopleDistribution.Domain.User;
import DatePeopleDistribution.Repository.UserRepo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Service {
    UserRepo userRepo;

    public Service(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    private void makeUserAccount(String id, String pwd, String name, String phoneNumber){
        userRepo.insertNewUser(id, pwd, name, phoneNumber);
    }

    private void addBuyInfo(int userdbid, int bacon, int icecream, int potatobean, int totalPay, String date, int point){
        userRepo.insertBuyInfo(userdbid, bacon, icecream, potatobean, totalPay, date, point);
    }

//    private void stackPoint(int dbid, int point){
//        userRepo.updatePoint(dbid, point);
//    }

    private void printReceits(String date){
        userRepo.selectReceit(date);
    }

    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        int[] visitPeople = new int[5]; //1월 1일 부터 1월 5일까지 방문 가정
        Map<Integer, Integer> movePeopleMap = new HashMap<>();
        int year = 2024, month = 1, day = 17;
        int inputVisitNum, inputDay;
        String inputID, inputPwd, inputName, inputPhoneNumber;
        int select;
        int bacon = 0, potatobean = 0, icecream = 0, inputPoint = 0;
        int baconMany=0, potatobeanMany = 0, icecreamMany=0;
        int maxPocketMoney = 100000, totalpay = 0;
        boolean moveFlag; //이전 된 사람이라면 true로 변경
        boolean pointFlag = false; //적립 했으면 true로 변경
        int visitNum = 1;
        int chai = 0;
        User user;
        int i=0;

        while (i<visitPeople.length){
            System.out.printf(i+1+"일에 몇명이 방문예정인가요?(15명이하로 입력) : ");
            inputVisitNum = sc.nextInt();
            visitPeople[i] += inputVisitNum;

            if(inputVisitNum > 15) continue;

            if(visitPeople[i] > 10 && i+1 < visitPeople.length){
                chai = visitPeople[i] - 10;
                visitPeople[i] = 10;
                visitPeople[i+1] = chai;
                movePeopleMap.put(i+1, chai);

            }
            i++;
        }

        if(visitPeople[4] > 10){
            visitPeople[4] = 10;
        }


        cautionPrint();//주의 사항

        while(day <= 5){

            System.out.println(year+"년 "+month+"월 "+day+"일");
            //회원가입 먼저 하기
            System.out.printf("사용할 아이디를 입력하세요. : ");
            inputID = sc.next();
            System.out.printf("사용할 비밀번호를 입력하세요. : ");
            inputPwd = sc.next();
            System.out.printf("이름을 입력하세요. : ");
            inputName = sc.next();
            System.out.printf("핸드폰 번호를 입력하세요. : ");
            inputPhoneNumber = sc.next();

            makeUserAccount(inputID, inputPwd, inputName, inputPhoneNumber); //회원가입

            user = new User(inputID, inputPwd);

            user.setId(userRepo.selectUserDBid(inputID));

            if(movePeopleMap.containsKey(day-1) && movePeopleMap.get(day-1) >= visitNum){
                moveFlag = true;
            }else{
                moveFlag = false;
            }

            while(true){
                //하고 싶은거 선택
                System.out.printf("1. 베이컨 5000원, 2. 아이스크림 3000원, 3. 감자콩 1000원, 4.다음사람, 5.종료, 6. 포인트 적립, 7.다음 날, 8.영수증 조회 => ");
                select = sc.nextInt();

                if(select == 1){

                    if(maxPocketMoney - 5000 >= 0){
                        bacon+=5000;
                        totalpay += 5000;
                        maxPocketMoney -= 5000;
                        baconMany++;
                        System.out.println("베이컨 1개 구매");

                    }else{
                        System.out.println("돈이 부족해서 구매가 불가합니다.");
                    }

                }else if(select == 2){

                    if(maxPocketMoney - 3000 >= 0){
                        icecream += 3000;
                        totalpay += 3000;
                        maxPocketMoney -= 3000;
                        icecreamMany++;
                        System.out.println("아이스크림 1개 구매");

                    }else{
                        System.out.println("돈이 부족해서 구매가 불가합니다.");
                    }

                }else if(select == 3){

                    if(maxPocketMoney - 1000 >= 0){
                        potatobean += 1000;
                        totalpay += 1000;
                        maxPocketMoney -= 1000;
                        potatobeanMany++;
                        System.out.println("감자콩 1개 구매");
                    }else{
                        System.out.println("돈이 부족해서 구매가 불가합니다.");
                    }

                }else if(select == 4){

                    //넘어가기 조건
                    if(icecreamMany % 10 != 0){
                        System.out.println("아이스크림을 더 많이 구매해야합니다.");
                        System.out.println("아이스크림 필요개수 : "+(10 - (icecreamMany % 10)));
                        continue;
                    }

                    if(potatobeanMany < icecreamMany){
                        System.out.println("감자콩이 아이스크림보다 적습니다.");
                        System.out.println("감자콩 필요 개수 : "+(icecreamMany - potatobeanMany));
                        continue;
                    }

                    //결제
                    if(moveFlag){
                        totalpay = totalpay  - (totalpay*5/100);
                        System.out.println("이전된 사람이어서 5%할인됩니다.");
                    }

                    addBuyInfo(user.getId(), bacon, icecream, potatobean, totalpay, dateToString(year, month, day), inputPoint);

                    visitNum++;
                    if(visitNum > visitPeople[day-1]){
                        day++;
                        visitNum = 1;
                        System.out.println("해당 인원이 모두 방문해서 다음날로 넘어갑니다.");
                    }

                    maxPocketMoney = 100000;
                    bacon = 0; icecream = 0; potatobean = 0;
                    baconMany = 0; icecreamMany = 0; potatobeanMany = 0; totalpay = 0;
                    inputPoint = 0;
                    pointFlag = false;
                    break;

                }else if(select == 5){
                    //넘어가기 조건
                    if(icecreamMany % 10 != 0){
                        System.out.println("아이스크림을 더 많이 구매해야합니다.");
                        System.out.println("아이스크림 필요개수 : "+(10 - (icecreamMany % 10)));
                        continue;
                    }

                    if(potatobeanMany < icecreamMany){
                        System.out.println("감자콩이 아이스크림보다 적습니다.");
                        System.out.println("감자콩 필요 개수 : "+(icecreamMany - potatobeanMany));
                        continue;
                    }
                    return;

                }else if(select == 6){

                    if(totalpay == 0){
                        System.out.println("아직 구매를 안 하셔서 포인트 적립이 되지 않습니다.");
                        continue;
                    }

                    if(!pointFlag){
                        inputPoint = totalpay / 100;
                        System.out.println(inputPoint+" 포인트가 적립되었습니다.");
                    }else{
                        System.out.println("이미 포인트를 적립하셨습니다.");
                    }

                    pointFlag = true;

                }else if(select == 7){

                    day++;
                    visitNum = 1;
                    //결제
                    if(moveFlag){
                        System.out.println("이전된 사람이어서 5%할인됩니다.");
                        totalpay = totalpay  - (totalpay*5/100);
                    }

                    addBuyInfo(user.getId(), bacon, icecream, potatobean, totalpay, dateToString(year, month, day), inputPoint);

                    maxPocketMoney = 100000;
                    bacon = 0; icecream = 0; potatobean = 0;
                    baconMany = 0; icecreamMany = 0; potatobeanMany = 0; totalpay = 0;
                    inputPoint = 0;
                    pointFlag = false;
                    break;

                }else if(select == 8){
                    System.out.printf("영수증을 확인할 일수를 입력하세요. : ");
                    inputDay = sc.nextInt();

                    printReceits(dateToString(2024, 1, inputDay));
                }

            }//선택 while문

        }
    }

    private void cautionPrint(){
        System.out.println("4,5번 종료를 누른 경우, 2번 아이스크림이 10의 배수가 아니라면 다시 구매해야 합니다.");
        System.out.println("4,5번 종료를 누른 경우, 3번 감자콩이 아이스크림보다 적다면 다시 구매하게 합니다.");
    }

    private String dateToString(int year, int month, int day){
        StringBuilder sb = new StringBuilder();

        sb.append(year);

        if(month < 10){
            sb.append(0);
        }

        sb.append(month);

        if(day < 30){
            sb.append(0);
        }

        sb.append(day);

        return sb.toString();
    }
}
