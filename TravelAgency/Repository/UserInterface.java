package springstart.TravelAgency.Repository;

public interface UserInterface {
    void insertNewUser(String userID, String pwd, String name, String phoneNumber, String email);
    int selectUserDBid(String userID);
    boolean selectUser(String userID, String pwd);
}
