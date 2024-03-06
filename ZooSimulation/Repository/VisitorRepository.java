package springstart.ZooSimulation.Repository;

import springstart.ZooSimulation.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorRepository implements VisitorInterface{

    public void insertNewFeedback(double score1, double score2, double score3, String text){
        String sql = "insert into zoofeedback(animalhealthstate, cleanning, service, text) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, score1);
            preparedStatement.setDouble(2, score2);
            preparedStatement.setDouble(3, score3);
            preparedStatement.setString(4, text);

            preparedStatement.executeUpdate();
            System.out.println("후기가 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewZooExhibition(String topic, int opendays, String factor){
        String sql = "insert into zooexhibition(topic, opendays, factor) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, topic);
            preparedStatement.setInt(2, opendays);
            preparedStatement.setString(3, factor);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String selectZooExhibitionFactor(String topic){
        String sql = "select * from zooexhibition where topic = ?";
        String str = "";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, topic);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                str = resultSet.getString("factor");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return str;
    }
}
