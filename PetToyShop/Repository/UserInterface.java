package springstart.PetToyShop.Repository;

public interface UserInterface {
    void insertNewUser(String id, String pwd, String name);
    int selectUserDBid(String id);
    boolean selectUser(String id, String pwd);
    void insertUserBuyInfo(int dbid, String date, int catfeedmany, int dogfeedmany, int catToiletmany, int dogToiletmany, int dogtoymany, int totalpay);
    boolean selectSameID(String id);
    void insertUserPetKindOf(int dbid, String petKindOf);
    void selectAllParcelAnimals();
    void selectAnyParcelAnimals(String kind);
    void insertParcelUser(int userDBid, int animalDBid);

}
