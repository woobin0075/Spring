package MenuChoiceSystem.Repository;

public interface UserInterface {
    void insertNewUser(String id, String pwd, String name);
    int selectUserDBid(String id);
    void insertBehaveInfo(int userdbid, int beforePocketMoney, int afterPocketMoney, int pizza, int hamburger,
                          int chicken, int pasta, int curry, int lendmoney, int borrowmoney);
    void updateBehaveInfo(int userdbid, int afterPocketMoney, int pizza, int hamburger,
                          int chicken, int pasta, int curry, int lendmoney, int borrowmoney);
    void selectUsersBehave();
}
