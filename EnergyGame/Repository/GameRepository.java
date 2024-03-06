package EnergyGame.Repository;

import EnergyGame.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRepository implements GameInterface{

    public void insertGameState(String date, String nickname, int penalty, String userID){ //게임 결과 db에 저장
        String sql = "insert into energygamestate(gamedate, nickname, penalty, userid) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, nickname);
            preparedStatement.setInt(3, penalty);
            preparedStatement.setString(4, userID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectGameState(String date, String userID){
        String sql = "select * from energygamestate where gamedate = ? and userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("닉네임 : "+resultSet.getString("nickname"));
                System.out.println("   페널티 : "+resultSet.getInt("penalty"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
