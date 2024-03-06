package MenuChoiceSystem.Repository;

public class UserRepo {

    private UserInterface repo;
    private static UserRepo userRepo;

    private UserRepo(UserInterface userInterface){
        repo = userInterface;
    }

    public static UserRepo getInstance(){
        if(userRepo == null){
            UserRepository userRepository = new UserRepository();
            userRepo = new UserRepo(userRepository);
        }

        return userRepo;
    }

    public void insertNewUser(String id, String pwd, String name){ //회원가입용
        repo.insertNewUser(id, pwd, name);
    }

    public int selectUserDBid(String id){
        return repo.selectUserDBid(id);
    }

    public void insertBehaveInfo(int userdbid, int beforePocketMoney, int afterPocketMoney, int pizza, int hamburger,
                                 int chicken, int pasta, int curry, int lendmoney, int borrowmoney){
        repo.insertBehaveInfo(userdbid, beforePocketMoney, afterPocketMoney, pizza, hamburger, chicken, pasta, curry, lendmoney, borrowmoney);
    }

    public void updateBehaveInfo(int userdbid, int afterPocketMoney, int pizza, int hamburger,
                                 int chicken, int pasta, int curry, int lendmoney, int borrowmoney){
        repo.updateBehaveInfo(userdbid, afterPocketMoney, pizza, hamburger, chicken, pasta, curry, lendmoney, borrowmoney);
    }

    public void selectUsersBehave(){
        repo.selectUsersBehave();
    }
}
