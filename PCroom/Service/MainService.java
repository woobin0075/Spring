package PCroom.Service;

import PCroom.Domain.Guest;
import PCroom.Domain.Member;
import PCroom.Domain.PCroomInterface;
import PCroom.Repository.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MainService {

    public void whileSelect(){
        Scanner scStr = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        String inputID, inputPwd;
        String enterNumber;
        boolean login = false; //true면 로그인 상태
        boolean guestLogin = false;
        Member member = new Member();
        Member nowMember = new Member(); //로그인 중인 멤버
        Guest guest = new Guest();
        SuperVisorService superVisorService = new SuperVisorService();
        MemberService memberService = new MemberService();
        GuestService guestService = new GuestService();
        LocalDateTime startTime = null; //5초에 1시간 지났다고 가정하기
        int fare; //이용 요금
        int bonousTime, vipBonusTime = 1;
        int inputSecretNum; //관리자 비밀번호
        int tableID = 0;
        int tableGuestID = 0;
        int select, findSelect, supervisorSelect;

        System.out.println("결제는 후불제이며 시간당 1천원입니다.");
        while (true){
            System.out.printf("1.회원가입, 2.로그인, 3.게스트 로그인, 4.계산, 로그아웃, 5.아이디/비번 찾기, 6.관리자, 7.회원 탈퇴 => ");
            select = sc.nextInt();

            if(select == 1){

                memberService.createMemberSerice(member, tableID);

                tableID++;

            }else if(select == 2){

                System.out.printf("ID를 입력하세요. : ");
                inputID = scStr.nextLine();
                System.out.printf("비밀번호를 입력하세요. : ");
                inputPwd = scStr.nextLine();

                login = memberService.memberLoginService(inputID, inputPwd);

                if(login){
                    startTime = LocalDateTime.now();
//                    nowMember.setID(inputID);
//                    nowMember.setPwd(inputPwd);
//                    name = memberService.bringMemberNameService(memberRepository, inputID); //이름 정보 가져오기
//                    nowMember.setName(name);
//                    nowMember.setVIP(memberService.isHeVIP(memberRepository, nowMember)); //로그인후 VIP인지 확인
//
//                    if(nowMember.isVIP()){
//                        System.out.println(nowMember.getName()+" VIP님 환영합니다.");
//                        System.out.println("보너스로 "+vipBonusTime+"시간을 더 드립니다.");
//                    }
                    memberService.loginAfter(nowMember, startTime, inputID, inputPwd);
                }

            }else if(select == 3){

                guest.makeEnterNumber();
                enterNumber = guest.getEnterNumber();

                guestLogin = guestService.guestLogin(enterNumber, tableGuestID);

                if(guestLogin){
                    startTime = LocalDateTime.now();
                    tableGuestID++;
                }

            }else if(select == 4){

                if(login){ //회원용

//                    nowMember.measureUsingTime(startTime);
//                    memberService.usingPcroomService(memberRepository, nowMember, visitDatetoString(startTime));
//                    fare = nowMember.getUsingTime() * 1000;
//
//                    //누적 사용시간이 5의 배수만큼 되는지 확인하기
//                    bonousTime = memberService.giveBonousService(memberRepository, nowMember);
//
//                    if(bonousTime > 0){ //보너스가 주어졌다면
//                        memberService.updateMemberUsingTimeService(memberRepository, nowMember, bonousTime);
//                    }
//
//                    //VIP시 보너스 시간 추가
//                    if(nowMember.isVIP()){
//                        memberService.updateMemberUsingTimeService(memberRepository, nowMember, vipBonusTime);
//                    }
//
//
//                    System.out.println("이용 요금 "+fare+"원이 부과되었습니다.");

                    login = memberService.memberLoginSituation(nowMember, startTime);

                }else if(guestLogin){ //비회원용
//                    guest.measureUsingTime(startTime);
//
//                    fare = guest.getUsingTime() * 1000;
//                    guestService.usingPcroomService(guestRepository, guest, visitDatetoString(startTime));
//
//                    System.out.println("이용 요금 "+fare+"원이 부과되었습니다.");

                    guestLogin = guestService.guestLoginSituation(guest, startTime);

                }else{
                    System.out.println("로그인을 먼저 해주세요.");
                }

            }else if(select == 5){

                System.out.printf("1.아이디 찾기, 2.비밀번호 변경 => ");
                findSelect = sc.nextInt();

                if(findSelect == 1){
                    memberService.findMyID();

                }else if(findSelect == 2){
                    memberService.changeMyPwd();
                }

            }else if(select == 6){
                System.out.printf("관리자 비밀번호를 입력하세요. : ");
                inputSecretNum = sc.nextInt();

                System.out.printf("1.역대 손님 사용 시간 목록 출력, 2.강제 퇴출 하기, 3.회원 정보 출력 => ");
                supervisorSelect = sc.nextInt();

                if(inputSecretNum == PCroomInterface.secretNum){

                    if(supervisorSelect == 1){
                        superVisorService.printMemberList();

                    }else if(supervisorSelect == 2){
                        superVisorService.deleteMemberService();

                    }else if(supervisorSelect == 3){
                        superVisorService.printMembersInfo();

                    }
                    else{
                        System.out.println("다시 입력해주세요. ");
                    }

                }else{
                    System.out.println("비밀번호가 일치하지 않습니다.");
                }

            }else if(select == 7){
                memberService.giveUpPcroomMember();

            }
            else{
                System.out.println("다시 입력해주세요.");
            }
        }

    }
}
