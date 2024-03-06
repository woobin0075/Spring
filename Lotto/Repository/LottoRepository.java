package Lotto.Repository;

import Lotto.Connection.DBdataUtility;
import Lotto.Domain.MyLotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

public class LottoRepository implements LottoRepoInterface {

    public void save(String name, String id, String pwd){ //회원가입
        String sql = "Insert into memberslotto(name, id, secretnum) values(?, ?, ?)";
        Connection connection = DBdataUtility.getConnection();

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, pwd);

            preparedStatement.executeLargeUpdate();

            System.out.println("회원가입이 완료되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewPoint(int table_id){ //처음 회원가입할때 포인트는 0
        String sql = "insert into memberslottopoint(member_table_id, point) values (?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, table_id);
            preparedStatement.setInt(2, 0);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int findMemberPoint(int table_id){ //해당 회원 포인트 조회
        String sql = "select * from memberslottopoint where member_table_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, table_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("point");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public boolean selectRecommendFirst(String memberID){
        String sql = "select * from memberslotto where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, memberID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getBoolean("recommend_first");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return true;
    }

    public void updatePoint(int point, int table_id){ //포인트 변경
        String sql = "update memberslottopoint set point = ? where member_table_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, point);
            preparedStatement.setInt(2, table_id);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void changeRecommendFirst(String id){
        String sql = "update memberslotto set recommend_first = true where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int findMemberTableID(String memberID){ //회원 테이블 id 찾기
        String sql = "select * from memberslotto where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, memberID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("table_id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;//아이디 존재하지 않을 때
    }

    public boolean canLogin(String id, String pwd){ //로그인 여부 확인
        String sql = "select * from memberslotto where id=? and secretnum=?";

        try {

            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println("로그인에 성공하셨습니다.");
                return true;
            }
            System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
            return false;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertLottonums(MyLotto myLotto, int papersMany){
        String sql = "insert into buylottotable (table_id, member_id, lottonum) values (?,?,?)";
        String strnum;
        int start = 1;

        try {

            Connection connection = DBdataUtility.getConnection();

            for(int i=0; i<papersMany; i++){

                for(int j=0; j<5; j++){

                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    strnum = changetoStringlottonum(myLotto.mylottoNumbers[i][j]);

                    preparedStatement.setInt(1, start);
                    preparedStatement.setString(2, myLotto.getId());
                    preparedStatement.setString(3, strnum);

                    preparedStatement.executeLargeUpdate();
                    start++;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public int[] recommendLottoNums(){ //로또 번호 추천
        String sql = "select * from historylottonums";
        HashMap<Integer, Integer> oneMap = new HashMap<>(); //key : 숫자, value : 빈도수
        HashMap<Integer, Integer> twoMap = new HashMap<>();
        HashMap<Integer, Integer> threeMap = new HashMap<>();
        HashMap<Integer, Integer> fourMap = new HashMap<>();
        HashMap<Integer, Integer> fiveMap = new HashMap<>();
        HashMap<Integer, Integer> sixMap = new HashMap<>();
        int[] recommend = new int[6];
        int one, two, three, four, five, six;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                one = resultSet.getInt("num_one");
                two = resultSet.getInt("num_two");
                three = resultSet.getInt("num_three");
                four = resultSet.getInt("num_four");
                five = resultSet.getInt("num_five");
                six = resultSet.getInt("num_six");

                oneMap.put(one, oneMap.getOrDefault(one, 0)+1);
                twoMap.put(two, twoMap.getOrDefault(two, 0)+1);
                threeMap.put(three, threeMap.getOrDefault(three, 0)+1);
                fourMap.put(four, fourMap.getOrDefault(four, 0)+1);
                fiveMap.put(five, fiveMap.getOrDefault(five, 0)+1);
                sixMap.put(six, sixMap.getOrDefault(six, 0)+1);

            }

            Iterator keySetIterator = oneMap.keySet().iterator();
            int bindo = 0;
            int rone = 0;
            int rtwo = 0, rthree = 0, rfour = 0, rfive=0, rsix=0;

            while(keySetIterator.hasNext()){
                int key = Integer.parseInt(keySetIterator.next().toString());
                //System.out.println("key : "+key);
                int value = oneMap.get(key);
                //System.out.println("value : "+value);

                if(value > bindo){
                    bindo = value;
                    rone = key;
                }
            }
            recommend[0] = rone;

            keySetIterator = twoMap.keySet().iterator();
            bindo = 0;
            while (keySetIterator.hasNext()){
                int key = Integer.parseInt(keySetIterator.next().toString());
                int value = twoMap.get(key);

                if(value > bindo){
                    bindo = value;
                    rtwo = key;
                }
            }
            recommend[1] = rtwo;

            keySetIterator = threeMap.keySet().iterator();
            bindo = 0;
            while (keySetIterator.hasNext()){
                int key = Integer.parseInt(keySetIterator.next().toString());
                int value = threeMap.get(key);

                if(value > bindo){
                    bindo = value;
                    rthree = key;
                }
            }
            recommend[2] = rthree;

            keySetIterator = fourMap.keySet().iterator();
            bindo = 0;
            while(keySetIterator.hasNext()){
                int key = Integer.parseInt(keySetIterator.next().toString());
                int value = fourMap.get(key);

                if(value > bindo){
                    bindo = value;
                    rfour = key;
                }
            }
            recommend[3] = rfour;

            keySetIterator = fiveMap.keySet().iterator();
            bindo = 0;
            while (keySetIterator.hasNext()){
                int key = Integer.parseInt(keySetIterator.next().toString());
                int value = fiveMap.get(key);

                if(value > bindo){
                    bindo = value;
                    rfive = key;
                }
            }
            recommend[4] = rfive;

            keySetIterator = sixMap.keySet().iterator();
            bindo = 0;
            while (keySetIterator.hasNext()){
                int key = Integer.parseInt(keySetIterator.next().toString());
                int value = sixMap.get(key);

                if(value > bindo){
                    bindo = value;
                    rsix = key;
                }
            }

            recommend[5] = rsix;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return recommend;
    }

    private static String changetoStringlottonum(int[] lottoNum){ //로또 숫자 문자열로 변환용
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<lottoNum.length; i++){
            stringBuilder.append(lottoNum[i]);
        }

        return stringBuilder.toString();
    }
}
