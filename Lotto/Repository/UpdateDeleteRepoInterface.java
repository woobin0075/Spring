package Lotto.Repository;

import Lotto.Domain.MyLotto;

public interface UpdateDeleteRepoInterface {
    void updateLottoNums(MyLotto myLotto);
    void deleteMyLottoNum(String lottoStr, String memberID);
    public void insertSeveralLottoNums();
}
