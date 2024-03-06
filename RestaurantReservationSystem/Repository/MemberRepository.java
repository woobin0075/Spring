package RestaurantReservationSystem.Repository;

import RestaurantReservationSystem.Connection.DBdataUtility;
import RestaurantReservationSystem.Domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository implements MemberInterface, ReservateInterface{

    public void insertNewMember(Member member){
        String sql = "insert into restaurantmember(name, old, phonenumber) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, member.getName());
            preparedStatement.setInt(2, member.getOld());
            preparedStatement.setString(3, member.getPhoneNumber());

            preparedStatement.executeUpdate();

            System.out.println("회원 가입이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectMember(Member member){ //로그인, 회원가입 둘다 씀
        String sql = "select * from restaurantmember where name = ? and old = ? and phonenumber = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, member.getName());
            preparedStatement.setInt(2, member.getOld());
            preparedStatement.setString(3, member.getPhoneNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
              return true;   //한명이라도 존재하면 true,
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public int selectLoginMemberID(Member member){ //로그인 중인 멤버 sql id 찾기
        String sql = "select * from restaurantmember where name = ? and old = ? and phonenumber = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, member.getName());
            preparedStatement.setInt(2, member.getOld());
            preparedStatement.setString(3, member.getPhoneNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertReservationInfo(String usingdate, int memberID, String visitTime){
        String sql = "insert into restaurantreservate(usingdate, member_id, visittime) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, usingdate);
            preparedStatement.setInt(2, memberID);
            preparedStatement.setString(3, visitTime);

            preparedStatement.executeUpdate();

            System.out.println("식당 예약 "+usingdate+" "+visitTime);
            System.out.println("예약이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
