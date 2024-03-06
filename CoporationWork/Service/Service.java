package CoporationWork.Service;

import CoporationWork.Domain.Advisor;
import CoporationWork.Domain.Customer;
import CoporationWork.Domain.User;
import CoporationWork.Repository.AdvisorRepo;
import CoporationWork.Repository.CustomerRepo;
import CoporationWork.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class Service {
    UserRepo userRepo;
    AdvisorRepo advisorRepo;
    CustomerRepo customerRepo;

    public Service(UserRepo userRepo, AdvisorRepo advisorRepo, CustomerRepo customerRepo){
        this.userRepo = userRepo;
        this.advisorRepo = advisorRepo;
        this.customerRepo = customerRepo;
    }

    private void makeUserAccount(String name, String userID, String pwd, String phoneNumber){ //회원가입
        userRepo.insertNewUser(name, userID, pwd, phoneNumber);
    }

    private void makeAdvisorAccount(String advisorID, String pwd, String name, String phoneNumber){ //관리자 회원가입
        advisorRepo.insertNewAdvisor(advisorID, pwd, name, phoneNumber);
    }

    private boolean advisorLogin(String advisorID, String pwd){ //관리자 로그인
        return advisorRepo.selectAdvisor(advisorID, pwd);
    }

    private void printAnyProject(String projectName){ //관리자용 프로젝트 검색
        advisorRepo.selectAnyProject(projectName);
    }

    private void printAllProjects(){
        advisorRepo.selectAllProjects();
    }

    private void changeProjectGrade(String projectName, int grade){ //등급 바꾸기
        advisorRepo.updateProjectGrade(projectName, grade);
    }

    private boolean userLogin(String userID, String pwd){ //로그인
        return userRepo.selectUser(userID, pwd);
    }

    private void uploadNewAdvisorProject(int advisorid, int grade, String projectName, String startTime, boolean isfinish){
        //관리자 프로젝트 업로드
        advisorRepo.insertNewProject(advisorid, grade, projectName, startTime, isfinish);
    }

    private int countProjectsNum(){
        return advisorRepo.selectAllProjectsNum();
    }

    private void registerUser(int projectID, int userID){
        advisorRepo.insertParticipantUser(projectID, userID);
    }

    private void removeUser(int projectID, int userID){
        advisorRepo.deleteParticipantUser(projectID, userID);
    }

    private void modifyProjectName(int projectID, String projectName){
        advisorRepo.updateProjectName(projectID, projectName);
    }

    private void finishedProject(int projectID){
        advisorRepo.updateFinishProject(projectID);
    }

    private boolean doseHeParticipateThis(int projectDBid ,int userDBid){
        return advisorRepo.selectParticipantUser(projectDBid, userDBid);
    }

    private void canAllowReadProject(int projectDBid ,int userDBid){
        advisorRepo.insertAllowedUser(projectDBid, userDBid);

    }

    private boolean isheAllowedProject(int projectDBid ,int userDBid){
        return userRepo.selectAllowedProjectID(projectDBid, userDBid);
    }

    private void promiesVoteDate(String date, int projectDBid){
        userRepo.insertVoteDate(date, projectDBid);
    }

    private String whosProjectKing(int projectDBid){
        return userRepo.selectProjectKing(projectDBid);
    }

    private String bringPromiseDate(int id){
        return userRepo.selectPromiseDate(id);
    }

    private String bringUserID(int dbid){
        return userRepo.selectUserID(dbid);
    }

    private int[] allParticipantsUser(int projectDBid){
        return userRepo.selectParticipantDBid(projectDBid);
    }

    private void addVoteResult(String date, String king, int voteNum, int projectDBid){
        userRepo.insertVoteResult(date, king, voteNum, projectDBid);
    }

    private void addFeedback(int userDBid, int projectDBid, float score, String text){
        userRepo.insertUserFeedback(userDBid, projectDBid, score, text);
    }

    private void confirmProjectFinish(String projectName){
        userRepo.selectProjectIsFinish(projectName);
    }

    private void inputNewKing(int projectDBid, String kingName){
        userRepo.insertNewKing(projectDBid, kingName);
    }

    private int getProjectParticipantsNum(int projectDBid){
        return userRepo.selectProjectParticipantsNum(projectDBid);
    }

    private void makeNewTeam(int projectID, String teamName, boolean isfinish, String finishTime){
        userRepo.insertNewProjectTeam(projectID, teamName, isfinish, finishTime);
    }

    private void addNewteamMember(int teamid, int userid){
        userRepo.insertTeamMember(teamid, userid);
    }

    private void addProjectReport(int teamid, String text){
        userRepo.insertReportText(teamid, text);
    }

    private int printReports(int teamid){
        return advisorRepo.selectReports(teamid);
    }

    private void confirmReports(int teamid){
        advisorRepo.updateReportConfirm(teamid);
    }

    private void finishProject(int projectID, String time){
        advisorRepo.updateFinishDate(projectID, time);
    }

    private boolean isThereNotConfrimReports(int projectID){
        int[] ids = advisorRepo.selectAllReports(projectID);
        int num = 0;

        if(ids == null){
            return false;
        }

        for(int i=0; i<ids.length; i++){
            if(advisorRepo.selectNotConfirmReport(ids[i])){
                num++;
            }
        }

        if(num > 0){
            return false;
        }else{
            return true;
        }
    }

    private void teamWorkFinish(int teamID, String date){
        advisorRepo.updateTeamFinishDate(teamID, date);
    }

    private void customerMakeAccount(String customerID, String pwd, String name, String phoneNumber){
        customerRepo.insertNewCustomer(customerID, pwd, name, phoneNumber);
    }

    private boolean customerLogin(String customerID, String pwd){
        return customerRepo.selectCustomer(customerID, pwd);
    }

    private boolean enterSiteForCustomer(String projectName){ //사이트 입장용
        return customerRepo.selectProjectForCustomer(projectName);
    }

    private int siteFeedbackGoodNum(int id){
        return customerRepo.selectFeedbackGood(id);
    }

    private int siteFeedbackbadNum(int id){
        return customerRepo.selectFeedbackBad(id);
    }

    private void upgradeFeedbackGood(int id, int num){
        customerRepo.updateFeedbackGood(id, num);
    }

    private void upgradeFeedbackBad(int id, int num){
        customerRepo.updateFeedbackBad(id, num);
    }

    private void addCustomerReview(int projectDbid, int customerDbid, String review){
        customerRepo.insertCustomerReview(projectDbid, customerDbid, review);
    }

    private void orderNewOutsource(int teamLeaderDBidnum, int architectUserDBidnum, int cutomerDBidnum, String untilfinishdate, String outsourcingName){
        customerRepo.insertNewOutsource(teamLeaderDBidnum, architectUserDBidnum, cutomerDBidnum, untilfinishdate, outsourcingName);
    }

    private void printAllOutsourcesInfo(){
        advisorRepo.selectAllOutsource();
    }

    private int myParticipateOutsource(int dbIDnum, String outsourceName){
        return advisorRepo.selectParticipateOutsource(dbIDnum, outsourceName);
    }

    private void changeOutsourceName(int idnum, String changeName){
        advisorRepo.updateOutsourceName(idnum, changeName);
    }

    private void changeFinishInfo(int finishInfo, int idnum){
        advisorRepo.updateOutsourceFinishInfo(finishInfo, idnum);
    }

    private void changeFinishDate(String date, int idnum){
        advisorRepo.updateUntilDateInfo(date, idnum);
    }

    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        boolean isUserLogin = false;
        boolean isAdvisorLogin = false;
        int select;
        User user = null;
        Advisor advisor = null;
        Customer customer = null;

        while (true){
            System.out.printf("1.사용자 로그인/회원가입, 2.관리자 로그인/회원가입, 3.고객 로그인/회원가입 => ");
            select = sc.nextInt();

            if(select == 1){
                user = userLoginOrMakeAccount();

                if(user != null){
                    isUserLogin = true;
                }else{
                    isUserLogin = false;
                }

                if(isUserLogin){ //로그인 성공시
                    whileSelectForUser(user);
                }

            }else if(select == 2){
                advisor = advisorLoginOrMakeAccount();

                if(advisor != null){
                    isAdvisorLogin = true;
                }else{
                    isAdvisorLogin = false;
                }

                if(isAdvisorLogin){
                    whileSelectForAdvisor(advisor);
                }

            }else if(select == 3){
                customer = customerLoginMakeAccount();

                whileSelectForCustomer(customer);
            }
        }
    }

    private Customer customerLoginMakeAccount(){
        Scanner sc = new Scanner(System.in);
        int select;
        String inputID, inputPwd, inputName, inputPhoneNum;
        Customer customer;

        while(true){
            System.out.printf("1.회원가입, 2.로그인 => ");
            select = sc.nextInt();

            if(select == 1){
                System.out.printf("이름을 입력하세요. : ");
                inputName = sc.next();
                System.out.printf("사용할 아이디를 입력하세요. : ");
                inputID = sc.next();
                System.out.printf("사용할 비밀번호를 입력하세요. : ");
                inputPwd = sc.next();
                System.out.printf("사용할 핸드폰 번호를 입력하세요. : ");
                inputPhoneNum = sc.next();

                customerMakeAccount(inputID, inputPwd, inputName, inputPhoneNum);

            }else if(select == 2){

                System.out.printf("아이디를 입력하세요. : ");
                inputID = sc.next();
                System.out.printf("비밀번호를 입력하세요. : ");
                inputPwd = sc.next();

                if(customerLogin(inputID, inputPwd)){

                    System.out.println("로그인에 성공하셨습니다.");
                    customer = new Customer(inputID);
                    customer.setId(customerRepo.selectCustomerDBid(inputID));

                    return customer;
                }else{
                    System.out.println("아이디 또는 비밀번호를 잘 못 입력하셨습니다.");
                }
            }
        }
    }

    private void whileSelectForCustomer(Customer customer){
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        int select, selectGoodOrBad;
        String inputSiteName, inputReview;
        String teamleaderid, architectuserid;
        int teamleaderdbidnum, architectuseridnum;
        String inputOutsourceName, untildate;
        int projectdbid, goodNum, badNum;
        int inputYear, inputMonth, inputDay;
        int customerdbid;
        //LocalDateTime dateTime = null;

        while (true){
            System.out.printf("1.사이트 입장, 2.피드백, 3.리뷰, 4.외주 신청, 5.뒤로 => ");
            select = sc.nextInt();

            if(select == 1){
                System.out.printf("입력 : ");
                inputSiteName = sc.next();

                if(enterSiteForCustomer(inputSiteName)){
                    System.out.println("사이트 입장!!!");
                }else{
                    System.out.println("존재 하지 않는 사이트 이거나 완성되지 않았습니다.");
                }

            }else if(select == 2){
                System.out.printf("사이트 이름 입력 : ");
                inputSiteName = sc.next();

                if(enterSiteForCustomer(inputSiteName)){

                    projectdbid = advisorRepo.advisorProjectID(inputSiteName);
                    System.out.printf("1.좋아요, 2.싫어요 => ");
                    selectGoodOrBad = sc.nextInt();

                    if(selectGoodOrBad == 1){

                        goodNum = siteFeedbackGoodNum(projectdbid);

                        upgradeFeedbackGood(projectdbid, goodNum);

                    }else if(selectGoodOrBad == 2){
                        badNum = siteFeedbackbadNum(projectdbid);

                        upgradeFeedbackBad(projectdbid, badNum);
                    }

                }else{
                    System.out.println("존재 하지 않는 사이트 이거나 완성되지 않았습니다.");
                }

            }else if(select == 3){
                System.out.printf("사이트 이름 입력 : ");
                inputSiteName = sc.next();

                if(enterSiteForCustomer(inputSiteName)){

                    projectdbid = advisorRepo.advisorProjectID(inputSiteName);
                    customerdbid = customerRepo.selectCustomerDBid(customer.getCustomerID());

                    System.out.printf("리뷰 입력 : ");
                    inputReview = scStr.nextLine();

                    addCustomerReview(projectdbid, customerdbid, inputReview);

                }else{
                    System.out.println("존재 하지 않는 사이트 이거나 완성되지 않았습니다.");
                }

            }else if(select == 4){
                System.out.printf("외주할 프로젝트 이름을 입력하세요. : ");
                inputOutsourceName = scStr.nextLine();
                System.out.println("기한 입력");
                System.out.printf("년도를 입력하세요. : ");
                inputYear = sc.nextInt();
                System.out.printf("월을 입력하세요. : ");
                inputMonth = sc.nextInt();
                System.out.printf("일을 입력하세요. : ");
                inputDay = sc.nextInt();
                System.out.println();

                System.out.printf("개발 팀장 아이디를 입력하세요. : ");
                teamleaderid = sc.next();
                System.out.printf("설계자로 일할 참여자 아이디를 입력하세요. : ");
                architectuserid = sc.next();

                teamleaderdbidnum = advisorRepo.selectAdvisordbId(teamleaderid);
                architectuseridnum = userRepo.selectUserdbid(architectuserid);

                if(teamleaderdbidnum != 0 && architectuseridnum != 0){

                    untildate = dateToString(LocalDateTime.of(inputYear, inputMonth, inputDay, 0 ,0)).substring(0,10);

                    orderNewOutsource(teamleaderdbidnum, architectuseridnum, customer.getId(), untildate, inputOutsourceName);

                }else{
                    System.out.println("해당하는 사람이 없습니다.");
                }
            }else if(select == 5){
                break;
            }
        }
    }

    private void whileSelectForAdvisor(Advisor advisor){
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        int select, inputGrade, reportConfirmSelect;
        String inputProjectName, inputUserID, inputNewProjectName, inputTeamName;
        final int startGrade = 9;
        LocalDateTime now = null;
        String startTime = null;
        String finishTime = null;
        int totalProjectsNum = 0;
        int userdbid = 0, projectdbid = 0;
        int teamdbid = 0;
        String inputOutsourceName, untildate;
        int outsourceidNum;
        int selectRewrite, selectFinish;
        int inputYear, inputMonth, inputDay;

        while (true){
            System.out.println("1.프로젝트 업로드, 2.전체 프로젝트 목록, 3.프로젝트 찾기, 4.프로젝트 등급 바꾸기, 5.프로젝트 이름 바꾸기, " +
                    "6.프로젝트에 사용자 할당, " );
            System.out.printf("7.프로젝트에 있는 사용자 제거, 8.프로젝트 완료 승인, " +
                    "9.다른 사용자 읽기 권한 접근 허용, 10.보고서 승인, 11.역대 외주 목록, 12.참여한 외주 정보 변경, 13.뒤로 => ");
            select = sc.nextInt();

            if(select == 1){

                totalProjectsNum = countProjectsNum();

                if(totalProjectsNum == 0 || advisorRepo.canUploadProject(advisor.getId())){ //이전에 생성한 프로젝트 중 완료 안 된거 있으며 업로드 불가
                    System.out.printf("새 프로젝트 이름을 입력하세요. : ");
                    inputProjectName = sc.next();
                    now = LocalDateTime.now();
                    startTime = dateToString(now);

                    uploadNewAdvisorProject(advisor.getId(), startGrade, inputProjectName, startTime, false);
                }else {
                    System.out.println("아직 미완성인 프로젝트가 있어서 업로드 할 수 없습니다.");
                }

            }else if(select == 2){

                printAllProjects();

            }else if(select == 3){

                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                printAnyProject(inputProjectName);

            }else if(select == 4){

                System.out.printf("바꾸고 싶은 프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                System.out.printf("바꿀 등급을 입력하세요. : ");
                inputGrade = sc.nextInt();

                changeProjectGrade(inputProjectName, inputGrade);

            }else if(select == 5){
                System.out.println("기존 프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                System.out.println("바꾸고 싶은 이름을 입력하세요. : ");
                inputNewProjectName = sc.next();

                projectdbid = advisorRepo.advisorProjectID(inputProjectName);
                modifyProjectName(projectdbid, inputNewProjectName);
            }
            else if(select == 6){

                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                System.out.printf("참여할 사용자의 아이디를 입력하세요. : ");
                inputUserID = sc.next();

                projectdbid = advisorRepo.advisorProjectID(inputProjectName);
                userdbid = userRepo.selectUserdbid(inputUserID);

                if(userdbid != 0 && projectdbid != 0){
                    registerUser(projectdbid, userdbid);

                }else{
                    System.out.println("해당하는 유저나 프로젝트가 없습니다.");
                }

            }else if(select == 7){

                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                System.out.printf("사퇴 사용자의 아이디를 입력하세요. : ");
                inputUserID = sc.next();

                projectdbid = advisorRepo.advisorProjectID(inputProjectName);
                userdbid = userRepo.selectUserdbid(inputUserID);

                if(userdbid != 0 && projectdbid != 0){
                    removeUser(projectdbid, userdbid);

                }else{
                    System.out.println("해당하는 유저나 프로젝트가 없습니다.");
                }

            }else if(select == 8){

                //보고서 제출 모두 승인 했는지 확인
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectdbid = advisorRepo.advisorProjectID(inputProjectName);

                if(isThereNotConfrimReports(projectdbid)){

                    now = LocalDateTime.now();
                    finishTime = dateToString(now);
                    finishProject(projectdbid, finishTime);
                    finishedProject(projectdbid);
                    //고객 평가 받기 시작
                    customerRepo.insertFeedbackStart(projectdbid);
                }else{

                    System.out.println("승인이 안된 리포트가 있거나 리포트가 아직 업로드가 안 됐습니다.");
                }

            }else if(select == 9){

                System.out.printf("접근을 허락할 프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                System.out.printf("접근을 허락할 다른 사용자 아이디를 입력하세요. : ");
                inputUserID = sc.next();

                projectdbid = advisorRepo.advisorProjectID(inputProjectName);
                userdbid = userRepo.selectUserdbid(inputUserID);

                if(projectdbid == 0 && userdbid == 0){
                    System.out.println("해당하는 프로젝트나 사용자가 없습니다.");

                }else{

                    if(doseHeParticipateThis(projectdbid, userdbid)){

                        canAllowReadProject(projectdbid, userdbid);

                    }else{
                        System.out.println("이미 해당 프로젝트에 참여자로 있는 사용자입니다.");
                    }
                }

            }else if(select == 10){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                now = LocalDateTime.now();
                projectdbid = advisorRepo.advisorProjectID(inputProjectName);

                if(advisor.getId() == advisorRepo.selectAdvisorDBidbyProjectName(inputProjectName) && projectdbid > 0){

                    System.out.printf("소속 팀 이름을 입력하세요. : ");
                    inputTeamName = sc.next();

                    teamdbid = userRepo.selectTeamID(projectdbid, inputTeamName);

                    if(teamdbid > 0){

                        if(printReports(teamdbid) == 1){
                            System.out.printf("해당 보고서를 승인 하실거면 1번을 아니면 그외 다른 숫자를 입력하세요. : ");
                            reportConfirmSelect = sc.nextInt();

                            if(reportConfirmSelect == 1){
                                confirmReports(teamdbid);
                                teamWorkFinish(teamdbid, dateToString(now)); //team db에 쓰는거
                            }
                        }

                    }else{
                        System.out.println("해당하는 팀이 없습니다.");
                    }

                }else{
                    System.out.println("해당 프로젝트가 없거나 관리자가 아닙니다.");
                }
            }else if(select == 11){

                printAllOutsourcesInfo();

            }else if(select == 12){

                System.out.printf("참여한 외주 이름을 입력하세요. : ");
                inputOutsourceName = scStr.nextLine();

                outsourceidNum = myParticipateOutsource(advisor.getId(), inputOutsourceName);

                if(outsourceidNum > 0){

                    System.out.println("바꾸고 싶은 정보를 선택하세요.");
                    System.out.printf("1.외주 이름, 2.완료 상태, 3.기한 설정 => ");

                    selectRewrite = sc.nextInt();

                    if(selectRewrite == 1){
                        System.out.printf("외주 이름을 입력하세요. : ");
                        inputOutsourceName = scStr.nextLine();

                        changeOutsourceName(advisor.getId(), inputOutsourceName);

                    }else if(selectRewrite == 2){

                        System.out.printf("1.완료, 2.미완료 => ");
                        selectFinish = sc.nextInt();

                        changeFinishInfo(selectFinish, advisor.getId());

                    }else if(selectRewrite == 3){
                        System.out.printf("년도를 입력하세요. : ");
                        inputYear = sc.nextInt();
                        System.out.printf("월을 입력하세요. : ");
                        inputMonth = sc.nextInt();
                        System.out.printf("일을 입력하세요. : ");
                        inputDay = sc.nextInt();

                        untildate = dateToString(LocalDateTime.of(inputYear, inputMonth, inputDay, 0,0)).substring(0,10);
                        changeFinishDate(untildate, advisor.getId());
                    }

                }else{
                    System.out.println("해당 외주의 팀장이 아닙니다.");
                }

            }else if(select == 13){
                break;
            }

        }
    }

    private String dateToString(LocalDateTime date){
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int hour = date.getHour();
        int minute = date.getMinute();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(year);
        stringBuilder.append("-");

        if(month <= 9){
            stringBuilder.append("0");
        }

        stringBuilder.append(month);
        stringBuilder.append("-");

        if(day <= 9){
            stringBuilder.append("0");
        }
        stringBuilder.append(day);
        stringBuilder.append(" ");
        stringBuilder.append(hour);
        stringBuilder.append(":");
        stringBuilder.append(minute);

        return stringBuilder.toString();
    }

    private LocalDateTime intToDate(int year, int month, int day){
        LocalDateTime date = LocalDateTime.of(year, month, day, 0, 0);

        return date;
    }

    private void whileSelectForUser(User user){ //사용자 로그인 이후
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        int select, selectMember;
        String inputProjectName, inputDate, person, userID, inputUserID, inputText, inputTeamName;
        float inputScore;
        int userdbid, projectid, teamid;
        String reportText;
        int answer = 0;
        LocalDateTime now  = LocalDateTime.now(); //투표할때 사용하기
        LocalDateTime date = null;
        int year, month, day;
        int maxVoteNum = 0;
        int peopleNum = 0;
        String king = null;
        int[] allParticipantsDBid;
        Map<String, Integer> userVoteMap = new HashMap<>();
        ArrayList<String> userList = new ArrayList<>();
        int idx = 0;
        boolean isheKing = false;

        while (true){
            System.out.printf("1.참여한 프로젝트 열람, 2.다른 프로젝트 열람, 3.투표 약속 정하기, " +
                    "4.투표 시작, 5.참여 프로젝트 피드백 남기기, 6.프로젝트 완료 승인(권위자일 경우), 7.프로젝트에 사용자 추가(권위자일 경우)" +
                    "8.프로젝트 완료도 확인, 9.팀 나누기, 10.보고서 제출 text 작성, 11.뒤로 => ");
            select = sc.nextInt();

            if(select == 1){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);
                userdbid = user.getId();

                if(userRepo.isParticipate(projectid, userdbid)){

                    System.out.printf("관리자에게 승인을 받았으면 1번을 아니면 그 외 다른 번호를 입력하세요. : ");
                    answer = sc.nextInt();

                    if(answer == 1){
                        printAnyProject(inputProjectName);
                    }

                }else{
                    System.out.println("참여한 프로젝트가 아닙니다.");
                }

            }else if(select == 2){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);
                userdbid = user.getId();

                if(projectid != 0 && isheAllowedProject(projectid, userdbid)){
                    printAnyProject(inputProjectName);

                }else{
                    System.out.println("접근 권한이 없거나 해당하는 프로젝트가 없습니다.");
                }

            }else if(select == 3){

                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);
                person = whosProjectKing(projectid);

                if(projectid != 0 && person == null){ //권위자 결과 아직 안 됐고 해당 프로젝트가 있으면
                    inputDate = inputRealDate();

                    promiesVoteDate(inputDate, projectid);

                }else{
                    System.out.println("해당하는 프로젝트가 없거나 이미 투표가 되었습니다.");
                }


            }else if(select == 4){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);
                inputDate = bringPromiseDate(projectid);

                if(projectid == 0){
                    System.out.println("해당하는 프로젝트가 없습니다.");
                    continue;
                }

                if(inputDate == null){
                    System.out.println("이미 투표가 끝났거나 참여한 프로젝트가 아니거나 투표가 끝났습니다.");
                    continue;
                }

                year = Integer.parseInt(inputDate.substring(0, 4));
                month = Integer.parseInt(inputDate.substring(5,7));
                day = Integer.parseInt(inputDate.substring(8));

                date = intToDate(year, month, day);
                if(now.isAfter(date)){ //오늘이 투표 약속날짜 이후라면
                    //투표 시작
                    allParticipantsDBid = allParticipantsUser(projectid);

                    System.out.println("투표 명단");
                    for(int i=0; i< allParticipantsDBid.length; i++){
                        userID = bringUserID(allParticipantsDBid[i]);
                        userList.add(userID);
                        System.out.println(i+1+" : "+userID);
                        userVoteMap.put(userID, 0);
                    }

                    idx = 0;
                    System.out.println("한 명씩 나와서 아이디를 입력해서 투표해주세요.");
                    System.out.println();
                    while (idx < userList.size()){
                        System.out.printf("아이디 입력 : ");
                        inputUserID = sc.next();

                        if(userVoteMap.containsKey(inputUserID)){
                            userVoteMap.put(inputUserID, userVoteMap.get(inputUserID)+1);

                            if(maxVoteNum < userVoteMap.get(inputUserID)){
                                maxVoteNum = userVoteMap.get(inputUserID);
                                king = inputUserID;
                            }

                        }else{
                            System.out.println("해당하는 사람이 없습니다. 다시 입력해주세요.");
                            continue;
                        }

                        idx++;
                    }
                    //투표 결과 저장
                    addVoteResult(inputDate, king, maxVoteNum, projectid);
                    //권위자 등록
                    inputNewKing(projectid, king);

                }else{
                    System.out.println("다시 입력하세요.");
                }

            }else if(select == 5){

                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);
                userdbid = user.getId();

                if(projectid == 0){
                    System.out.println("해당하는 프로젝트가 없습니다.");
                    continue;
                }

                if(userRepo.isParticipate(projectid, userdbid)){

                    System.out.printf("10점 만점에 본인이 생각하는 점수를 입력하세요. : ");
                    inputScore = sc.nextFloat();

                    if(inputScore > 10.0){
                        System.out.println("다시 입력해주세요.");
                        continue;
                    }

                    System.out.println("후기를 남겨보세요. : ");
                    inputText = sc.next();

                    //피드백 남기기
                    addFeedback(userdbid, projectid, inputScore, inputText);
                }else{
                    System.out.println("참여한 프로젝트가 아닙니다.");
                }

            }else if(select == 6){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);

                if(projectid == 0){
                    System.out.println("해당하는 프로젝트가 없습니다.");
                    continue;
                }

                isheKing = userRepo.selectKing(user.getUserID(), projectid);

                if(isheKing){
                    finishedProject(projectid);

                }else{
                    System.out.println("권위자가 아니라서 승인할 수 없습니다.");
                }

            }else if(select == 7){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);

                if(projectid == 0){
                    System.out.println("해당하는 프로젝트가 없습니다.");
                    continue;
                }

                isheKing = userRepo.selectKing(user.getUserID(), projectid);

                if(isheKing){

                    System.out.printf("참여할 사용자의 아이디를 입력하세요. : ");
                    inputUserID = sc.next();

                    userdbid = userRepo.selectUserdbid(inputUserID);

                    if(userdbid != 0){
                        registerUser(projectid, userdbid);

                    }else{
                        System.out.println("해당하는 유저가 없습니다.");
                    }

                }else{
                    System.out.println("권위자가 아니라서 추가할 수 없습니다.");
                }
            }else if(select == 8){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();

                confirmProjectFinish(inputProjectName);

            }else if(select == 9){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                System.out.printf("만들고 싶은 팀 이름을 입력하세요. : ");
                inputTeamName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);
                peopleNum = getProjectParticipantsNum(projectid);

                makeNewTeam(projectid, inputTeamName, false, null);
                teamid = userRepo.selectTeamID(projectid, inputTeamName);

                if(peopleNum >= 2 && projectid > 0){

                    while (true){

                        System.out.printf("팀에 넣을 사용자 아이디를 입력하세요. : ");
                        inputUserID = sc.next();

                        userdbid = userRepo.selectUserdbid(inputUserID);

                        if(userRepo.selectRightParticipant(projectid, userdbid)){

                            addNewteamMember(teamid, userdbid);

                        }else{
                            System.out.println("참가하고 있는 사용자가 아닙니다.");
                        }

                        System.out.printf("계속 하실 거면 1번을 아니면 그 외 다른 번호를 입력하세요. : ");
                        selectMember = sc.nextInt();

                        if(selectMember != 1){
                            break;
                        }
                    }

                }else{
                    System.out.println("해당 팀원 수가 2명 이하라서 팀 나누기를 할 수 없거나 해당하는 프로젝트가 없습니다.");
                }
            }else if(select == 10){
                System.out.printf("프로젝트 이름을 입력하세요. : ");
                inputProjectName = sc.next();
                System.out.printf("소속 팀 이름을 입력하세요. : ");
                inputTeamName = sc.next();

                projectid = advisorRepo.advisorProjectID(inputProjectName);
                teamid = userRepo.selectTeamID(projectid, inputTeamName);

                if(!userRepo.selectRightTeam(teamid, user.getId())){
                    System.out.println("소속 팀이 아닙니다.");
                    continue;
                }

                if(teamid > 0){

                    System.out.println("프로젝트에 해당하는 완료 보고서를 입력하세요.");
                    inputText = scStr.nextLine();

                    addProjectReport(teamid, inputText);
                }else{
                    System.out.println("해당하는 프로젝트나 팀이 없습니다.");
                }

            }else if(select == 11){
                break;
            }
        }
    }

    private String inputRealDate(){
        Scanner sc = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int year, month, day;

        while (true){
            System.out.printf("투표할 연도를 입력하세요. : ");
            year = sc.nextInt();

            if(year < 2024){
                System.out.println("처음부터 다시 입력하세요.");
                continue;
            }

            stringBuilder.append(year);
            stringBuilder.append("-");

            System.out.printf("월을 입력하세요. :");
            month = sc.nextInt();

            if(month < 1 || month > 12){
                System.out.println("처음부터 다시 입력하세요.");
                continue;
            }

            if(month <= 9){
                stringBuilder.append("0");
            }

            stringBuilder.append(month);
            stringBuilder.append("-");

            System.out.printf("일을 입력하세요. : ");
            day = sc.nextInt();

            if(day < 1 || day > 31){
                System.out.println("처음부터 다시 입력하세요.");
                continue;
            }

            if(day <= 9){
                stringBuilder.append("0");
            }

            stringBuilder.append(day);

            return stringBuilder.toString();
        }

    }

    private User userLoginOrMakeAccount(){ //사용자 로그인 회원가입
        Scanner sc = new Scanner(System.in);
        int select;
        String inputUserID, inputPwd, inputName, inputPhoneNumber;
        User user = null;
        boolean canBeLogin = false;

        while (true){
            System.out.printf("1.회원 가입, 2.로그인, 3.뒤로 => ");
            select = sc.nextInt();

            if(select == 1){

                System.out.printf("이름을 입력하세요. : ");
                inputName = sc.next();
                System.out.printf("사용할 아이디를 입력하세요. : ");
                inputUserID = sc.next();
                System.out.printf("사용할 비밀번호를 입력하세요. : ");
                inputPwd = sc.next();
                System.out.printf("핸드폰 번호를 입력하세요. : ");
                inputPhoneNumber = sc.next();

                makeUserAccount(inputName, inputUserID, inputPwd, inputPhoneNumber);

            }else if(select == 2){
                System.out.printf("아이디를 입력하세요. : ");
                inputUserID = sc.next();
                System.out.printf("비밀번호를 입력하세요. : ");
                inputPwd = sc.next();

                canBeLogin = userLogin(inputUserID, inputPwd);

                if(canBeLogin){
                    user = new User(inputUserID, inputPwd);

                    user.setId((userRepo.selectUserdbid(inputUserID)));

                    System.out.println("로그인에 성공하셨습니다.");

                }else{
                    System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
                }

                return user;
            }
            else if(select == 3){
                return null;
            }
        }
    }

    private Advisor advisorLoginOrMakeAccount(){ //관리자 로그인 회원가입
        Scanner sc = new Scanner(System.in);
        int select;
        String inputAdvisorID, inputPwd, inputName, inputPhoneNumber;
        boolean canBeLogin = false;
        Advisor advisor = null;

        while(true){
            System.out.printf("1.회원가입, 2.로그인, 3.뒤로 => ");
            select = sc.nextInt();

            if(select == 1){
                System.out.printf("이름을 입력하세요. : ");
                inputName = sc.next();
                System.out.printf("사용할 아이디를 입력하세요. : ");
                inputAdvisorID = sc.next();
                System.out.printf("사용할 비밀번호를 입력하세요. : ");
                inputPwd = sc.next();
                System.out.printf("핸드폰 번호를 입력하세요. : ");
                inputPhoneNumber = sc.next();

                makeAdvisorAccount(inputAdvisorID, inputPwd, inputName, inputPhoneNumber);

            }else if(select == 2){

                System.out.printf("아이디를 입력하세요. : ");
                inputAdvisorID = sc.next();
                System.out.printf("비밀번호를 입력하세요. : ");
                inputPwd = sc.next();

                canBeLogin = advisorLogin(inputAdvisorID, inputPwd);

                if(canBeLogin){
                    advisor = new Advisor(inputAdvisorID, inputPwd);
                    advisor.setId(advisorRepo.selectAdvisordbId(inputAdvisorID));
                    System.out.println("로그인에 성공하셨습니다.");
                }else{
                    System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
                }

                return advisor;

            }else if(select == 3){
                return null;
            }
        }
    }
}
