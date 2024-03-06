package Delivery.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepo {
    UserInterface repo;

    @Autowired
    public UserRepo(UserInterface repo){
        this.repo = repo;
    }

    public void insertAccount(String name, String pwd, String phoneNumber, String userID){
        repo.insertAccount(name, pwd, phoneNumber, userID);
    }

    public boolean inspectSameID(String id){
        return repo.selectSameID(id);
    }

    public boolean selectAccount(String id, String pwd){
        return repo.selectAccount(id, pwd);
    }

    public int findTableID(String id, String pwd){
        return repo.findTableID(id, pwd);
    }

    public void insertOrder(String date, int userID, int masterID, String food, int price){
        repo.insertOrder(date, userID, masterID, food, price);
    }

    public void insertPay(String date, int userID, int totalCost, String pay){
        repo.insertPay(date, userID, totalCost, pay);
    }

    public void insertSatisfaction(int masterID, int userID, int stars){
        repo.insertSatisfaction(masterID, userID, stars);
    }
}
