package springstart.ZooSimulation.Main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springstart.ZooSimulation.AppConfig.AppConfig;
import springstart.ZooSimulation.Service.MainWhile;

public class Main {

    public static void main(String[] args){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MainWhile mw = ac.getBean(MainWhile.class);

        mw.whileSelect();
    }

}
