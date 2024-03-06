package Lotto.Service;

import Lotto.Repository.LottoRepo;
import Lotto.Repository.LottoRepository;
import Lotto.Repository.UpdateDeleteRepository;
import org.springframework.stereotype.Component;

@Component
public class LottoServiceSingleTon {
    private static LottoService lottoService;

    private LottoServiceSingleTon(){

    }

    public static LottoService getLottoService(){
        if(lottoService == null){
            LottoRepository lottoRepository = new LottoRepository();
            UpdateDeleteRepository updateDeleteRepository = new UpdateDeleteRepository();

            LottoRepo lottoRepo = new LottoRepo(lottoRepository, updateDeleteRepository);
            lottoService = new LottoService(lottoRepo);
        }

        return lottoService;
    }
}
