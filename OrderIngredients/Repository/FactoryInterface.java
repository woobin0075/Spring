package OrderIngredients.Repository;

public interface FactoryInterface {
    void insertNewFood(String food, int price);
    int selectFoodID(String food, int price);
    void deleteFood(int id);
    void updateFoodPrice(int id, int price);
}
