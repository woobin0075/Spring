package RestaurantReservationSystem.Repository;

import RestaurantReservationSystem.Domain.Member;

public interface MemberInterface {
    void insertNewMember(Member member);
    boolean selectMember(Member member);


}
