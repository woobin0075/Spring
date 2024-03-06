package MenuChoiceSystem.Main;

import MenuChoiceSystem.Service.Service;
import MenuChoiceSystem.WebConfig.WebConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebConfig.class);

        Service service = applicationContext.getBean("service", Service.class);

        service.whileSelect();
    }
}
