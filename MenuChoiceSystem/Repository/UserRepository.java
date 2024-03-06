package MenuChoiceSystem.Repository;

import MenuChoiceSystem.Connection.DBdataUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserInterface{

    public void insertNewUser(String id, String pwd, String name){ //회원가입용

        String sql = "insert into menuchoiceaccount(user_id, pwd, name) values (?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);

            preparedStatement.executeUpdate();
            System.out.println("회원가입이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectUserDBid(String id){
        String sql = "select * from menuchoiceaccount where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void insertBehaveInfo(int userdbid, int beforePocketMoney, int afterPocketMoney, int pizza, int hamburger,
                                 int chicken, int pasta, int curry, int lendmoney, int borrowmoney){

        String sql = "insert into menuchoiceuserbehaveinfo(userdbid, beforepocketmoney, afterpocketmoney, pizza, hamburger, chickent, pasta," +
                "curry, lendmoney, borrowmoney) values (?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userdbid);
            preparedStatement.setInt(2, beforePocketMoney);
            preparedStatement.setInt(3, afterPocketMoney);
            preparedStatement.setInt(4, pizza);
            preparedStatement.setInt(5, hamburger);
            preparedStatement.setInt(6, chicken);
            preparedStatement.setInt(7, pasta);
            preparedStatement.setInt(8, curry);
            preparedStatement.setInt(9, lendmoney);
            preparedStatement.setInt(10, borrowmoney);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateBehaveInfo(int userdbid, int afterPocketMoney, int pizza, int hamburger,
                                 int chicken, int pasta, int curry, int lendmoney, int borrowmoney){
        String sql = "update menuchoiceuserbehaveinfo set afterpocketmoney = ?, pizza = ?, hamburger = ?, chickent = ?, pasta = ?, " +
                "curry = ?, lendmoney = ?, borrowmoney = ? where userdbid = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, afterPocketMoney);
            preparedStatement.setInt(2, pizza);
            preparedStatement.setInt(3, hamburger);
            preparedStatement.setInt(4, chicken);
            preparedStatement.setInt(5, pasta);
            preparedStatement.setInt(6, curry);
            preparedStatement.setInt(7, lendmoney);
            preparedStatement.setInt(8, borrowmoney);
            preparedStatement.setInt(9, userdbid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectUsersBehave(){
        String sql = "select * from menuchoiceuserbehaveinfo";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("원래 가지고 있던 금액 : "+resultSet.getInt("beforepocketmoney"));
                System.out.println("현재 가지고 있는 금액 : "+resultSet.getInt("afterpocketmoney"));
                if(resultSet.getInt("pizza") > 0){
                    System.out.println("구매한 피자 : "+resultSet.getInt("pizza"));
                }
                if(resultSet.getInt("hamburger") > 0){
                    System.out.println("구매한 햄버거 : "+resultSet.getInt("hamburger"));
                }
                if(resultSet.getInt("chickent") > 0){
                    System.out.println("구매한 치킨 : "+resultSet.getInt("chickent"));
                }
                if(resultSet.getInt("pasta") > 0){
                    System.out.println("구매한 파스타 : "+resultSet.getInt("pasta"));
                }
                if(resultSet.getInt("curry") > 0){
                    System.out.println("구매한 커리 : "+resultSet.getInt("curry"));
                }

                System.out.println("빌린 금액 : "+resultSet.getInt("lendmoney"));
                System.out.println("빌려준 금액 : "+resultSet.getInt("borrowmoney"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
