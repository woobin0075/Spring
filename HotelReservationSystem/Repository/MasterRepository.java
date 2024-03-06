package HotelReservationSystem.Repository;

import HotelReservationSystem.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasterRepository {

    public void selectRoomsInfo(){
        String sql = "select * from roomtable";
        int roomid;
        String name;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                roomid = resultSet.getInt("id");
                System.out.println("방 번호 : "+resultSet.getInt("room_number"));
                System.out.println("룸 타입 : "+resultSet.getString("room_type"));
                System.out.println("최대 수용 인원 : "+resultSet.getInt("max_people"));
                System.out.println("1박당 가격 : "+resultSet.getInt("price_oneday"));

                name = findNameById(roomid);
                if(!resultSet.getBoolean("isreservate")){

                    if(name == null){
                        System.out.println("예약 불가 상태");
                    }else{
                        System.out.println("예약자 성함 : "+name);
                    }

                }else{
                    System.out.println("예약 가능");
                }
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String findNameById(int roomid){
        String sql = "select * from roomservicecustomers where room_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("name");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public void updateIsResevateFalse(int roomNumber){
        String sql = "update roomtable set isreservate = 0 where room_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomNumber);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("변경되었습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateIsResevateTrue(int roomNumber){
        String sql = "update roomtable set isreservate = 1 where room_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, roomNumber);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("변경되었습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllCustomers(){
        String sql = "select * from roomservicecustomers";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("이름 : "+resultSet.getString("name"));
                System.out.println("예약 날짜 : "+resultSet.getString("reservate_date"));
                System.out.println("지불 금액 : "+resultSet.getInt("pay"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
