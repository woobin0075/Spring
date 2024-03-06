package CoporationWork.Repository;

import CoporationWork.Connection.DBdataUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvisorRepository implements AdvisorInterface {

    public void insertNewAdvisor(String advisorID, String pwd, String name, String phoneNumber){ //회원가입용
        String sql = "insert into coporateadvisor(advisor_id, pwd, name, phone_number) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, advisorID);
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

    public int selectAdvisordbId(String advisorID){
        String sql = "select * from coporateadvisor where advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, advisorID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public boolean selectAdvisor(String advisorID, String pwd){ //로그인용
        String sql = "select * from coporateadvisor where advisor_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, advisorID);
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

    public void insertNewProject(int advisordbid, int grade, String projectName, String startTime, boolean isfinish){
        String sql = "insert into advisorgrade(advisorid, grade, project_name, start_time, isfinish) values(?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisordbid);
            preparedStatement.setInt(2, grade);
            preparedStatement.setString(3, projectName);
            preparedStatement.setString(4, startTime);
            preparedStatement.setBoolean(5, isfinish);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("프로젝트가 업로드되었습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAnyProject(String projectName){
        String sql1 = "select * from advisorgrade where project_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

            preparedStatement1.setString(1, projectName);

            ResultSet resultSet1 = preparedStatement1.executeQuery();

            if(resultSet1.next()){
                System.out.println("프로젝트 이름 : "+resultSet1.getString("project_name")+", 등급 : "+resultSet1.getInt("grade"));
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllProjects(){
        String sql1 = "select * from advisorgrade";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while(resultSet1.next()){
                System.out.println("프로젝트 이름 : "+resultSet1.getString("project_name")+", 등급 : "+resultSet1.getInt("grade")+"," +
                        "업로드 시간 : "+resultSet1.getString("start_time"));
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateProjectGrade(String projectName, int grade){ //프로젝트 등급 조정
        String sql2 = "update advisorgrade set grade = ? where project_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);

            preparedStatement.setInt(1, grade);
            preparedStatement.setString(2, projectName);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("수정되었습니다.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int advisorProjectID(String projectName){ //해당 프로젝트 고유 아이디 반환
        String sql = "select * from advisorgrade where project_name = ?";
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, projectName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                num = resultSet.getInt("id");
                return num;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int selectAllProjectsNum(){ //업로드한 프로젝트 전체 갯수 반환
        String sql = "select * from advisorgrade";
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                num++;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return num;
    }

    public boolean canUploadProject(int advisorDBid){ //프로젝트 업로드 할 수 있는지 확인
        String sql = "select * from advisorgrade where advisorid = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                if(!resultSet.getBoolean("isfinish")){
                    return false;
                }
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return true;
    }

    public void insertParticipantUser(int projectID, int userDBid){ //프로젝트에 사용자 등록시키기
        String sql = "insert into projectparticipantuser(advisorgrade_id, userid) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);
            preparedStatement.setInt(2, userDBid);

            preparedStatement.executeUpdate();
            System.out.println("등록되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteParticipantUser(int projectID, int userDBid){
        String sql = "delete from projectparticipantuser where advisorgrade_id = ? and userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);
            preparedStatement.setInt(2, userDBid);

            preparedStatement.executeUpdate();
            System.out.println("등록이 해제되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateProjectName(int projectID, String changeName){ //프로젝트 이름 바꾸기
        String sql = "update advisorgrade set project_name = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, changeName);
            preparedStatement.setInt(2, projectID);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("수정되었습니다.");
            }else{
                System.out.println("다시 입력해주세요.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateFinishProject(int projectID){ //프로젝트 완료 승인
        String sql = "update advisorgrade set isfinish = true where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);

            if(preparedStatement.executeUpdate() != 0){
                System.out.println("완료되었습니다.");
            }else{
                System.out.println("다시 입력해주세요.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectParticipantUser(int projectDBid ,int userDBid){ //이미 참여하고 있는 사용자인지 확인용
        String sql = "select * from projectparticipantuser where advisorgrade_id = ? and userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);
            preparedStatement.setInt(2, userDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return false;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return true;
    }

    public void insertAllowedUser(int projectDBid, int userDBid){ //다른 사용자 프로젝트 접근 허용하기
        String sql = "insert into alloweduser(advisorgrade_id, userid) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);
            preparedStatement.setInt(2, userDBid);

            preparedStatement.executeUpdate();
            System.out.println("접근 권한에 허용을 하였습니다.");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectAdvisorDBidbyProjectName(String projectName){ //프로젝트 이름으로 관리자 id 알아내기
        String sql = "select * from advisorgrade where project_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, projectName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("advisorid");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int selectReports(int teamID){ //각 팀들이 쓴 보고서 출력하기, 미승인 있으면 1을 리턴
        String sql = "select * from projectsreport where team_id = ?";
        String isConfirm = null;
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                if(resultSet.getBoolean("isconfirm")){
                    isConfirm = "승인";
                }else{
                    isConfirm = "미승인";
                    num++;
                }

                System.out.println(isConfirm +" => "+ resultSet.getString("text"));

            }else{
                System.out.println("작성된 보고서가 없습니다.");
            }

            if(num > 0){
                return 1;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void updateReportConfirm(int teamID){ //제출된 리포트 승인 해주기
        String sql = "update projectsreport set isconfirm = true where team_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamID);

            preparedStatement.executeUpdate();
            System.out.println("승인 되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int[] selectAllReports(int projectID){ //해당 프로젝트와 관련있는 모든 team id 가져오기
        List<Integer> list = new ArrayList<>();
        String sql = "select * from projectsteam where advisorgrade_id = ?";
        int[] nums = null;

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                return null;
            }

            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        nums = list.stream().mapToInt(Integer::intValue).toArray();

        return nums;
    }

    public boolean selectNotConfirmReport(int teamID){ //미승인 된 보고서 있는지 알아내기용
        String sql = "select * from projectsreport where team_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if(!resultSet.getBoolean("isconfirm")){
                    return true;
                }
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public void updateFinishDate(int projectID, String time){
        String sql = "update advisorgrade set finishtime = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, time);
            preparedStatement.setInt(2, projectID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateTeamFinishDate(int teamID, String date){
        String sql = "update projectsteam set isfinish = true, finishtime = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, teamID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void selectAllOutsource(){
        String sql = "select * from OUTSOURCINGCUSTOMERS";
        String finish = "";
        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                if(resultSet.getBoolean("isfinish")){
                    finish = "완료";
                }else{
                    finish = "미완료";
                }

                System.out.println("외주 이름 : "+resultSet.getString("outsourcingname")+" 기한 : "+resultSet.getString("untilfinishdate")
                +" 진행상태 : "+finish);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectParticipateOutsource(int dbIDnum, String outsourceName){
        String sql = "select * from OUTSOURCINGCUSTOMERS where team_leader_idnum = ? and outsourcingname = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, dbIDnum);
            preparedStatement.setString(2, outsourceName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void updateOutsourceName(int idnum, String changeName){
        String sql = "update OUTSOURCINGCUSTOMERS set outsourcingname = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, changeName);
            preparedStatement.setInt(2, idnum);

            preparedStatement.executeUpdate();
            System.out.println("이름이 변경되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateOutsourceFinishInfo(int finishInfo, int idnum){
        String sql = "update OUTSOURCINGCUSTOMERS set isfinish = ? where id = ?";
        boolean isfinish = (finishInfo == 1);

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBoolean(1, isfinish);
            preparedStatement.setInt(2, idnum);

            preparedStatement.executeUpdate();
            System.out.println("진행상태가 변경되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateUntilDateInfo(String date, int idnum){
        String sql = "update OUTSOURCINGCUSTOMERS set untilfinishdate = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, idnum);

            preparedStatement.executeUpdate();
            System.out.println("기한일이 변경되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
