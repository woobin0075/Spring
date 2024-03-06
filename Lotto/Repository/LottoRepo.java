package Lotto.Repository;

import Lotto.Domain.MyLotto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LottoRepo {
    LottoRepoInterface lottoRepo;
    UpdateDeleteRepoInterface udlottoRepo;

    @Autowired
    public LottoRepo(LottoRepoInterface lottoRepo, UpdateDeleteRepoInterface udlottoRepo){
        this.lottoRepo = lottoRepo;
        this.udlottoRepo = udlottoRepo;
    }

    public void save(String name, String id, String pwd){
        lottoRepo.save(name, id, pwd);
    }

    public void insertLottonums(MyLotto myLotto, int papersMany){
        lottoRepo.insertLottonums(myLotto, papersMany);
    }

    public void updateLottoNums(MyLotto myLotto){
        udlottoRepo.updateLottoNums(myLotto);
    }

    public void deleteMyLottoNum(String lottoStr, String memberID){
        udlottoRepo.deleteMyLottoNum(lottoStr, memberID);
    }

    public boolean canLogin(String id, String pwd){
        return lottoRepo.canLogin(id, pwd);
    }

    public void insertNewPoint(int table_id){
        lottoRepo.insertNewPoint(table_id);
    }

    public int findMemberTableID(String memberID){
        return lottoRepo.findMemberTableID(memberID);
    }

    public int findMemberPoint(int table_id){
       return lottoRepo.findMemberPoint(table_id);
    }

    public void updatePoint(int point, int table_id){
        lottoRepo.updatePoint(point, table_id);
    }

    public void severalLottonums(){
        udlottoRepo.insertSeveralLottoNums();
    }

    public boolean isYouRecommendFirst(String id){
        return lottoRepo.selectRecommendFirst(id);
    }

    public void changeRecommendFirst(String id){
        lottoRepo.changeRecommendFirst(id);
    }

    public int[] recommendLottoNum(){
        return lottoRepo.recommendLottoNums();
    }
}
