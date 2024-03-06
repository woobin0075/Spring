package FoodSave.Domain;

public class Pizza implements Food{
    int price;
    String name = "피자";
    @Override
    public void save(int price) {
        this.price = price;
    }

    @Override
    public void printPrice() {
        System.out.println("피자 가격 : "+price+"원");
    }

    public int foodPrice() {
        return price;
    }

    public String foodName() {
        return name;
    }
}
