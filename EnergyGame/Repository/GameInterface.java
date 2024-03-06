package EnergyGame.Repository;

public interface GameInterface {
    void insertGameState(String date, String nickname, int penalty, String userID);
    void selectGameState(String date, String userID);
}
