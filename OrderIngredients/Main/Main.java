package OrderIngredients.Main;

import OrderIngredients.AutoWebConfig.AutoWebConfig;
import OrderIngredients.Service.Service;
import OrderIngredients.WebConfig.WebConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoWebConfig.class);

        Service service = applicationContext.getBean(Service.class);

        service.whileSelect();
    }
}
