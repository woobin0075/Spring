package springstart.TravelAgency.Domain;
import lombok.Data;

@Data
public class TravelPackage {
    String packageName;
    int price;
    String region;
    int distance;
    String accommodation;
    int travelDays;
    String guideName;
    String sendWay;
    double avgScore; //평점용

    public TravelPackage(String packageName, int price, String region, int distance, String accommodation, int travelDays,
                         String guideName, String sendWay){
        this.packageName = packageName;
        this.price = price;
        this.region = region;
        this.distance = distance;
        this.accommodation = accommodation;
        this.travelDays = travelDays;
        this.guideName = guideName;
        this.sendWay = sendWay;
    }
}
