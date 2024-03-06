package springstart.TravelAgency.Service;

import springstart.TravelAgency.Domain.TravelPackage;
import springstart.TravelAgency.Repository.TravelAgencyRepo;
import springstart.TravelAgency.Repository.TravelAgencyRepository;

import java.util.ArrayList;

public class TravelAgency { //여행사는 하나니깐 싱글톤으로 구현

    TravelAgencyRepo travelAgencyRepo;
    private static TravelAgency travelAgency;

    private TravelAgency(){
        this.travelAgencyRepo = new TravelAgencyRepo();
    }

    public static TravelAgency getInstance(){
        if(travelAgency == null){
            travelAgency = new TravelAgency();
        }

        return travelAgency;
    }

    public void addNewPackage(TravelPackage travelPackage){ //새 패키지 상품 추가
        travelAgencyRepo.insertNewPackage(travelPackage.getPackageName(), travelPackage.getPrice(), travelPackage.getRegion(),
                travelPackage.getDistance(), travelPackage.getAccommodation(), travelPackage.getTravelDays(), travelPackage.getGuideName(),
                travelPackage.getSendWay());
    }

    public void searchPackageByName(String packageName){ //패키지 이름 검색
        travelAgencyRepo.selectPackageByName(packageName);
    }

    public ArrayList<TravelPackage> bringAllPackages(){ //정렬할때 쓸 모든 패키지 가져오기
        return travelAgencyRepo.selectAllPackages();
    }

    public int bringPackageDBid(String name){ //패키지 상품 등록 db id 가져오기
        return travelAgencyRepo.selectPackageDBid(name);
    }

    public void addNewPostScript(int packageDBid, int customerDBid, int score, String postscript){
        travelAgencyRepo.insertNewPostScript(packageDBid, customerDBid, score, postscript); //상품 후기 등록
    }

    public int bringPackagePrice(String name){ //상품 가격 가져오기
        return travelAgencyRepo.selectPackagePrice(name);
    }

    public void inputAvgScore(int packageid, double avg){ //평점 업데이트
        travelAgencyRepo.updatePackageAvgScore(packageid, avg);
    }

    public int calcTotalScore(int packageid){ //평점 구하기 위한 전체 후기 점수 합계
        return travelAgencyRepo.calcTotalScore(packageid);
    }

    public int calcPostScriptPacackageNum(int packageid){ //특정 패키지 후기 수 구하기
        return travelAgencyRepo.calcPostScriptPacackageNum(packageid);
    }

    public void printRecommendPackages(int price){ //price 이내 가격 추천 해서 보여주기
        travelAgencyRepo.selectRecommendPackages(price);
    }
}
