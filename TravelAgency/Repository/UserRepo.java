package springstart.TravelAgency.Repository;

public class UserRepo {
    UserInterface repo;

    public UserRepo(){
        repo = new UserRepository();
    }

    public void insertNewUser(String userID, String pwd, String name, String phoneNumber, String email){
        repo.insertNewUser(userID, pwd, name, phoneNumber, email);
    }

    public int selectUserDBid(String userID){
        return repo.selectUserDBid(userID);
    }

    public boolean selectUser(String userID, String pwd){
        return repo.selectUser(userID, pwd);
    }
}
