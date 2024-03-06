package EnergyGame.Repository;

public class Game {
    GameInterface repo;

    public Game(GameInterface repo){
        this.repo = repo;
    }

    public void insertGameState(String date, String nickname, int penalty, String userID){
        repo.insertGameState(date, nickname, penalty, userID);
    }

    public void selectGameState(String date, String userID){
        repo.selectGameState(date, userID);
    }
}
