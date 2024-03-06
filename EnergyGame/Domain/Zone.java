package EnergyGame.Domain;

import java.util.Random;
import lombok.Data;

@Data
public class Zone {
    int personNum = 0; //수용되어있는 사람 수, 최대 4명까지 수용 가능
    int produceEnergy; //생산한 에너지 수
    int spendEnergy; //소비한 에너지 수
    int energyBuildings = 0; //에너지 건물 수, 에너지 건물 하나당 에너지 2 자동 생산

    public void plusPersonNum(int num){

        if(personNum + num <= 4){
            personNum += num;
            System.out.println("수용 인원 : "+num);

        }else{
            System.out.println("4명을 넘을 수 없습니다.");
        }

    }

    public void producingEnergy(){
        Random random = new Random();

        if(personNum > 0){
            produceEnergy = random.nextInt(personNum)+1;
        }

        produceEnergy += (energyBuildings)*2;
    }

    public void spendingEnergy(){
        Random random = new Random();

        if(personNum > 0){
            spendEnergy = random.nextInt(personNum) + 1;

            if(energyBuildings > 0){
                spendEnergy -= (energyBuildings+1); //에너지 빌딩도 에너지 소모 한다고 가정하기,
            }

        }else{
            spendEnergy = 0;
        }
    }

    public void makeBuilding(){
        energyBuildings++;
        System.out.println("에너지 건물 수 : "+energyBuildings);
    }
}
