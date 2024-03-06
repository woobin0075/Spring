package springstart.TravelAgency.Repository;

import springstart.TravelAgency.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserInterface{

    public void insertNewUser(String userID, String pwd, String name, String phoneNumber, String email){

        String sql = "insert into travelagencyuser(user_id, pwd, name, phone_number, email) values(?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, email);

            preparedStatement.executeUpdate();
            System.out.println("회원가입에 성공하셨습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectUserDBid(String userID){
        String sql = "select * from travelagencyuser where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean selectUser(String userID, String pwd){
        String sql = "select * from travelagencyuser where user_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, pwd);

            ResultSet resultSet = preparedStatement.executeQuery();;

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }
}
