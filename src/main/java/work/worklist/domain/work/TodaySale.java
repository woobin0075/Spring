package work.worklist.domain.work;

import lombok.Data;

@Data
public class TodaySale {

    public int year;
    public int month;
    public int day;
    public int totalSale;

    public TodaySale(int year, int month, int day, int totalSale){
        this.year = year;
        this.month = month;
        this.day = day;
        this.totalSale = totalSale;
    }
}
