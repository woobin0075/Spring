package PCroom.Service;

import PCroom.Domain.Member;
import PCroom.Repository.*;

import java.time.LocalDateTime;
import java.util.Scanner;


public class MemberService {

    MemberRepo memberRepo;

    public MemberService(){
        memberRepo = new MemberRepo();
    }

    public void createMemberSerice(Member member, int tableID){
        member.createMember();
        memberRepo.insertNewMember(member, tableID);
    }

    public boolean memberLoginService(String inputID, String inputPwd){

        return memberRepo.memberLogin(inputID, inputPwd);
    }

    public void usingPcroomService(Member member, String visitDate){

        memberRepo.insertUsingPcroomTime(visitDate, member);

    }

    public String bringMemberNameService(String inputID){

        return memberRepo.bringMemberName(inputID);
    }

    public int giveBonousService(Member member){

        return memberRepo.calcTotalUsingPcroom(member);
    }

    public boolean isHeVIP(Member member){
        return memberRepo.calcTotalUsingPcroomForVIP(member);
    }

    public void updateMemberUsingTimeService(Member member, int bonous){
        memberRepo.updateMemberUsingTime(member, bonous);
    }

    public boolean memberLoginSituation(Member nowMember, LocalDateTime startTime){
        int bonusTime;
        final int vipBonusTime = 1;
        int fare;

        nowMember.measureUsingTime(startTime);
        usingPcroomService(nowMember, visitDatetoString(startTime));
        fare = nowMember.getUsingTime() * 1000;

        //누적 사용시간이 5의 배수만큼 되는지 확인하기
        bonusTime = giveBonousService(nowMember);

        if(bonusTime > 0){ //보너스가 주어졌다면
            updateMemberUsingTimeService(nowMember, bonusTime);
        }

        //VIP시 보너스 시간 추가
        if(nowMember.isVIP()){
            updateMemberUsingTimeService(nowMember, vipBonusTime);
        }

        System.out.println("이용 요금 "+fare+"원이 부과되었습니다.");

        return false;
    }

    public void loginAfter(Member nowMember, LocalDateTime startTime, String inputID, String inputPwd){

        String name;
        final int vipBonusTime = 1;
        nowMember.setID(inputID);
        nowMember.setPwd(inputPwd);

        name = bringMemberNameService(inputID); //이름 정보 가져오기
        nowMember.setName(name);
        nowMember.setVIP(isHeVIP(nowMember)); //로그인후 VIP인지 확인

        if(nowMember.isVIP()){
            System.out.println(nowMember.getName()+" VIP님 환영합니다.");
            System.out.println("보너스로 "+vipBonusTime+"시간을 더 드립니다.");
        }
    }

    public void findMyID(){
        Scanner scStr = new Scanner(System.in);
        String inputName, inputPhoneNumber;

        System.out.printf("이름을 입력해주세요. : ");
        inputName = scStr.nextLine();
        System.out.printf("핸드폰 번호를 입력해주세요 : ");
        inputPhoneNumber = scStr.nextLine();

        memberRepo.selectMyID(inputName, inputPhoneNumber);
    }

    public void changeMyPwd(){
        Scanner scStr = new Scanner(System.in);
        String inputId, changePwd;

        System.out.printf("ID를 입력하세요. : ");
        inputId = scStr.nextLine();
        System.out.printf("변경할 비밀번호를 입력하세요. : ");
        changePwd = scStr.nextLine();

        memberRepo.updateMyPwd(inputId, changePwd);
    }

    public void giveUpPcroomMember(){
        Scanner scStr = new Scanner(System.in);
        String inputId, inputName;

        System.out.printf("이름을 입력하세요. : ");
        inputName = scStr.nextLine();
        System.out.printf("아이디를 입력하세요. : ");
        inputId = scStr.nextLine();

        memberRepo.deleteMember(inputName, inputId);
    }

    private String visitDatetoString(LocalDateTime time){
        StringBuilder stringBuilder = new StringBuilder();
        int year = time.getYear();
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();

        stringBuilder.append(year);
        stringBuilder.append(month);
        stringBuilder.append(day);

        return stringBuilder.toString();
    }
}
