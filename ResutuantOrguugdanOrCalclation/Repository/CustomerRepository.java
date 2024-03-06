package ResutuantOrguugdanOrCalclation.Repository;

import ResutuantOrguugdanOrCalclation.Domain.ChinaRestaurntPrice;
import ResutuantOrguugdanOrCalclation.Domain.Customer;
import ResutuantOrguugdanOrCalclation.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository implements CustomerInterface{

    public void priceSave(){
        String sql = "Insert into chinarestaurant(jajangmyeon, fried_rice, tangsuyuk_small, tangsuyuk_medium, tangsuyuk_large) " +
                "values (?,?,?,?,?)";
        Connection connection = hello.Connection.DBdataUtility.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, ChinaRestaurntPrice.jajangmyeon);
            preparedStatement.setInt(2, ChinaRestaurntPrice.fried_Rice);
            preparedStatement.setInt(3, ChinaRestaurntPrice.tangsuyuk_small);
            preparedStatement.setInt(4, ChinaRestaurntPrice.tangsuyuk_medium);
            preparedStatement.setInt(5, ChinaRestaurntPrice.tangsuyuk_large);

            preparedStatement.executeLargeUpdate();

            System.out.println("식당 가격 정보 등록");
            System.out.println();

        }catch (SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
    }

    public void insert(Object customer, int idx) {
        String sql = "Insert into customertable(id, name, jajangmyeon, fried_Rice, tangsuyuk_small, tangsuyuk_medium, tangsuyuk_large, purchase)" +
                "values (?,?,?,?,?,?,?,?)";

        Connection connection = DBdataUtility.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idx);
            preparedStatement.setString(2, ((Customer)customer).getName());
            preparedStatement.setInt(3, ((Customer)customer).getJajangmyeonNum());
            preparedStatement.setInt(4, ((Customer)customer).getFried_RiceNum());
            preparedStatement.setInt(5, ((Customer)customer).getTangsuyuk_smallNum());
            preparedStatement.setInt(6, ((Customer)customer).getTangsuyuk_mediumNum());
            preparedStatement.setInt(7, ((Customer)customer).getTangsuyuk_largeNum());
            preparedStatement.setInt(8, ((Customer)customer).getPurchase());

            preparedStatement.executeLargeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void find(String customerName) {
        String sql = "select * from customertable where name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Customer customer = new Customer();
                customer.setName(resultSet.getString("name"));
                customer.setPurchase(resultSet.getInt("purchase"));

                System.out.println("손님 이름 : "+customer.getName());
                System.out.println("누적 구매 금액 : "+customer.getPurchase());

            }else{
                System.out.println("없는 정보 입니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void delete(String name) {
        String sql = "delete from customertable where name = ?";

        try {

            Connection connection = DBdataUtility.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("성공적으로 탈퇴되었습니다.");

            }else{
                System.out.println("존재하는 이름이 아닙니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
