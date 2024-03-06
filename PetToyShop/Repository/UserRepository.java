package springstart.PetToyShop.Repository;

import org.springframework.stereotype.Component;
import springstart.PetToyShop.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRepository implements UserInterface {

    public void insertNewUser(String id, String pwd, String name){
        String sql = "insert into pettoyshopusers(user_id, pwd, name) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);

            preparedStatement.executeUpdate();
            System.out.println("회원가입이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectUserDBid(String id){ //DB id확인겸
        String sql = "select * from pettoyshopusers where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean selectSameID(String id){
        String sql = "select * from pettoyshopusers where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean selectUser(String id, String pwd){ //로그인 허용
        String sql = "select * from pettoyshopusers where user_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
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

    public void insertUserBuyInfo(int dbid, String date, int catfeedmany, int dogfeedmany, int catToiletmany, int dogToiletmany, int dogtoymany, int totalpay){
        String sql = "insert into PETTOYSHOPSALEINFO(userdbid, date, catfeed, dogfeed, cattoiletsand, dogtoiletped, dogtoy, totalpay) values(?,?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, dbid);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, catfeedmany);
            preparedStatement.setInt(4, dogfeedmany);
            preparedStatement.setInt(5, catToiletmany);
            preparedStatement.setInt(6, dogToiletmany);
            preparedStatement.setInt(7, dogtoymany);
            preparedStatement.setInt(8, totalpay);

            preparedStatement.executeUpdate();
            System.out.println("구매를 완료하였습니다.");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertUserPetKindOf(int dbid, String petKindOf){
        String sql = "insert into PETTOYSHOPUSERPETKINDOF(user_id, pet) values(?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, dbid);
            preparedStatement.setString(2, petKindOf);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllParcelAnimals(){
        String sql = "select * from PETSHOPPARCELANIMALS where sell_finish = false";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("1.품종 : "+resultSet.getString("animalkind"));
                System.out.println("2.세부 품종 : "+resultSet.getString("specifickind"));
                System.out.println("3.이름 : "+resultSet.getString("name"));
                System.out.println("4.성별 : "+resultSet.getString("sex"));
                System.out.println("5.분양가 : "+resultSet.getInt("sell_money"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAnyParcelAnimals(String kind){

        String sql = "select * from PETSHOPPARCELANIMALS where sell_finish = false and (animalkind = ? or specifickind = ?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, kind);
            preparedStatement.setString(2, kind);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("1.품종 : "+resultSet.getString("animalkind"));
                System.out.println("2.세부 품종 : "+resultSet.getString("specifickind"));
                System.out.println("3.이름 : "+resultSet.getString("name"));
                System.out.println("4.성별 : "+resultSet.getString("sex"));
                System.out.println("5.분양가 : "+resultSet.getInt("sell_money"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertParcelUser(int userDBid, int animalDBid){
        String sql = "insert into PETSHOPPARCELUSERS(user_id, animal_id) values (?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);
            preparedStatement.setInt(2, animalDBid);

            preparedStatement.executeUpdate();
            System.out.println("분양이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
