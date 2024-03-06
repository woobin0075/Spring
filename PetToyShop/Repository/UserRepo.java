package springstart.PetToyShop.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepo {
    UserInterface repo;

    @Autowired
    public UserRepo(UserRepository userRepository){
        repo = userRepository;
    }

    public void insertNewUser(String id, String pwd, String name){
        repo.insertNewUser(id, pwd, name);
    }

    public int selectUserDBid(String id){
       return repo.selectUserDBid(id);
    }

    public boolean selectUser(String id, String pwd){
        return repo.selectUser(id, pwd);
    }

    public void insertUserBuyInfo(int dbid, String date, int catfeedmany, int dogfeedmany, int catToiletmany, int dogToiletmany, int dogtoymany, int totalpay){
        repo.insertUserBuyInfo(dbid, date, catfeedmany, dogfeedmany, catToiletmany, dogToiletmany, dogtoymany, totalpay);
    }

    public boolean selectSameID(String id){
        return repo.selectSameID(id);
    }

    public void insertUserPetKindOf(int dbid, String petKindOf){
        repo.insertUserPetKindOf(dbid, petKindOf);
    }

    public void selectAllParcelAnimals(){
        repo.selectAllParcelAnimals();;
    }

    public void selectAnyParcelAnimals(String kind){
        repo.selectAnyParcelAnimals(kind);
    }

    public void insertParcelUser(int userDBid, int animalDBid){
        repo.insertParcelUser(userDBid, animalDBid);
    }
}
