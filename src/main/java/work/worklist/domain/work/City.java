package work.worklist.domain.work;
import lombok.Data;

@Data
public class City {
    public Zone[] zones = new Zone[4];

    public City(){
        for(int i=0; i< zones.length; i++){
            zones[i] = new Zone();
        }
    }
}
