package work.worklist.domain.repowitory;

import org.springframework.stereotype.Repository;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import work.worklist.domain.work.*;
import work.worklist.web.Connection.DBdataUtility;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static work.worklist.web.Connection.DBdata.*;

@Repository
public class WorkRepository {
    private static final Map<Long, Work> data = new HashMap<>();

    private static long sequence = 0L;

    public Work save(Work work){
        work.setId(++sequence);
        data.put(work.getId(), work);
        return work;
    }

    public Work findById(Long id){
        return data.get(id);
    }
    public List<Work> findAll(){
        return new ArrayList<>(data.values());
    }
    public void update(Long workId, Work updateData){
        Work findWork = findById(workId);
        findWork.setWorkName(updateData.getWorkName());
        findWork.setWorkAddress(updateData.getWorkAddress());
        findWork.setPay(updateData.getPay());
    }

    public void insertNewAccount(Account account){
        String sql = "insert into ONLINEACCOUNTUSERS(personalnum, handphonenum, cardsecretnum) values(?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getPersonalNum());
            preparedStatement.setString(2, account.getHandPhoneNum());
            preparedStatement.setString(3, account.getCardSecretNum());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectUserDBid(String personalNum, String handPhoneNum, String cardSecretNum){
        String sql = "select * from ONLINEACCOUNTUSERS where personalnum = ? and handphonenum = ? and cardsecretnum = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, personalNum);
            preparedStatement.setString(2, handPhoneNum);
            preparedStatement.setString(3, cardSecretNum);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void insertNewZeroAccount(int id){
        String sql = "insert into ONLINEACCOUNTINFO(userid, money) values(?, 0)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectUserMoney(int id){
        String sql = "select * from ONLINEACCOUNTINFO where userid = ?";
        int money = 0;
        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                money = resultSet.getInt("money");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return money;
    }

    public void updateUserMoney(int id, int totalMoney){
        String sql = "update ONLINEACCOUNTINFO set money = ? where userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, totalMoney);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewThing(String thingname, String thingContent){
        String sql = "insert into thingstable(name, content) values(?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, thingname);
            preparedStatement.setString(2, thingContent);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Thing> selectAllThings(){
        String sql = "select * from thingstable";
        ArrayList<Thing> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                list.add(new Thing(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("content")
                ));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public ArrayList<Thing> selectSearchThings(String text, String text2){
        ArrayList<Thing> list = new ArrayList<>();
        String sql = "select * from thingstable where locate(?, name) > 0 or locate(?, content) > 0";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, text);
            preparedStatement.setString(2, text2);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new Thing(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("content")));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public String selectThingInfos(int id){
        String sql = "select * from thingstable where id = ?";
        String str = "";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                str = resultSet.getString("name")+"/"+resultSet.getString("content");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return str;
    }

    public ArrayList<Review> selectThatThingReviews(int id){
        String sql = "select * from thingsreview where thing_id = ?";
        ArrayList<Review> reviewList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<Integer, String> starMap = new HashMap<>();

        starMap.put(1, "★");
        starMap.put(2, "★★");
        starMap.put(3, "★★★");
        starMap.put(4, "★★★★");
        starMap.put(5, "★★★★★");

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                if(!resultSet.getBoolean("ishidden")){
                    String time = String.valueOf(resultSet.getString("reviewtime"));
                    LocalDateTime reviewtime = LocalDateTime.parse(time, formatter);

                    Review review = new Review(starMap.get(resultSet.getInt("star")),
                            resultSet.getString("review"),
                            reviewtime);
                    reviewList.add(review);
                }

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return reviewList;
    }

    public ArrayList<Review> selectAllThatThingsReview(int id){
        String sql = "select * from thingsreview where thing_id = ?";
        ArrayList<Review> reviewList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<Integer, String> starMap = new HashMap<>();

        starMap.put(1, "★");
        starMap.put(2, "★★");
        starMap.put(3, "★★★");
        starMap.put(4, "★★★★");
        starMap.put(5, "★★★★★");

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String starstr = starMap.get(resultSet.getInt("star"));
                String time = String.valueOf(resultSet.getString("reviewtime"));
                LocalDateTime reviewtime = LocalDateTime.parse(time, formatter);

                reviewList.add(new Review(starstr,
                        resultSet.getString("review"),
                        reviewtime));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return reviewList;
    }

    public void insertNewReivew(int id, String review, int star){
        String sql = "insert into thingsreview(thing_id, review, ishidden, star, reviewtime) values(?, ?, false, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, review);
            preparedStatement.setInt(3, star);
            preparedStatement.setTime(4, Time.valueOf(LocalDateTime.now().toLocalTime()));

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteReviewByid(int id, String review){
        String sql = "delete thingsreview where thing_id = ? and review = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, review);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateIshiddenTrueReview(int id, String review){
        String sql = "update thingsreview set ishidden = true where thing_id = ? and review = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, review);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateIshiddenfalseReview(int id, String review){
        String sql = "update thingsreview set ishidden = false where thing_id = ? and review = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, review);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateThingInfo(int id, String name, String content){
        String sql = "update THINGSTABLE set name = ?, content = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, content);
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertAdvisorAccount(String id, String pwd){
        String sql = "insert into THINGSADVISORACCOUNT(advisorid, pwd) values(?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectAdvisorAccount(String id, String pwd){
        String sql = "select * from THINGSADVISORACCOUNT where advisorid = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
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

    public void insertMovieUserAccount(String id, String pwd){
        String sql = "insert into movieuseraccount(userid, pwd) values(? ,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectMovieUser(String id, String pwd){
        String sql = "select * from movieuseraccount where userid = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
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

    public void insertMovieAdvisorAccount(String id, String pwd){
        String sql = "insert into movieadvisoraccount(advisorid, pwd) values(? ,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectMovieAdvisorAccount(String id, String pwd){
        String sql = "select * from movieadvisoraccount where advisorid = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
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

    public int getMovieAdvisorDBid(String id){

        String sql = "select * from movieadvisoraccount where advisorid = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertNewMovie(int advisor_id, String movieTitle, LocalDateTime opendate,
                               LocalDateTime movieStart, int totalSize, int price,
                               LocalDateTime closeDate, int time){

        String sql = "insert into movielist(advisor_id, movie_title, opendate, movie_start," +
                "totalsize, price, closedate, time) values(?,?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisor_id);
            preparedStatement.setString(2, movieTitle);
            preparedStatement.setDate(3, Date.valueOf(opendate.toLocalDate()));

            preparedStatement.setTime(4, Time.valueOf(movieStart.toLocalTime()));
            preparedStatement.setInt(5, totalSize);
            preparedStatement.setInt(6, price);
            preparedStatement.setDate(7, Date.valueOf(closeDate.toLocalDate()));
            preparedStatement.setInt(8, time);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Movie> selectAllMovies(int advisorDBid){
        String sql = "select * from movielist where advisor_id = ?";
        ArrayList<Movie> movies = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int advisor_id = resultSet.getInt("advisor_id");
                String movieTitle = resultSet.getString("movie_title");
                String movieStartstr = String.valueOf(resultSet.getString("movie_start"));
                int totalsize = resultSet.getInt("totalsize");
                int price = resultSet.getInt("price");
                int time = resultSet.getInt("time");

                LocalDateTime movieStart = LocalDateTime.parse(movieStartstr, formatter2);
                Date openDate = resultSet.getDate("opendate");
                Date closeDate = resultSet.getDate("closedate");

                Movie movie = new Movie(
                        advisor_id,
                        movieTitle,
                        openDate,
                        movieStart,
                        totalsize,
                        price,
                        closeDate,
                        time
                );
                movies.add(movie);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return movies;
    }

    public String getMovieStartTime(int id, String title){
        String sql = "select * from movielist where advisor_id = ? and movie_title = ?";
        String time = "";
        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                time = String.valueOf(resultSet.getString("movie_start"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return time;
    }

    public void updateMovieStartTime(int id, String title, int hour, int min){
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "update movielist set movie_start = ? where advisor_id = ? and movie_title = ?";
        String movieStartstr = getMovieStartTime(id, title);
        LocalDateTime beforeMovieStart = LocalDateTime.parse(movieStartstr, formatter2);

        LocalDateTime afterMovieStart = LocalDateTime.of(
                beforeMovieStart.getYear(),
                beforeMovieStart.getMonthValue(),
                beforeMovieStart.getDayOfMonth(),
                hour,
                min
        );

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setTime(1, Time.valueOf(afterMovieStart.toLocalTime()));
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, title);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int getUserDBid(String userid){
        String sql = "select * from movieuseraccount where userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public int getSeatTotalSize(int advisor_id, String movieTitle){
        String sql = "select * from movielist where advisor_id = ? and movie_title = ?";
        int totalSize = 0;
        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisor_id);
            preparedStatement.setString(2, movieTitle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                totalSize = resultSet.getInt("totalsize");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return totalSize;
    }

    public void insertNewUserReservation(int userDBid, int advisorDBid, String movieTitle,
                                         LocalDateTime watchDate, String seatNum, int roomNum){
        String sql = "insert into movieuserreservation(user_id, advisor_id, movie_title, watchdate, seat, room_number) " +
                "values(?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);
            preparedStatement.setInt(2, advisorDBid);
            preparedStatement.setString(3, movieTitle);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(watchDate));
            preparedStatement.setString(5, seatNum);
            preparedStatement.setInt(6, roomNum);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectNowAccSale(int advisorDBid){
        String sql = "select * from movieadvisoraccsale where advisor_id = ?";
        int accSale = 0;

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                accSale = resultSet.getInt("accsale");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return accSale;
    }

    public void updateAccSale(int advisorDBid, int nowAccSale, int plusSale){
        String sql = "update movieadvisoraccsale set accsale = ? where advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, nowAccSale+plusSale);
            preparedStatement.setInt(2, advisorDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int getMoviePrice(int advisorDBid, String movieTitle, String startTime){
        String sql = "select * from movielist where advisor_id = ? and movie_title = ? and movie_start = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, movieTitle);
            preparedStatement.setString(3, startTime);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("price");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<String> getReservatedSeats(int advisorid, String title,
                                                LocalDateTime watchdate, int movieRoom){
        ArrayList<String> seats = new ArrayList<>();
        String sql = "select * from MOVIEUSERRESERVATION where advisor_id = ? and movie_title = ? and watchdate = ? and room_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorid);
            preparedStatement.setString(2, title);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(watchdate));
            preparedStatement.setInt(4, movieRoom);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                seats.add(resultSet.getString("seat"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return seats;
    }

    public ArrayList<MyMovieReservation> selectMyMovieReservations(int userDBid, int advisorDBid){
        String sql = "select * from MOVIEUSERRESERVATION where user_id = ? and advisor_id = ?";
        ArrayList<MyMovieReservation> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);
            preparedStatement.setInt(2, advisorDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                String[] arr = resultSet.getString("seat").split("");

                MyMovieReservation m = new MyMovieReservation(
                        resultSet.getString("movie_title"),
                        LocalDateTime.parse(resultSet.getString("watchdate"), formatter),
                        arr[0]+"행 "+arr[1]+"열"
                );
                list.add(m);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public String getMyMovieWatchDate(int userid, int advisorid, String title, String seat){
        String sql = "select * from MOVIEUSERRESERVATION where user_id = ? and advisor_id = ? and movie_title = ? and seat = ?";
        String date = "";
        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, advisorid);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, seat);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                date = resultSet.getString("watchdate");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return date;
    }

    public void deleteMyMovieReservation(int userid, int advisorid, String title, String watchDate, String seat){
        String sql = "delete from MOVIEUSERRESERVATION where user_id = ? and advisor_id = ? and movie_title = ? and " +
                "watchdate = ? and seat = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, advisorid);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, watchDate);
            preparedStatement.setString(5, seat);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Movie> selectMoviesByTitle(int advisorDBid, String title){
        String sql = "select * from movielist where advisor_id = ? and locate(?, movie_title)";
        ArrayList<Movie> movies = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, title);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int advisor_id = resultSet.getInt("advisor_id");
                String movieTitle = resultSet.getString("movie_title");
                String movieStartstr = String.valueOf(resultSet.getString("movie_start"));
                int totalsize = resultSet.getInt("totalsize");
                int price = resultSet.getInt("price");
                int time = resultSet.getInt("time");

                LocalDateTime movieStart = LocalDateTime.parse(movieStartstr, formatter2);
                Date openDate = resultSet.getDate("opendate");
                Date closeDate = resultSet.getDate("closedate");

                Movie movie = new Movie(
                        advisor_id,
                        movieTitle,
                        openDate,
                        movieStart,
                        totalsize,
                        price,
                        closeDate,
                        time
                );
                movies.add(movie);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return movies;
    }

    public void insertNoticeMovie(int advisor_id, String movieTitle, LocalDateTime opendate,
                                 LocalDateTime movieStart, int totalSize, int price,
                                 LocalDateTime closeDate, int time){

        String sql = "insert into movienoticelist(advisor_id, movie_title, opendate, movie_start," +
                "totalsize, price, closedate, time) values(?,?,?,?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisor_id);
            preparedStatement.setString(2, movieTitle);
            preparedStatement.setDate(3, Date.valueOf(opendate.toLocalDate()));

            preparedStatement.setTimestamp(4, Timestamp.valueOf(movieStart));
            preparedStatement.setInt(5, totalSize);
            preparedStatement.setInt(6, price);
            preparedStatement.setDate(7, Date.valueOf(closeDate.toLocalDate()));
            preparedStatement.setInt(8, time);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Movie> selectAllNoticeMovies(int advisorDBid){
        String sql = "select * from movienoticelist where advisor_id = ?";
        ArrayList<Movie> movies = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int advisor_id = resultSet.getInt("advisor_id");
                String movieTitle = resultSet.getString("movie_title");
                String movieStartstr = String.valueOf(resultSet.getString("movie_start"));
                int totalsize = resultSet.getInt("totalsize");
                int price = resultSet.getInt("price");
                int time = resultSet.getInt("time");

                LocalDateTime movieStart = LocalDateTime.parse(movieStartstr, formatter);
                Date openDate = resultSet.getDate("opendate");
                Date closeDate = resultSet.getDate("closedate");

                Movie movie = new Movie(
                        advisor_id,
                        movieTitle,
                        openDate,
                        movieStart,
                        totalsize,
                        price,
                        closeDate,
                        time
                );
                movies.add(movie);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return movies;
    }

    public void updateMoviePrice(int id, String title, int price){
        String sql = "update movielist set price = ? where advisor_id = ? and movie_title = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, title);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteMovieByTitle(int id, String title){
        String sql = "delete from movielist where advisor_id = ? and movie_title = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteNoticeMovieByTitle(int id, String title){
        String sql = "delete from movienoticelist where advisor_id = ? and movie_title = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private boolean isExistThatDate(int year, int month, int day){

        String sql = "select * from moviedaysalereport where year_sale = ? and month_sale = ? and day_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, day);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    private void insertTodayDate(int id, int year, int month, int day){
        String sql = "insert into moviedaysalereport(advisor_id, year_sale, month_sale, day_sale, totalsale) " +
                "values(?, ?, ?, ?, 0)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void insertMonthDate(int id, int year, int month){
        String sql = "insert into moviemonthsalereport(advisor_id, year_sale, month_sale, totalSale) "+
                "values(?, ?, ?, 0)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void insertYearDate(int id, int year){
        String sql = "insert into movieyearsalereport(advisor_id, year_sale, totalsale) values(?, ?, 0)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private int selectTodayMovieSale(int id, int year, int month, int day){
        String sql = "select * from moviedaysalereport where advisor_id = ? and year_sale = ? and " +
                "month_sale = ? and day_sale = ?";
        int sale = 0;
        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                sale = resultSet.getInt("totalsale");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return sale;
    }

    private int selectMonthSale(int id, int year, int month){
        String sql = "select * from moviemonthsalereport where advisor_id = ? and year_sale = ? and month_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("totalsale");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    private int selectYearSale(int id, int year){
        String sql = "select * from movieyearsalereport where advisor_id = ? and year_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("totalsale");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void updateMovieTodaySale(int id, LocalDateTime today, int price){
        int todayYear = today.getYear();
        int todayMonth = today.getMonthValue();
        int todayDay = today.getDayOfMonth();

        if(!isExistThatDate(todayYear, todayMonth, todayDay)){
            insertTodayDate(id, todayYear, todayMonth, todayDay);
        }

        int afterSale = selectTodayMovieSale(id, todayYear, todayMonth, todayDay) + price;

        String sql = "update moviedaysalereport set totalsale = ? where advisor_id = ? and year_sale = ? " +
                "and month_sale = ? and day_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, afterSale);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, todayYear);
            preparedStatement.setInt(4, todayMonth);
            preparedStatement.setInt(5, todayDay);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateMonthSale(int id, LocalDateTime today, int price){ //월 매출 증가용
        int Year = today.getYear();
        int Month = today.getMonthValue();

        if(!isThatYearandMonthExist(id, Year, Month)){
            insertMonthDate(id, Year, Month);
        }

        int afterSale = selectMonthSale(id, Year, Month) + price;

        String sql = "update moviemonthsalereport set totalsale = ? where advisor_id = ? and year_sale = ? " +
                "and month_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, afterSale);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, Year);
            preparedStatement.setInt(4, Month);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateYearSale(int id, LocalDateTime today, int price){ //연 매출 증가용
        int year = today.getYear();

        if(!isThatYear(id, year)){
            insertYearDate(id, year);
        }

        int afterSale = selectYearSale(id, year) + price;

        String sql = "update movieyearsalereport set totalsale = ? where advisor_id = ? and year_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, afterSale);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, year);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<TodaySale> selectAllDaySales(int id){
        String sql = "select * from moviedaysalereport where advisor_id = ?";
        ArrayList<TodaySale> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new TodaySale(
                        resultSet.getInt("year_sale"),
                        resultSet.getInt("month_sale"),
                        resultSet.getInt("day_sale"),
                        resultSet.getInt("totalsale")
                ));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    private ArrayList<Integer> selectSaleYears(){ //일일 매출 연도 뽑아내기
        ArrayList<Integer> years;
        HashSet<Integer> set = new HashSet<>();
        String sql = "select * from moviedaysalereport";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                set.add(resultSet.getInt("year_sale"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        years = new ArrayList<>(set);

        return years;
    }

    private int selectSumMonthSale(int id, int year, int month){ //해당 연도 월 매출 합산 값 가져오기
        String sql = "select * from moviedaySalereport where advisor_id = ? and year_sale = ? and month_sale = ?";
        int total = 0;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                total += resultSet.getInt("totalsale");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return total;
    }

    private void insertNewMonthSaleRecord(int id, int year, int month, int totalsale){
        String sql = "insert into moviemonthsalereport(advisor_id, year_sale, month_sale, totalsale) values(?, ?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, totalsale);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void calcSalesByMonth(int id){ //월별 매출 계산 하기
        ArrayList<Integer> years = selectSaleYears();

        for(int i=0; i< years.size(); i++){

            for(int j=1; j<=12; j++){

                if(!isThatYearandMonthExist(id, years.get(i), j)){

                    insertNewMonthSaleRecord(id, years.get(i), j, selectSumMonthSale(id, years.get(i), j));
                }
            }
        }

    }

    private boolean isThatYearandMonthExist(int id, int year, int month){
        //해당 연도 월이 테이블에 존재하는지 확인
        String sql = "select * from moviemonthsalereport where advisor_id = ? and year_sale = ? and month_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public ArrayList<MonthSale> selectMovieMonthSales(int id){
        ArrayList<MonthSale> list = new ArrayList<>();
        String sql = "select * from moviemonthsalereport where advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new MonthSale(
                        resultSet.getInt("year_sale"),
                        resultSet.getInt("month_sale"),
                        resultSet.getInt("totalsale")
                ));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    private boolean isThatYear(int id, int year){
        String sql = "select * from movieyearsalereport where advisor_id = ? and year_sale = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    private int selectSumYearSale(int id, int year){
        String sql = "select * from moviemonthsalereport where advisor_id = ? and year_sale = ?";
        int total = 0;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                total += resultSet.getInt("totalsale");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return total;
    }

    private void insertNewYearSaleRecord(int id, int year, int totalsale){
        String sql = "insert into movieyearsalereport(advisor_id, year_sale, totalsale) values(?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, totalsale);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void calcYearSaleReocrd(int id){
        ArrayList<Integer> years = selectSaleYears();

        for(int i=0; i<years.size(); i++){
            if(!isThatYear(id, years.get(i))){
                insertNewYearSaleRecord(id, years.get(i), selectSumYearSale(id, years.get(i)));
            }
        }
    }

    public ArrayList<YearSale> selectMovieYearSales(int id){
        String sql = "select * from movieyearsalereport where advisor_id = ?";
        ArrayList<YearSale> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new YearSale(
                        resultSet.getInt("year_sale"),
                        resultSet.getInt("totalsale")
                ));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public void insertMovieReview(int advisorDBid, int userDBid, String title, int stars, String review){
        String sql = "insert into moviereviews(advisor_id, user_id, title, stars, review) values(?, ?, ?, ? ,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setInt(2, userDBid);
            preparedStatement.setString(3, title);
            preparedStatement.setInt(4, stars);
            preparedStatement.setString(5, review);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<MovieReview> selectMovieReviews(int advisorDBid, String title){
        Map<Integer, String> starMap = new HashMap<>();
        ArrayList<MovieReview> list = new ArrayList<>();

        starMap.put(1, "★");
        starMap.put(2, "★★");
        starMap.put(3, "★★★");
        starMap.put(4, "★★★★");
        starMap.put(5, "★★★★★");

        String sql = "select * from moviereviews where advisor_id = ? and title = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, title);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new MovieReview(
                        starMap.get(resultSet.getInt("stars")),
                        resultSet.getString("review")
                ));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public void deleteMovieReview(int id, String title, String review){
        String sql = "delete from moviereviews where advisor_id = ? and title = ? and review = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, review);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewMovieEvent(String title, String content, int advisorDBid){
        String sql = "insert into movieevents(event_title, event_content, advisor_id) values(?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setInt(3, advisorDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectMovieEventId(String title){
        String sql = "select * from movieevents where event_title = ?";
        int id = 0;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, title);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                id = resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return id;
    }

    public void insertNewMoviePromotionCode(int eventId, String code, int advisorDBid, int saleper){
        String sql = "insert into MOVIEVENTPROMOTIONS(event_id, code, isused," +
                " isofferd, advisor_id, saleper) values(?, ?, false, false, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, eventId);
            preparedStatement.setString(2, code);
            preparedStatement.setInt(3, advisorDBid);
            preparedStatement.setInt(4, saleper);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<MovieEvent> selectMovieEvents(int advisorid){
        ArrayList<MovieEvent> list = new ArrayList<>();
        String sql = "select * from movieevents where advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                MovieEvent movieEvent = new MovieEvent(
                        resultSet.getInt("id"),
                        resultSet.getString("event_title"),
                        resultSet.getString("event_content")
                );

                list.add(movieEvent);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean selectUserEventId(int userid, int eventid){
        String sql = "select * from moviepromotionuserid where user_id = ? and event_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, eventid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return  true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean selectNotOfferdPromotion(int eventid, int advisorId){
        String sql = "select * from MOVIEVENTPROMOTIONS where event_id = ? and isofferd = false and advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, eventid);
            preparedStatement.setInt(2, advisorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public int getNotOfferedPromotion(int eventid, int advisorId){
        String sql = "select * from movieventpromotions where event_id = ? and isofferd = ? and advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, eventid);
            preparedStatement.setBoolean(2, false);
            preparedStatement.setInt(3, advisorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void updatePromotionUsed(int promotionId, int advisorId){
        String sql = "update movieventpromotions set isofferd = true where id = ? and advisor_id = ? limit 1";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, promotionId);
            preparedStatement.setInt(2, advisorId);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertUserPromotionId(int userid, int eventid, int promotionid){
        String sql = "insert into moviepromotionuserid(user_id, event_id, promotion_id) values(?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, eventid);
            preparedStatement.setInt(3, promotionid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public MoviePromotionInfo selectUserPromotionById(int id){
        String sql = "select * from movieventpromotions where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new MoviePromotionInfo(
                        resultSet.getString("code"),
                        resultSet.getBoolean("isused"),
                        resultSet.getInt("saleper")
                );
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public ArrayList<Integer> selectUserPromotions(int userId){
        ArrayList<Integer> promotionIds = new ArrayList<>();
        String sql = "select * from moviepromotionuserid where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                promotionIds.add(resultSet.getInt("promotion_id"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return promotionIds;
    }

    public ArrayList<MoviePromotionInfo> selectAllPromotionCodes(int advisorId){
        String sql = "select * from movieventpromotions where advisor_id = ?";
        ArrayList<MoviePromotionInfo> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new MoviePromotionInfo(
                        resultSet.getString("code"),
                        resultSet.getBoolean("isused"),
                        resultSet.getInt("saleper")
                ));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean isCanUseThePromotion(String code, int advisorId){
        String sql = "select * from movieventpromotions where code = ? and isused = false and isofferd = true and advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, code);
            preparedStatement.setInt(2, advisorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public int getThePromotionId(String code, int advisorId){
        String sql = "select * from movieventpromotions where code = ? and isused = false and isofferd = true and advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, code);
            preparedStatement.setInt(2, advisorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void updatePromotionisUsedTrue(int id, int advisorId){
        String sql = "update movieventpromotions set isused = true where id = ? and isofferd = true and advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, advisorId);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int getSalePercentById(int id){
        String sql = "select * from movieventpromotions where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("saleper");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void insertMovieRoomNumber(int advisorDBid, String title, int room){
        String sql = "insert into movieroomlist(advisor_id, movie_title, room_number) values(?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, room);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> selectMovieRoomlist(int advisorDBid, String title){
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select * from movieroomlist where advisor_id = ? and movie_title = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, title);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getInt("room_number"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public boolean isUserSeatReservated(int userid, int advisorid,
                                 String title, LocalDateTime watchDate,
                                 String seat, int movieRoom){

        String sql = "select * from MOVIEUSERRESERVATION where user_id = ? and advisor_id = ? and movie_title = ? " +
                "and watchdate = ? and seat = ? and room_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, advisorid);
            preparedStatement.setString(3, title);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(watchDate));
            preparedStatement.setString(5, seat);
            preparedStatement.setInt(6, movieRoom);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public ArrayList<Movie> selectAllOtherMovieList(){
        ArrayList<Movie> list = new ArrayList<>();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "select * from MOVIEOTHERLIST";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int advisor_id = 0;
                String movieTitle = resultSet.getString("movie_title");
                String movieStartstr = String.valueOf(resultSet.getString("movie_start"));
                int totalsize = resultSet.getInt("totalszie");
                int price = resultSet.getInt("price");
                int time = resultSet.getInt("time");

                LocalDateTime movieStart = LocalDateTime.parse(movieStartstr, formatter2);
                Date openDate = resultSet.getDate("opendate");
                Date closeDate = resultSet.getDate("closedate");

                list.add(new Movie(
                        advisor_id,
                        movieTitle,
                        openDate,
                        movieStart,
                        totalsize,
                        price,
                        closeDate,
                        time
                ));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    private int getOtherMovieEventsNum(){
        int size = 0;
        String sql = "select * from MOVIEOTHEREVENTS";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                size++;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return  size;
    }

    public MovieEvent getRandomEvent(){
        MovieEvent movieEvent;
        Random random = new Random();
        int eventSize = getOtherMovieEventsNum();
        int id = random.nextInt(eventSize)+1;
        String sql = "select * from MOVIEOTHEREVENTS where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println(resultSet.getString("event_title"));
                movieEvent = new MovieEvent(id,
                        resultSet.getString("event_title"),
                        resultSet.getString("event_content"));

                return movieEvent;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean isTheMovieRoomExist(int advisorDBid, String title, int roomNumber){
        String sql = "select * from MOVIEROOMLIST where advisor_id = ? and movie_title = ? and room_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, roomNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public int getReservedSeatsNum(int advisorDBid, String title, int roomNumber, LocalDateTime watchDate){
        int size = 0;
        String sql = "select * from MOVIEUSERRESERVATION where advisor_id = ? and movie_title = ? and " +
                "watchdate = ? and room_number = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, title);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(watchDate));
            preparedStatement.setInt(4, roomNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                size++;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return size;
    }

    public String getOtherTheatorName(int id){
        String sql = "select * from MOVIETHEATORSOTHERNAMES where id = ?";
        String name = "";
        try {

            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("name");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return name;
    }

    /*협력 작업 웹 개발 파트*/
    public void insertNewAdvisor(String advisorID, String pwd, String name, String phoneNumber){ //회원가입용
        String sql = "insert into coporateadvisor(advisor_id, pwd, name, phone_number) values(?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, advisorID);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phoneNumber);

            System.out.println("회원가입이 완료되었습니다.");


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertNewUser(String name, String userID, String pwd, String phoneNumber){ //회원가입용
        String sql = "insert into coporateuser(user_id, pwd, name, phone_number) values (?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phoneNumber);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectUser(String userID, String pwd){
        String sql = "select * from coporateuser where user_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public int selectAdvisordbId(String advisorID){
        String sql = "select * from coporateadvisor where advisor_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisordbid);
            preparedStatement.setInt(2, grade);
            preparedStatement.setString(3, projectName);
            preparedStatement.setString(4, startTime);
            preparedStatement.setBoolean(5, isfinish);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectAllProjectsNum(){ //업로드한 프로젝트 전체 갯수 반환
        String sql = "select * from advisorgrade";
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public int advisorProjectID(String projectName){ //해당 프로젝트 고유 아이디 반환
        String sql = "select * from advisorgrade where project_name = ?";
        int num = 0;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public void insertParticipantUser(int projectID, int userDBid){ //프로젝트에 사용자 등록시키기
        String sql = "insert into projectparticipantuser(advisorgrade_id, userid) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);
            preparedStatement.setInt(2, userDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectParticipantUser(int projectDBid ,int userDBid){ //이미 참여하고 있는 사용자인지 확인용
        String sql = "select * from projectparticipantuser where advisorgrade_id = ? and userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);
            preparedStatement.setInt(2, userDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public void insertAllowedUser(int projectDBid, int userDBid){ //다른 사용자 프로젝트 접근 허용하기
        String sql = "insert into alloweduser(advisorgrade_id, userid) values(?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);
            preparedStatement.setInt(2, userDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String selectAnyProject(String projectName){
        String sql1 = "select * from advisorgrade where project_name = ?";
        String str = "";
        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

            preparedStatement1.setString(1, projectName);

            ResultSet resultSet1 = preparedStatement1.executeQuery();

            if(resultSet1.next()){
                if(resultSet1.getBoolean("isfinish")){
                    str = resultSet1.getString("project_name")+","+resultSet1.getInt("grade")+","+
                            "완성";
                }else{
                    str = resultSet1.getString("project_name")+","+resultSet1.getInt("grade")+","+
                    "미완성";
                }

            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return str;
    }

    public boolean selectAllowedProjectID(int projectDBid, int userDBid){ //접근 권한 허용된 프로젝트가 뭔지 알아내기
        String sql = "select * from alloweduser where userid = ? and advisorgrade_id = ?";

        try {

            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public boolean isParticipate(int projectid, int userid){ //해당 프로젝트에 참가 하고 있는건지 확인용
        String sql = "select * from projectparticipantuser where userid = ? and advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public int selectProjectParticipantsNum(int projectID){ //해당 프로젝트에 참여한 인원 알아내기
        String sql = "select * from projectparticipantuser where advisorgrade_id = ?";
        int num = 0;
        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public void insertNewProjectTeam(int projectID, String teamName, boolean isfinish, String finishTime){ //새 팀 만들기
        String sql = "insert into projectsteam(advisorgrade_id, team_name, isfinish, finishtime) values (?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);
            preparedStatement.setString(2, teamName);
            preparedStatement.setBoolean(3, isfinish);
            preparedStatement.setString(4, finishTime);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectTeamID(int projectID, String teamName){
        String sql = "select * from projectsteam where advisorgrade_id = ? and team_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public boolean selectRightParticipant(int projectID, int userid){ //해당 프로젝트 참가자 맞는지 확인용
        String sql = "select * from projectparticipantuser where advisorgrade_id = ? and userid = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public void insertTeamMember(int teamid, int userid){ //만들어진 팀에 등록시키기
        String sql = "insert into projectteam_member(team_id, userdb_id) values (?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamid);
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean isHeHaveTeam(int teamid, int userid){
        String sql = "select * from projectteam_member where team_id = ? and userdb_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamid);
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

    public void updateReportConfirm(int teamID){ //제출된 리포트 승인 해주기
        String sql = "update projectsreport set isconfirm = true where team_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamID);

            preparedStatement.executeUpdate();
            //System.out.println("승인 되었습니다.");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateTeamFinishDate(int teamID, String date){
        String sql = "update projectsteam set isfinish = true, finishtime = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, teamID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertReportText(int teamid, String text){ //레포트 업로드용
        String sql = "insert into projectsreport(team_id, text, isconfirm) values(?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamid);
            preparedStatement.setString(2, text);
            preparedStatement.setBoolean(3, false);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectRightTeam(int teamID, int userID){ //해당 팀에 속한 사용자 맞는지 확인용
        String sql = "select * from projectteam_member where team_id = ? and userdb_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public int selectAdvisorDBidbyProjectName(String projectName){ //프로젝트 이름으로 관리자 id 알아내기
        String sql = "select * from advisorgrade where project_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public Report selectReports(int teamID){ //각 팀이 쓴 보고서 출력하기
        String sql = "select * from projectsreport where team_id = ?";
        String isConfirm;
        Report report;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                if(resultSet.getBoolean("isconfirm")){
                    isConfirm = "승인";
                }else{
                    isConfirm = "미승인";

                }

                report = new Report(isConfirm, resultSet.getString("text"));

            }else{
                report = new Report("미확인", "작성된 보고서가 없습니다.");
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return report;
    }

    public int[] selectAllReports(int projectID){ //해당 프로젝트와 관련있는 모든 team id 가져오기
        List<Integer> list = new ArrayList<>();
        String sql = "select * from projectsteam where advisorgrade_id = ?";
        int[] nums = null;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, time);
            preparedStatement.setInt(2, projectID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateFinishProject(int projectID){ //프로젝트 완료 승인
        String sql = "update advisorgrade set isfinish = true where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public boolean isAlreadySetLimitDate(int projectDBid){
        String sql = "select * from PROJECTLIMITDATE where project_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public void setLimitDate(int projectDBid, String date){
        String sql = "insert into PROJECTLIMITDATE(project_id, limitdate) values(?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);
            preparedStatement.setString(2, date);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String getProjectLimitDate(int projectDBid){
        String sql = "select * from PROJECTLIMITDATE where project_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("limitdate");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public void deleteProjectParticipantUser(int userDBid){
        String sql = "delete from PROJECTPARTICIPANTUSER where userid = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteProjectTeamMember(int userDBid){
        String sql = "delete from PROJECTTEAM_MEMBER where userdb_id = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> selectAllTeamID(int projectDBid){
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select * from PROJECTSTEAM where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public boolean isTheTeamExistInTable(int teamId){
        String sql = "select * from PROJECTTEAM_MEMBER where team_id = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public void deleteProjectTeam(int teamId){
        String sql = "delete from PROJECTSTEAM where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamId);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateProjectGrade(String projectName, int grade){ //프로젝트 등급 조정
        String sql2 = "update advisorgrade set grade = ? where project_name = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);

            preparedStatement.setInt(1, grade);
            preparedStatement.setString(2, projectName);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateProjectName(int projectID, String changeName){ //프로젝트 이름 바꾸기
        String sql = "update advisorgrade set project_name = ? where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, changeName);
            preparedStatement.setInt(2, projectID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteProject(int projectDBid){
        String sql = "delete from ADVISORGRADE where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteProjectLimitDate(int projectDBid){
        String sql = "delete from PROJECTLIMITDATE where project_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteProjectParticipants(int projectDBid){
        String sql = "delete from PROJECTPARTICIPANTUSER where advisorgrade_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteProjectTeamByTeamID(int teamID){
        String sql = "delete from PROJECTTEAM_MEMBER where team_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, teamID);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteAllProjectTeamsByProjectID(int projectDBid){
        String sql = "delete from PROJECTSTEAM where advisorgrade_id = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean getProjectIsFinish(int projectDBid){ //프로젝트 끝난건지 아닌지 확인용
        String sql = "select * from ADVISORGRADE where id = ?";
        boolean bool = false;

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                bool = resultSet.getBoolean("isfinish");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return bool;
    }

    public String getProjectFinishTime(int projectDBid){
        String sql = "select * from ADVISORGRADE where id = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("finishtime");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public ArrayList<Project> getProjectIDbyAdvisorDBid(int advisorDBid){
        ArrayList<Project> list = new ArrayList<>();
        String sql = "select * from ADVISORGRADE where advisorid = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("project_name")));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<Project> getProjectIDbyUserDBid(int userDBid){
        ArrayList<Project> list = new ArrayList<>();
        String sql = "select  * from PROJECTPARTICIPANTUSER where userid = ?";
        int projectID = 0;

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                projectID = resultSet.getInt("advisorgrade_id");

                list.add(new Project(
                        projectID,
                   getProjectNameById(projectID)
                ));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public String getProjectNameById(int projectID){
        String sql = "select * from ADVISORGRADE where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("project_name");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public int getProjectIdBytwoInfo(int advisorDBid, String inputProjectName){
        String sql = "select * from ADVISORGRADE where advisorid = ? and project_name = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, advisorDBid);
            preparedStatement.setString(2, inputProjectName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertProgressZero(int projectDBid){
        String sql = "insert into PROJECTPROGRESSPERCENT(project_id, progress) values(?, 0)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int getProgressInfo(int projectDBid){
        String sql = "select * from PROJECTPROGRESSPERCENT where project_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("progress");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void updateProgress(int projectDBid, int progress){
        String sql = "update PROJECTPROGRESSPERCENT set progress = ? where project_id = ?";

        try{
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, progress);
            preparedStatement.setInt(2, projectDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteProgressInfo(int projectDBid){
        String sql = "delete from PROJECTPROGRESSPERCENT where project_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, projectDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /*======================================================================================*/
    /*여행사 프로그램 이후*/
    public void insertNewTravelUser(String userID, String pwd, String name, String phoneNumber, String email){

        String sql = "insert into travelagencyuser(user_id, pwd, name, phone_number, email) values(?,?,?,?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, email);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int selectTravelUserDBid(String userID){
        String sql = "select * from travelagencyuser where user_id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
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

    public boolean selectTravelUser(String userID, String pwd){
        String sql = "select * from travelagencyuser where user_id = ? and pwd = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, pwd);

            ResultSet resultSet = preparedStatement.executeQuery();;

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean isExistTravelUserLikeInfo(int userDBid){
        String sql = "select * from TRAVELAGENCYUSERLIKE where userdbid = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return false;
    }

    public Travellike selectTravelUserLike(int userDBid){
        String sql = "select * from TRAVELAGENCYUSERLIKE where userdbid = ?";
        Travellike travellike = null;

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                travellike = new Travellike(
                        resultSet.getInt("maxprice"),
                        resultSet.getInt("maxdays"),
                        resultSet.getString("region")
                );
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return travellike;
    }

    public void insertTravelUserLikeInfo(int userDBid, int maxPrice, int maxDays,
                                         String region){

        String sql = "insert into TRAVELAGENCYUSERLIKE(userdbid, maxprice, maxdays, region) values(?, ?, ?, ?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userDBid);
            preparedStatement.setInt(2, maxPrice);
            preparedStatement.setInt(3, maxDays);
            preparedStatement.setString(4, region);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateTravelUserLikeInfo(int userDBid, int maxPrice, int maxDays,
                                         String region){
        String sql = "update TRAVELAGENCYUSERLIKE set maxprice = ?, maxDays = ?, region = ? where userdbid = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, maxPrice);
            preparedStatement.setInt(2, maxDays);
            preparedStatement.setString(3, region);
            preparedStatement.setInt(4, userDBid);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Travel> selectTravelsByPrice(int lowPrice, int highPrice){
        String sql = "select * from travelagencypackagedb where price >= ? and price <= ?";
        ArrayList<Travel> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, lowPrice);
            preparedStatement.setInt(2, highPrice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Travel travel = new Travel(
                        resultSet.getString("package_name"),
                        resultSet.getInt("price"),
                        resultSet.getString("region"),
                        resultSet.getInt("travel_distance"),
                        resultSet.getString("accomodation"),
                        resultSet.getInt("travel_day"),
                        resultSet.getString("guide_name"),
                        resultSet.getString("send_way")
                );
                list.add(travel);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<Travel> selectTravlesByLikeInfo(int maxPrice, int maxDays, String region){
        String sql = "select * from travelagencypackagedb where price <= ? and travel_day <= ? and region = ?";
        ArrayList<Travel> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, maxPrice);
            preparedStatement.setInt(2, maxDays);
            preparedStatement.setString(3, region);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Travel travel = new Travel(
                        resultSet.getString("package_name"),
                        resultSet.getInt("price"),
                        resultSet.getString("region"),
                        resultSet.getInt("travel_distance"),
                        resultSet.getString("accomodation"),
                        resultSet.getInt("travel_day"),
                        resultSet.getString("guide_name"),
                        resultSet.getString("send_way")
                );
                list.add(travel);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    public ArrayList<Travel> selectTravlesByDays(int shortDays, int longDays){
        String sql = "select * from travelagencypackagedb where travel_day >= ? and travel_day <= ?";
        ArrayList<Travel> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, shortDays);
            preparedStatement.setInt(2, longDays);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Travel travel = new Travel(
                        resultSet.getString("package_name"),
                        resultSet.getInt("price"),
                        resultSet.getString("region"),
                        resultSet.getInt("travel_distance"),
                        resultSet.getString("accomodation"),
                        resultSet.getInt("travel_day"),
                        resultSet.getString("guide_name"),
                        resultSet.getString("send_way")
                );
                list.add(travel);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public ArrayList<Travel> selectTravlesByRegion(String region){
        String sql = "select * from travelagencypackagedb where region = ?";
        ArrayList<Travel> list = new ArrayList<>();

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, region);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Travel travel = new Travel(
                        resultSet.getString("package_name"),
                        resultSet.getInt("price"),
                        resultSet.getString("region"),
                        resultSet.getInt("travel_distance"),
                        resultSet.getString("accomodation"),
                        resultSet.getInt("travel_day"),
                        resultSet.getString("guide_name"),
                        resultSet.getString("send_way")
                );
                list.add(travel);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    /*에너지 관리자 이후*/
    public void insertEnergyGameAccount(String id, String pwd){ //회원가입
        String sql = "insert into energygameaccount(id, password) values (?,?)";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean selectEnergyGameAccount(String id, String pwd){ //로그인
        String sql = "select * from energygameaccount where id = ? and password = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
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

    public int selectEnegryGameUserDBid(String id){
        String sql = "select * from energygameaccount where id = ?";

        try {
            Connection connection = DBdataUtility.getConnection(URL, NAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("db_id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }
}
