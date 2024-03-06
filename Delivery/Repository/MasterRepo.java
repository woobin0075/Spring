package Delivery.Repository;

import Delivery.Domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MasterRepo {
    MasterInterface repo;

    @Autowired
    public MasterRepo(MasterInterface repo){
        this.repo = repo;
    }

    public void insertMaster(String restaurantName, String pwd){
        repo.insertMaster(restaurantName, pwd);
    }

    public boolean masterLogin(String restaurantName, String pwd){
        return repo.selectMaster(restaurantName, pwd);
    }

    public void printAllUsers(){
        repo.selectAllUsers();
    }

    public void insertZeroAssests(int master_id){
        repo.insertZeroAssests(master_id);
    }

    public void insertFoodsAndDrinks(int masterID, String food, int price){
        repo.insertFoodsAndDrinks(masterID, food, price);
    }

    public void deleteMenu(String food){
        repo.deleteMenu(food);
    }

    public ArrayList<Menu> selectMenusByID(int masterID){
        return repo.selectMenusByID(masterID);
    }

    public int findMasterID(String restaurantName){
        return repo.findMasterID(restaurantName);
    }

    public int selectAssests(int masterID){
        return repo.selectAssests(masterID);
    }

    public void updateAssests(int masterID, int money){
        repo.updateAssests(masterID, money);
    }

    public void insertNewBranch(int masterID, String branchname, int chefnum, int deliverymannum){
        repo.insertNewBranch(masterID, branchname, chefnum, deliverymannum);
    }

    public ArrayList<Menu> selectRandomOrderMenu(int masterID){
        return repo.selectRandomOrderMenu(masterID);
    }

    public int selectBranchID(String branchname){
        return repo.selectBranchID(branchname);
    }

    public void insertNewBranchSale(int branchID, String todaydate){
        repo.insertNewBranchSale(branchID, todaydate);
    }

    public int selectRandomBranch(int masterID){
        return repo.selectRandomBranch(masterID);
    }

    public int selectBranchTotalSale(int branchID){
        return repo.selectBranchTotalSale(branchID);
    }

    public void updateBranchTotalSale(int branchID, int total){
        repo.updateBranchTotalSale(branchID, total);
    }

    public int selectTodayDeliveryOrdersNum(String todaydate, int masterID){
        return repo.selectTodayDeliveryOrdersNum(todaydate, masterID);
    }
}
