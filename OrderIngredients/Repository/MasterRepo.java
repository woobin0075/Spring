package OrderIngredients.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MasterRepo {
    MasterInterface repo;
    private static MasterRepo masterRepo;

    @Autowired
    public MasterRepo(MasterInterface repo){
        this.repo = repo;
    }

    public static MasterRepo getInstance(){
        if(masterRepo == null){
            MasterRepository masterRepository = new MasterRepository();
            masterRepo = new MasterRepo(masterRepository);
        }

        return masterRepo;
    }

    public void insertNewMaster(String name, String pwd, String phoneNumber){ //회원가입용
        repo.insertNewMaster(name, pwd, phoneNumber);
    }

    public boolean selectMaster(String name, String pwd, String phoneNumber){
        return repo.selectMaster(name, pwd, phoneNumber);
    }
    public int selectMasterID(String name, String pwd, String phoneNumber){
        return repo.selectMasterID(name, pwd, phoneNumber);
    }

    public void insertNewOrder(String date, int factoryOrderid, int masterId){
        repo.insertNewOrder(date, factoryOrderid, masterId);
    }

    public void deleteMyOrder(String date, int id, int masterid){
        repo.deleteMyOrder(date, id, masterid);
    }

    public void selectMyOrders(String todayDate, int id){
        repo.selectMyOrders(todayDate, id);
    }

    public void selectBeforeMyOrders(String todayDate, int masterID){
        repo.selectBeforeMyOrders(todayDate, masterID);
    }

    public void selectFood(int id){
        repo.selectFood(id);
    }
}
