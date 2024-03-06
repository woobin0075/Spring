package Delivery.Repository;

import Delivery.Domain.Menu;

import java.util.ArrayList;

public interface MasterInterface {
    void insertMaster(String restaurantName, String pwd);
    void insertZeroAssests(int master_id);
    public void selectAllUsers();
    void insertFoodsAndDrinks(int masterID, String food, int price);
    void deleteMenu(String food);
    ArrayList<Menu> selectMenusByID(int masterID);
    int findMasterID(String restaurantName);
    int selectAssests(int masterID);
    void updateAssests(int masterID, int money);
    boolean selectMaster(String restaurantName, String pwd);
    void insertNewBranch(int masterID, String branchname, int chefnum, int deliverymannum);
    ArrayList<Menu> selectRandomOrderMenu(int masterID);
    int selectBranchID(String branchname);
    void insertNewBranchSale(int branchID, String todaydate);
    int selectRandomBranch(int masterID);
    int selectBranchTotalSale(int branchID);
    void updateBranchTotalSale(int branchID, int total);
    int selectTodayDeliveryOrdersNum(String todaydate, int masterID);
}
