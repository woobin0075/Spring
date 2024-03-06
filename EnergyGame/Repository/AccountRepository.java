package EnergyGame.Repository;

import EnergyGame.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//회원 가입, 로그인 레포지토리
public class AccountRepository implements AccountInterface{

    public void insertAccount(String id, String pwd){ //회원가입
        String sql = "insert into energygameaccount(id, password) values (?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            preparedStatement.executeUpdate();

            System.out.println("회원가입이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectAccount(String id, String pwd){ //로그인
        String sql = "select * from energygameaccount where id = ? and password = ?";

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
}
