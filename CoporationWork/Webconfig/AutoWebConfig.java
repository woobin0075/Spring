package CoporationWork.Webconfig;

import CoporationWork.Repository.AdvisorRepo;
import CoporationWork.Repository.CustomerRepo;
import CoporationWork.Repository.UserRepo;
import CoporationWork.Repository.UserRepository;
import CoporationWork.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "CoporationWork.Domain",
        basePackageClasses = AutoWebConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoWebConfig {

    @Bean
    public Service service(){
        UserRepository userRepository = new UserRepository();
        UserRepo userRepo = new UserRepo(userRepository);
        return new Service(userRepo, AdvisorRepo.getInstance(), new CustomerRepo());
    }
}
