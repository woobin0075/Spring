package springstart.PetToyShop.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerRepo {
    OwnerInterface face;
    private static OwnerRepo repo;

    @Autowired
    private OwnerRepo(OwnerRepository ownerRepository){
        face = ownerRepository;
    }

    public static OwnerRepo getOwnerRepo(){
        if(repo == null){
            OwnerRepository repository = new OwnerRepository();
            repo = new OwnerRepo(repository);
        }

        return repo;
    }

    public int selectOwnerLogin(String id, String pwd){
        return face.selectOwnerLogin(id, pwd);
    }

    public void insertStartTodaySale(String date){
        face.insertStartTodaySale(date);
    }

    public void updateTodaySale(String date, int catfeed, int dogfeed, int catToilet, int dogToilet, int dogtoy){
        face.updateTodaySale(date, catfeed, dogfeed, catToilet, dogToilet, dogtoy);
    }

    public void selectTodaySalesRecord(String date){
        face.selectTodaySalesRecord(date);
    }

    public void selectAllSalesRecord(){
        face.selectAllSalesRecord();
    }

    public void insertParcelAnimal(String animalKind, String specificKind, String name, int age, String sex, int sellMoney){
        face.insertParcelAnimal(animalKind, specificKind, name, age, sex, sellMoney);
    }

    public int selectParcelAnimalDBid(String animalKind, String specificKind, String name, int age, String sex, int sellMoney){
        return face.selectParcelAnimalDBid(animalKind, specificKind, name, age, sex, sellMoney);
    }

    public void updateCompleteParcelAnimal(int animalDBid){
        face.updateCompleteParcelAnimal(animalDBid);
    }
}
