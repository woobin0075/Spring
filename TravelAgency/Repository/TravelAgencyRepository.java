package springstart.TravelAgency.Repository;

import springstart.TravelAgency.Connection.DBdataUtility;
import springstart.TravelAgency.Domain.TravelPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravelAgencyRepository implements TravelAgencyInterface {

    public void insertNewPackage(String packageName, int price, String region, int distance, String accommodation, int travelDays,
                                 String guideName, String sendWay){
        String sql = "insert into travelagencypackagedb(package_name, price, region, travel_distance, accomodation, travel_day," +
                "guide_name, send_way, avgscore) values(?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, packageName);
            preparedStatement.setInt(2, price);
            preparedStatement.setString(3, region);
            preparedStatement.setInt(4, distance);
            preparedStatement.setString(5, accommodation);
            preparedStatement.setInt(6, travelDays);
            preparedStatement.setString(7, guideName);
            preparedStatement.setString(8, sendWay);
            preparedStatement.setDouble(9, 0.0);

            preparedStatement.executeUpdate();
            System.out.println("새로운 패키지 상품이 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectPackageByName(String packageName){
        String sql = "select * from travelagencypackagedb where package_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, packageName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("패키지 이름 : "+resultSet.getString("package_name"));
                System.out.println("가격 : "+resultSet.getInt("price"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<TravelPackage> selectAllPackages(){
        String sql = "select * from travelagencypackagedb";
        ArrayList<TravelPackage> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            TravelPackage travelPackage;

            while(resultSet.next()){
                travelPackage = new TravelPackage(resultSet.getString("package_name"), resultSet.getInt("price"),
                        resultSet.getString("region"), resultSet.getInt("travel_distance"),
                        resultSet.getString("accomodation"), resultSet.getInt("travel_day"),
                        resultSet.getString("guide_name"), resultSet.getString("send_way"));

                travelPackage.setAvgScore(resultSet.getDouble("avgscore"));

                list.add(travelPackage);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public void insertNewPostScript(int packageDBid, int customerDBid, int score, String postscript){
        String sql = "insert into TRAVELAGENCYPACKAGEPOSTSCRIPT(package_id, customer_id, score, postscript) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, packageDBid);
            preparedStatement.setInt(2, customerDBid);
            preparedStatement.setInt(3, score);
            preparedStatement.setString(4, postscript);

            preparedStatement.executeUpdate();
            System.out.println("후기가 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectPackageDBid(String name){
        String sql = "select * from travelagencypackagedb where package_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public int selectPackagePrice(String name){
        String sql = "select * from travelagencypackagedb where package_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("price");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public int calcTotalScore(int packageid){
        String sql = "select * from TRAVELAGENCYPACKAGEPOSTSCRIPT where package_id = ?";
        int sum = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, packageid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                sum += resultSet.getInt("score");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return sum;
    }

    public int calcPostScriptPacackageNum(int packageid){
        String sql = "select * from TRAVELAGENCYPACKAGEPOSTSCRIPT where package_id = ?";
        int sum = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, packageid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                sum++;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return sum;
    }

    public void updatePackageAvgScore(int packageid, double avg){
        String sql = "update travelagencypackagedb set avgscore = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, avg);
            preparedStatement.setInt(2, packageid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectRecommendPackages(int price){
        String sql = "select * from travelagencypackagedb where price <= ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, price);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("패키지 이름 : "+resultSet.getString("package_name"));
                System.out.println("가격 : "+resultSet.getInt("price"));
                System.out.println("평점 : "+resultSet.getDouble("avgscore"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
