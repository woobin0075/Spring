package PCroom.Service;

import PCroom.Repository.SuperVisorRepo;
import PCroom.Repository.SuperVisorRepository;

import java.util.Scanner;

public class SuperVisorService {

    SuperVisorRepo superVisorRepo;

    public SuperVisorService(){
        superVisorRepo = new SuperVisorRepo();
    }

    public void printMemberList(){

        superVisorRepo.bringMemberUsingTimeInfo();
        superVisorRepo.bringGuestUsingTimeInfo();

    }

    public void deleteMemberService(){
        Scanner scStr = new Scanner(System.in);
        String inputMemberName, inputMemberID;

        System.out.printf("탈퇴할 회원의 이름을 입력하세요. : ");
        inputMemberName = scStr.nextLine();

        System.out.printf("탈퇴할 회원의 아이디를 입력하세요. : ");
        inputMemberID = scStr.nextLine();

        superVisorRepo.deleteMember(inputMemberName, inputMemberID);
        superVisorRepo.insertDeletedMember(inputMemberName, inputMemberID);

    }

    public void printMembersInfo(){
        int select;
        String inputName;
        Scanner scStr = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);

        System.out.printf("1.모든 회원 정보 출력, 2.이름 검색 출력 => ");
        select = sc.nextInt();

        if(select == 1){

            superVisorRepo.selectAllMember();

        }else if(select == 2){

            System.out.printf("이름을 입력하세요. : ");
            inputName = scStr.nextLine();

            superVisorRepo.findByName(inputName);

        }else{
            System.out.println("다시 입력하세요.");
        }
    }
}
