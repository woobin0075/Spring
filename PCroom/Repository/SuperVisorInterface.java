package PCroom.Repository;

public interface SuperVisorInterface {
    void bringMemberUsingTimeInfo();
    void bringGuestUsingTimeInfo();
    void deleteMember(String memberName, String memberID);
    void insertDeletedMember(String memberName, String memberID);
    void selectAllMember();
    void findByName(String name);
}
