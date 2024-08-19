package work.worklist.domain.work;
import java.util.*;
public class Player {
    public String nickname; //게임할 때 쓸 닉네임
    int energyPool = 0; //여기선 hp라고 정하기
    public ArrayList<City> cities = new ArrayList<>();
    public ArrayList<String> alliances = new ArrayList<>(); //동맹한 플레이어 목록
    public Map<String, Integer> rentsMap = new HashMap<>(); //key : 대출해준 플레이어 닉네임, value : 빌린 에너지 량
    public int turn = 0;
    public int penalty = 0;
    public int population = 0; // 총 인구수
    public int energyBuildingsNum = 1; //총 에너지 발전소 수
    public int sunshineEnergyPlantsNum = 1; //처음엔 태양광 하나로 시작하게 하기
    public int fossilFuelEnergyPlantsNum = 0;
    public int windEnergyPlantsNum = 0;
    public int waterEnergyPlantsNum = 0;
    public int totalEnvironmentEffect = 3;//발전소들 통해 나온 환경 영향 지표
    public int commercialCentersNum = 0; //상업센터 수

    int tax = 0; //세금

    int cannotRequestAlliance = 0;
    //public boolean allianceRequest = false; //요청전에는 false, 요청 성공하면 true

    public int getCannotRequestAlliance(){
        return cannotRequestAlliance;
    }

    public void setCannotRequestAlliance(int s){
        cannotRequestAlliance = s;
    }

    public int getTax(){
        return tax;
    }

    public void setTax(int tax){
        this.tax = tax;
    }

    public int getEnergyPool(){
        return energyPool;
    }

    public void setEnergyPool(int e){
        energyPool = e;
    }

    public void calcTotalEnergyBuildings(){
        energyBuildingsNum = sunshineEnergyPlantsNum + fossilFuelEnergyPlantsNum +
                windEnergyPlantsNum + waterEnergyPlantsNum;
    }

    public void calcPopulation(){
        int p = 0;

        for(int i=0; i< cities.size(); i++){
            for(int j=0; j<cities.get(i).zones.length; j++){
                p += cities.get(i).zones[j].getPersonNum();
            }
        }
        population = p;
    }

//    public void calcEnergyBuildingsNum(){
//        int e = 0;
//        for(int i=0; i< cities.size(); i++){
//            for(int j=0; j<cities.get(i).zones.length; j++){
//                e += cities.get(i).zones[j].getEnergyBuildings();
//            }
//        }
//        energyBuildingsNum = e;
//    }

    public void addCity(){
        cities.add(new City());
        //System.out.println("도시 하나가 추가되었습니다.");
    }

    public void addAlliance(String name){
        alliances.add(name);
    }

    public void saveEnergy(int e){
        energyPool += e;
        //System.out.println("에너지 풀 : "+energyPool);
    }

    public void useEnergy(int e){

        energyPool -= e;

    }

    public void lendEnergy(String name, int energy){
        rentsMap.put(name, energy);
    }

//    public int allZonesProduceEnergy(){
//        int total = 0;
//
//        if(cities.size() > 0){
//
//            for(int i=0; i< cities.size(); i++){
//
//                for(int j=0; j<cities.get(i).zones.length; j++){
//                    cities.get(i).zones[i].producingEnergy();
//                    total += cities.get(i).zones[i].produceEnergy;
//                }
//            }
//        }
//
//        return total;
//    }

//    public int allZonesSpendEnergy(){
//        int total = 0;
//
//        if(cities.size() > 0){
//            for(int i=0; i< cities.size(); i++){
//
//                for(int j=0; j<cities.get(i).zones.length; j++){
//                    cities.get(i).zones[i].spendingEnergy();
//                    total += cities.get(i).zones[i].spendEnergy;
//                }
//            }
//        }
//
//        return total;
//    }

    public void inputCannotRequestAlliance(){
        cannotRequestAlliance = turn + 5;
    }


//    public void printPlayerState(){ //플레이어 현재 상태 출력
//        System.out.println("도시 수 : "+cities.size());
//
//        if(cities.size() > 0){
//            System.out.println("에너지 풀 : "+energyPool);
//            System.out.println("페널티 : "+penalty);
//
//            for(int i=0; i< cities.size(); i++){
//                System.out.println("도시 "+(i+1));
//                for(int j=0; j<cities.get(i).zones.length; j++){
//                    System.out.println("구역 "+(j+1));
//                    System.out.println("  사람 수 : "+cities.get(i).zones[j].personNum);
//                    System.out.println("  에너지 빌딩 수 : "+cities.get(i).zones[j].energyBuildings);
//                }
//                System.out.println();
//            }
//
//            if(alliances.size() > 0){
//                System.out.println("동맹한 플레이어");
//                for(int i=0; i<alliances.size(); i++){
//                    System.out.printf(alliances.get(i)+"  ");
//                }
//            }
//            System.out.println();
//            System.out.println();
//        }
//    }

}
