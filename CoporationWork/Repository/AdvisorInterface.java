package CoporationWork.Repository;

public interface AdvisorInterface {
    void insertNewAdvisor(String advisorID, String pwd, String name, String phoneNumber);
    int selectAdvisordbId(String advisorID);
    boolean selectAdvisor(String advisorID, String pwd);
    void selectAnyProject(String projectName);
    void selectAllProjects();
    void updateProjectGrade(String projectName, int grade);
    void insertNewProject(int advisordbid, int grade, String projectName, String startTime, boolean isfinish);
    int advisorProjectID(String projectName);
    int selectAllProjectsNum();
    boolean canUploadProject(int advisorDBid);
    void insertParticipantUser(int projectID, int userDBid);
    void deleteParticipantUser(int projectID, int userDBid);
    void updateProjectName(int projectID, String changeName);
    void updateFinishProject(int projectID);
    boolean selectParticipantUser(int projectDBid ,int userDBid);
    void insertAllowedUser(int projectDBid, int userDBid);
    int selectAdvisorDBidbyProjectName(String projectName);
    int selectReports(int teamID);
    void updateReportConfirm(int teamID);
    boolean selectNotConfirmReport(int teamID);
    int[] selectAllReports(int projectID);
    void updateFinishDate(int projectID, String time);
    void updateTeamFinishDate(int teamID, String date);
    void selectAllOutsource();
    int selectParticipateOutsource(int dbIDnum, String outsourceName);
    void updateOutsourceName(int idnum, String changeName);
    void updateOutsourceFinishInfo(int finishInfo, int idnum);
    void updateUntilDateInfo(String date, int idnum);
}
