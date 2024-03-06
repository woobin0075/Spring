package RestaurantReservationSystem.Repository;

import RestaurantReservationSystem.Domain.Member;

public interface ReservateInterface {
    void insertReservationInfo(String usingdate, int memberID, String visitTime);
    int selectLoginMemberID(Member member);
}
