package FoodSave.Repository;

import FoodSave.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository {

    public void insertSaleTable(String nickname, String today, String menu, int many){
        String sql = "insert into myrestaurantsales(nickname, date, menu, totalsale) values(?,?,?,?)";
        int totalSale;
        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nickname);
            preparedStatement.setString(2, today);
            preparedStatement.setString(3, menu);
            totalSale = selectMenuPrice(menu) * many;
            preparedStatement.setInt(4, totalSale);

            if(totalSale != 0){
                preparedStatement.executeUpdate();
                System.out.println("주문이 완료되었습니다.");
            }else{
                System.out.println("해당 메뉴가 존재하지 않거나 잘못 입력하셨습니다.");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private int selectMenuPrice(String menu){
        String sql = "select * from myrestaurantmenu where menu = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, menu);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(2);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertStartsAndFeeling(float stars, String feeling){
        String sql = "insert into MYRESTAURANTDELIVERYFEELINGS(stars, feeling) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setFloat(1, stars);
            preparedStatement.setString(2, feeling);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
