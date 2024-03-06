package springstart.PetToyShop.Domain;
import lombok.Data;

@Data
public class Owner {
    int id;
    String ownerID;

    public Owner(int id, String ownerID){
        this.id = id;
        this.ownerID = ownerID;
    }
}
