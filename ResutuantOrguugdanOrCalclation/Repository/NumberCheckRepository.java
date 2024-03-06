package ResutuantOrguugdanOrCalclation.Repository;

import ResutuantOrguugdanOrCalclation.Domain.NumberCheck;
import hello.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NumberCheckRepository implements NumberCheckInterface{

    public void insert(Object numberCheck, int idx) {
        String sql = "Insert into numbercheck(id, name, numbercheck_calc, numbercheck_gugudan, numbercheck_restaurant) values (?,?,?,?,?)";
        try {

            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idx);
            preparedStatement.setString(2, ((NumberCheck)numberCheck).getName());
            preparedStatement.setInt(3, ((NumberCheck)numberCheck).getNumberCheck_calc());
            preparedStatement.setInt(4, ((NumberCheck)numberCheck).getNumberCheck_gugudan());
            preparedStatement.setInt(5, ((NumberCheck)numberCheck).getNumberCheck_Restaurant());

            preparedStatement.executeLargeUpdate();

        }catch (SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
    }

    public void update(String beforeName, String afterName) { //이름 바꾸기
        String sql = "update numbercheck set name = ? where name = ?";

        try {

            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, afterName);
            preparedStatement.setString(2, beforeName);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("정보가 수정되었습니다.");
            }else{
                System.out.println("등록되어 있는 이름이 아닙니다.");
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
