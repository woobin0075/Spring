package springstart.TravelAgency.Repository;

import springstart.TravelAgency.Domain.TravelPackage;

import java.util.ArrayList;

public class TravelAgencyRepo {
    TravelAgencyInterface repo;

    public TravelAgencyRepo(){
        repo = new TravelAgencyRepository();
    }

    public void insertNewPackage(String packageName, int price, String region, int distance, String accommodation, int travelDays,
                                 String guideName, String sendWay){
        repo.insertNewPackage(packageName, price, region, distance, accommodation, travelDays, guideName, sendWay);
    }

    public void selectPackageByName(String packageName){
        repo.selectPackageByName(packageName);
    }

    public ArrayList<TravelPackage> selectAllPackages(){
        return repo.selectAllPackages();
    }

    public int selectPackageDBid(String name){
        return repo.selectPackageDBid(name);
    }

    public void insertNewPostScript(int packageDBid, int customerDBid, int score, String postscript){
        repo.insertNewPostScript(packageDBid, customerDBid, score, postscript);
    }

    public int selectPackagePrice(String name){
        return repo.selectPackagePrice(name);
    }

    public int calcTotalScore(int packageid){
        return repo.calcTotalScore(packageid);
    }

    public void updatePackageAvgScore(int packageid, double avg){
        repo.updatePackageAvgScore(packageid, avg);
    }

    public int calcPostScriptPacackageNum(int packageid){
        return repo.calcPostScriptPacackageNum(packageid);
    }

    public void selectRecommendPackages(int price){
        repo.selectRecommendPackages(price);
    }
}
