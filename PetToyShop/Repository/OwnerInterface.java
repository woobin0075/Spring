package springstart.PetToyShop.Repository;

public interface OwnerInterface {

    int selectOwnerLogin(String id, String pwd);
    void insertStartTodaySale(String date);
    void updateTodaySale(String date, int catfeed, int dogfeed, int catToilet, int dogToilet, int dogtoy);
    void selectTodaySalesRecord(String date);
    void selectAllSalesRecord();
    void insertParcelAnimal(String animalKind, String specificKind, String name, int age, String sex, int sellMoney);
    int selectParcelAnimalDBid(String animalKind, String specificKind, String name, int age, String sex, int sellMoney);
    void updateCompleteParcelAnimal(int animalDBid);
}
