package RestaurantReservationSystem.Main;

import RestaurantReservationSystem.AutoWebConfig.AutoWebConfig;
import RestaurantReservationSystem.Service.MainService;
import RestaurantReservationSystem.Service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoWebConfig.class);

        Service service = ac.getBean(Service.class);

        service.whileSelect();

    }
}
