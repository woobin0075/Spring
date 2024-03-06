package Lotto.Main;

import Lotto.Service.LottoService;
import Lotto.WebConfig.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class main {

    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoWebConfig.class);
        LottoService service = applicationContext.getBean(LottoService.class);

        service.whileSelect();
    }
}
