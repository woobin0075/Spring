package springstart.TravelAgency.Repository;

import springstart.TravelAgency.Domain.TravelPackage;
import java.util.ArrayList;

public interface TravelAgencyInterface {
    void insertNewPackage(String packageName, int price, String region, int distance, String accommodation, int travelDays,
                          String guideName, String sendWay);
    void selectPackageByName(String packageName);
    ArrayList<TravelPackage> selectAllPackages();
    void insertNewPostScript(int packageDBid, int customerDBid, int score, String postscript);
    int selectPackageDBid(String name);
    int selectPackagePrice(String name);
    void updatePackageAvgScore(int packageid, double avg);
    int calcTotalScore(int packageid);
    int calcPostScriptPacackageNum(int packageid);
    void selectRecommendPackages(int price);
}
