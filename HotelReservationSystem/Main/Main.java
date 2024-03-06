package HotelReservationSystem.Main;

import HotelReservationSystem.Service.MainService;
import HotelReservationSystem.WebConfig.Webconfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Webconfig.class);
        MainService service = applicationContext.getBean("service", MainService.class);

        service.whileSelect();
    }
}
