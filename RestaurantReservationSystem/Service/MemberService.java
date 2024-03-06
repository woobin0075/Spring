package RestaurantReservationSystem.Service;

import RestaurantReservationSystem.Domain.Member;
import RestaurantReservationSystem.Domain.Restaurant;
import RestaurantReservationSystem.Repository.MemberRepo;
import RestaurantReservationSystem.Repository.ReservateRepo;
import org.springframework.stereotype.Component;

@Component
public class MemberService {

    private static MemberService service;

    private MemberService(){

    }

    public static MemberService getService(){
        if(service == null){
            service = new MemberService();
        }
        return service;
    }

    public void makeAccountService(MemberRepo memberRepository, Member member){
        boolean isMember = memberRepository.selectMember(member);

        if(!isMember){ //회원 가입 가능

            memberRepository.insertNewMember(member);

        }else{ //이미 존재하는 회원이라서 불가
            System.out.println("이미 존재하는 계정입니다.");
        }
    }

    public boolean canLoginService(MemberRepo memberRepository, Member member){
        boolean login = memberRepository.selectMember(member);

        if(login){
            System.out.println("로그인에 성공하셨습니다.");
            return true;
        }else{
            System.out.println("존재하지 않는 계정입니다.");
        }

        return false;
    }

    public void reservationService(ReservateRepo memberRepository, Member member, Restaurant restaurant){
        int memberID = memberRepository.selectLoginMemberID(member);

        memberRepository.insertReservationInfo(restaurant.getUsingdate(), memberID, restaurant.getVisitTime());
    }

    public int reducePrice(int pay){
        System.out.println("예약자에 한해서 10%할인을 할인해드립니다.");

        return (int)(pay*0.9);
    }
}
