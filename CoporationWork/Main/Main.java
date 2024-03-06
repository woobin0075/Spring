package CoporationWork.Main;

import CoporationWork.Service.Service;
import CoporationWork.Webconfig.AutoWebConfig;
import CoporationWork.Webconfig.WebConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoWebConfig.class);

        Service service = applicationContext.getBean("service", Service.class);

        service.whileSelect();
    }
}
