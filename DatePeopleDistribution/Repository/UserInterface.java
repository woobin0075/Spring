package DatePeopleDistribution.Repository;

public interface UserInterface {
    void insertNewUser(String id, String pwd, String name, String phoneNumber);
    int selectUserDBid(String userID);
    void insertBuyInfo(int userdbid, int bacon, int icecream, int potatobean, int totalPay, String date, int point);
    void selectUserInfo(int dbid);
    void selectReceit(String date);
}
