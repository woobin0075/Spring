package DatePeopleDistribution.Repository;

import DatePeopleDistribution.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserInterface{

    public void insertNewUser(String id, String pwd, String name, String phoneNumber){ //회원가입용
        String sql = "insert into dateusertable(user_id, pwd, name, phone_number) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phoneNumber);

            preparedStatement.executeUpdate();
            System.out.println("회원가입이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectUserDBid(String userID){ //유저 테이블 id 가져오기
        String sql = "select * from dateusertable where user_id = ?";

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

    public void insertBuyInfo(int userdbid, int bacon, int icecream, int potatobean, int totalPay, String date, int point){ //구매 정보 입력용
        String sql = "insert into datebuyandpoint(userdbid, icecream, bacon, potatobean, buydate, totalpay, point) values(?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userdbid);
            preparedStatement.setInt(2, icecream);
            preparedStatement.setInt(3, bacon);
            preparedStatement.setInt(4, potatobean);
            preparedStatement.setString(5, date);
            preparedStatement.setInt(6, totalPay);
            preparedStatement.setInt(7, point);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

//    public void updatePoint(int userdbid, int point){ //포인트 적립용
//        String sql = "update datebuyandpoint set point = ? where userdbid = ?";
//
//        try {
//            Connection connection = DBdataUtility.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1, point);
//            preparedStatement.setInt(2, userdbid);
//
//            preparedStatement.executeUpdate();
//
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }

    public void selectUserInfo(int dbid){
        String sql = "select * from dateusertable where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, dbid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println("이름 : "+resultSet.getString("name"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectReceit(String date){ //영수증 조회용
        String sql = "select * from datebuyandpoint where buydate = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                selectUserInfo(resultSet.getInt("userdbid"));
                System.out.println("베이컨 : "+resultSet.getInt("bacon"));
                System.out.println("아이스크림 : "+resultSet.getInt("icecream"));
                System.out.println("감자콩 : "+resultSet.getInt("potatobean"));
                System.out.println("결제 금액 : "+resultSet.getInt("totalpay")+"원");

                if(resultSet.getInt("point") > 0){
                    System.out.println("포인트 : "+resultSet.getInt("point"));
                }
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
