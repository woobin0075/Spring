package Lotto.WebConfig;

import Lotto.Repository.LottoRepo;
import Lotto.Repository.LottoRepository;
import Lotto.Repository.UpdateDeleteRepository;
import Lotto.Service.LottoService;
import Lotto.Service.LottoServiceSingleTon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    LottoService service(){

        LottoService lottoService = LottoServiceSingleTon.getLottoService();

        return lottoService;
    }
}
