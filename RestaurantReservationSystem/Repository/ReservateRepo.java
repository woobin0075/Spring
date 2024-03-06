package RestaurantReservationSystem.Repository;

import RestaurantReservationSystem.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservateRepo {
    ReservateInterface reservateInterface;

    @Autowired
    public ReservateRepo(ReservateInterface memberRepository){
        reservateInterface = memberRepository;
    }

    public void insertReservationInfo(String usingdate, int memberID, String visitTime){
        reservateInterface.insertReservationInfo(usingdate, memberID, visitTime);
    }

    public int selectLoginMemberID(Member member){
        return reservateInterface.selectLoginMemberID(member);
    }
}
