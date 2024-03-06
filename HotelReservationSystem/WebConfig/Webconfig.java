package HotelReservationSystem.WebConfig;

import HotelReservationSystem.Service.MainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Webconfig {

    @Bean
    public MainService service(){
        return new MainService();
    }
}
