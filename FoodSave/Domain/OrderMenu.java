package FoodSave.Domain;

public class OrderMenu {
    String menuName;
    int orderMany; //주문 수량

    public String getMenuName(){
        return menuName;
    }

    public int getOrderMany(){
        return orderMany;
    }

    public OrderMenu(String menuName, int orderMany){
        this.menuName = menuName;
        this.orderMany = orderMany;
    }
}
