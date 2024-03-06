package Delivery.Repository;

import Delivery.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserInterface{

    public void insertAccount(String name, String pwd, String phoneNumber, String userID){ //회원가입용
        String sql = "insert into deliveryusers(name, password, phone_number, user_id) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, userID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectSameID(String id){ //아이디 중복 검사용
        String sql = "select * from deliveryusers where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;    //중복 아이디 존재하면 true반환 아니면 false
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean selectAccount(String id, String pwd){ //로그인용
        String sql = "select * from deliveryusers where user_id = ? and password = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public int findTableID(String id, String pwd){
        String sql = "select * from deliveryusers where user_id = ? and password = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0; //없다는 의미
    }

    public void insertOrder(String date, int userID, int masterID, String food, int price){ //주문할 때
        String sql = "insert into deliveryOrders(date, user_id, master_id, food, food_price) values(?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, userID);
            preparedStatement.setInt(3, masterID);
            preparedStatement.setString(4, food);
            preparedStatement.setInt(5, price);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertPay(String date, int userID, int totalCost, String pay){ //결제용
        String sql = "insert into deliverypay(date, user_id, totalcost, pay) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, userID);
            preparedStatement.setInt(3, totalCost);
            preparedStatement.setString(4, pay);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertSatisfaction(int masterID, int userID, int stars){ //만족도 시스템용
        String sql = "insert into deliverysatisfaction(master_id, user_id, stars) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, masterID);
            preparedStatement.setInt(2, userID);
            preparedStatement.setInt(3, stars);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
