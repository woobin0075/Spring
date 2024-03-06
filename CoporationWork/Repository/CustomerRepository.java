package CoporationWork.Repository;

import CoporationWork.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository implements CustomerInterface{

    public void insertNewCustomer(String customerID, String pwd, String name, String phoneNumber){

        String sql = "insert into projectcustomersdb(name, customer_id, pwd, phone_number) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, customerID);
            preparedStatement.setString(3, pwd);
            preparedStatement.setString(4, phoneNumber);

            preparedStatement.executeUpdate();
            System.out.println("회원 가입이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectCustomer(String customerID, String pwd){
        String sql = "select * from projectcustomersdb where customer_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customerID);
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

    public int selectCustomerDBid(String customerID){
        String sql = "select * from projectcustomersdb where customer_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customerID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public boolean selectProjectForCustomer(String projectName){
        String sql = "select * from advisorgrade where project_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, projectName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next() && resultSet.getBoolean("isfinish")){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public void insertFeedbackStart(int projectDbid){
        String sql = "insert into projectcustomersevaluatedb(advisorgrade_id, good_num, bad_num) values(?,0,0)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDbid);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectFeedbackGood(int projectDbid){
        String sql = "select * from projectcustomersevaluatedb where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDbid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("good_num");
            }
            return 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectFeedbackBad(int projectDbid){
        String sql = "select * from projectcustomersevaluatedb where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDbid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("bad_num");
            }

            return 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateFeedbackGood(int projectDbid, int beforeGoodNum){
        String sql = "update projectcustomersevaluatedb set good_num = ? where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, beforeGoodNum + 1);
            preparedStatement.setInt(2, projectDbid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateFeedbackBad(int projectDbid, int beforeBadNum){
        String sql = "update projectcustomersevaluatedb set bad_num = ? where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, beforeBadNum + 1);
            preparedStatement.setInt(2, projectDbid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertCustomerReview(int projectDbid, int customerDbid, String review){
        String sql = "insert into PROJECTCUSTOMERSREVIEWDB(advisorgrade_id, customer_id, review) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDbid);
            preparedStatement.setInt(2, customerDbid);
            preparedStatement.setString(3, review);

            preparedStatement.executeUpdate();
            System.out.println("리뷰가 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewOutsource(int teamLeaderDBidnum, int architectUserDBidnum, int cutomerDBidnum, String untilfinishdate, String outsourcingName){
        String sql = "insert into outsourcingcustomers(team_leader_idnum, architect_user_idnum, customer_idnum, " +
                "untilfinishdate, outsourcingname, isfinish) values(?,?,?,?,?,?)";

        try{
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamLeaderDBidnum);
            preparedStatement.setInt(2, architectUserDBidnum);
            preparedStatement.setInt(3, cutomerDBidnum);
            preparedStatement.setString(4, untilfinishdate);
            preparedStatement.setString(5, outsourcingName);
            preparedStatement.setBoolean(6, false);

            preparedStatement.executeUpdate();
            System.out.println("외주가 성공적으로 신청되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
