package FoodSave.Domain;

public class Porkbelly implements Food{
    int price;
    String name = "삼겹살";

    @Override
    public void save(int price) {
        this.price = price;
    }

    @Override
    public void printPrice() {
        System.out.println("삼겹살 가격 : "+price+"원");
    }

    public int foodPrice() {
        return price;
    }

    public String foodName() {
        return name;
    }
}
