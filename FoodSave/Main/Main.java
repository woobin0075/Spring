package FoodSave.Main;

import FoodSave.Service.MainService;

public class Main {
    public static void main(String[] args){
        MainService mainService = new MainService();

        mainService.whileSelect();
    }
}
