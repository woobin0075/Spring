package PCroom.Service;

import PCroom.Domain.Guest;
import PCroom.Repository.GuestRepo;
import PCroom.Repository.GuestRepository;
import PCroom.Repository.MemberRepository;

import java.time.LocalDateTime;

public class GuestService {

    GuestRepo guestRepo;

    public GuestService(){
        guestRepo = new GuestRepo();
    }

    public boolean guestLogin(String number, int tableID){
        return guestRepo.insertGuestInfo(number, tableID);
    }

    public void usingPcroomService(Guest guest, String visitDate){

        guestRepo.insertUsingPcroomTime(visitDate, guest);

    }

    public boolean guestLoginSituation(Guest guest, LocalDateTime startTime){
        int fare;

        guest.measureUsingTime(startTime);
        fare = guest.getUsingTime() * 1000;

        usingPcroomService(guest, visitDatetoString(startTime));
        System.out.println("이용 요금 "+fare+"원이 부과되었습니다.");

        return false;
    }

    private String visitDatetoString(LocalDateTime time){
        StringBuilder stringBuilder = new StringBuilder();
        int year = time.getYear();
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();

        stringBuilder.append(year);
        stringBuilder.append(month);
        stringBuilder.append(day);

        return stringBuilder.toString();
    }
}
