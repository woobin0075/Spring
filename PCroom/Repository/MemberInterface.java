package PCroom.Repository;

import PCroom.Domain.Member;

public interface MemberInterface {
    void insertNewMember(Member member, int tableID);
    boolean memberLogin(String inputID, String inputPwd);
    String bringMemberName(String inputID);
    void insertUsingPcroomTime(String visitDate, Member member);
    int calcTotalUsingPcroom(Member member);
    boolean calcTotalUsingPcroomForVIP(Member member);
    void updateMemberUsingTime(Member member, int bonus);
    void selectMyID(String name, String phoneNumber);
    void updateMyPwd(String id, String pwd);
    void deleteMember(String name, String id);
}
