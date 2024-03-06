package Delivery.Repository;

public interface UserInterface {
    void insertAccount(String name, String pwd, String phoneNumber, String userID);
    boolean selectSameID(String id);
    boolean selectAccount(String id, String pwd);
    int findTableID(String id, String pwd);
    void insertOrder(String date, int userID, int masterID, String food, int price);
    void insertPay(String date, int userID, int totalCost, String pay);
    void insertSatisfaction(int masterID, int userID, int stars);
}
