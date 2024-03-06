package CoporationWork.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AdvisorRepo {
    private AdvisorInterface repo;
    private static final AdvisorRepo advisorRepo = new AdvisorRepo();

    @Autowired
    private AdvisorRepo(){
        this.repo = new AdvisorRepository();
    }

    public static AdvisorRepo getInstance(){
        return advisorRepo;
    }

    public void insertNewAdvisor(String advisorID, String pwd, String name, String phoneNumber){
        repo.insertNewAdvisor(advisorID, pwd, name, phoneNumber);
    }

    public int selectAdvisordbId(String advisorID){
        return repo.selectAdvisordbId(advisorID);
    }

    public boolean selectAdvisor(String advisorID, String pwd){
        return repo.selectAdvisor(advisorID, pwd);
    }

    public void selectAnyProject(String projectName){
        repo.selectAnyProject(projectName);
    }

    public void selectAllProjects(){
        repo.selectAllProjects();
    }

    public void updateProjectGrade(String projectName, int grade){
        repo.updateProjectGrade(projectName, grade);
    }
    public void insertNewProject(int advisordbid, int grade, String projectName, String startTime, boolean isfinish){
        repo.insertNewProject(advisordbid, grade, projectName, startTime, isfinish);
    }

    public int advisorProjectID(String projectName){
        return repo.advisorProjectID(projectName);
    }
    public int selectAllProjectsNum(){
        return repo.selectAllProjectsNum();
    }
    public boolean canUploadProject(int advisorDBid){
        return repo.canUploadProject(advisorDBid);
    }
    public void insertParticipantUser(int projectID, int userDBid){
        repo.insertParticipantUser(projectID, userDBid);
    }
    public void deleteParticipantUser(int projectID, int userDBid){
        repo.deleteParticipantUser(projectID, userDBid);
    }

    public void updateProjectName(int projectID, String changeName){
        repo.updateProjectName(projectID, changeName);
    }

    public void updateFinishProject(int projectID){
        repo.updateFinishProject(projectID);
    }

    public boolean selectParticipantUser(int projectDBid ,int userDBid){
        return repo.selectParticipantUser(projectDBid, userDBid);
    }

    public void insertAllowedUser(int projectDBid, int userDBid){
        repo.insertAllowedUser(projectDBid, userDBid);
    }

    public int selectAdvisorDBidbyProjectName(String projectName){
        return repo.selectAdvisorDBidbyProjectName(projectName);
    }

    public int selectReports(int teamID){
        return repo.selectReports(teamID);
    }

    public void updateReportConfirm(int teamID){
        repo.updateReportConfirm(teamID);
    }

    public boolean selectNotConfirmReport(int teamID){
        return repo.selectNotConfirmReport(teamID);
    }

    public int[] selectAllReports(int projectID){
        return repo.selectAllReports(projectID);
    }

    public void updateFinishDate(int projectID, String time){
        repo.updateFinishDate(projectID, time);
    }

    public void updateTeamFinishDate(int teamID, String date){
        repo.updateTeamFinishDate(teamID, date);
    }

    public void selectAllOutsource(){
        repo.selectAllOutsource();
    }

    public int selectParticipateOutsource(int dbIDnum, String outsourceName){
        return repo.selectParticipateOutsource(dbIDnum, outsourceName);
    }

    public void updateOutsourceName(int idnum, String changeName){
        repo.updateOutsourceName(idnum, changeName);
    }

    public void updateOutsourceFinishInfo(int finishInfo, int idnum){
        repo.updateOutsourceFinishInfo(finishInfo, idnum);
    }

    public void updateUntilDateInfo(String date, int idnum){
        repo.updateUntilDateInfo(date, idnum);
    }
}
