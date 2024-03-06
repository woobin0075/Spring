package RestaurantReservationSystem.Repository;

import RestaurantReservationSystem.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberRepo {
    MemberInterface memberInterface;

    @Autowired
    public MemberRepo(MemberInterface memberRepository){
        memberInterface = memberRepository;
    }

    public void insertNewMember(Member member){
        memberInterface.insertNewMember(member);
    }

    public boolean selectMember(Member member){
        return memberInterface.selectMember(member);
    }


}
