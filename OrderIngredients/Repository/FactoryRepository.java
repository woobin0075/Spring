package OrderIngredients.Repository;

import OrderIngredients.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactoryRepository implements FactoryInterface {
    public void insertNewFood(String food, int price){ //발주 가능 음식 추가
        String sql = "insert into orderpossiblefactory(food, price) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, food);
            preparedStatement.setInt(2, price);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("추가 되었습니다.");
            }else{
                System.out.println("추가되지 않았습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectFoodID(String food, int price){ //발주 음식 테이블 고유 아이디 리턴
        String sql = "select * from orderpossiblefactory where food = ? and price = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, food);
            preparedStatement.setInt(2, price);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void deleteFood(int id){ //발주 가능 음식 삭제
        String sql = "delete from orderpossiblefactory where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateFoodPrice(int id, int price){ //발주 금액 변경
        String sql = "update orderpossiblefactory set price = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, id);

           if(preparedStatement.executeUpdate() != 0){
               System.out.println("금액이 변경되었습니다.");
           }else{
               System.out.println("잘못된 정보 입력으로 변경되지 않았습니다.");
           }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
