package PCroom.Repository;

import PCroom.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberRepo {
    MemberInterface memberRepo;

    @Autowired
    public MemberRepo(){
        memberRepo = new MemberRepository();
    }

    public void insertNewMember(Member member, int tableID){
        memberRepo.insertNewMember(member, tableID);
    }

    public boolean memberLogin(String inputID, String inputPwd){
        return memberRepo.memberLogin(inputID, inputPwd);
    }

    public String bringMemberName(String inputID){
        return memberRepo.bringMemberName(inputID);
    }

    public void insertUsingPcroomTime(String visitDate, Member member){
        memberRepo.insertUsingPcroomTime(visitDate, member);
    }

    public int calcTotalUsingPcroom(Member member){
        return memberRepo.calcTotalUsingPcroom(member);
    }

    public boolean calcTotalUsingPcroomForVIP(Member member){
        return memberRepo.calcTotalUsingPcroomForVIP(member);
    }

    public void updateMemberUsingTime(Member member, int bonus){
        memberRepo.updateMemberUsingTime(member, bonus);
    }

    public void selectMyID(String name, String phoneNumber){
        memberRepo.selectMyID(name, phoneNumber);
    }

    public void updateMyPwd(String id, String pwd){
        memberRepo.updateMyPwd(id, pwd);
    }

    public void deleteMember(String name, String id){
        memberRepo.deleteMember(name, id);
    }
}
