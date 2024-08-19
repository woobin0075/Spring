package work.worklist.domain.work;

import lombok.Data;

@Data
public class Travel {

    String packageName;
    int price;
    String region;
    int distance;
    String accomodation;
    int days;
    String guide;
    String sendWay;

    public Travel(String packageName, int price, String region, int distance,
                  String accomodation, int days, String guide, String sendWay){

        this.packageName = packageName;
        this.price = price;
        this.region = region;
        this.distance = distance;
        this.accomodation = accomodation;
        this.days = days;
        this.guide = guide;
        this.sendWay = sendWay;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getPrice() {
        return price;
    }

    public String getRegion() {
        return region;
    }

    public int getDistance() {
        return distance;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public int getDays() {
        return days;
    }

    public String getGuide() {
        return guide;
    }

    public String getSendWay() {
        return sendWay;
    }
}
