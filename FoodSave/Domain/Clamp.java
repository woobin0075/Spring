package FoodSave.Domain;

public class Clamp implements Food{
    int price;
    String name = "조개";

    @Override
    public void save(int price) {
        this.price = price;
    }

    @Override
    public void printPrice() {
        System.out.println("조개 가격 : "+price+"원");
    }

    public int foodPrice() {
        return price;
    }

    public String foodName() {
        return name;
    }
}
