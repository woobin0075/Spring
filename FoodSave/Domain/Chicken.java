package FoodSave.Domain;

public class Chicken implements Food{
    int price;
    String name = "치킨";

    @Override
    public void save(int price) {
        this.price = price;
    }

    @Override
    public void printPrice() {
        System.out.println("치킨 가격 : "+price+"원");
    }

    @Override
    public int foodPrice() {
        return price;
    }

    @Override
    public String foodName() {
        return name;
    }
}
