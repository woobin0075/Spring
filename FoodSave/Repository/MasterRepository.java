package FoodSave.Repository;

import FoodSave.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasterRepository {

    public void selectAllMenus(){
        String sql = "select * from myrestaurantmenu";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println(resultSet.getString("menu")+" : "+resultSet.getInt("price"));
            }
            System.out.println();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateMenuPrice(String menuName, int price){
        String sql = "update myrestaurantmenu set price = ? where menu = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, price);
            preparedStatement.setString(2, menuName);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("수정되었습니다.");
            }else{
                System.out.println("해당하는 메뉴가 없습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllSales(){
        String sql = "select * from MYRESTAURANTSALES";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("주문한 손님 : "+resultSet.getString("nickname"));
                System.out.println("주문 날짜 : "+resultSet.getString("date"));
                System.out.println("주문 메뉴 : "+resultSet.getString("menu"));
                System.out.println("판매액 : "+resultSet.getInt("totalsale"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
