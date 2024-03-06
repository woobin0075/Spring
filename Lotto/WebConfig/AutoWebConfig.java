package Lotto.WebConfig;

import Lotto.Service.LottoService;
import Lotto.Service.LottoServiceSingleTon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoWebConfig {
    @Bean
    LottoService lottoService(){
        return LottoServiceSingleTon.getLottoService();
    }
}
