package RestaurantReservationSystem.AutoWebConfig;

import RestaurantReservationSystem.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "RestaurantReservationSystem.Domain",
        basePackageClasses = AutoWebConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoWebConfig {

    @Bean
    Service service(){
        return new Service();
    }
}
