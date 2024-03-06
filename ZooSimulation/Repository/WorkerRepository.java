package springstart.ZooSimulation.Repository;

import springstart.ZooSimulation.Connection.DBdataUtility;
import springstart.ZooSimulation.Domain.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WorkerRepository implements WorkerInterface {

    Animal animal = new Animal();

    public void insertWorkerInfo(String name, String id, String pwd){
        String sql = "insert into zooworkersaccounts(name, workerid, pwd) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, pwd);

            preparedStatement.executeUpdate();
            System.out.println("동물원 직원으로 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectLoginWorker(String id, String pwd){
        String sql = "select * from zooworkersaccounts where workerid = ? and pwd = ?";

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

    public void insertNewAnimal(String kindof, String kindof2,  String name, int age){
        String sql = "insert into zooanimalsdb(kindof, kindof2, name, age, healthstate) values(?,?,?,?,10)";
        //건강 상태는 입주할땐 10로 고정

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, kindof);
            preparedStatement.setString(2, kindof2);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, age);

            preparedStatement.executeUpdate();
            System.out.println("새로운 동물이 입주하셨습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public int selectAnimalDBid(String kindOf, String name){
        String sql = "select * from zooanimalsdb where kindof = ? and name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, kindOf);
            preparedStatement.setString(2, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void insertNewCage(String cageName){ //우리 확장
        String sql = "insert into zoocageinfo(cage_name) values(?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, cageName);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectCageDbid(String cageName){
        String sql = "select * from zoocageinfo where cage_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, cageName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertCageInBranch(int branchID, int cageID){ //지어진 동물원 구역안에 새로운 우리 확장
        String sql = "insert into zoobranchincage(branch_id, cage_id) values(?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, branchID);
            preparedStatement.setInt(2, cageID);

            preparedStatement.executeUpdate();
            System.out.println("우리가 추가되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewZooBranch(String branchName){
        String sql = "insert into zoobranchinfo(branch_name) values(?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, branchName);

            preparedStatement.executeUpdate();
            System.out.println("새로운 구역이 추가되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectZooBranchid(String branchName){
        String sql = "select * from zoobranchinfo where branch_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, branchName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public int selectAnimalHealthState(String kindOf, String name){
        String sql = "select * from zooanimalsdb where kindof = ? and name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, kindOf);
            preparedStatement.setString(2, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("healthstate");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int selectBranchNum(){ //구역 수 가져오기
        String sql = "select * from zoobranchinfo";
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                num++;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return num;
    }

    public int selectCageNum(){ //우리 수 출력
        String sql = "select * from zoocageinfo";
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                num++;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return num;
    }

    public void selectAnimalInfoByid(int animalDBid){
        String sql = "select * from zooanimalsdb where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, animalDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println("종류 : "+resultSet.getString("kindof"));
                System.out.println("품종 : "+resultSet.getString("kindof2"));
                System.out.println("이름 : "+resultSet.getString("name"));
                System.out.println("나이 : "+resultSet.getInt("age"));
                System.out.println("건강 상태 : "+resultSet.getInt("healthState"));

                //무슨 행동 하는 중
                animal.behave(resultSet.getString("kindof2"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> selectAllBranchId(){
        String sql = "select * from zoobranchinfo";
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<Integer> selectAllCagesIdinBranch(int branchID){
        String sql = "select * from zoobranchincage where branch_id = ?";
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, branchID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getInt("cage_id"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public void insertAnimalinCageinBranch(int cageId, int branchId, int animalId){
        String sql = "insert into zoocageinanimal(cage_id, animal_id, branch_id) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, cageId);
            preparedStatement.setInt(2, animalId);
            preparedStatement.setInt(3, branchId);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> selectAllanimalsIdinCage(int cageId, int branchId){
        String sql = "select * from zoocageinanimal where cage_id = ? and branch_id = ?";
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, cageId);
            preparedStatement.setInt(2, branchId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getInt("animal_id"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public String selectBranchNamebyId(int branchId){
        String sql = "select * from zoobranchinfo where id = ?";
        String name = "";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, branchId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                name = resultSet.getString("branch_name");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return name;
    }

    public String selectCageNameByid(int cageid){
        String sql = "select * from zoocageinfo where id = ?";
        String name = "";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, cageid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                name = resultSet.getString("cage_name");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return name;
    }

    public void selectAllHealthState(){
        String sql = "select * from zooanimalsdb";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("이름 : "+resultSet.getString("name"));
                System.out.println("품종 : "+resultSet.getString("kindof"));
                System.out.println("나이 : "+resultSet.getInt("age"));
                System.out.println("건강 상태 : "+resultSet.getInt("healthstate"));
                System.out.println();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public HashMap<Integer, Integer> selectAllAnimalsDBidAndHealthDown(){
        String sql = "select * from zooanimalsdb";
        HashMap<Integer, Integer> map = new HashMap<>();

         try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                map.put(resultSet.getInt("id"), resultSet.getInt("healthstate"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

         return map;
    }

    public void updateRandomAnimalsHealthDown(HashMap<Integer, Integer> map){ //랜덤으로 2마리 뽑아서 건강 악화
        List<Integer> keylist = new ArrayList<>(map.keySet());
        String sql = "update zooanimalsdb set healthstate = ? where id = ?";
        Random random = new Random();
        int randomNum;
        int newhealthState = 0;
        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for(int i=0; i<2; i++){

                if(keylist.size() == 0)
                    continue;

                randomNum = random.nextInt(keylist.size());
                newhealthState = map.get(keylist.get(randomNum)) - 1;
                preparedStatement.setInt(1, newhealthState);
                preparedStatement.setInt(2, keylist.get(randomNum));

                preparedStatement.executeUpdate();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> selectHealthStateZeroAnimalsDBid(){
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select * from zooanimalsdb where healthstate = 0";

        try{
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public void deleteDiedAnimals(ArrayList<Integer> listDBids){
        String sql = "delete from zoocageinanimal where animal_id = ?";
        String sql2 = "delete from zooanimalsdb where id = ?";
        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

            for(int i=0; i<listDBids.size(); i++){
                preparedStatement.setInt(1, listDBids.get(i));
                preparedStatement2.setInt(1, listDBids.get(i));

                preparedStatement.executeUpdate();
                preparedStatement2.executeUpdate();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectCorrectBranchandCage(int branchId, int cageId){
        String sql = "select * from zoobranchincage where branch_id = ? and cage_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, branchId);
            preparedStatement.setInt(2, cageId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public void insertNewImprovement(String improve){
        String sql = "insert into ZOOIMPROVEMENTSWRITE(improve, finish) values(?, false)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, improve);

            preparedStatement.executeUpdate();
            System.out.println("등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllImprovements(){
        String sql = "select * from ZOOIMPROVEMENTSWRITE";
        boolean check = false;
        String print = "";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                check = resultSet.getBoolean("finish");
                print = check ? "실행 완료" : "미실행";

                System.out.println(resultSet.getInt("id")+". "+resultSet.getString("improve")+", "+print);

            }
            System.out.println();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectImprovementFinish(int num){
        String sql = "select * from ZOOIMPROVEMENTSWRITE where id = ?";
        boolean finish = false;
        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, num);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                finish = resultSet.getBoolean("finish");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return finish;
    }

    public void updateFinishImprovement(int num){
        String sql = "update ZOOIMPROVEMENTSWRITE set finish = true where id = ?";

        try {

            if(selectImprovementFinish(num)){
                System.out.println("이미 실행완료한 개선점 입니다.");

            }else{
                Connection connection = DBdataUtility.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, num);

                preparedStatement.executeUpdate();
                System.out.println("등록되었습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectAllAnimalsHealth(){
        String sql = "select * from ZOOANIMALSDB";

        try {

            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                if(resultSet.getInt("healthstate") < 8){
                    return false;
                }
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return true;
    }
}
