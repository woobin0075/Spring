package OrderIngredients.AutoWebConfig;

import OrderIngredients.Repository.FactoryRepo;
import OrderIngredients.Repository.MasterRepo;
import OrderIngredients.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "OrderIngredients.Domain",
        basePackageClasses = AutoWebConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

public class AutoWebConfig {

    @Bean
    public Service service(){
        FactoryRepo factoryRepo = FactoryRepo.getInstance();
        MasterRepo masterRepo = MasterRepo.getInstance();

        return new Service(factoryRepo, masterRepo);
    }
}
