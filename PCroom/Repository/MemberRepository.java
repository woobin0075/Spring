package PCroom.Repository;

import PCroom.Connection.DBdataUtility;
import PCroom.Domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository implements MemberInterface{

    public void insertNewMember(Member member, int tableID){

        String sql = "Insert into pcroom_member(table_id, name, id, pwd, phone_number) values(?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, tableID);
            preparedStatement.setString(2, member.getName());
            preparedStatement.setString(3, member.getID());
            preparedStatement.setString(4, member.getPwd());
            preparedStatement.setString(5, member.getPhone_number());

            if(preparedStatement.executeUpdate() != 0){

                System.out.println("회원 가입이 완료되었습니다.");
            }else{
                System.out.println("회원 가입이 되질 않습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean memberLogin(String inputID, String inputPwd){
        String sql = "select * from pcroom_member where id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, inputID);
            preparedStatement.setString(2, inputPwd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                System.out.println("로그인에 성공했습니다.");
                return true;
            }else{
                System.out.println("로그인에 실패했습니다.");
                return false;
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String bringMemberName(String inputID){
        String sql = "select * from pcroom_member where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, inputID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                return resultSet.getString(2);

            }

            return null;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertUsingPcroomTime(String visitDate, Member member){
        String sql = "insert into using_pcroom_member(visit_date, name, id, using_time) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, visitDate);
            preparedStatement.setString(2, member.getName());
            preparedStatement.setString(3, member.getID());
            preparedStatement.setInt(4, member.getUsingTime());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int calcTotalUsingPcroom(Member member){
        String sql = "select * from using_pcroom_member where id = ?";

        try {
            int total = 0;
            int bonousTime;
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, member.getID());
            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){
                total += resultSet.getInt(4);
            }

            if(total >= 5 && total % 5 == 0){
                bonousTime = total/5;
                System.out.println("누적 사용시간이 "+total+"시간 이어서 보너스로 "+bonousTime+"시간을 드립니다.");

                return bonousTime;
            }

            return 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean calcTotalUsingPcroomForVIP(Member member){
        String sql = "select * from using_pcroom_member where id = ?";

        try {
            int total = 0;
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, member.getID());
            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){
                total += resultSet.getInt("USING_TIME");
            }

            return total >= 100;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateMemberUsingTime(Member member, int bonus){
        String sql = "update using_pcroom_member set using_time = ? where id = ? and using_time = ? limit 1";
        int updateTime = bonus + member.getUsingTime();

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, updateTime);
            preparedStatement.setString(2, member.getID());
            preparedStatement.setInt(3, member.getUsingTime());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectMyID(String name, String phoneNumber){
        String sql = "select * from pcroom_member where name = ? and phone_number = ?";
        String findid;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                findid = resultSet.getString("id");
                System.out.println("찾으시는 아이디는 "+findid+"입니다.");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateMyPwd(String id, String pwd){
        String sql = "update pcroom_member set pwd = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, pwd);
            preparedStatement.setString(2, id);

            if(preparedStatement.executeUpdate() != 0){

                System.out.println("비밀번호가 변경되었습니다.");

            }else{
                System.out.println("존재하지 않는 아이디여서 비밀번호 변경이 불가합니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteMember(String name, String id){
        String sql = "delete from pcroom_member where name = ? and id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("성공적으로 탈퇴하셨습니다.");

            }else{
                System.out.println("이름 또는 아이디를 잘못 입력하셨습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
