package HotelReservationSystem.Repository;

import HotelReservationSystem.Connection.DBdata;
import HotelReservationSystem.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository {

    public void selectAllRoomStates(){
        String sql = "select * from roomtable";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("방 번호 : "+resultSet.getInt("room_number"));
                System.out.println("룸 타입 : "+resultSet.getString("room_type"));
                System.out.println("최대 수용 인원 : "+resultSet.getInt("max_people"));
                System.out.println("1박당 가격 : "+resultSet.getInt("price_oneday"));

                if(resultSet.getBoolean("isreservate")){
                    System.out.println("예약 가능");
                }else{
                    System.out.println("예약 불가");
                }
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int findRoomID(int roomNumber, String roomType){ //db에 저장된 room ID 반환

        String sql = "select * from roomtable where room_number = ? and room_type = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setString(2, roomType);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;//예약 불가나 정보 없을시 0을 리턴
    }

    public boolean canReservate(int roomNumber, String roomType){

        String sql = "select * from roomtable where room_number = ? and room_type = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setString(2, roomType);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getBoolean("isreservate");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public int findRoomPrice(int roomId){ //예약할 룸 가격 알아내기
        String sql = "select * from roomtable where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("price_oneday");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertReservationInfo(String name, String reservateDate, String checkinDate, String checkoutDate, int people, int pay, int roomID){
        String sql = "insert into roomservicecustomers(name, reservate_date, checkin_date, checkout_date, people, pay, room_id) values(?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, reservateDate);
            preparedStatement.setString(3, checkinDate);
            preparedStatement.setString(4, checkoutDate);
            preparedStatement.setInt(5, people);
            preparedStatement.setInt(6, pay);
            preparedStatement.setInt(7, roomID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateIsResevateFalse(int roomId){
        String sql = "update roomtable set isreservate = false where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomId);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteReservationInfo(int roomId, String name){
        String sql = "delete from roomservicecustomers where room_id = ? and name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomId);
            preparedStatement.setString(2, name);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("예약이 취소되었습니다.");

            }else{
                System.out.println("해당 호실을 예약하신 적이 없습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateIsResevateTrue(int roomId){
        String sql = "update roomtable set isreservate = 1 where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomId);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
