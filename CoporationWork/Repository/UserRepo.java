package CoporationWork.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepo {
    UserInterface userRepo;

    @Autowired
    public UserRepo(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public void insertNewUser(String name, String userID, String pwd, String phoneNumber){ //회원가입
        userRepo.insertNewUser(name, userID, pwd, phoneNumber);
    }

    public boolean selectUser(String userID, String pwd){ //로그인
        return userRepo.selectUser(userID, pwd);
    }

    public int selectUserdbid(String userID){
        return userRepo.selectUserdbid(userID);
    }

    public boolean isParticipate(int projectid, int userid){
        return userRepo.isParticipate(projectid, userid);
    }

    public boolean selectAllowedProjectID(int projectDBid, int userDBid){
        return userRepo.selectAllowedProjectID(projectDBid, userDBid);
    }

    public void insertVoteDate(String date, int projectDBid){
        userRepo.insertVoteDate(date, projectDBid);
    }

    public String selectProjectKing(int projectDBid){
        return userRepo.selectProjectKing(projectDBid);
    }

    public String selectPromiseDate(int projectDBid){
        return userRepo.selectPromiseDate(projectDBid);
    }

    public String selectUserID(int id){
        return userRepo.selectUserID(id);
    }

    public int[] selectParticipantDBid(int projectDBid){
        return userRepo.selectParticipantDBid(projectDBid);
    }

    public void insertVoteResult(String date, String king, int voteNum, int projectDBid){
        userRepo.insertVoteResult(date, king, voteNum, projectDBid);
    }

    public boolean selectKing(String userID, int projectDBid){
        return userRepo.selectKing(userID, projectDBid);
    }

    public void insertUserFeedback(int userDBid, int projectDBid, float score, String text){
        userRepo.insertUserFeedback(userDBid, projectDBid, score, text);
    }

    public void insertNewKing(int projectDBid, String kingname){
        userRepo.insertNewKing(projectDBid, kingname);
    }

    public void selectProjectIsFinish(String projectName){
        userRepo.selectProjectIsFinish(projectName);
    }

    public int selectProjectParticipantsNum(int projectID){
        return userRepo.selectProjectParticipantsNum(projectID);
    }

    public boolean selectRightParticipant(int projectID, int userid){
        return userRepo.selectRightParticipant(projectID, userid);
    }

    public void insertNewProjectTeam(int projectID, String teamName, boolean isfinish, String finishTime){
        userRepo.insertNewProjectTeam(projectID, teamName, isfinish, finishTime);
    }

    public int selectTeamID(int projectID, String teamName){
        return userRepo.selectTeamID(projectID, teamName);
    }

    public void insertTeamMember(int teamid, int userid){
        userRepo.insertTeamMember(teamid, userid);
    }

    public void insertReportText(int teamid, String text){
        userRepo.insertReportText(teamid, text);
    }

    public boolean selectRightTeam(int teamID, int userID){
        return userRepo.selectRightTeam(teamID, userID);
    }
}
