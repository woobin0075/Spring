package OrderIngredients.WebConfig;

import OrderIngredients.Repository.FactoryRepo;
import OrderIngredients.Repository.MasterRepo;
import OrderIngredients.Repository.MasterRepository;
import OrderIngredients.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    Service service(){
        MasterRepository masterRepository = new MasterRepository();

        FactoryRepo factoryRepo = FactoryRepo.getInstance();
        MasterRepo masterRepo = new MasterRepo(masterRepository);

        return new Service(factoryRepo, masterRepo);
    }
}
