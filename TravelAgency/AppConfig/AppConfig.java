package springstart.TravelAgency.AppConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springstart.TravelAgency.Repository.UserRepo;
import springstart.TravelAgency.Service.TravelAgency;
import springstart.TravelAgency.Service.TravelCalculator;
import springstart.TravelAgency.Service.UserService;

@Configuration
@ComponentScan("springstart.TravelAgency.AppConfig")
public class AppConfig {

    @Bean
    UserService userService(){

        return new UserService();
    }

    @Bean
    TravelAgency travelAgencyService(){
        return TravelAgency.getInstance();
    }

}
