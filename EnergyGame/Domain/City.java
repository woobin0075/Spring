package EnergyGame.Domain;
import lombok.Data;

@Data
public class City { //도시 하나당 구역 4개 자동 할당으로 규정
    public Zone[] zones = new Zone[4];

    public City(){
        for(int i=0; i< zones.length; i++){
            zones[i] = new Zone();
        }
    }
}
