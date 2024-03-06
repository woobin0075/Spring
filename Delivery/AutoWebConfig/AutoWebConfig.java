package Delivery.AutoWebConfig;

import Delivery.Repository.MasterRepo;
import Delivery.Repository.MasterRepository;
import Delivery.Repository.UserRepo;
import Delivery.Repository.UserRepository;
import Delivery.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "Delivery.Domain",
        basePackageClasses = AutoWebConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoWebConfig {

    @Bean
    public Service service(){
        UserRepository userRepository = new UserRepository();
        MasterRepository masterRepository = new MasterRepository();

        UserRepo userRepo = new UserRepo(userRepository);
        MasterRepo masterRepo = new MasterRepo(masterRepository);

        return new Service(masterRepo, userRepo);
    }
}
