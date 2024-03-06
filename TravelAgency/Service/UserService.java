package springstart.TravelAgency.Service;

import springstart.TravelAgency.Repository.UserRepo;

public class UserService {

    UserRepo userRepo;

    public UserService(){
        userRepo = new UserRepo();
    }

    public void makeNewAccount(String userID, String pwd, String name, String phoneNumber, String email){ //회원가입
        userRepo.insertNewUser(userID, pwd, name, phoneNumber, email);
    }
    public int bringUserDBid(String userID){ //사용자 dbid 가져오기
        return userRepo.selectUserDBid(userID);
    }

    public boolean canbeLogin(String id, String pwd){ //로그인용
        return userRepo.selectUser(id, pwd);
    }
}
