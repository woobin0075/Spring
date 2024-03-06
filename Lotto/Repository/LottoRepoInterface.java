package Lotto.Repository;

import Lotto.Domain.MyLotto;

public interface LottoRepoInterface {
    void save(String name, String id, String pwd);
    void insertLottonums(MyLotto myLotto, int papersMany);
    boolean canLogin(String id, String pwd);
    void insertNewPoint(int table_id);
    int findMemberTableID(String memberID);
    int findMemberPoint(int table_id);
    void updatePoint(int point, int table_id);
    boolean selectRecommendFirst(String memberID);
    int[] recommendLottoNums();
    void changeRecommendFirst(String id);
}
