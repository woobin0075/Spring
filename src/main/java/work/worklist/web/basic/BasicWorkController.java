package work.worklist.web.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import work.worklist.domain.repowitory.WorkRepository;
import work.worklist.domain.work.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/basic/work-list")
@RequiredArgsConstructor
public class BasicWorkController {
    private final WorkRepository workRepository;

    @GetMapping
    public String works(Model model){
        List<Work> works = workRepository.findAll();
        model.addAttribute("works", works);
        return "basic/work-list";
    }

    @GetMapping("/{workId}")
    public String work(@PathVariable long workId, Model model){
        Work work = workRepository.findById(workId);
        model.addAttribute("work", work);
        return "basic/work";
    }

    @GetMapping("/account/start")
    public String startMakingAccount(Model model){
        model.addAttribute("account", new Account("", "", ""));
        model.addAttribute("cautionPersonalNum", "");
        model.addAttribute("cautionCardNum", "");
        model.addAttribute("cautionPhoneNum", "");

        return "basic/makingOnlineAccount";
    }

    @GetMapping("/accountlogin")
    public String startLoginAccount(HttpSession session, Model model){

        session.setAttribute("tryedLoginNum", 0); //로그인 횟수 등록
        model.addAttribute("loginErrorMsg", "");
        return "basic/loginOnlineAccount";
    }

    @GetMapping("/accountlogin/mainpage")
    public String gotoAccountMainPage(Model model){


        return "basic/onlineAccountMainPage";
    }

    @PostMapping("/account/start")
    public String makeNewAccount(@RequestParam String inputPersonalNum, @RequestParam String inputPhoneNum,
                                 @RequestParam String inputCardNum, Model model){
        boolean flag = false;
        if(inputPersonalNum.length() != 6){
            model.addAttribute("cautionPersonalNum", "6자리가 아닙니다.");
            flag = true;
        }

        if(inputPhoneNum.length() != 11){
            model.addAttribute("cautionPhoneNum", "핸드폰 번호가 맞지 않습니다.");
            flag = true;
        }

        if(inputCardNum.length() != 4){
            model.addAttribute("cautionCardNum", "4자리가 아닙니다.");
            flag = true;
            return "basic/makingOnlineAccount";
        }

        if(flag){
            return "basic/makingOnlineAccount";
        }

        Account newAccount = new Account(inputPersonalNum, inputPhoneNum, inputCardNum);

        workRepository.insertNewAccount(newAccount);
        int id = workRepository.selectUserDBid(inputPersonalNum, inputPhoneNum, inputCardNum);
        workRepository.insertNewZeroAccount(id);

        return "basic/loginOnlineAccount";

    }

    @PostMapping("/accountlogin/login/")
    public String loginControll(@RequestParam String inputPersonalNum, @RequestParam String inputPhoneNum,
                                @RequestParam String inputCardNum, Model model, HttpSession session){

        int id = workRepository.selectUserDBid(inputPersonalNum, inputPhoneNum, inputCardNum);
        int loginNum = (int)session.getAttribute("tryedLoginNum");

        if(id != 0){
            session.setAttribute("userDBid", id);
            session.setAttribute("confirmAccountNum", 0);

            return "basic/onlineAccountMainPage";
        }

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호가 틀렸습니다.");
        session.setAttribute("tryedLoginNum", loginNum +1);

        return "basic/loginOnlineAccount";
    }

    @GetMapping("/accountlogin/mainpage/{id}")
    public String confirmAccountMoney(@PathVariable int id, Model model, HttpSession session){

        int num = (int)session.getAttribute("confirmAccountNum");

        if(num == 0){
            int money = workRepository.selectUserMoney(id);
            model.addAttribute("nowUserMoney", money);
            session.setAttribute("confirmAccountNum", 1);

        }else{
            model.addAttribute("confirmMsg", "계좌 조회는 1번만 가능합니다.");
        }

        return "basic/onlineAccountMainPage";
    }

    @PostMapping("/accountlogin/mainpage/inputrequest")
    public String requestInputMoney(@RequestParam String inputMoney, HttpSession session, Model model){

        if(inputMoney.equals("")){

            model.addAttribute("alarm", "입금할 돈을 입력해주세요.");
            return "basic/onlineAccountMainPage";
        }

        int num = (int)session.getAttribute("confirmAccountNum");

        if(num == 0){
            model.addAttribute("alarm", "처음에는 계좌조회만 가능합니다.");
            return "basic/onlineAccountMainPage";
        }

        int money = Integer.parseInt(inputMoney);
        int id = (int)session.getAttribute("userDBid");
        int nowMoney = workRepository.selectUserMoney(id);

        workRepository.updateUserMoney(id, nowMoney+money);
        model.addAttribute("alarm", "입금되었습니다.");

        return "basic/onlineAccountMainPage";
    }

    @PostMapping("/accountlogin/mainpage/outputrequest")
    public String requestOutputMoney(@RequestParam String outputMoney, HttpSession session, Model model){

        if(outputMoney.equals("")){

            model.addAttribute("alarm", "입금할 돈을 입력해주세요.");
            return "basic/onlineAccountMainPage";
        }

        int num = (int)session.getAttribute("confirmAccountNum");

        if(num == 0){
            model.addAttribute("alarm", "처음에는 계좌조회만 가능합니다.");
            return "basic/onlineAccountMainPage";
        }

        int money = Integer.parseInt(outputMoney);
        int id = (int)session.getAttribute("userDBid");
        int nowMoney = workRepository.selectUserMoney(id);

        if(money > nowMoney){

            model.addAttribute("alarm", "잔액 초과입니다.");

        }else{
            workRepository.updateUserMoney(id, nowMoney - money);
            model.addAttribute("alarm", "출금했습니다.");
        }

        return "basic/onlineAccountMainPage";
    }

    @GetMapping("/addnewthing")
    public String viewNewThingPage(){

        return "basic/addnewThingPage";
    }

    @PostMapping("/addnewthing/add/")
    public String addNewThing(@RequestParam String inputNewThingName, @RequestParam String inputNewThingContent, Model model){

        workRepository.insertNewThing(inputNewThingName, inputNewThingContent);

        model.addAttribute("notify", "물품이 추가되었습니다.");

        return "basic/addnewThingPage";
    }

    @GetMapping("/viewthings")
    public String viewThings(Model model){
        ArrayList<Thing> list = workRepository.selectAllThings();

        model.addAttribute("allThings", list);


        return "basic/viewAllthingsPage";
    }

    @PostMapping("/viewthings")
    public String searchviewThings(@RequestParam String search, Model model){

        ArrayList<Thing> list = workRepository.selectSearchThings(search, search);

        model.addAttribute("allThings", list);

        return "basic/viewAllthingsPage";
    }

    @GetMapping("/viewthings/{id}")
    public String viewThingDetails(@PathVariable int id, Model model){

        String str = workRepository.selectThingInfos(id);
        ArrayList<Review> reviews = workRepository.selectThatThingReviews(id);

        String name = str.split("/")[0];
        String content = str.split("/")[1];

        model.addAttribute("thingID", id);
        model.addAttribute("thingName", name);
        model.addAttribute("thingContent", content);
        model.addAttribute("thingReviews", reviews);

        return "basic/thingDetailPage";
    }

    @PostMapping("/viewthings/addreview")
    public String addNewReivewAndViewAllReviews(@RequestParam int id, @RequestParam String reviewname,
            @RequestParam String starnum, Model model){
        workRepository.insertNewReivew(id, reviewname, Integer.parseInt(starnum));
        ArrayList<Review> reviews = workRepository.selectThatThingReviews(id);
        String str = workRepository.selectThingInfos(id);

        String name = str.split("/")[0];
        String content = str.split("/")[1];

        model.addAttribute("thingID", id);
        model.addAttribute("thingName", name);
        model.addAttribute("thingContent", content);
        model.addAttribute("thingReviews", reviews);

        model.addAttribute("notify", "리뷰가 등록되었습니다.");

        return "basic/thingDetailPage";
    }

    @GetMapping("/advisereviews")
    public String viewAllThingsReview(Model model){

        ArrayList<Thing> list = workRepository.selectAllThings();
        model.addAttribute("allThings", list);

        HashMap<Integer, ArrayList<Review>> reviewMap = new HashMap<>();

        for(int i=0; i< list.size(); i++){

            ArrayList<Review> reviewList = workRepository.selectAllThatThingsReview(list.get(i).getId());
            reviewMap.put(list.get(i).getId(), reviewList);

            model.addAttribute("reviewMap", reviewMap);
        }
        return "basic/reviewAdvisorPage";
    }

    @GetMapping("/deleteReview/{id}/{review}")
    public String deleteTheReview(@PathVariable int id, @PathVariable String review, Model model){

        workRepository.deleteReviewByid(id, review);

        ArrayList<Thing> list = workRepository.selectAllThings();
        model.addAttribute("allThings", list);
        HashMap<Integer, ArrayList<Review>> reviewMap = new HashMap<>();

        for(int i=0; i< list.size(); i++){

            ArrayList<Review> reviewList = workRepository.selectAllThatThingsReview(list.get(i).getId());
            reviewMap.put(list.get(i).getId(), reviewList);

            model.addAttribute("reviewMap", reviewMap);
        }

        return "basic/reviewAdvisorPage";
    }

    @GetMapping("/hideReview/{id}/{review}")
    public String hideTheReview(@PathVariable int id, @PathVariable String review, Model model){

        workRepository.updateIshiddenTrueReview(id, review);

        ArrayList<Thing> list = workRepository.selectAllThings();
        model.addAttribute("allThings", list);
        HashMap<Integer, ArrayList<Review>> reviewMap = new HashMap<>();

        for(int i=0; i< list.size(); i++){

            ArrayList<Review> reviewList = workRepository.selectAllThatThingsReview(list.get(i).getId());
            reviewMap.put(list.get(i).getId(), reviewList);

            model.addAttribute("reviewMap", reviewMap);
        }

        return "basic/reviewAdvisorPage";
    }

    @GetMapping("/cancelHide/{id}/{review}")
    public String cancelHide(@PathVariable int id, @PathVariable String review, Model model){

        workRepository.updateIshiddenfalseReview(id, review);

        ArrayList<Thing> list = workRepository.selectAllThings();
        model.addAttribute("allThings", list);
        HashMap<Integer, ArrayList<Review>> reviewMap = new HashMap<>();

        for(int i=0; i< list.size(); i++){

            ArrayList<Review> reviewList = workRepository.selectAllThatThingsReview(list.get(i).getId());
            reviewMap.put(list.get(i).getId(), reviewList);

            model.addAttribute("reviewMap", reviewMap);
        }

        return "basic/reviewAdvisorPage";
    }

    @GetMapping("/updateinfo/{id}")
    public String movetoUpdateThinginfo(@PathVariable int id, Model model){
        model.addAttribute("thingIdNum", id);
        model.addAttribute("notifyUpdate", "");
        return "basic/rewriteThingPage";
    }

    @PostMapping("/updateinfo")
    public String updateThingInfo(@RequestParam int id, @RequestParam String inputThingName,
                                  @RequestParam String inputThingContent, Model model){

        workRepository.updateThingInfo(id, inputThingName, inputThingContent);

        model.addAttribute("notify", "수정되었습니다.");

        return "basic/rewriteThingPage";
    }

    @GetMapping("/thingsAdvisorMakeAccount")
    public String viewThingsAdvisorMakingAccount(){

        return "basic/thingsAdvisorMakeAccount";
    }

    @GetMapping("/thingsAdvisorlogin")
    public String viewThingsloginPage(Model model){

        return "basic/thingsloginpage";
    }

    @PostMapping("/thingsAdvisorMakeAccount")
    public String makeAdvisorAccount(@RequestParam String inputid, @RequestParam String inputpwd){

        workRepository.insertAdvisorAccount(inputid, inputpwd);

        return "basic/thingsloginpage";
    }

    @PostMapping("/thingsAdvisorlogin")
    public String loginThingAdivosr(@RequestParam String inputid, @RequestParam String inputpwd, Model model){

        //입력한 아이디 비밀번호 조회 기능 추가
        if(workRepository.selectAdvisorAccount(inputid, inputpwd)){
            ArrayList<Thing> list = workRepository.selectAllThings();
            model.addAttribute("allThings", list);

            HashMap<Integer, ArrayList<Review>> reviewMap = new HashMap<>();

            for(int i=0; i< list.size(); i++){

                ArrayList<Review> reviewList = workRepository.selectAllThatThingsReview(list.get(i).getId());
                reviewMap.put(list.get(i).getId(), reviewList);

                model.addAttribute("reviewMap", reviewMap);
            }

            return "basic/reviewAdvisorPage";
        }
        model.addAttribute("caution", "아이디 또는 비밀번호가 틀렸습니다.");
        return "basic/thingsloginpage";
    }

    @GetMapping("/movieuserlogin")
    public String viewMovieUserLoginPage(HttpSession session){

        session.setAttribute("todayYear", 2024);
        session.setAttribute("todayMonth", 1);
        session.setAttribute("todayDay", 1);

        return "basic/movieloginpage";
    }

    @GetMapping("/movieusermakeaccount")
    public String viewMovieUserMakeAccountPage(){

        return "basic/movieUserMakeAccount";
    }

    @PostMapping("/movieusermakeaccount")
    public String makeUserMovieAccount(@RequestParam String inputid, @RequestParam String inputpwd, Model model){

        workRepository.insertMovieUserAccount(inputid, inputpwd);

        model.addAttribute("alarm", "회원가입이 완료되었습니다.");

        return "basic/movieUserMakeAccount";
    }

    @PostMapping("/movieuserlogin")
    public String loginMovieUser(@RequestParam String inputUserId,
                                 @RequestParam String inputUserPwd,
                                 Model model,
                                 HttpSession session){

        if(workRepository.selectMovieUser(inputUserId, inputUserPwd)){

            int userDBid = workRepository.getUserDBid(inputUserId);

            ArrayList<Movie> movies = workRepository.selectAllMovies(1); //임시로 숫자로 넣음
            ArrayList<Movie> noticeMovies = workRepository.selectAllNoticeMovies(1);
            model.addAttribute("allMovies", movies);
            model.addAttribute("allNoticeMovies", noticeMovies);

            session.setAttribute("advisor_id", 1);
            session.setAttribute("user_id", userDBid);

            ArrayList<MovieEvent> movieEvents = workRepository.selectMovieEvents(1);

            model.addAttribute("MovieEvents", movieEvents);

            return "basic/movieMainPage";
        }

        model.addAttribute("alarm", "아이디 또는 비밀번호가 잘못되었습니다.");
        return "basic/movieloginpage";
    }

    @GetMapping("/movieNextDay")
    public String viewNextDay(Model model, HttpSession session){

        int advisorDBid = (int)session.getAttribute("advisor_id");

        ArrayList<Movie> movies = workRepository.selectAllMovies(advisorDBid);
        ArrayList<Movie> noticeMovies = workRepository.selectAllNoticeMovies(advisorDBid);
        ArrayList<MovieEvent> movieEvents = workRepository.selectMovieEvents(advisorDBid);

        model.addAttribute("allMovies", movies);
        model.addAttribute("allNoticeMovies", noticeMovies);
        model.addAttribute("MovieEvents", movieEvents);

        int todayDay = (int)session.getAttribute("todayDay")+1;
        int todayMonth = (int)session.getAttribute("todayMonth");
        int todayYear = (int)session.getAttribute("todayYear");

        if(todayDay > 30){
            todayMonth++;
            session.setAttribute("todayDay", 1);
            session.setAttribute("todayMonth", todayMonth);
        }else{
            session.setAttribute("todayDay", todayDay);
        }

        if(todayMonth > 12){
            todayYear++;
            session.setAttribute("todayYear", todayYear);
        }

        return "basic/movieMainPage";
    }

    @GetMapping("/movieAdvisorlogin")
    public String viewMovieAdvisorloginPage(){

        return "basic/movieAdvisorloginPage";
    }

    @GetMapping("/movieAdvisorMakeAccount")
    public String viewMovieAdvisorMakeAccountPage(){

        return "basic/movieAdvisorMakeAccountPage";
    }

    @PostMapping("/movieAdvisorMakeAccount")
    public String makeAdvisorMovieAccount(@RequestParam String inputid, @RequestParam String inputpwd, Model model){
        workRepository.insertMovieAdvisorAccount(inputid, inputpwd);

        model.addAttribute("alarm", "회원가입이 완료되었습니다.");
        return "basic/movieAdvisorMakeAccountPage";
    }

    @PostMapping("/movieAdvisorlogin")
    public String loginMovieAdvisor(@RequestParam String inputAdvisorId,
                                    @RequestParam String inputAdvisorPwd,
                                    Model model,
                                    HttpSession session){
        if(workRepository.selectMovieAdvisorAccount(inputAdvisorId, inputAdvisorPwd)){
            int dbid = workRepository.getMovieAdvisorDBid(inputAdvisorId);

            session.setAttribute("advisorDBid", dbid);

            return "basic/moviePageforAdvisor";
        }

        model.addAttribute("alarm", "아이디 또는 비밀번호가 잘못되었습니다.");
        return "basic/movieAdvisorloginPage";
    }

    @PostMapping("/addmovie")
    public String addNewMovie(@RequestParam String movietitle,
                              @RequestParam int movieopenyear,
                              @RequestParam int movieopenmonth,
                              @RequestParam int movieopenday,
                              @RequestParam int movietime,
                              @RequestParam int moviestarttimehour,
                              @RequestParam int moviestarttimeminute,
                              @RequestParam int movietotalsize,
                              @RequestParam int moviecloseyear,
                              @RequestParam int movieclosemonth,
                              @RequestParam int moviecloseday,
                              @RequestParam int movieprice,
                              @RequestParam int roomNumber,
                              HttpSession session,
                              Model model){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        LocalDateTime opendate = LocalDateTime.of(movieopenyear, movieopenmonth, movieopenday, 0, 0);
        LocalDateTime start = LocalDateTime.of(movieopenyear, movieopenmonth, movieopenday, moviestarttimehour, moviestarttimeminute);
        LocalDateTime closedate = LocalDateTime.of(moviecloseyear, movieclosemonth, moviecloseday, 0, 0);

        workRepository.insertNewMovie(
                advisorDBid,
                movietitle,
                opendate,
                start,
                movietotalsize,
                movieprice,
                closedate,
                movietime
        );

        workRepository.insertMovieRoomNumber(
                advisorDBid,
                movietitle,
                roomNumber
        );
        model.addAttribute("alarm", "영화가 추가되었습니다.");
        return "basic/moviePageforAdvisor";
    }

    @GetMapping("/movielistpageforadvisor")
    public String viewmovieListPageforAdvisor(Model model, HttpSession session){
        Random random = new Random();
        final int bonus = 100000;
        int advisorDBid = (int) session.getAttribute("advisorDBid");
        ArrayList<Movie> movies = workRepository.selectAllMovies(advisorDBid);
        int allParticipantsTheators = random.nextInt(40)+20; //20~60;
        //예고편 영화 리스트 출력
        ArrayList<Movie> noticeMovies = workRepository.selectAllNoticeMovies(advisorDBid);
        ArrayList<MovieTheator> theators = new ArrayList<>();

        model.addAttribute("allMovies", movies);
        model.addAttribute("accSale", workRepository.selectNowAccSale(advisorDBid));

        model.addAttribute("allNoticeMovies", noticeMovies);
        int beforeNum = 0;
        for(int i=0; i<2; i++){
            int afterNum = random.nextInt(9)+1;
            if(afterNum != beforeNum){
                theators.add(new MovieTheator(workRepository.getOtherTheatorName(afterNum), afterNum));
                beforeNum = afterNum;
            }else{
                i--;
            }

        }

        //램덤 클래스로 수상확률 구분하기
        int probability = random.nextInt(allParticipantsTheators);
        int todayDay = LocalDateTime.now().getDayOfMonth();

        if(probability < 3 && todayDay == 1){

            if(probability == 0){
                theators.add(0, new MovieTheator("MyTheator", 0));

            }else if(probability == 1){
                theators.add(1, new MovieTheator("MyTheator", 0));

            }else{
                theators.add(2, new MovieTheator("MyTheator", 0));
            }

            model.addAttribute("winText", "이번 달 우수한 영화관으로 등재되었습니다.");
            model.addAttribute("winTheators", theators);
            //보너스 추가
            workRepository.updateMovieTodaySale(advisorDBid, LocalDateTime.now(), bonus);
        }

        return "basic/movieListPageforAdvisor";
    }

    @GetMapping("/movieUpdateTime")
    public String viewUpdateTimePage(){

        return "basic/movieStartTimeUpdatePage";
    }

    @PostMapping("/movieUpdateTime")
    public String updateMovieStartTime(@RequestParam String inputMovieTitle, @RequestParam int inputHour,
                                       @RequestParam int inputMinute, HttpSession session, Model model){
        int advisorDBid = (int) session.getAttribute("advisorDBid");

        workRepository.updateMovieStartTime(advisorDBid, inputMovieTitle, inputHour, inputMinute);

        model.addAttribute("alarm", "수정 되었습니다.");
        return "basic/movieStartTimeUpdatePage";
    }

    @GetMapping("/movieReservate/{title}")
    public String viewmovieReservationforUserPage(@PathVariable String title,
                                                  Model model,
                                                  HttpSession session){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("title", title);

        int advisorDBid = (int)session.getAttribute("advisor_id");
        //상영관 정보 알아내기
        ArrayList<Integer> roomlist = workRepository.selectMovieRoomlist(advisorDBid, title);

        LocalDateTime startTime = LocalDateTime.parse(workRepository.getMovieStartTime(advisorDBid, title), formatter);

        model.addAttribute("startTime", startTime);
        model.addAttribute("room_numbers", roomlist);

        return "basic/movieReservationforUser";
    }

    @PostMapping("/movieReservate")
    public String moveToSeatSelectPage(@RequestParam String movieTitle,
                                       @RequestParam int inputWatchMonth,
                                       @RequestParam int inputWatchDay,
                                       @RequestParam String inputCode,
                                       @RequestParam int inputRoomNumber,
                                       HttpSession session,
                                       Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int advisorDBid = (int)session.getAttribute("advisor_id");
        int userDBid = (int)session.getAttribute("user_id");
        int promotionId = 0;

        //할인 코드 검사, 세션에 id 추가
        if(!inputCode.equals("") && !workRepository.isCanUseThePromotion(inputCode, advisorDBid)){

            model.addAttribute("title", movieTitle);
            LocalDateTime startTime = LocalDateTime.parse(workRepository.getMovieStartTime(advisorDBid, movieTitle), formatter);

            model.addAttribute("startTime", startTime);
            model.addAttribute("alarm", "할인 코드가 사용불가합니다.");

            return "basic/movieReservationforUser";
        }

        if(!inputCode.equals("")){
            promotionId = workRepository.getThePromotionId(inputCode, advisorDBid);
            session.setAttribute("promotion_id", promotionId);

        }else{
            session.setAttribute("promotion_id", 0);
        }

        int totalSeatSize = workRepository.getSeatTotalSize(advisorDBid, movieTitle);
        LocalDateTime startTime = LocalDateTime.parse(workRepository.getMovieStartTime(advisorDBid, movieTitle), formatter);
        LocalDateTime watchDate = LocalDateTime.of(2024, inputWatchMonth,
                inputWatchDay, startTime.getHour(), startTime.getMinute(), 0);

        model.addAttribute("watchMonth", inputWatchMonth);
        model.addAttribute("watchDay", inputWatchDay);
        model.addAttribute("title", movieTitle);
        model.addAttribute("startTime", startTime);
        model.addAttribute("movieRoom", inputRoomNumber);

        //예약 좌석 미리 알아내기
        ArrayList<MovieSeat> list = new ArrayList<>();
        ArrayList<String> reservatedSeats = workRepository.getReservatedSeats(advisorDBid,
                movieTitle, watchDate, inputRoomNumber);

        int i=0;
        int r=1, c;
        MovieSeat movieSeat;
        while(i < totalSeatSize){

            for(c=1; c<=4; c++){
                movieSeat = new MovieSeat(r, c);
                movieSeat.reservated = false;
                list.add(movieSeat);
                i++;

                if(i == totalSeatSize)
                    break;
            }
            r++;
        }

        for(int k=0; k< reservatedSeats.size(); k++){
            String[] arr = reservatedSeats.get(k).split("");
            int row = Integer.parseInt(arr[0]); int col = Integer.parseInt(arr[1]);

            for(int j=0; j<list.size(); j++){
                if(list.get(j).row == row && list.get(j).col == col){
                    list.get(j).reservated = true;
                }
            }
        }

        model.addAttribute("MovieSeats", list);

        return "basic/movieReservationforUser2";
    }

    @GetMapping("/movieSeatReservate/{title}/{month}/{day}/{row}/{col}/{movieRoom}")
    String movieSeatReservation(@PathVariable String title,
                                @PathVariable int month,
                                @PathVariable int day,
                                @PathVariable int row,
                                @PathVariable int col,
                                @PathVariable int movieRoom,
                                HttpSession session,
                                Model model){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int advisorDBid = (int)session.getAttribute("advisor_id");
        int userDBid = (int)session.getAttribute("user_id");

        int totalSeatSize = workRepository.getSeatTotalSize(advisorDBid, title);
        LocalDateTime startTime = LocalDateTime.parse(workRepository.getMovieStartTime(advisorDBid, title), formatter);
        LocalDateTime watchDate = LocalDateTime.of(2024, month,
                day, startTime.getHour(), startTime.getMinute(), 0);

        workRepository.insertNewUserReservation( //예매 정보 저장
                userDBid,
                advisorDBid,
                title,
                watchDate,
                ""+row+col,
                movieRoom
        );

        //매출 증가
        int nowAccSale = workRepository.selectNowAccSale(advisorDBid);
        int moviePrice = workRepository.getMoviePrice(advisorDBid, title, workRepository.getMovieStartTime(advisorDBid, title));

        //할인 코드 있는지 확인
        int promotionId = (int)session.getAttribute("promotion_id");
        if(promotionId != 0){
            int salePercent = workRepository.getSalePercentById(promotionId);
            moviePrice = (moviePrice/100)*(100 - salePercent);
            workRepository.updatePromotionisUsedTrue(promotionId, advisorDBid);
        }

        workRepository.updateAccSale(advisorDBid, nowAccSale, moviePrice);

        LocalDateTime today = LocalDateTime.now();
        //일매출 증가
        workRepository.updateMovieTodaySale(advisorDBid, today, moviePrice);
        //월매출 증가
        workRepository.updateMonthSale(advisorDBid, today, moviePrice);
        //연매출 증가
        workRepository.updateYearSale(advisorDBid, today, moviePrice);


        model.addAttribute("alarm", "예약 되었습니다.");

        model.addAttribute("title", title);
        model.addAttribute("watchMonth", month);
        model.addAttribute("watchDay", day);
        model.addAttribute("startTime", startTime);

        ArrayList<MovieSeat> list = new ArrayList<>();
        ArrayList<String> reservatedSeats = workRepository.getReservatedSeats(advisorDBid, title, watchDate, movieRoom);

        int i=0;
        int r=1, c;
        MovieSeat movieSeat;
        while(i < totalSeatSize){


            for(c=1; c<=4; c++){
                movieSeat = new MovieSeat(r, c);
                list.add(movieSeat);
                i++;

                if(i == totalSeatSize)
                    break;
            }
            r++;
        }

        for(int k=0; k< reservatedSeats.size(); k++){
            String[] arr = reservatedSeats.get(k).split("");
            int rows = Integer.parseInt(arr[0]); int cols = Integer.parseInt(arr[1]);

            for(int j=0; j<list.size(); j++){
                if(list.get(j).row == rows && list.get(j).col == cols){
                    list.get(j).reservated = true;
                }
            }
        }

        model.addAttribute("MovieSeats", list);

        return "basic/movieReservationforUser2";
    }

    //좌석 랜덤 선택 예매
    @GetMapping("/movieSeatReservate/{title}/{month}/{day}/{movieRoom}")
    public String randomMovieSeatReservation(@PathVariable String title,
                                             @PathVariable int month,
                                             @PathVariable int day,
                                             @PathVariable int movieRoom,
                                             HttpSession session,
                                             Model model){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int advisorDBid = (int)session.getAttribute("advisor_id");
        int userDBid = (int)session.getAttribute("user_id");

        int totalSeatSize = workRepository.getSeatTotalSize(advisorDBid, title);
        LocalDateTime startTime = LocalDateTime.parse(workRepository.getMovieStartTime(advisorDBid, title), formatter);
        LocalDateTime watchDate = LocalDateTime.of(2024, month,
                day, startTime.getHour(), startTime.getMinute(), 0);

        //좌석 랜덤
        Random random = new Random();
        int endRow = (int)Math.ceil(((double)totalSeatSize/4.0));
        int endCol = 4;
        int row, col;

        do {
            row = random.nextInt(endRow) + 1;
            col = random.nextInt(endCol) + 1;

        } while (workRepository.isUserSeatReservated(userDBid, advisorDBid, title, watchDate,
                "" + row + col, movieRoom));

        workRepository.insertNewUserReservation( //예매 정보 저장
                userDBid,
                advisorDBid,
                title,
                watchDate,
                ""+row+col,
                movieRoom
        );

        //매출 증가
        int nowAccSale = workRepository.selectNowAccSale(advisorDBid);
        int moviePrice = workRepository.getMoviePrice(advisorDBid, title, workRepository.getMovieStartTime(advisorDBid, title));

        //할인 코드 있는지 확인
        int promotionId = (int)session.getAttribute("promotion_id");
        if(promotionId != 0){
            int salePercent = workRepository.getSalePercentById(promotionId);
            moviePrice = (moviePrice/100)*(100 - salePercent);
            workRepository.updatePromotionisUsedTrue(promotionId, advisorDBid);
        }

        workRepository.updateAccSale(advisorDBid, nowAccSale, moviePrice);

        LocalDateTime today = LocalDateTime.now();
        //일매출 증가
        workRepository.updateMovieTodaySale(advisorDBid, today, moviePrice);
        //월매출 증가
        workRepository.updateMonthSale(advisorDBid, today, moviePrice);
        //연매출 증가
        workRepository.updateYearSale(advisorDBid, today, moviePrice);

        model.addAttribute("alarm", "예약 되었습니다.");

        model.addAttribute("title", title);
        model.addAttribute("watchMonth", month);
        model.addAttribute("watchDay", day);
        model.addAttribute("startTime", startTime);

        ArrayList<MovieSeat> list = new ArrayList<>();
        ArrayList<String> reservatedSeats = workRepository.getReservatedSeats(advisorDBid, title, watchDate, movieRoom);

        int i=0;
        int r=1, c;
        MovieSeat movieSeat;
        while(i < totalSeatSize){

            for(c=1; c<=4; c++){
                movieSeat = new MovieSeat(r, c);
                list.add(movieSeat);
                i++;

                if(i == totalSeatSize)
                    break;
            }
            r++;
        }

        for(int k=0; k< reservatedSeats.size(); k++){
            String[] arr = reservatedSeats.get(k).split("");
            int rows = Integer.parseInt(arr[0]); int cols = Integer.parseInt(arr[1]);

            for(int j=0; j<list.size(); j++){
                if(list.get(j).row == rows && list.get(j).col == cols){
                    list.get(j).reservated = true;
                }
            }
        }

        model.addAttribute("MovieSeats", list);

        return "basic/movieReservationforUser2";
    }

    @GetMapping("/moviemainpage")
    public String gotoMovieMainPage(Model model, HttpSession session){

        int advisorDBid = (int)session.getAttribute("advisor_id");
        ArrayList<Movie> movies = workRepository.selectAllMovies(advisorDBid); //임시로 숫자로 넣음
        ArrayList<Movie> noticeMovies = workRepository.selectAllNoticeMovies(advisorDBid);
        ArrayList<MovieEvent> movieEvents = workRepository.selectMovieEvents(advisorDBid);

        model.addAttribute("MovieEvents", movieEvents);
        model.addAttribute("allMovies", movies);
        model.addAttribute("allNoticeMovies", noticeMovies);

        return "basic/movieMainPage";
    }

    @GetMapping("/moviemypage")
    public String gotoMovieMyPage(HttpSession session, Model model){

        int advisorDBid = (int)session.getAttribute("advisor_id");
        int userDBid = (int)session.getAttribute("user_id");

        ArrayList<MyMovieReservation> mylist = workRepository.selectMyMovieReservations(userDBid, advisorDBid);

        model.addAttribute("reservations", mylist);

        //할인코드 목록
        ArrayList<MoviePromotionInfo> myCodes = new ArrayList<>();
        ArrayList<Integer> mycodeIds = workRepository.selectUserPromotions(userDBid);
        for(Integer i : mycodeIds){
            myCodes.add(workRepository.selectUserPromotionById(i));
        }

        model.addAttribute("MyCodes", myCodes);

        return "basic/movieUserMyPage";
    }

    @GetMapping("/moviemypage/{title}/{seat}")
    public String cancelMyMovieReservation(@PathVariable String title,
                                           @PathVariable String seat,
                                           HttpSession session,
                                           Model model){

        int advisorDBid = (int)session.getAttribute("advisor_id");
        int userDBid = (int)session.getAttribute("user_id");

        String mySeat = seat.replace("행 ", "");
        mySeat = mySeat.replace("열" , "");

        String watchDate = workRepository.getMyMovieWatchDate(userDBid, advisorDBid, title, mySeat);
        String startTime = workRepository.getMovieStartTime(advisorDBid, title);
        int price = workRepository.getMoviePrice(advisorDBid, title, startTime);
        int nowAccSale = workRepository.selectNowAccSale(advisorDBid);

        workRepository.deleteMyMovieReservation(userDBid, advisorDBid, title, watchDate, mySeat);
        workRepository.updateAccSale(advisorDBid, nowAccSale, (-1)*price);

        ArrayList<MyMovieReservation> mylist = workRepository.selectMyMovieReservations(userDBid, advisorDBid);

        model.addAttribute("reservations", mylist);
        model.addAttribute("alarm", "취소 되었습니다.");

        //할인코드 목록
        ArrayList<MoviePromotionInfo> myCodes = new ArrayList<>();
        ArrayList<Integer> mycodeIds = workRepository.selectUserPromotions(userDBid);
        for(Integer i : mycodeIds){
            myCodes.add(workRepository.selectUserPromotionById(i));
        }

        model.addAttribute("MyCodes", myCodes);

        //일매출 하락 코드 작성

        return "basic/movieUserMyPage";
    }

    @PostMapping("/moviemainpage")
    String searchMoviesByTitle(@RequestParam String inputMovieTitle, HttpSession session, Model model){

        int advisorDBid = (int)session.getAttribute("advisor_id");
        ArrayList<Movie> movies = workRepository.selectMoviesByTitle(advisorDBid, inputMovieTitle);
        model.addAttribute("allMovies", movies);

        return "basic/movieMainPage";
    }

    @PostMapping("/addnoticemovie")
    String addNoticeMovie(@RequestParam String movietitle,
                          @RequestParam int movieopenyear,
                          @RequestParam int movieopenmonth,
                          @RequestParam int movieopenday,
                          @RequestParam int movietime,
                          @RequestParam int moviestarttimehour,
                          @RequestParam int moviestarttimeminute,
                          @RequestParam int movietotalsize,
                          @RequestParam int moviecloseyear,
                          @RequestParam int movieclosemonth,
                          @RequestParam int moviecloseday,
                          @RequestParam int movieprice,
                          HttpSession session,
                          Model model){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        LocalDateTime opendate = LocalDateTime.of(movieopenyear, movieopenmonth, movieopenday, 0, 0);
        LocalDateTime start = LocalDateTime.of(movieopenyear, movieopenmonth, movieopenday, moviestarttimehour, moviestarttimeminute);
        LocalDateTime closedate = LocalDateTime.of(moviecloseyear, movieclosemonth, moviecloseday, 0, 0);


        workRepository.insertNoticeMovie(
                advisorDBid,
                movietitle,
                opendate,
                start,
                movietotalsize,
                movieprice,
                closedate,
                movietime
        );
        model.addAttribute("alarm", "예고 영화가 추가되었습니다.");
        return "basic/moviePageforAdvisor";
    }

    @GetMapping("/movieUpdatePrice")
    public String viewUpdatePricePage(){

        return "basic/moviePriceUpdatePage";
    }

    @PostMapping("/movieUpdatePrice")
    public String updateMoviePrice(@RequestParam String inputMovieTitle, @RequestParam int inputPrice,
                                   HttpSession session, Model model){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        workRepository.updateMoviePrice(advisorDBid, inputMovieTitle, inputPrice);
        model.addAttribute("alarm", "수정 되었습니다.");

        return "basic/moviePriceUpdatePage";
    }

    @GetMapping("/deleteMovie/{title}")
    public String deleteMovie(@PathVariable String title, HttpSession session, Model model){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        workRepository.deleteMovieByTitle(advisorDBid, title);

        ArrayList<Movie> movies = workRepository.selectAllMovies(advisorDBid);
        //예고편 영화 리스트 출력
        ArrayList<Movie> noticeMovies = workRepository.selectAllNoticeMovies(advisorDBid);

        model.addAttribute("allMovies", movies);
        model.addAttribute("accSale", workRepository.selectNowAccSale(advisorDBid));

        model.addAttribute("allNoticeMovies", noticeMovies);

        return "basic/movieListPageforAdvisor";
    }

    @GetMapping("/deleteNoticeMovie/{title}")
    public String deleteNoticeMovie(@PathVariable String title, HttpSession session, Model model){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        workRepository.deleteNoticeMovieByTitle(advisorDBid, title);

        ArrayList<Movie> movies = workRepository.selectAllMovies(advisorDBid);
        //예고편 영화 리스트 출력
        ArrayList<Movie> noticeMovies = workRepository.selectAllNoticeMovies(advisorDBid);

        model.addAttribute("allMovies", movies);
        model.addAttribute("accSale", workRepository.selectNowAccSale(advisorDBid));

        model.addAttribute("allNoticeMovies", noticeMovies);

        return "basic/movieListPageforAdvisor";
    }

    @GetMapping("/saleReport")
    public String viewSaleReportPage(Model model, HttpSession session){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        ArrayList<TodaySale> daySales = workRepository.selectAllDaySales(advisorDBid);

        workRepository.calcSalesByMonth(advisorDBid);
        ArrayList<MonthSale> monthSales = workRepository.selectMovieMonthSales(advisorDBid);

        workRepository.calcYearSaleReocrd(advisorDBid);
        ArrayList<YearSale> yearSales = workRepository.selectMovieYearSales(advisorDBid);

        //일 매출 저장
        model.addAttribute("daySales", daySales);
        //월 매출 저장
        model.addAttribute("monthSales", monthSales);
        //년 매출 저장
        model.addAttribute("yearSales", yearSales);

        return "basic/movieSaleReport";
    }

    @GetMapping("/movieReview/{title}")
    public String viewMovieReviewsPage(@PathVariable String title, HttpSession session, Model model){
        int advisorDBid = (int) session.getAttribute("advisor_id");
        ArrayList<MovieReview> movieReviews = workRepository.selectMovieReviews(advisorDBid, title);
        model.addAttribute("movieTitle", title);

        double avg = 0.0;
        int sum = 0;

        if(movieReviews.size() > 0){
            for(int i=0; i<movieReviews.size(); i++){
                sum += movieReviews.get(i).stars.length();
            }
            avg = (double)sum/(double)(movieReviews.size());
        }

        String avgStr = String.format("%.2f", avg);
        model.addAttribute("reviewAvg", avgStr);
        model.addAttribute("MovieReviews", movieReviews);

        return "basic/movieUserReviews";
    }

    @PostMapping("/movieReview")
    public String addNewMovieReview(@RequestParam String movieTitle,
                                    @RequestParam int inputStar,
                                    @RequestParam String inputReview,
                                    HttpSession session,
                                    Model model){

        int advisorDBid = (int) session.getAttribute("advisor_id");
        int userDBid = (int)session.getAttribute("user_id");

        workRepository.insertMovieReview(advisorDBid, userDBid, movieTitle,
                inputStar, inputReview);

        model.addAttribute("movieTitle", movieTitle);

        ArrayList<MovieReview> movieReviews = workRepository.selectMovieReviews(advisorDBid, movieTitle);

        double avg = 0.0;
        int sum = 0;

        if(movieReviews.size() > 0){
            for(int i=0; i<movieReviews.size(); i++){
                sum += movieReviews.get(i).stars.length();
            }
            avg = (double)sum/(double)(movieReviews.size());
        }

        String avgStr = String.format("%.2f", avg);
        model.addAttribute("reviewAvg", avgStr);
        model.addAttribute("MovieReviews", movieReviews);

        return "basic/movieUserReviews";
    }

    @GetMapping("/movieReviewAdvisor/{title}")
    public String viewMovieReviewPageAdvisor(@PathVariable String title,
                                             HttpSession session,
                                             Model model){

        int advisorDBid = (int)session.getAttribute("advisorDBid");

        model.addAttribute("movieTitle", title);

        ArrayList<MovieReview> movieReviews = workRepository.selectMovieReviews(advisorDBid, title);
        model.addAttribute("MovieReviews", movieReviews);

        return "basic/movieReviewForAdvisor";
    }

    @GetMapping("/movieReviewAdvisor/delete/{title}/{review}")
    public String deleteMovieReivew(@PathVariable String title,
                                    @PathVariable String review,
                                    HttpSession session,
                                    Model model){
        int advisorDBid = (int)session.getAttribute("advisorDBid");

        model.addAttribute("movieTitle", title);

        workRepository.deleteMovieReview(advisorDBid, title, review);

        ArrayList<MovieReview> movieReviews = workRepository.selectMovieReviews(advisorDBid, title);
        model.addAttribute("MovieReviews", movieReviews);

        return "basic/movieReviewForAdvisor";
    }

    @GetMapping("/movieEvent")
    public String viewMovieMakeEventPage(Model model, HttpSession session){
        int advisorDBid = (int)session.getAttribute("advisorDBid");
        ArrayList<MovieEvent> movieEvents = workRepository.selectMovieEvents(advisorDBid);
        ArrayList<MoviePromotionInfo> myCodes = workRepository.selectAllPromotionCodes(advisorDBid);

        model.addAttribute("MovieEvents", movieEvents);
        model.addAttribute("MyCodes", myCodes);

        return "basic/movieMakeEventPage";
    }

    @PostMapping("/movieEvent")
    public String makeMovieEvent(@RequestParam String inputEventTitle,
                                 @RequestParam int inputPromotionNum,
                                 @RequestParam int inputSalePercent,
                                 @RequestParam String inputEventContent,
                                 Model model,
                                 HttpSession session){

        int advisorDBid = (int)session.getAttribute("advisorDBid");

        workRepository.insertNewMovieEvent(inputEventTitle, inputEventContent, advisorDBid);
        int eventId = workRepository.selectMovieEventId(inputEventTitle);
        MoviePromotion moviePromotion;
        for(int i=0; i<inputPromotionNum; i++){
            moviePromotion = new MoviePromotion();
            workRepository.insertNewMoviePromotionCode(eventId, moviePromotion.getCode(),
                    advisorDBid, inputSalePercent);

        }

        ArrayList<MovieEvent> movieEvents = workRepository.selectMovieEvents(advisorDBid);
        model.addAttribute("MovieEvents", movieEvents);
        model.addAttribute("alarm", "이벤트가 생성되었습니다.");

        return "basic/movieMakeEventPage";
    }

    @GetMapping("/moviemypage/getcode/{eventid}")
    public String getEventSaleCode(@PathVariable int eventid,
                                   Model model,
                                   HttpSession session){

        int userDBid = (int)session.getAttribute("user_id");
        int advisorDBid = (int)session.getAttribute("advisor_id");

        ArrayList<Movie> movies = workRepository.selectAllMovies(advisorDBid); //임시로 숫자로 넣음
        ArrayList<Movie> noticeMovies = workRepository.selectAllNoticeMovies(advisorDBid);
        ArrayList<MovieEvent> movieEvents = workRepository.selectMovieEvents(advisorDBid);

        model.addAttribute("MovieEvents", movieEvents);
        model.addAttribute("allMovies", movies);
        model.addAttribute("allNoticeMovies", noticeMovies);

        if(workRepository.selectUserEventId(userDBid, eventid)){
            model.addAttribute("alarm", "이미 참여한 이벤트입니다.");

            return "basic/movieMainPage";
        }

        if(!workRepository.selectNotOfferdPromotion(eventid, advisorDBid)){
            model.addAttribute("alarm", "할인 코드가 모두 소진되었습니다.");

            return "basic/movieMainPage";
        }

        int promotionId = workRepository.getNotOfferedPromotion(eventid, advisorDBid);
        workRepository.updatePromotionUsed(promotionId, advisorDBid);

        workRepository.insertUserPromotionId(userDBid, eventid, promotionId);

        model.addAttribute("alarm", "할인 코드를 받았습니다.");

        return "basic/movieMainPage";
    }

    @GetMapping("/addMovieRoom/{movieTitle}")
    public String viewAddMovieRoomPage(@PathVariable String movieTitle,
                                Model model,
                                HttpSession session){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        ArrayList<Integer> roomlist = workRepository.selectMovieRoomlist(advisorDBid, movieTitle);
        model.addAttribute("title", movieTitle);
        model.addAttribute("room_numbers", roomlist);

        return "basic/movieAddRoom";
    }

    @PostMapping("/addMovieRoom")
    public String addMovieRoom(@RequestParam String movieTitle,
                        @RequestParam int inputMovieRoom,
                        Model model,
                        HttpSession session){

        int advisorDBid = (int) session.getAttribute("advisorDBid");

        workRepository.insertMovieRoomNumber(
                advisorDBid,
                movieTitle,
                inputMovieRoom
        );
        model.addAttribute("title", movieTitle);
        model.addAttribute("alarm", "상영관이 추가 되었습니다.");
        return "basic/movieAddRoom";
    }

    @GetMapping("/randomOtherMovielist")
    public String viewOtherMoviePage(Model model){

        ArrayList<Movie> movieList = workRepository.selectAllOtherMovieList();
        MovieEvent event = workRepository.getRandomEvent();
        Random random = new Random();
        int num = random.nextInt(10);
        String name = workRepository.getOtherTheatorName(num);

        model.addAttribute("Theator", name);
        model.addAttribute("allMovies", movieList);
        model.addAttribute("movieEvent", event);

        return "basic/movieOtherMainPage";
    }

    @GetMapping("/congestion")
    public String calcDegreeCongestion(@RequestParam String movieTitle,
                                       @RequestParam int inputWatchYear,
                                       @RequestParam int inputWatchMonth,
                                       @RequestParam int inputWatchDay,
                                       @RequestParam int inputRoomNumber,
                                       Model model,
                                       HttpSession session){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("title", movieTitle);
        int advisorDBid = (int)session.getAttribute("advisor_id");
        //상영관 정보 알아내기
        ArrayList<Integer> roomlist = workRepository.selectMovieRoomlist(advisorDBid, movieTitle);

        LocalDateTime startTimedate = LocalDateTime.parse(workRepository.getMovieStartTime(advisorDBid, movieTitle), formatter);

        LocalDateTime watchDate = LocalDateTime.of(inputWatchYear, inputWatchMonth, inputWatchDay,
                startTimedate.getHour(),startTimedate.getMinute());
        int totalSeatSize = workRepository.getSeatTotalSize(advisorDBid, movieTitle);
        String degree = "";

        if(workRepository.isTheMovieRoomExist(advisorDBid, movieTitle, inputRoomNumber)){

            int chai = totalSeatSize - workRepository.getReservedSeatsNum(advisorDBid, movieTitle, inputRoomNumber, watchDate);
            degree = chai+"/"+totalSeatSize;

        }else{
            degree = "해당 상영관에 상영하지 않습니다.";
        }

        model.addAttribute("startTime", startTimedate);
        model.addAttribute("room_numbers", roomlist);
        model.addAttribute("degree", degree);

        return "basic/movieReservationforUser";
    }

    /*협력작업 웹 개발 파트 */
    @GetMapping("/coporateLogin")
    public String viewCoporateLoginPage(){

        return "basic/coporateLogin";
    }

    @GetMapping("/coporateAdvisor")
    public String viewCoporateAdvisorMakeAccount(){

        return "basic/coporateAdvisorMakeAccount";
    }

    @GetMapping("/coporateUser")
    public String viewCoporateUserMakeAccount(){
        return "basic/coporateUserMakeAccount";
    }

    @PostMapping("/coporateAdvisor")
    public String makeCoporateAdvisorAccount(@RequestParam String inputName,
                                             @RequestParam String inputID,
                                             @RequestParam String inputPwd,
                                             @RequestParam String inputPhoneNum,
                                             Model model){

        workRepository.insertNewAdvisor(inputID, inputPwd, inputName, inputPhoneNum);
        model.addAttribute("alarm", "회원가입이 완료되었습니다.");
        return "basic/coporateAdvisorMakeAccount";
    }

    @PostMapping("/coporateUser")
    public String makeCoporateUserAccount(@RequestParam String inputName,
                                          @RequestParam String inputID,
                                          @RequestParam String inputPwd,
                                          @RequestParam String inputPhoneNum,
                                          Model model){

        workRepository.insertNewUser(inputName, inputID, inputPwd, inputPhoneNum);
        model.addAttribute("alarm", "회원가입이 완료되었습니다.");
        return "basic/coporateUserMakeAccount";
    }

    @GetMapping("/coporatePageforAdvisor")
    public String viewCoporatePageforAdvisor(HttpSession session, Model model){

        //마감기한 프로젝트 알람
        int advisorDBid = (int)session.getAttribute("advisorDBid");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        //마감기한 프로젝트 알람
        String limitDate = null;  //마감기한 가져오기
        boolean isFinish;
        ArrayList<Project> projectList = workRepository.getProjectIDbyAdvisorDBid(advisorDBid);
        ArrayList<Project> alarmProjectList = new ArrayList<>();

        for(Project project : projectList){

            limitDate = workRepository.getProjectLimitDate(project.getProjectID());

            if(limitDate != null){
                isFinish = workRepository.getProjectIsFinish(project.getProjectID());

                LocalDate limitLocalDate = LocalDate.parse(limitDate, formatter);
                Period period = Period.between(today, limitLocalDate);

                if(!isFinish && limitLocalDate.isAfter(today) && period.getDays() <= 7){
                    //아직 미완성이고 마감기한까지 7일 이내라면
                    alarmProjectList.add(project);
                }
            }

        }

        if(!alarmProjectList.isEmpty()){
            model.addAttribute("alarm", "[마감 해야 할 프로젝트가 존재합니다!]");
            model.addAttribute("unFinished", alarmProjectList);
        }

        return "basic/coporatePageforAdvisor";
    }

    @GetMapping("/coporatePageforUser")
    public String viewCoporatePageforUser(HttpSession session, Model model){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        int userDBid = (int)session.getAttribute("userDBid");

        //마감기한 프로젝트 알람
        ArrayList<Project> projectList = workRepository.getProjectIDbyUserDBid(userDBid);
        String limitDate = null;  //마감기한 가져오기
        boolean isFinish;
        ArrayList<Project> alarmProjectList = new ArrayList<>();

        for(Project project : projectList){

            limitDate = workRepository.getProjectLimitDate(project.getProjectID());

            if(limitDate != null){
                isFinish = workRepository.getProjectIsFinish(project.getProjectID());

                LocalDate limitLocalDate = LocalDate.parse(limitDate, formatter);
                Period period = Period.between(today, limitLocalDate);

                if(!isFinish && limitLocalDate.isAfter(today) && period.getDays() <= 7){
                    //아직 미완성이고 마감기한까지 7일 이내라면
                    alarmProjectList.add(project);
                }
            }

        }

        if(!alarmProjectList.isEmpty()){
            model.addAttribute("alarm", "[마감 해야 할 프로젝트가 존재합니다!]");
            model.addAttribute("unFinished", alarmProjectList);
        }

        return "basic/coporatePageforUser";
    }

    @GetMapping("/coporateLogin/AdvisorLogin")
    public String CoporateAdvisorLogin(@RequestParam String inputAdvisorID,
                                       @RequestParam String inputAdvisorPwd,
                                       Model model, HttpSession session){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        if(workRepository.selectAdvisor(inputAdvisorID, inputAdvisorPwd)){

            int advisorDBid = workRepository.selectAdvisordbId(inputAdvisorID);
            session.setAttribute("advisorDBid", advisorDBid);

            //마감기한 프로젝트 알람
            String limitDate = null;  //마감기한 가져오기
            boolean isFinish;
            ArrayList<Project> projectList = workRepository.getProjectIDbyAdvisorDBid(advisorDBid);
            ArrayList<Project> alarmProjectList = new ArrayList<>();

            for(Project project : projectList){

                limitDate = workRepository.getProjectLimitDate(project.getProjectID());

                if(limitDate != null){
                    isFinish = workRepository.getProjectIsFinish(project.getProjectID());

                    LocalDate limitLocalDate = LocalDate.parse(limitDate, formatter);
                    Period period = Period.between(today, limitLocalDate);

                    if(!isFinish && limitLocalDate.isAfter(today) && period.getDays() <= 7){
                        //아직 미완성이고 마감기한까지 7일 이내라면
                        alarmProjectList.add(project);
                    }
                }

            }

            if(!alarmProjectList.isEmpty()){
                model.addAttribute("alarm", "[마감 해야 할 프로젝트가 존재합니다!]");
                model.addAttribute("unFinished", alarmProjectList);
            }

            return "basic/coporatePageforAdvisor";
        }

        model.addAttribute("alarm", "아이디 또는 비밀번호가 틀렸습니다.");
        return "redirect:/basic/coporateLogin";
    }

    @GetMapping("/coporateLogin/UserLogin")
    public String CoporateUserLogin(@RequestParam String inputUserID,
                                    @RequestParam String inputUserPwd,
                                    Model model, HttpSession session){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        if(workRepository.selectUser(inputUserID, inputUserPwd)){

            int userDBid = workRepository.selectUserdbid(inputUserID);
            session.setAttribute("userDBid", userDBid);

            //마감기한 프로젝트 알람
            ArrayList<Project> projectList = workRepository.getProjectIDbyUserDBid(userDBid);
            String limitDate = null;  //마감기한 가져오기
            boolean isFinish;
            ArrayList<Project> alarmProjectList = new ArrayList<>();

            for(Project project : projectList){

                limitDate = workRepository.getProjectLimitDate(project.getProjectID());

                if(limitDate != null){
                    isFinish = workRepository.getProjectIsFinish(project.getProjectID());

                    LocalDate limitLocalDate = LocalDate.parse(limitDate, formatter);
                    Period period = Period.between(today, limitLocalDate);

                    if(!isFinish && limitLocalDate.isAfter(today) && period.getDays() <= 7){
                        //아직 미완성이고 마감기한까지 7일 이내라면
                        alarmProjectList.add(project);
                    }
                }

            }

            if(!alarmProjectList.isEmpty()){
                model.addAttribute("alarm", "[마감 해야 할 프로젝트가 존재합니다!]");
                model.addAttribute("unFinished", alarmProjectList);
            }

            return "basic/coporatePageforUser";
        }
        model.addAttribute("alarm", "아이디 또는 비밀번호가 틀렸습니다.");
        return "redirect:/basic/coporateLogin";
    }

    @GetMapping("/coporateParticipateUser")
    public String viewCoporateParticipateUserPage(Model model, HttpSession session){

        return "basic/coporateParticipateUser";
    }

    @PostMapping("/coporateParticipateUser")
    public String participateUser(@RequestParam String inputProjectName,
                                  @RequestParam String inputUserID,
                                  Model model){

        int userDBid = workRepository.selectUserdbid(inputUserID);
        int projectDBid = workRepository.advisorProjectID(inputProjectName);

        if(userDBid !=0 && projectDBid != 0){

            workRepository.insertParticipantUser(projectDBid, userDBid);
            model.addAttribute("alarm", "해당 프로젝트에 참여되었습니다.");

        }else{
            model.addAttribute("alarm", "아이디 또는 프로젝트 이름이 잘못되었습니다.");
        }

        return "redirect:/basic/coporateParticipateUser";
    }

    @GetMapping("/coporateAllowedUser")
    public String viewCoporateAllowedUserPage(){

        return "basic/coporateAllowedUser";
    }

    @PostMapping("/coporateAllowedUser")
    public String AllowUsertoProject(@RequestParam String inputProjectName,
                                     @RequestParam String inputUserID,
                                     Model model){
        int userDBid = workRepository.selectUserdbid(inputUserID);
        int projectDBid = workRepository.advisorProjectID(inputProjectName);

        if(userDBid == 0 || projectDBid == 0){
            model.addAttribute("alarm", "해당하는 사용자나 프로젝트가 없습니다.");

        }else if(workRepository.selectParticipantUser(projectDBid, userDBid)){
            model.addAttribute("alarm", "이미 해당 프로젝트에 참여하고 있는 사용자입니다.");

        }else{
            workRepository.insertAllowedUser(projectDBid, userDBid);
            model.addAttribute("alarm", "권한이 허용되었습니다.");
        }

        return "redirect:/basic/coporateAllowedUser";
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

    @GetMapping("/coporateProjectUpload")
    public String viewcoporateProjectUploadPage(){
        return "basic/coporateProjectUpload";
    }

    @PostMapping("/coporateProjectUpload")
    public String coporateProjectUpload(@RequestParam String inputProjectName, Model model,
                                        HttpSession session){
        final int startGrade = 9;
        String startTime = dateToString(LocalDateTime.now());
        int advisorDBid = (int)session.getAttribute("advisorDBid");
        int totalProjectsNum = workRepository.selectAllProjectsNum();
        int projectID = 0;
        if(totalProjectsNum == 0 || workRepository.canUploadProject(advisorDBid)){

            workRepository.insertNewProject(advisorDBid, startGrade, inputProjectName, startTime, false);
            projectID = workRepository.getProjectIdBytwoInfo(advisorDBid, inputProjectName);
            //진행도 0% insert
            workRepository.insertProgressZero(projectID);

            model.addAttribute("alarm", "프로젝트가 업로드 되었습니다.");
        }else{
            model.addAttribute("alarm", "아직 미완성인 프로젝트가 있어서 업로드 할 수 없습니다.");
        }

        return "basic/coporateProjectUpload";
    }

    @GetMapping("/coporateOpenProject")
    public String viewCoporateOpenProjectPage(){

        return "basic/coporateFindProjectAdvisor";
    }

    @GetMapping("/coporateOpenProjectUser")
    public String viewCoporateOpenProjectForUser(){

        return "basic/coporateFindProjectUser";
    }

    @GetMapping("/coporateOpenProject/find")
    public String findProjectForAdvisor(@RequestParam String inputProjectName,
                                        Model model, HttpSession session){

        String str = workRepository.selectAnyProject(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");
        //int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int projectDBid = workRepository.getProjectIdBytwoInfo(advisorDBid, inputProjectName);
        LocalDateTime today = LocalDateTime.now();
        String s = "";

        if(!str.equals("")){
            String[] arr = str.split(",");
            String limitDate = workRepository.getProjectLimitDate(projectDBid); //마감기한 가져오기
            String finishDate = workRepository.getProjectFinishTime(projectDBid); //프로젝트 완성 날짜 가져오기
            //진행도 select
            int progress = workRepository.getProgressInfo(projectDBid);

            if(limitDate == null){
                limitDate = "마감 기한 미설정";

            }else{
                String[] limitDateArr = limitDate.split("-");
                LocalDateTime limitDateTime = LocalDateTime.of(Integer.parseInt(limitDateArr[0]),
                        Integer.parseInt(limitDateArr[1]), Integer.parseInt(limitDateArr[2]) , 0 ,0, 0);

                if(finishDate != null){ //프로젝트 마감 했을 때
                    String[] finishDateArr = finishDate.split(" ")[0].split("-");
                    LocalDateTime finishDateTime = LocalDateTime.of(Integer.parseInt(finishDateArr[0]), Integer.parseInt(finishDateArr[1]),
                            Integer.parseInt(finishDateArr[2]), 0, 0, 0);

                    if(finishDateTime.isAfter(limitDateTime)){
                        s = "[마감 실패]";

                    }else{
                        s = "[마감 성공 및 보고서 제출]";
                    }

                }else{
                    //아직 프로젝트 마감 안 됐을 때
                    if(today.isBefore(limitDateTime)){
                        s = "[진행중]";
                    }else{
                        s= "[마감 실패]";
                    }
                }
            }

            String info = "이름 : "+arr[0]+
                    ", 등급 : "+arr[1]+", "+arr[2]+", 마감기한 : "+
                    limitDate + ", "+s+", 진행도 : "+progress+"%";
            model.addAttribute("info", info);

        }else{
            model.addAttribute("alarm", "해당하는 프로젝트가 없습니다.");
        }

        return "basic/coporateFindProjectAdvisor";
    }

    @GetMapping("/coporateOpenProjectUser/find")
    public String findProjectForUser(@RequestParam String inputProjectName,
                                     Model model, HttpSession session){

        int userDBid = (int)session.getAttribute("userDBid");
        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        LocalDateTime today = LocalDateTime.now();
        String s = "";
        String str = workRepository.selectAnyProject(inputProjectName);

        if(workRepository.isParticipate(projectDBid, userDBid) || workRepository.selectAllowedProjectID(projectDBid, userDBid)){

            if(!str.equals("")){
                String[] arr = str.split(",");
                String limitDate = workRepository.getProjectLimitDate(projectDBid); //마감기한 가져오기
                //boolean isFinish = workRepository.getProjectIsFinish(projectDBid);
                String finishDate = workRepository.getProjectFinishTime(projectDBid); //프로젝트 완성 날짜 가져오기
                //진행도 select
                int progress = workRepository.getProgressInfo(projectDBid);

                if(limitDate == null){
                    limitDate = "마감 기한 미설정";

                }else{
                    String[] limitDateArr = limitDate.split("-");
                    LocalDateTime limitDateTime = LocalDateTime.of(Integer.parseInt(limitDateArr[0]),
                            Integer.parseInt(limitDateArr[1]), Integer.parseInt(limitDateArr[2]) , 0 ,0, 0);

                    if(finishDate != null){ //프로젝트 마감 했을 때
                        String[] finishDateArr = finishDate.split(" ")[0].split("-");
                        LocalDateTime finishDateTime = LocalDateTime.of(Integer.parseInt(finishDateArr[0]), Integer.parseInt(finishDateArr[1]),
                                Integer.parseInt(finishDateArr[2]), 0, 0, 0);

                        if(finishDateTime.isAfter(limitDateTime)){
                            s = "[마감 실패]";

                        }else{
                            s = "[마감 성공 및 보고서 제출]";
                        }

                    }else{
                        //아직 프로젝트 마감 안 됐을 때
                        if(today.isBefore(limitDateTime)){
                            s = "[진행중]";
                        }else{
                            s= "[마감 실패]";
                        }
                    }
                }

                String info = "이름 : "+arr[0]+
                        ", 등급 : "+arr[1]+", "+arr[2]
                        +", 마감기한 : "+limitDate + ", "+s+", 진행도 : "+progress+"%";;
                model.addAttribute("info", info);

            }else{
                model.addAttribute("alarm", "해당하는 프로젝트가 없습니다.");
            }

        }else{
            model.addAttribute("alarm", "해당하는 프로젝트가 없거나 권한이 없습니다.");
        }

        return "basic/coporateFindProjectUser";
    }

    @GetMapping("/coporateMakeTeam")
    public String viewCoporateMakeTeamPage(){

        return "basic/coporateMakeTeam";
    }

    @PostMapping("/coporateMakeTeam")
    public String makeNewTeam(@RequestParam String inputProjectName,
                              @RequestParam String inputTeamName,
                              @RequestParam String inputUserID,
                              Model model, HttpSession session){

        int myUserDBid = (int)session.getAttribute("userDBid");
        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int peopleNum = workRepository.selectProjectParticipantsNum(projectDBid);
        int teamId = 0;
        int userDBid = 0;

        if(peopleNum >=2 && projectDBid > 0){
            userDBid = workRepository.selectUserdbid(inputUserID);
            teamId = workRepository.selectTeamID(projectDBid, inputTeamName);

            if(teamId == 0){
                workRepository.insertNewProjectTeam(projectDBid, inputTeamName, false, null);
                teamId = workRepository.selectTeamID(projectDBid, inputTeamName);
            }

            if(!workRepository.isHeHaveTeam(teamId, myUserDBid)){
                workRepository.insertTeamMember(teamId, myUserDBid);
            }

            if(workRepository.selectRightParticipant(projectDBid, userDBid)){

                workRepository.insertTeamMember(teamId, userDBid);
                model.addAttribute("alarm","같은 팀으로 등록되었습니다.");
            }else{
                model.addAttribute("alarm","참가하고 있는 사용자가 아닙니다.");
            }

        }else{
            model.addAttribute("alarm", "해당 팀원 수가 2명 이하라서 팀 나누기를 할 수 없거나 " +
                    "해당하는 프로젝트가 없습니다.");
        }

        return "basic/coporateMakeTeam";
    }

    @GetMapping("/coporateReportSubmit")
    public String viewCoporateReportSubmitPage(){
        return "basic/coporateReportSubmit";
    }

    @PostMapping("/coporateReportSubmit")
    public String reportSubmit(@RequestParam String inputProjectName,
                               @RequestParam String inputTeamName,
                               @RequestParam String inputText,
                               Model model, HttpSession session){

        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int teamId = workRepository.selectTeamID(projectDBid, inputTeamName);
        int userDBid = (int)session.getAttribute("userDBid");

        if(teamId == 0){
            model.addAttribute("alarm","해당하는 프로젝트나 팀이 없습니다.");
            return "basic/coporateReportSubmit";
        }

        if(workRepository.selectRightTeam(teamId, userDBid)){

            workRepository.insertReportText(teamId, inputText);
            model.addAttribute("alarm","레포트가 업로드 되었습니다.");

        }else{
            model.addAttribute("alarm","소속 팀이 아닙니다.");
        }

        return "basic/coporateReportSubmit";
    }

    @GetMapping("/coporateReportConfirm")
    public String viewCoporateReportConfirmPage(){
        return "basic/coporateReportConfirm";
    }

    @GetMapping("/coporateReportConfirm/confirm")
    public String reportConfirm(@RequestParam String inputProjectName,
                                @RequestParam String inputTeamName,
                                @RequestParam String inputCheck, Model model,
                                HttpSession session){

        LocalDateTime today = LocalDateTime.now();
        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");
        int teamdbid;

        if(advisorDBid == workRepository.selectAdvisorDBidbyProjectName(inputProjectName) &&
        projectDBid > 0){

            teamdbid = workRepository.selectTeamID(projectDBid, inputTeamName);
            if(teamdbid > 0){

                if(inputCheck.equals("0")){ //확인용

                    Report report = workRepository.selectReports(teamdbid);
                    model.addAttribute("report", report.content);

                }else{//승인용
                    workRepository.updateReportConfirm(teamdbid);
                    workRepository.updateTeamFinishDate(teamdbid, dateToString(today));
                    model.addAttribute("alarm", "승인 되었습니다.");
                }

            }else{
                model.addAttribute("alarm", "해당하는 팀이 없습니다.");
            }

        }else{
            model.addAttribute("alarm", "해당 프로젝트가 없거나 관리자가 아닙니다.");
        }

        return "basic/coporateReportConfirm";
    }

    @GetMapping("/coporateProjectFinish")
    public String viewCoporateProjectFinishPage(){
        return "basic/coporateProjectFinish";
    }

    private boolean isThereNotConfrimReports(int projectID){
        int[] ids = workRepository.selectAllReports(projectID);
        int num = 0;

        if(ids == null){
            return false;
        }

        for(int i=0; i<ids.length; i++){
            if(workRepository.selectNotConfirmReport(ids[i])){
                num++;
            }
        }

        if(num > 0){
            return false;
        }else{
            return true;
        }
    }

    @GetMapping("/coporateProjectFinish/finish")
    public String projectFinish(@RequestParam String inputProjectName, Model model,
                                HttpSession session){

        LocalDateTime today = LocalDateTime.now();
        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");
        String finishTime;

        if(advisorDBid == workRepository.selectAdvisorDBidbyProjectName(inputProjectName) &&
                projectDBid > 0){

            if(isThereNotConfrimReports(projectDBid)){

                finishTime = dateToString(today);
                workRepository.updateFinishDate(projectDBid, finishTime);
                workRepository.updateFinishProject(projectDBid);
                //진행도 100% 수정
                workRepository.updateProgress(projectDBid, 100);

                model.addAttribute("alarm",
                        "해당 프로젝트 완성 승인");

            }else{
                model.addAttribute("alarm",
                        "승인이 안된 리포트가 있거나 리포트가 아직 업로드가 안 됐습니다.");
            }

        }else{
            model.addAttribute("alarm", "해당 프로젝트가 없거나 관리자가 아닙니다.");
        }

        return "basic/coporateProjectFinish";
    }

    @GetMapping("/coporateProjectLimitDate")
    public String viewCoporateProjectLimitDatePage(){
        return "basic/coporateProjectLimitDate";
    }

    @PostMapping("/coporateProjectLimitDate")
    public String setProjectLimitDate(@RequestParam String inputProjectName,
                                      @RequestParam int inputYear,
                                      @RequestParam int inputMonth,
                                      @RequestParam int inputDay,
                                      Model model, HttpSession session){

        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");

        if(advisorDBid == workRepository.selectAdvisorDBidbyProjectName(inputProjectName) &&
                projectDBid > 0){

            if(workRepository.isAlreadySetLimitDate(projectDBid)){
                model.addAttribute("alarm", "이미 마감기한이 설정되었습니다.");

            }else{
                StringBuilder date = new StringBuilder();

                date.append(inputYear);
                date.append("-");
                if(inputMonth < 10){
                    date.append("0");
                }
                date.append(inputMonth);
                date.append("-");
                if(inputDay < 10){
                    date.append("0");
                }
                date.append(inputDay);

                workRepository.setLimitDate(projectDBid, date.toString());
                model.addAttribute("alarm", "마감기한이 설정되었습니다.");
            }

        }else{
            model.addAttribute("alarm", "해당 프로젝트가 없거나 관리자가 아닙니다.");
        }

        return "basic/coporateProjectLimitDate";
    }

    @GetMapping("/coporateRemoveUser")
    public String viewCoporateRemoveUserPage(){
        return "basic/coporateRemoveUser";
    }

    @GetMapping("/coporateRemoveUser/remove")
    public String removeUserPage(@RequestParam String inputProjectName,
                                 @RequestParam String inputUserId,
                                 Model model, HttpSession session) {

        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");

        if(advisorDBid == workRepository.selectAdvisorDBidbyProjectName(inputProjectName) &&
                projectDBid > 0){
            int userDBid = workRepository.selectUserdbid(inputUserId);

            if(workRepository.isParticipate(projectDBid, userDBid)){

                workRepository.deleteProjectParticipantUser(userDBid);
                workRepository.deleteProjectTeamMember(userDBid);

                //팀 멤버 확인 후 팀 제거
                ConfirmRemoveTeam(projectDBid);

                model.addAttribute("alarm", "퇴출되었습니다.");
            }else{
                model.addAttribute("alarm", "해당 프로젝트 참여자가 아닙니다.");
            }

        }else{
            model.addAttribute("alarm", "해당 프로젝트가 없거나 관리자가 아닙니다.");
        }

        return "basic/coporateRemoveUser";
    }

    private void ConfirmRemoveTeam(int projectDBid){
        ArrayList<Integer> list = workRepository.selectAllTeamID(projectDBid);

        for(int id : list){
            if(!workRepository.isTheTeamExistInTable(id)){
                workRepository.deleteProjectTeam(id);
            }
        }
    }

    @GetMapping("/coporateProjectModifyAndRemove")
    public String viewCoporateProjectModifyAndRemovePage(){
        return "basic/coporateProjectModifyAndRemove";
    }


    //진행도 DB 수정
    @GetMapping("/coporateProgressModify")
    public String coporateProjectProgressModify(@RequestParam String inputProjectName,
                                                @RequestParam int inputProgress,
                                                Model model, HttpSession session){

        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");

        if(advisorDBid == workRepository.selectAdvisorDBidbyProjectName(inputProjectName) &&
                projectDBid > 0){

            workRepository.updateProgress(projectDBid, inputProgress);
            model.addAttribute("alarm", "진행도가 수정돼었습니다.");
        }else{
            model.addAttribute("alarm", "해당 프로젝트가 없거나 관리자가 아닙니다.");
        }

        return "basic/coporateProjectModifyAndRemove";
    }

    @GetMapping("/coporateProjectModifyAndRemove/modify")
    public String coporateProjectModify(@RequestParam String inputProjectName,
                                        @RequestParam int inputGrade,
                                        @RequestParam String inputNewProjectName,
                                        HttpSession session, Model model){

        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");

        if(advisorDBid == workRepository.selectAdvisorDBidbyProjectName(inputProjectName) &&
                projectDBid > 0){

            workRepository.updateProjectGrade(inputProjectName, inputGrade);
            workRepository.updateProjectName(projectDBid, inputNewProjectName);

            model.addAttribute("alarm", "수정돼었습니다.");

        }else{
            model.addAttribute("alarm", "해당 프로젝트가 없거나 관리자가 아닙니다.");
        }

        return "basic/coporateProjectModifyAndRemove";
    }

    @GetMapping("/coporateProjectModifyAndRemove/remove")
    public String coporateProjectRemove(@RequestParam String inputProjectName,
                                        HttpSession session, Model model){

        int projectDBid = workRepository.advisorProjectID(inputProjectName);
        int advisorDBid = (int)session.getAttribute("advisorDBid");

        if(advisorDBid == workRepository.selectAdvisorDBidbyProjectName(inputProjectName) &&
                projectDBid > 0){

            workRepository.deleteProject(projectDBid);
            workRepository.deleteProjectLimitDate(projectDBid);
            workRepository.deleteProjectParticipants(projectDBid);
            //진행도 DB 삭제
            workRepository.deleteProgressInfo(projectDBid);

            ArrayList<Integer> list = workRepository.selectAllTeamID(projectDBid);
            for(int id : list){
                workRepository.deleteProjectTeamByTeamID(id);
            }

            workRepository.deleteAllProjectTeamsByProjectID(projectDBid);
            model.addAttribute("alarm", "삭제되었습니다.");

        }else{
            model.addAttribute("alarm", "해당 프로젝트가 없거나 관리자가 아닙니다.");
        }

        return "basic/coporateProjectModifyAndRemove";
    }

    /*======================================================================================*/
    /*여행사 프로그램 이후*/

    @GetMapping("/travelUserLogin")
    public String veiwTravelUserLoginPage(){

        return "basic/travelAgencyUserLogin";
    }

    @GetMapping("/travelUserLogin/login")
    public String loginTravelUser(@RequestParam String inputUserID,
                                  @RequestParam String inputUserPwd,
                                  HttpSession session, Model model){

        if(workRepository.selectTravelUser(inputUserID, inputUserPwd)){

            int userDBid = workRepository.selectTravelUserDBid(inputUserID);
            session.setAttribute("userDBid", userDBid);

            return "basic/travelAgencyUserMainPage";

        }else{
            model.addAttribute("alarm", "아이디 또는 비밀번호가 잘못되었습니다.");
        }

        return "basic/travelAgencyUserLogin";
    }

    @GetMapping("/travelUserMakeAccount")
    public String viewTravelUserMakeAccountPage(){
        return "basic/travelAgencyUserMakeAccount";
    }

    @PostMapping("/travelUserMakeAccount")
    public String travelUserMakeAccount(Model model,
                                        @RequestParam String inputUserID,
                                        @RequestParam String inputUserPwd,
                                        @RequestParam String inputUserName,
                                        @RequestParam String inputUserPhoneNum,
                                        @RequestParam String inputUserEmail){

        workRepository.insertNewTravelUser(inputUserID, inputUserPwd, inputUserName, inputUserPhoneNum, inputUserEmail);
        model.addAttribute("alarm", "회원가입이 완료되었습니다.");

        return "basic/travelAgencyUserMakeAccount";
    }

    @GetMapping("/travleUserMainPage")
    public String viewTravleUserMainPage(){
        return "basic/travelAgencyUserMainPage";
    }

    @GetMapping("/travelAgencyUserLike")
    public String viewTravelAgencyUserLike(){
        return "basic/travelAgencyUserLike";
    }

    @PostMapping("/travelAgencyUserLike")
    public String addTravelAgencyUserLike(@RequestParam String inputMaxPrice,
                                          @RequestParam String inputMaxDays,
                                          @RequestParam String inputRegion,
                                          HttpSession session, Model model){
        int userDBid = (int)session.getAttribute("userDBid");
        int maxPrice, maxDays;

        if(inputMaxPrice.equals("") || inputMaxDays.equals("") || inputRegion.equals("")){

            model.addAttribute("alarm", "모두 다 입력해야합니다.");
        }else{

            maxPrice = Integer.parseInt(inputMaxPrice);
            maxDays = Integer.parseInt(inputMaxDays);

            if(workRepository.isExistTravelUserLikeInfo(userDBid)){
                workRepository.updateTravelUserLikeInfo(userDBid, maxPrice, maxDays, inputRegion);
            }else{
                workRepository.insertTravelUserLikeInfo(userDBid, maxPrice, maxDays, inputRegion);
            }


            model.addAttribute("alarm", "저장되었습니다.");
        }

        return "basic/travelAgencyUserLike";
    }

    @GetMapping("/travelAgency/price")
    public String printTravelsByPrice(@RequestParam String inputLowPrice,
                                      @RequestParam String inputHighPrice,
                                      Model model){

        int lowPrice = inputLowPrice.equals("") ? 0 : Integer.parseInt(inputLowPrice);
        int highPrice = inputHighPrice.equals("") ? 0 : Integer.parseInt(inputHighPrice);

        if(lowPrice < highPrice){

            ArrayList<Travel> travels = workRepository.selectTravelsByPrice(lowPrice, highPrice);

            model.addAttribute("travels", travels);
        }else{
            model.addAttribute("alarm", "최소 가격이 최대 가격보다 낮아야 합니다.");
        }

        return "basic/travelAgencyUserMainPage";
    }

    @GetMapping("/travelAgency/recommend")
    public String recommendTravels(HttpSession session, Model model){

        int userDBid = (int)session.getAttribute("userDBid");

        if(workRepository.isExistTravelUserLikeInfo(userDBid)){
            Travellike travellike = workRepository.selectTravelUserLike(userDBid);

            ArrayList<Travel> travels = workRepository.selectTravlesByLikeInfo(travellike.getMaxPrice(),
                    travellike.getMaxDays(), travellike.getRegion());

            if(travels.isEmpty()){
                model.addAttribute("alarm", "결과가 없습니다.");
            }else{
                model.addAttribute("travels", travels);
            }


        }else{
            model.addAttribute("alarm", "선호도 정보부터 입력하세요.");
        }

        return "basic/travelAgencyUserMainPage";
    }

    @GetMapping("/travelAgency/days")
    public String selectTravelsBydays(@RequestParam String inputShortDays,
                                      @RequestParam String inputLongDays,
                                      Model model){

        int shortDays = inputShortDays.equals("") ? 0 : Integer.parseInt(inputShortDays);
        int longDays = inputLongDays.equals("") ? 0 : Integer.parseInt(inputLongDays);

        if(shortDays < longDays){

            ArrayList<Travel> travels = workRepository.selectTravlesByDays(shortDays, longDays);

            if(travels.isEmpty()){
                model.addAttribute("alarm", "결과가 없습니다.");
            }else{
                model.addAttribute("travels", travels);
            }

        }else{
            model.addAttribute("alarm", "최장 기간이 최소 기간보다 짧습니다.");
        }

        return "basic/travelAgencyUserMainPage";
    }

    @GetMapping("/travelAgency/region")
    public String selectTravlesByRegion(@RequestParam String inputRegion, Model model){

        ArrayList<Travel> travels = workRepository.selectTravlesByRegion(inputRegion);

        if(travels.isEmpty()){
            model.addAttribute("alarm", "결과가 없습니다.");

        }else{
            model.addAttribute("travels", travels);
        }

        return "basic/travelAgencyUserMainPage";
    }

    /*==================================================================
    * 에너지 관리자*/
    private int lastEnergyGameTurn = 12;
    @GetMapping("/energyGameLogin")
    public String viewEnergyGameLoginPage(){

        return "basic/energyGameLogin";
    }

    @GetMapping("/energyGameMakeAccount")
    public String viewEnergyGameMakeAccount(){
        return "basic/energyGameMakeAccount";
    }

    @GetMapping("/energyGameLogin/login")
    public String loginEnergyGame(@RequestParam String inputID,
                                  @RequestParam String inputPwd,
                                  HttpSession session, Model model){

        if(workRepository.selectEnergyGameAccount(inputID, inputPwd)){

            int userDBid = workRepository.selectEnegryGameUserDBid(inputID);
            session.setAttribute("userDBid", userDBid);
            Player player = new Player();
            session.setAttribute("playerState", player);

            model.addAttribute("player", player);

            return "basic/energyGamePlay";
        }

        model.addAttribute("alarm", "아이디 또는 비밀번호가 잘못되었습니다.");

        return "basic/energyGameLogin";
    }

    @PostMapping("/energyGameMakeAccount")
    public String makeEnergyGameAccount(@RequestParam String inputID,
                                        @RequestParam String inputPwd,
                                        HttpSession session, Model model){

        workRepository.insertEnergyGameAccount(inputID, inputPwd);

        int userDBid = workRepository.selectEnegryGameUserDBid(inputID);
        session.setAttribute("userDBid", userDBid);

        model.addAttribute("alarm", "회원가입이 완료되었습니다.");

        return "basic/energyGameMakeAccount";
    }

    @GetMapping("/energyGamePlay")
    public String viewEnergyGamePlayPage(HttpSession session, Model model){

        Player player = (Player)session.getAttribute("playerState");

        player.calcPopulation();
        player.calcTotalEnergyBuildings();
        //에너지 소비 코드 마지막 턴 끝났을때 실행
        if(player.turn > lastEnergyGameTurn){
            int totalSpendEnergy = 0;

            totalSpendEnergy += player.population;
            totalSpendEnergy += player.commercialCentersNum*2;

            player.useEnergy(totalSpendEnergy);

            player.turn = 0;

        }

        if(player.getEnergyPool() < 0 && player.totalEnvironmentEffect < 0){
            model.addAttribute("alarm", "파산합니다.");

        }else if(player.getEnergyPool() < 0){//hp 상태 확인
            model.addAttribute("alarm", "에너지가 부족합니다.");
        }

        model.addAttribute("player", player);
        return "basic/energyGamePlay";
    }

    @GetMapping("/energyGamePlay/addCity")
    public String addCityEnergyGame(HttpSession session, Model model){
        Player player = (Player)session.getAttribute("playerState");
        player.addCity();
        player.turn++;

        if(player.turn > lastEnergyGameTurn){
            //에너지 소비 코드
            int totalSpendEnergy = 0;

            totalSpendEnergy += player.population;
            totalSpendEnergy += player.commercialCentersNum*2;

            player.useEnergy(totalSpendEnergy);

            player.turn = 0;

        }

        if(player.getEnergyPool() < 0 && player.totalEnvironmentEffect < 0){
            model.addAttribute("alarm", "파산합니다.");

        }else if(player.getEnergyPool() < 0){//hp 상태 확인
            model.addAttribute("alarm", "에너지가 부족합니다.");
        }

        session.setAttribute("playerState", player);

        model.addAttribute("player", player);
        return "basic/energyGamePlay";
    }

    @GetMapping("/energyGameAddPerson")
    public String viewEnergyGameAddPerson(HttpSession session, Model model,
                                          RedirectAttributes reAttr){
        Player player = (Player)session.getAttribute("playerState");
        ArrayList<City> playerCities = player.cities;

        if(player.turn >= 4 && player.turn <= 6){

            model.addAttribute("cities", playerCities);
        }else{
            reAttr.addAttribute("status", true);

            return "redirect:/basic/work-list/energyGamePlay";
        }

        return "basic/energyGameAddPerson";
    }

    private int maxPeopleZoneNum = 4; //기본 수용 인구
    @PostMapping("/energyGameAddPerson")
    public String addPersonEnergyGame(@RequestParam int inputCityNum,
                                      @RequestParam int inputZoneNum,
                                      @RequestParam int inputPersonNum,
                                      HttpSession session, Model model){

        Player player = (Player)session.getAttribute("playerState");
        ArrayList<City> playerCities = player.cities;

        if(inputCityNum <= playerCities.size() && inputCityNum >= 1 && inputZoneNum <= 4 && inputZoneNum >= 1){
            maxPeopleZoneNum += player.cities.get(inputCityNum-1).zones[inputZoneNum-1].getResidantArea()*2;

            if(inputPersonNum > maxPeopleZoneNum || inputPersonNum < 1){
                model.addAttribute("alarm", "사람 수가 너무 적거나 많습니다.");

            }else{

                player.cities.get(inputCityNum-1).zones[inputZoneNum-1].plusPersonNum(inputPersonNum);

                if(player.cities.get(inputCityNum-1).zones[inputZoneNum-1].getPersonNum() > maxPeopleZoneNum){
                    model.addAttribute("alarm", "수용 인원 초과입니다.");
                    player.cities.get(inputCityNum-1).zones[inputZoneNum-1].plusPersonNum(inputPersonNum*(-1));

                }else{

                    player.calcPopulation();

                    player.turn++;
                    model.addAttribute("alarm", "인원이 추가되었습니다.");
                }

            }

        }else{

            if(inputCityNum > playerCities.size() || inputCityNum < 1){
                model.addAttribute("alarm", "해당하는 도시가 없습니다.");

            }else {
                model.addAttribute("alarm", "해당하는 구역이 없습니다.");
            }

        }


        model.addAttribute("cities", playerCities);

        return "basic/energyGameAddPerson";
    }

    @GetMapping("/energyGamePlay/saveEnergy")
    public String saveEnergy(HttpSession session, Model model,
                             RedirectAttributes reAttr){
        Player player = (Player)session.getAttribute("playerState");

        if(player.turn >= 0 && player.turn <= 3){

            int energy = player.fossilFuelEnergyPlantsNum*PowerPlant.fossilFuelEfficient +
                    player.sunshineEnergyPlantsNum * PowerPlant.sunshineEffienct+
                    player.windEnergyPlantsNum * PowerPlant.windEfficient+
                    player.waterEnergyPlantsNum * PowerPlant.waterEfficient;

            int environ = player.fossilFuelEnergyPlantsNum*PowerPlant.fossilFuelEnvironmentEffect +
                    player.sunshineEnergyPlantsNum * PowerPlant.sunshineEnvironmentEffect+
                    player.windEnergyPlantsNum * PowerPlant.windEnvironmentEffect+
                    player.waterEnergyPlantsNum * PowerPlant.waterEnvironmentEffect;

            player.saveEnergy(energy); //발전소로 에너지 저장
            player.totalEnvironmentEffect -= environ; //발전소 통한 환경오염
            player.turn++;
            player.calcTotalEnergyBuildings();

            session.setAttribute("playerState", player);
        }else{
           reAttr.addAttribute("status3", true);
        }

        return "redirect:/basic/work-list/energyGamePlay";
    }

    @GetMapping("/energyGameTax")
    public String makeTax(HttpSession session,
                          RedirectAttributes reAttr){

        Player player = (Player)session.getAttribute("playerState");

        if(player.turn == 7 && (player.population > 0 || player.commercialCentersNum > 0)){
            //사람 인구, 상업센터 포함해서 세금 징수
            int tax = player.getTax() + player.population + player.commercialCentersNum*2;
            player.setTax(tax); //세금 납부

            player.turn++;
            session.setAttribute("playerState", player);

        }else{
            reAttr.addAttribute("status2", true);
        }

        return "redirect:/basic/work-list/energyGamePlay";
    }

    @GetMapping("/energyGamePlay/addEnergyBuilding")
    public String viewEnergyBuildingsPage(HttpSession session, Model model,
                                    RedirectAttributes reAttr){

        Player player = (Player)session.getAttribute("playerState");

        if(!(player.turn >= 7 && player.turn <= 9)){

            reAttr.addAttribute("status4", true);
            return "redirect:/basic/work-list/energyGamePlay";

        }

        model.addAttribute("player", player);

        return "basic/energyPowerPlantPage";
    }

    private final int energyPlantCost = 2;
    @PostMapping("/energyGamePlay/addEnergyBuilding/add")
    public String addEnergyBuilding(@RequestParam int inputPlantNum,
                                    HttpSession session, Model model,
                                    RedirectAttributes reAttr){
        Player player = (Player)session.getAttribute("playerState");

        if((inputPlantNum >= 1 && inputPlantNum <= 4) && (player.getTax() >= energyPlantCost)){

            switch (inputPlantNum){
                case 1 :
                    player.fossilFuelEnergyPlantsNum++;
                    break;
                case 2 :
                    player.sunshineEnergyPlantsNum++;
                    break;
                case 3 :
                    player.windEnergyPlantsNum++;
                    break;
                case 4:
                    player.waterEnergyPlantsNum++;
            }

            player.setTax(player.getTax() - energyPlantCost);
            player.turn++;
            session.setAttribute("playerState", player);
            model.addAttribute("player", player);
            model.addAttribute("alarm", "추가되었습니다.");
           return "basic/energyPowerPlantPage";

        }else if((inputPlantNum >= 1 && inputPlantNum <= 4) && (player.getTax() < energyPlantCost)){

            model.addAttribute("player", player);
            model.addAttribute("alarm", "잔액이 부족합니다.");
            return "basic/energyPowerPlantPage";
        }

        model.addAttribute("player", player);
        reAttr.addAttribute("plantStatus", true);

        return "redirect:/basic/work-list/energyGamePlay/addEnergyBuilding";
    }

    @GetMapping("/energyGamePlay/pass")
    public String gamePass(HttpSession session, Model model){
        Player player = (Player)session.getAttribute("playerState");
        player.turn++;
        player.calcPopulation();

        //에너지 소비 코드 마지막 턴 끝났을때 실행
        if(player.turn > lastEnergyGameTurn){
            int totalSpendEnergy = 0;

            totalSpendEnergy += player.population;
            totalSpendEnergy += player.commercialCentersNum*2;

            player.useEnergy(totalSpendEnergy);

            player.turn = 0;

        }

        if(player.getEnergyPool() < 0 && player.totalEnvironmentEffect < 0){
            model.addAttribute("alarm", "파산합니다.");

        }else if(player.getEnergyPool() < 0){//hp 상태 확인
            model.addAttribute("alarm", "에너지가 부족합니다.");
        }

        model.addAttribute("player", player);
        session.setAttribute("playerState", player);
        return "basic/energyGamePlay";
    }

    @GetMapping("/energyGamePlay/addResidentArea")
    public String viewAddResidentAreaPage(HttpSession session, Model model,
                                          RedirectAttributes reAttr){
        Player player = (Player)session.getAttribute("playerState");
        ArrayList<City> playerCities = player.cities;

        if(player.turn >= 10 && player.turn <= 12){
            model.addAttribute("cities", playerCities);
            return "basic/energyGameAddResidentArea";
        }

        reAttr.addAttribute("status5", true);
        return "redirect:/basic/work-list/energyGamePlay";
    }

    @PostMapping("/energyGamePlay/addResidentArea")
    public String addResidentArea(@RequestParam int inputCityNum,
                                  @RequestParam int inputZoneNum,
                                  HttpSession session, Model model){ //주거 시설 추가

        Player player = (Player)session.getAttribute("playerState");
        ArrayList<City> playerCities = player.cities;
        int totalSpendEnergy ;
        if(inputCityNum <= playerCities.size() && inputCityNum >= 1 && inputZoneNum <= 4 && inputZoneNum >= 1){

            player.cities.get(inputCityNum-1).zones[inputZoneNum-1].addResidentArea();
            player.turn++;
            model.addAttribute("alarm", "주거 지역이 추가되었습니다.");

            //에너지 소비 코드 마지막 턴 끝났을때 실행
            if(player.turn > lastEnergyGameTurn){
                totalSpendEnergy = 0;

                totalSpendEnergy += player.population;
                totalSpendEnergy += player.commercialCentersNum*2;

                player.useEnergy(totalSpendEnergy);

                player.turn = 0;

            }

            if(player.getEnergyPool() < 0 && player.totalEnvironmentEffect < 0){
                model.addAttribute("alarm", "파산합니다.");

            }else if(player.getEnergyPool() < 0){//hp 상태 확인
                model.addAttribute("alarm", "에너지가 부족합니다.");
            }

        }else{

            if(inputCityNum > playerCities.size() || inputCityNum < 1){
                model.addAttribute("alarm", "해당하는 도시가 없습니다.");

            }else {
                model.addAttribute("alarm", "해당하는 구역이 없습니다.");
            }

        }

        model.addAttribute("cities", playerCities);
        return "basic/energyGameAddResidentArea";
    }

    @GetMapping("/energyGamePlay/addCommercialCenter") //상업센터 추가
    public String addCommercialCenter(HttpSession session, Model model,
                                      RedirectAttributes reAttr){

        Player player = (Player)session.getAttribute("playerState");
        int totalSpendEnergy;
        if(player.turn >= 10 && player.turn <= 12){

            player.calcPopulation();
            player.turn++;
            player.commercialCentersNum++;

            //에너지 소비 코드 마지막 턴 끝났을때 실행
            if(player.turn > lastEnergyGameTurn){
                totalSpendEnergy = 0;

                totalSpendEnergy += player.population;
                totalSpendEnergy += player.commercialCentersNum*2;

                player.useEnergy(totalSpendEnergy);

                player.turn = 0;

            }

            if(player.getEnergyPool() < 0 && player.totalEnvironmentEffect < 0){
                model.addAttribute("alarm", "파산합니다.");

            }else if(player.getEnergyPool() < 0){//hp 상태 확인
                model.addAttribute("alarm", "에너지가 부족합니다.");
            }

            session.setAttribute("playerState", player);
            model.addAttribute("player", player);

            return "basic/energyGamePlay";
        }

        reAttr.addAttribute("status6", true);
        return "redirect:/basic/work-list/energyGamePlay";
    }

    @GetMapping("/energyGamePlay/payCost")
    public String energyStayPayCost(HttpSession session, Model model,
                                    RedirectAttributes reAttr){
        Player player = (Player)session.getAttribute("playerState");
        //System.out.println(player.turn);
        int playerTax, totalCost;
        int totalSpendEnergy = 0;

        if(player.turn == 12 && player.totalEnvironmentEffect < 0){
            player.calcPopulation();
            player.turn++;
            playerTax = player.getTax();
            totalCost = player.fossilFuelEnergyPlantsNum*PowerPlant.fossilFuelStayCost+
                    player.sunshineEnergyPlantsNum*PowerPlant.sunshineStayCost+
                    player.windEnergyPlantsNum*PowerPlant.windStayCost+
                    player.waterEnergyPlantsNum*PowerPlant.waterStayCost;

            playerTax -= totalCost;
            player.setTax(playerTax);
            player.totalEnvironmentEffect += totalCost;

            totalSpendEnergy += player.population;
            totalSpendEnergy += player.commercialCentersNum*2;
            player.useEnergy(totalSpendEnergy);

            player.turn = 0;

            if(player.getEnergyPool() < 0 && player.totalEnvironmentEffect < 0){
                model.addAttribute("alarm", "파산합니다.");
            }

            session.setAttribute("playerState", player);
            model.addAttribute("player", player);
            return "basic/energyGamePlay";

        }else if(player.turn == 12){
            session.setAttribute("playerState", player);
            model.addAttribute("player", player);
            model.addAttribute("alarm", "유지 비용 지불할 필요가 없습니다.");
            return "basic/energyGamePlay";

        }else{
            reAttr.addAttribute("status7", true);
            return "redirect:/basic/work-list/energyGamePlay";
        }

    }

    @PostConstruct
    public void init(){
        workRepository.save(new Work("삼성전자에서 최대한 열심히 같이 일하실 분 오세요!", "서울시", 3000000));
        workRepository.save(new Work("LG전자에서 같이 일하고 스팩도 쌓으실 분 모셔요~!", "경기도", 2800000));
    }
}
