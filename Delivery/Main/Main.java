package Delivery.Main;

import Delivery.AutoWebConfig.AutoWebConfig;
import Delivery.Service.Service;
import Delivery.WebConfig.WebConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoWebConfig.class);
        Service service = applicationContext.getBean(Service.class);

        service.whileSelect();
    }
}
