package ResutuantOrguugdanOrCalclation.Repository;

import ResutuantOrguugdanOrCalclation.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCustomerRepository implements UpdateCustomerInterface{

    public void refundChineseRestaurant(String name){ //중국집 환불
        String sql = "update customertable set purchase = 0 where name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("전액 환불 되었습니다.");
            }else{
                System.out.println("등록되어 있는 이름이 아닙니다.");
            }



        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void update(String beforeName, String afterName) { //회원 정보 수정
        String sql = "update customertable set name = ? where name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, afterName);
            preparedStatement.setString(2, beforeName);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("정보가 수정되었습니다.");
            }else{
                System.out.println("등록되어 있는 이름이 아닙니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
