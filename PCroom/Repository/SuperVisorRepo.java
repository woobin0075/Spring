package PCroom.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuperVisorRepo {
    SuperVisorInterface superVisorRepo;

    @Autowired
    public SuperVisorRepo(){
        superVisorRepo = new SuperVisorRepository();
    }

    public void bringMemberUsingTimeInfo(){
        superVisorRepo.bringMemberUsingTimeInfo();
    }

    public void bringGuestUsingTimeInfo(){
        superVisorRepo.bringGuestUsingTimeInfo();
    }

    public void deleteMember(String memberName, String memberID){
        superVisorRepo.deleteMember(memberName, memberID);
    }

    public void insertDeletedMember(String memberName, String memberID){
        superVisorRepo.insertDeletedMember(memberName, memberID);
    }

    public void selectAllMember(){
        superVisorRepo.selectAllMember();
    }

    public void findByName(String name){
        superVisorRepo.findByName(name);
    }
}
