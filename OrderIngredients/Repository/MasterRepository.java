package OrderIngredients.Repository;

import OrderIngredients.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasterRepository implements MasterInterface{

    public void insertNewMaster(String name, String pwd, String phoneNumber){
        String sql = "insert into masterinfofactory(name, pwd, phone_number) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, phoneNumber);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("회원가입이 완료되었습니다.");
            }else{
                System.out.println("다시 입력해주세요.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectMaster(String name, String pwd, String phoneNumber){ //로그인용
        String sql = "select * from masterinfofactory where name = ? and pwd = ? and phone_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public int selectMasterID(String name, String pwd, String phoneNumber){
        String sql = "select * from masterinfofactory where name = ? and pwd = ? and phone_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertNewOrder(String date, int factoryOrder_id, int master_id){ //발주
        String sql = "insert into masterordertofactory(date, factoryorder_id, master_id) values(?,?,?)";

        try{
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, factoryOrder_id);
            preparedStatement.setInt(3, master_id);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("발주 되었습니다.");
            }else{
                System.out.println("다시 입력해주세요.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteMyOrder(String date, int id, int masterID){ //발주 취소
        String sql = "delete from masterordertofactory where date = ? and factoryorder_id = ? and master_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, masterID);

           if(preparedStatement.executeUpdate() != 0){
               System.out.println("취소되었습니다.");
           }else{
               System.out.println("잘못된 정보 입력으로 취소가 안 됐습니다.");
           }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectMyOrders(String todayDate, int masterID){ //각 사장님의 오늘날짜별 발주 목록 조회
        String sql = "select * from masterordertofactory where date = ? and master_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, todayDate);
            preparedStatement.setInt(2, masterID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                selectFood(resultSet.getInt("factoryorder_id"));
            }
            System.out.println();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectBeforeMyOrders(String todayDate, int masterID){ //이전 발주 목록 조회용
        String sql = "select * from masterordertofactory where date != ? and master_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, todayDate);
            preparedStatement.setInt(2, masterID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                selectFood(resultSet.getInt("factoryorder_id"));
            }
            System.out.println();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    public void selectFood(int id){ //발주 목록 조회할때 사용
        String sql = "select * from orderpossiblefactory where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println(resultSet.getString("food")+" : "+resultSet.getInt("price"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
