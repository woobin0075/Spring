package MenuChoiceSystem.WebConfig;

import MenuChoiceSystem.Repository.UserRepo;
import MenuChoiceSystem.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    Service service(){
        UserRepo userRepo = UserRepo.getInstance();

        return new Service(userRepo);
    }
}
