package Delivery.Repository;

import Delivery.Connection.DBdataUtility;
import Delivery.Domain.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class MasterRepository implements MasterInterface{

    public void insertMaster(String restaurantName, String pwd){ //식당 등록용
        String sql = "insert into deliveryMaster(restaurant_name, password) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, restaurantName);
            preparedStatement.setString(2, pwd);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllUsers(){ //가입된 회원 전체 조회하기
        String sql = "select * from deliveryusers";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("이름 : "+resultSet.getString("name"));
                System.out.println("아이디 : "+resultSet.getString("user_id"));
                System.out.println("핸드폰 번호 : "+resultSet.getString("phone_number"));
                System.out.println();
            }
            System.out.println();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectMaster(String restaurantName, String pwd){ //식당 주인 로그인용
        String sql = "select * from deliveryMaster where restaurant_name = ? and password = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, restaurantName);
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

    public void insertZeroAssests(int master_id){ //식당 등록할때 처음엔 자산 0원 이라고 가정
        String sql = "insert into delivercompanyassest(master_id, assest) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, master_id);
            preparedStatement.setInt(2, 0);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertFoodsAndDrinks(int masterID, String food, int price){ //메뉴 추가용
        String sql = "insert into deliverymenu(master_id, food, price) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, masterID);
            preparedStatement.setString(2, food);
            preparedStatement.setInt(3, price);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteMenu(String food){ //메뉴 삭제용
        String sql = "delete from deliverymenu where food = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, food);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Menu> selectMenusByID(int masterID){ //식당 주인 테이블 아이디로 메뉴 종류 알아내기
        ArrayList<Menu> menuList = new ArrayList<>();
        String sql = "select * from deliverymenu where master_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, masterID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                menuList.add(new Menu(resultSet.getString("food"), resultSet.getInt("price")));
                System.out.println(resultSet.getString("food")+" : "+resultSet.getInt("price")+"원");
            }
            System.out.println();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return menuList;
    }

    public ArrayList<Menu> selectRandomOrderMenu(int masterID){
        ArrayList<Menu> menuAllList = new ArrayList<>();
        ArrayList<Menu> list = new ArrayList<>();
        String sql = "select * from deliverymenu where master_id = ?";
        Random random = new Random();
        int orderNum, menuNum;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, masterID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                menuAllList.add(new Menu(resultSet.getString("food"), resultSet.getInt("price")));
            }

            orderNum = random.nextInt(menuAllList.size()) + 1;
            int i = 0;
            while(i < orderNum){
                menuNum = random.nextInt(orderNum);

                list.add(menuAllList.get(menuNum));
                i++;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public int selectBranchID(String branchname){
        String sql = "select * from DELIVERYMASTERBRANCHESINFO where branchname = ?";
        int id = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, branchname);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                id = resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }

    public void insertNewBranchSale(int branchID, String todaydate){
        String sql = "insert into deliverybranchsales(branch_id, todaydate, totalsale) values(?, ?, 0)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, branchID);
            preparedStatement.setString(2, todaydate);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int findMasterID(String restaurantName){
        String sql = "select * from deliveryMaster where restaurant_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, restaurantName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0; //존재하지 않는다.
    }

    public int selectAssests(int masterID){ //자산 조회용
        String sql = "select * from delivercompanyassest where master_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, masterID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                return resultSet.getInt(2);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void updateAssests(int masterID, int money){
        String sql = "update delivercompanyassest set assest = ? where master_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, money);
            preparedStatement.setInt(2, masterID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewBranch(int masterID, String branchname, int chefnum, int deliverymannum){
        String sql = "insert into DELIVERYMASTERBRANCHESINFO(master_id, branchname, chefnum," +
                " deliverymannum) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, masterID);
            preparedStatement.setString(2, branchname);
            preparedStatement.setInt(3, chefnum);
            preparedStatement.setInt(4, deliverymannum);

            preparedStatement.executeUpdate();
            System.out.println("새로운 지점이 추가되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectRandomBranch(int masterID){
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select * from DELIVERYMASTERBRANCHESINFO where master_id = ?";
        Random random = new Random();

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, masterID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        if(list.size() != 0){
            return list.get(random.nextInt(list.size()));
        }

        return 0;
    }

    public int selectBranchTotalSale(int branchID){
        String sql = "select * from deliverybranchsales where branch_id = ?";
        int totalsale = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, branchID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                totalsale = resultSet.getInt("totalsale");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return totalsale;
    }

    public void updateBranchTotalSale(int branchID, int total){
        String sql = "update deliverybranchsales set totalsale = ? where branch_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, total);
            preparedStatement.setInt(2, branchID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectTodayDeliveryOrdersNum(String todaydate, int masterID){
        String sql = "select * from deliveryorders where date = ? and master_id = ?";
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, todaydate);
            preparedStatement.setInt(2, masterID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                num++;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return num;
    }
}
