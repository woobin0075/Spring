package DatePeopleDistribution.Repository;

public class UserRepo {
    private UserInterface repo;

    private static final UserRepo userRepo = new UserRepo();

    private UserRepo(){
        this.repo = new UserRepository();
    }

    public static UserRepo getInstance(){
        return userRepo;
    }

    public void insertNewUser(String id, String pwd, String name, String phoneNumber){
        repo.insertNewUser(id, pwd, name, phoneNumber);
    }

    public int selectUserDBid(String userID){
        return repo.selectUserDBid(userID);
    }

    public void insertBuyInfo(int userdbid, int bacon, int icecream, int potatobean, int totalPay, String date, int point){
        repo.insertBuyInfo(userdbid, bacon, icecream, potatobean, totalPay, date, point);
    }

//    public void updatePoint(int userdbid, int point){
//        repo.updatePoint(userdbid, point);
//    }

    public void selectReceit(String date){
        repo.selectReceit(date);
    }
}
