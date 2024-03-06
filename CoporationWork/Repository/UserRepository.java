package CoporationWork.Repository;

import CoporationWork.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository implements UserInterface{

    public void insertNewUser(String name, String userID, String pwd, String phoneNumber){ //회원가입용
        String sql = "insert into coporateuser(user_id, pwd, name, phone_number) values (?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phoneNumber);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("회원가입이 완료되었습니다.");
            }else{
                System.out.println("다시 입력해주세요.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectUser(String userID, String pwd){
        String sql = "select * from coporateuser where user_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
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

    public int selectUserdbid(String userID){
        String sql = "select * from coporateuser where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public boolean isParticipate(int projectid, int userid){ //해당 프로젝트에 참가 하고 있는건지 확인용
        String sql = "select * from projectparticipantuser where userid = ? and advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, projectid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean selectAllowedProjectID(int projectDBid, int userDBid){ //접근 권한 허용된 프로젝트가 뭔지 알아내기
        String sql = "select * from alloweduser where userid = ? and advisorgrade_id = ?";

        try {

            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);
            preparedStatement.setInt(2, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public void insertVoteDate(String date, int projectDBid){
        String sql = "insert into projectpromisedate(date, advisorgrade_id) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, projectDBid);

            preparedStatement.executeUpdate();
            System.out.println("투표날이 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String selectProjectKing(int projectDBid){ //권위자 누군지 알아내기, 권위자 있는지 확인용
        String sql = "select * from advisorgrade where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("king");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public String selectPromiseDate(int projectDBid){ //투표 약속 날짜 가져오기
        String sql = "select * from projectpromisedate where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("date");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public String selectUserID(int id){ //사용자 아이디 알아내기
        String sql = "select * from coporateuser where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("user_id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public int[] selectParticipantDBid(int projectDBid){ //프로젝트 참여자들 알아내기
        ArrayList<Integer> userDbIdList = new ArrayList<>();
        String sql = "select * from projectparticipantuser where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                userDbIdList.add(resultSet.getInt("userid"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return userDbIdList.stream().mapToInt(Integer::intValue).toArray();
    }

    public void insertVoteResult(String date, String king, int voteNum, int projectDBid){
        String sql = "insert into projectvote(date, king, vote_num, advisorgrade_id) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, king);
            preparedStatement.setInt(3, voteNum);
            preparedStatement.setInt(4, projectDBid);

            preparedStatement.executeUpdate();
            System.out.println("결과가 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectKing(String userID, int projectDBid){ //권위자인지 확인하기
        String sql = "select * from projectvote where king = ? and advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            preparedStatement.setInt(2, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public void insertNewKing(int projectDBid, String kingname){ //새로운 권위자 입력
        String sql = "update advisorgrade set king = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, kingname);
            preparedStatement.setInt(2, projectDBid);

            preparedStatement.executeUpdate();
            System.out.println("새로운 권위자로 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertUserFeedback(int userDBid, int projectDBid, float score, String text){
        String sql = "insert into projectuserfeedback(userdbid, advisorgrade_id, score, text)" +
                "values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);
            preparedStatement.setInt(2, projectDBid);
            preparedStatement.setFloat(3, score);
            preparedStatement.setString(4, text);

            preparedStatement.executeUpdate();
            System.out.println("피드백이 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectProjectIsFinish(String projectName){ //어느 사용자든 프로젝트 완료가 된건지 확인 가능함
        String sql = "select * from advisorgrade where project_name = ?";
        boolean isfinish = false;
        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, projectName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                isfinish = resultSet.getBoolean("isfinish");

                if(isfinish){
                    System.out.println("완료된 프로젝트입니다.");
                }else{
                    System.out.println("아직 진행중인 프로젝트입니다.");
                }

            }else{
                System.out.println("해당하는 프로젝트가 없습니다.");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectProjectParticipantsNum(int projectID){ //해당 프로젝트에 참여한 인원 알아내기
        String sql = "select * from projectparticipantuser where advisorgrade_id = ?";
        int num = 0;
        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                num++;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return num;
    }

    public boolean selectRightParticipant(int projectID, int userid){ //해당 프로젝트 참가자 맞는지 확인용
        String sql = "select * from projectparticipantuser where advisorgrade_id = ? and userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);
            preparedStatement.setInt(2, userid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public void insertNewProjectTeam(int projectID, String teamName, boolean isfinish, String finishTime){ //새 팀 만들기
        String sql = "insert into projectsteam(advisorgrade_id, team_name, isfinish, finishtime) values (?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);
            preparedStatement.setString(2, teamName);
            preparedStatement.setBoolean(3, isfinish);
            preparedStatement.setString(4, finishTime);

            preparedStatement.executeUpdate();

            System.out.println("새로운 팀이 생겼습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectTeamID(int projectID, String teamName){
        String sql = "select * from projectsteam where advisorgrade_id = ? and team_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);
            preparedStatement.setString(2, teamName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertTeamMember(int teamid, int userid){ //만들어진 팀에 등록시키기
        String sql = "insert into projectteam_member(team_id, userdb_id) values (?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamid);
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();
            System.out.println("팀에 등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertReportText(int teamid, String text){ //레포트 업로드용
        String sql = "insert into projectsreport(team_id, text, isconfirm) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamid);
            preparedStatement.setString(2, text);
            preparedStatement.setBoolean(3, false);

            preparedStatement.executeUpdate();
            System.out.println("레포트가 업로드 되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectRightTeam(int teamID, int userID){ //해당 팀에 속한 사용자 맞는지 확인용
        String sql = "select * from projectteam_member where team_id = ? and userdb_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamID);
            preparedStatement.setInt(2, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }
}
