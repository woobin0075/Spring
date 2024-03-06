package Delivery.WebConfig;

import Delivery.Repository.MasterRepo;
import Delivery.Repository.MasterRepository;
import Delivery.Repository.UserRepo;
import Delivery.Repository.UserRepository;
import Delivery.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public Service service(){
        UserRepository userRepository = new UserRepository();
        MasterRepository masterRepository = new MasterRepository();

        UserRepo userRepo = new UserRepo(userRepository);
        MasterRepo masterRepo = new MasterRepo(masterRepository);

        return new Service(masterRepo, userRepo);
    }
}
