package PCroom.Repository;

import PCroom.Connection.DBdataUtility;
import PCroom.Domain.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GuestRepository implements GuestInterface {

    public boolean insertGuestInfo(String enterNumber, int table_ID){

        String sql = "insert into pcroom_guests(table_id, enter_number) values (?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, table_ID);
            preparedStatement.setString(2, enterNumber);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("게스트로 로그인 되었습니다.");
                return true;

            }else{
                System.out.println("로그인 실패입니다.");
                return false;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertUsingPcroomTime(String visitDate, Guest guest){
        String sql = "insert into using_pcroom_guests(visit_date, enter_number, using_time) values(?,?,?)";

        try {

            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, visitDate);
            preparedStatement.setString(2, guest.getEnterNumber());
            preparedStatement.setInt(3, guest.getUsingTime());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
