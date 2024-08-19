package work.worklist.domain.work;

public class Travellike {

    int maxPrice;
    int maxDays;
    String region;

    public Travellike(int maxPrice, int maxDays, String region){
        this.maxPrice = maxPrice;
        this.maxDays = maxDays;
        this.region = region;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public String getRegion() {
        return region;
    }
}
