package FoodSave.Domain;

public class Hamburger implements Food{
    int price;
    String name = "햄버거";
    @Override
    public void save(int price) {
        this.price = price;
    }

    @Override
    public void printPrice() {
        System.out.println("햄버거 가격 : "+price+"원");
    }

    public int foodPrice() {
        return price;
    }
    public String foodName() {
        return name;
    }
}
