package springstart.PetToyShop.Repository;

import springstart.PetToyShop.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerRepository implements OwnerInterface{

    public int selectOwnerLogin(String id, String pwd){
        String sql = "select * from PETTOYSHOPOWNERINFO where owner_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertStartTodaySale(String date){
        String sql = "insert into PETTOYSHOPSALESRECORD(date, catfeed, dogfeed, cattoiletsand, dogtoiletped, dogtoy, totalsale) values(?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.setInt(6, 0);
            preparedStatement.setInt(7, 0);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateTodaySale(String date, int catfeed, int dogfeed, int catToilet, int dogToilet, int dogtoy){
        int totalsale = catfeed + dogfeed + catToilet + dogToilet + dogtoy;
        String sql = "update PETTOYSHOPSALESRECORD set catfeed = ?, dogfeed = ?, cattoiletsand = ?, dogtoiletped = ?, dogtoy = ?, totalsale = ? " +
                "where date = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, catfeed);
            preparedStatement.setInt(2, dogfeed);
            preparedStatement.setInt(3, catToilet);
            preparedStatement.setInt(4, dogToilet);
            preparedStatement.setInt(5, dogtoy);
            preparedStatement.setInt(6, totalsale);
            preparedStatement.setString(7, date);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectTodaySalesRecord(String date){
        String sql = "select * from PETTOYSHOPSALESRECORD where date = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println("반려견 사료 : "+resultSet.getInt("dogfeed"));
                System.out.println("반려묘 사료 : "+resultSet.getInt("catfeed"));
                System.out.println("반려묘 화장실 모래 : "+resultSet.getInt("cattoiletsand"));
                System.out.println("반려견 화장실 패드 : "+resultSet.getInt("dogtoiletped"));
                System.out.println("반려견 장난감 : "+resultSet.getInt("dogtoy"));
                System.out.println("매출액 : "+resultSet.getInt("totalsale"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllSalesRecord(){
        String sql = "select * from PETTOYSHOPSALESRECORD";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("날짜 : "+resultSet.getString("date"));
                System.out.println("반려견 사료 : "+resultSet.getInt("dogfeed"));
                System.out.println("반려묘 사료 : "+resultSet.getInt("catfeed"));
                System.out.println("반려묘 화장실 모래 : "+resultSet.getInt("cattoiletsand"));
                System.out.println("반려견 화장실 패드 : "+resultSet.getInt("dogtoiletped"));
                System.out.println("반려견 장난감 : "+resultSet.getInt("dogtoy"));
                System.out.println("매출액 : "+resultSet.getInt("totalsale"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertParcelAnimal(String animalKind, String specificKind, String name, int age, String sex, int sellMoney){ //분양 동물 추가
        String sql = "insert into PETSHOPPARCELANIMALS(animalkind, specifickind, name, age, sex, sell_money, sell_finish) values(?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, animalKind);
            preparedStatement.setString(2, specificKind);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, sex);
            preparedStatement.setInt(6, sellMoney);
            preparedStatement.setBoolean(7, false);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectParcelAnimalDBid(String animalKind, String specificKind, String name, int age, String sex, int sellMoney){
        String sql = "select * from PETSHOPPARCELANIMALS where animalkind = ? and specifickind = ? and name = ? and age = ? and " +
                "sex = ? and sell_money = ? and sell_finish = false";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, animalKind);
            preparedStatement.setString(2, specificKind);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, sex);
            preparedStatement.setInt(6, sellMoney);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void updateCompleteParcelAnimal(int animalDBid){
        String sql = "update PETSHOPPARCELANIMALS set sell_finish = true where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, animalDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
