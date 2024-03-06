package RestaurantReservationSystem.Service;

import RestaurantReservationSystem.Domain.Member;
import RestaurantReservationSystem.Domain.Restaurant;
import RestaurantReservationSystem.Repository.MemberRepo;
import RestaurantReservationSystem.Repository.MemberRepository;
import RestaurantReservationSystem.Repository.ReservateRepo;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MainService {

    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        boolean isLogin = false;
        int select, selectVisitTime;
        Member newMember;
        Member loginMember = null;
        Restaurant restaurant = new Restaurant();
        String inputName, inputPhoneNumber;
        String usingdate;
        int inputOld;
        int inputYear, inputMonth, inputDay, inputMoney;
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = MemberService.getService();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime reservateDate;
        int totalPrice; //5만원어치 주문했다고 가정

        ReservateRepo reservateRepo = new ReservateRepo(memberRepository);
        MemberRepo memberRepo = new MemberRepo(memberRepository);

        while (true){

            printSelectMenu(isLogin);
            select = sc.nextInt();

            if(!isLogin && select == 1){
                System.out.printf("이름 입력 : ");
                inputName = sc.next();

                System.out.printf("나이 입력 : ");
                inputOld = sc.nextInt();

                System.out.printf("핸드폰 번호 입력 : ");
                inputPhoneNumber = sc.next();

                newMember = new Member(inputName, inputOld, inputPhoneNumber, 0);
                memberService.makeAccountService(memberRepo, newMember);

            }else if(isLogin && select == 1){

                isLogin = false;
                System.out.println("로그아웃 돼었습니다.");
            }

            if(!isLogin && select == 3){
                System.out.println("로그인을 먼저 해주세요.");
            }

            if(!isLogin && select == 2){
                System.out.printf("이름 입력 : ");
                inputName = sc.next();

                System.out.printf("나이 입력 : ");
                inputOld = sc.nextInt();

                System.out.printf("핸드폰 번호 입력 : ");
                inputPhoneNumber = sc.next();

                System.out.printf("갖고 있는 금액을 입력하세요. : ");
                inputMoney = sc.nextInt();

                loginMember = new Member(inputName, inputOld, inputPhoneNumber, inputMoney);

                isLogin = memberService.canLoginService(memberRepo, loginMember);

            }else if(isLogin && select == 2){
                System.out.println("예약하고 싶은 날짜를 입력하세요.");
                System.out.printf("연도 입력 : ");
                inputYear = sc.nextInt();
                System.out.printf("월 입력 : ");
                inputMonth = sc.nextInt();
                System.out.printf("일 입력 : ");
                inputDay = sc.nextInt();

                reservateDate = LocalDateTime.of(inputYear, inputMonth, inputDay, 0, 0);

                if(today.isBefore(reservateDate)){ //예약 가능한 날

                    usingdate = dateTostring(inputYear, inputMonth, inputDay);
                    restaurant.setUsingdate(usingdate);

                    System.out.println("예약하고 싶은 시간을 선택하세요.");
                    while (true){

                        System.out.printf("1. 17:30, 2. 18:00, 3.18:30 => ");
                        selectVisitTime = sc.nextInt();

                        if(selectVisitTime == 1){
                            restaurant.setVisitTime("17:30");
                            break;
                        }else if(selectVisitTime == 2){
                            restaurant.setVisitTime("18:00");
                            break;
                        }else if(selectVisitTime == 3){
                            restaurant.setVisitTime("18:30");
                            break;
                        }else{
                            System.out.println("다시 선택하세요.");
                        }
                    }

                    memberService.reservationService(reservateRepo, loginMember, restaurant);
                    totalPrice = memberService.reducePrice(loginMember.getMoney());
                    System.out.println("지불 가격 : "+totalPrice+"원");

                }else{ //예약 불가능한 날
                    System.out.println("오늘 날짜 이후로만 예약이 가능합니다.");
                }
            }

            System.out.println();
        }
    }

    public void printSelectMenu(boolean isLogin){

        if(!isLogin){

            System.out.printf("1.회원 가입, 2.로그인, 3.레스토랑 예약 : ");

        }else{
            System.out.printf("1.로그 아웃, 2.레스토랑 예약 : ");
        }
    }

    public String dateTostring(int year, int month, int day){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);

        return stringBuilder.toString();
    }
}
