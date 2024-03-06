package EnergyGame.Repository;

public interface AccountInterface {
    void insertAccount(String id, String pwd);
    boolean selectAccount(String id, String pwd);
}
