package OrderIngredients.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryRepo {
    FactoryInterface repo;
    private static FactoryRepo factoryRepo;

    @Autowired
    private FactoryRepo(FactoryInterface repo){
        this.repo = repo;
    }

    public static FactoryRepo getInstance(){
        if(factoryRepo == null){
            FactoryRepository factoryRepository = new FactoryRepository();
            factoryRepo = new FactoryRepo(factoryRepository);
        }

        return factoryRepo;
    }

    public void insertNewFood(String food, int price){
        repo.insertNewFood(food, price);
    }

    public int selectFoodID(String food, int price){
        return repo.selectFoodID(food, price);
    }

    public void deleteFood(int id){
        repo.deleteFood(id);
    }

    public void updateFoodPrice(int id, int price){
        repo.updateFoodPrice(id, price);
    }
}
