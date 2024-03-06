package DatePeopleDistribution.Webconfig;

import DatePeopleDistribution.Repository.UserRepo;
import DatePeopleDistribution.Service.Service;
import Delivery.Domain.User;
import org.springframework.context.annotation.Bean;

public class WebConfig {

    @Bean
    Service service(){

        return new Service(UserRepo.getInstance());
    }
}
