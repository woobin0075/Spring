package CoporationWork.Repository;

public interface UserInterface {
    void insertNewUser(String name, String userID, String pwd, String phoneNumber);
    boolean selectUser(String userID, String pwd);
    int selectUserdbid(String userID);
    boolean isParticipate(int projectid, int userid);
    boolean selectAllowedProjectID(int projectDBid, int userDBid);
    void insertVoteDate(String date, int projectDBid);
    String selectProjectKing(int projectDBid);
    String selectPromiseDate(int projectDBid);
    String selectUserID(int id);
    int[] selectParticipantDBid(int projectDBid);
    void insertVoteResult(String date, String king, int voteNum, int projectDBid);
    boolean selectKing(String userID, int projectDBid);
    void insertUserFeedback(int userDBid, int projectDBid, float score, String text);
    void insertNewKing(int projectDBid, String kingname);
    void selectProjectIsFinish(String projectName);
    int selectProjectParticipantsNum(int projectID);
    boolean selectRightParticipant(int projectID, int userid);
    void insertNewProjectTeam(int projectID, String teamName, boolean isfinish, String finishTime);
    int selectTeamID(int projectID, String teamName);
    void insertTeamMember(int teamid, int userid);
    void insertReportText(int teamid, String text);
    boolean selectRightTeam(int teamID, int userID);
}
