package OrderIngredients.Repository;

public interface MasterInterface {
    void insertNewMaster(String name, String pwd, String phoneNumber);
    boolean selectMaster(String name, String pwd, String phoneNumber);
    int selectMasterID(String name, String pwd, String phoneNumber);
    void insertNewOrder(String date, int id, int id2);
    void deleteMyOrder(String date, int id, int id2);
    void selectMyOrders(String todayDate, int id);
    void selectBeforeMyOrders(String todayDate, int masterID);
    void selectFood(int id);
}
