package PCroom.Repository;

import PCroom.Connection.DBdata;
import PCroom.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperVisorRepository implements SuperVisorInterface{

    public void bringMemberUsingTimeInfo(){
        String sql = "select * from using_pcroom_member";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("회원");
            while (resultSet.next()){
                System.out.println("날짜 : "+resultSet.getString(1));
                System.out.println("이름 : "+resultSet.getString(2));
                System.out.println("ID : "+resultSet.getString(3));
                System.out.println("이용 시간 : "+resultSet.getInt(4));
                System.out.println();
            }
            System.out.println();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void bringGuestUsingTimeInfo(){
        String sql = "select * from using_pcroom_guests";

        try {
            Connection connection = DBdataUtility.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("비회원");
            while (resultSet.next()){
                System.out.println("날짜 : "+resultSet.getString(1));
                System.out.println("등록 번호 : "+resultSet.getString(2));
                System.out.println("이용 시간 : "+resultSet.getInt(3));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteMember(String memberName, String memberID){
        String sql = "delete from pcroom_member where name = ? and id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, memberName);
            preparedStatement.setString(2, memberID);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("삭제 되었습니다.");
            }else{
                System.out.println("해당하는 회원이 없습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertDeletedMember(String memberName, String memberID){
        String sql = "insert into getoutmember(name, member_id) values (?,?) ";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, memberName);
            preparedStatement.setString(2, memberID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllMember(){
        String sql = "select * from pcroom_member";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("이름 : "+resultSet.getString(2));
                System.out.println("아이디 : "+resultSet.getString(3));
                System.out.println("비번 : "+resultSet.getString(4));
                System.out.println("핸드폰 번호 : "+resultSet.getString(5));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void findByName(String name){
        String sql = "select * from pcroom_member where name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("이름 : "+resultSet.getString(2));
                System.out.println("아이디 : "+resultSet.getString(3));
                System.out.println("비번 : "+resultSet.getString(4));
                System.out.println("핸드폰 번호 : "+resultSet.getString(5));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
