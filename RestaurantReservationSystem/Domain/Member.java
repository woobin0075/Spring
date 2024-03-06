package RestaurantReservationSystem.Domain;
import lombok.Data;

@Data
public class Member {
    String name;
    int old;
    String phoneNumber;
    int money;

    public Member(String name, int old, String phoneNumber, int money){
        this.name = name;
        this.old = old;
        this.phoneNumber = phoneNumber;
        this.money = money;
    }
}
