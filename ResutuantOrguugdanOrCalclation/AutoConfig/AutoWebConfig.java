package ResutuantOrguugdanOrCalclation.AutoConfig;

import ResutuantOrguugdanOrCalclation.Service.CustomerService;
import ResutuantOrguugdanOrCalclation.Service.SelectMenuService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoWebConfig {

    @Bean
    CustomerService customerService(){
        return CustomerService.getService();
    }

    @Bean
    SelectMenuService selectMenuService(){
        return SelectMenuService.getService();
    }
}
