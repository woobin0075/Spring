package FoodSave.Domain;

public interface Food {
    void save(int price);
    void printPrice();
    int foodPrice();
    String foodName();
}
