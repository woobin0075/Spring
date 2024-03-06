package EnergyGame.Repository;

public class Account {
    AccountInterface repo;

    public Account(AccountInterface repo){
        this.repo = repo;
    }

    public void insertAccount(String id, String pwd){
        repo.insertAccount(id, pwd);
    }

    public boolean selectAccount(String id, String pwd){
        return repo.selectAccount(id, pwd);
    }
}
