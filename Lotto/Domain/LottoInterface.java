package Lotto.Domain;

public interface LottoInterface {
    //1등 2등 3등
    int firstMoney = 5000000;
    int secondMoney = 500000;
    int thirdMoney = 50000;

    void passiveLotto();//수동용
    void autoLotto();
    void semiautoLotto(int inputNumbers);
}
