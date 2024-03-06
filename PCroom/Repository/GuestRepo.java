package PCroom.Repository;

import PCroom.Domain.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuestRepo {
    GuestInterface guestRepo;

    @Autowired
    public GuestRepo(){
        guestRepo = new GuestRepository();
    }

    public boolean insertGuestInfo(String enterNumber, int table_ID){
        return guestRepo.insertGuestInfo(enterNumber, table_ID);
    }

    public void insertUsingPcroomTime(String visitDate, Guest guest){
        guestRepo.insertUsingPcroomTime(visitDate, guest);
    }
}
