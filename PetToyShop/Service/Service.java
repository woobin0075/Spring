package springstart.PetToyShop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springstart.PetToyShop.Repository.OwnerRepo;
import springstart.PetToyShop.Repository.UserRepo;

@Component
public class Service {
    UserRepo userRepo;
    OwnerRepo ownerRepo;

    @Autowired
    public Service(UserRepo userRepo, OwnerRepo ownerRepo){
        this.userRepo = userRepo;
        this.ownerRepo = ownerRepo;
    }

    public void makeNewAccount(String id, String pwd, String name){ //회원가입
        userRepo.insertNewUser(id, pwd, name);
    }

    public int bringUserDBid(String id){ //DB id확인겸, 중복 아이디 확인용
        return userRepo.selectUserDBid(id);
    }

    public boolean isheUser(String id, String pwd){
        return userRepo.selectUser(id, pwd);
    }

    public void addUserBuyInfo(int dbid, String date, int catfeedmany, int dogfeedmany, int catToiletmany, int dogToiletmany, int dogtoymany, int totalpay){
        userRepo.insertUserBuyInfo(dbid, date, catfeedmany, dogfeedmany, catToiletmany, dogToiletmany, dogtoymany, totalpay);
    }

    public boolean isThereSameID(String id){
        return userRepo.selectSameID(id);
    }

    public void addUserPet(int dbid, String petKindOf){
        userRepo.insertUserPetKindOf(dbid, petKindOf);
    }

    public int ownerLogin(String ownerid, String pwd){
        return ownerRepo.selectOwnerLogin(ownerid, pwd);
    }

    public void startTodaySale(String date){
        ownerRepo.insertStartTodaySale(date);
    }

    public void todaySaleRecord(String date, int catfeed, int dogfeed, int catToilet, int dogToilet, int dogtoy){
        ownerRepo.updateTodaySale(date, catfeed, dogfeed, catToilet, dogToilet, dogtoy);
    }

    public void printTodaySales(String date){
        ownerRepo.selectTodaySalesRecord(date);
    }

    public void printAllSales(){
        ownerRepo.selectAllSalesRecord();
    }

    public void addParcelAnimal(String animalKind, String specificKind, String name, int age, String sex, int sellMoney){
        ownerRepo.insertParcelAnimal(animalKind, specificKind, name, age, sex, sellMoney);
    }

    public void printAllParcelAnimals(){
        userRepo.selectAllParcelAnimals();
    }

    public void printAnyParcelAnimals(String kind){
        userRepo.selectAnyParcelAnimals(kind);
    }

    public int bringAnimalDBid(String animalKind, String specificKind, String name, int age, String sex, int sellMoney){
        return ownerRepo.selectParcelAnimalDBid(animalKind, specificKind, name, age, sex, sellMoney);
    }

    public void completeParcelAnimal(int userDBid, int animalDBid){
        userRepo.insertParcelUser(userDBid, animalDBid);
    }

    public void completeParcel(int id){
        ownerRepo.updateCompleteParcelAnimal(id);
    }
}
