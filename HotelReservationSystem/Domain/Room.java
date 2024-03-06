package HotelReservationSystem.Domain;
import lombok.Data;

@Data
public class Room {
    int roomID;
    int roomNumber;
    int roomType;
    int maxPeople;
    int price;
    boolean isrequire;

}
