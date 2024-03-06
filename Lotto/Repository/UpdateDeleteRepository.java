package Lotto.Repository;

import Lotto.Connection.DBdataUtility;
import Lotto.Domain.MyLotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class UpdateDeleteRepository implements UpdateDeleteRepoInterface {

    public void updateLottoNums(MyLotto myLotto){

        int tableIdx = 1;
        String sql = "update buylottotable set lottonum = ? where table_id = ? and member_id = ?";
        String strnum;

        try {
            Connection connection = DBdataUtility.getConnection();

            for(int i=0; i< myLotto.getPapersMany(); i++){
                for(int j=0; j<5; j++){

                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    strnum = changetoStringlottonum(myLotto.mylottoNumbers[i][j]);

                    preparedStatement.setString(1, strnum);
                    preparedStatement.setInt(2, tableIdx);
                    preparedStatement.setString(3, myLotto.getId());

                    tableIdx++;
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("수정을 완료했습니다.");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void deleteMyLottoNum(String lottoStr, String memberID){
        String sql = "delete from buylottotable where member_id = ? and lottonum = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, memberID);
            preparedStatement.setString(2, lottoStr);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("성공적으로 삭제되었습니다.");

            }else{
                System.out.println("해당하는 번호가 없습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertSeveralLottoNums(){
        Random random = new Random();
        String sql = "insert into historylottonums(num_one, num_two, num_three, num_four, num_five, num_six) values(?,?,?,?,?,?)";
        int one, two, three, four, five, six;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for(int i=0; i<300; i++){
                one = random.nextInt(50)+1;
                two = random.nextInt(50)+1;
                three = random.nextInt(50)+1;
                four = random.nextInt(50)+1;
                five = random.nextInt(50)+1;
                six = random.nextInt(50)+1;

                preparedStatement.setInt(1, one);
                preparedStatement.setInt(2, two);
                preparedStatement.setInt(3, three);
                preparedStatement.setInt(4, four);
                preparedStatement.setInt(5, five);
                preparedStatement.setInt(6, six);

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static String changetoStringlottonum(int[] lottoNum){ //로또 숫자 문자열로 변환용
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<lottoNum.length; i++){
            stringBuilder.append(lottoNum[i]);
        }

        return stringBuilder.toString();
    }
}
