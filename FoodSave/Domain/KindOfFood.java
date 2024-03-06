package FoodSave.Domain;

public class KindOfFood {
    private Food food;

    public KindOfFood(Food food){
        this.food = food;
    }

    public void save(int price){
        food.save(price);
    }

    public void print(){
        food.printPrice();
    }

    public String foodName(){
        return food.foodName();
    }

    public int foodPrice(){
        return food.foodPrice();
    }
}
