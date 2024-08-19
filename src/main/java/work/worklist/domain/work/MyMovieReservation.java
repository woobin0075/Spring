package work.worklist.domain.work;

import java.time.LocalDateTime;

public class MyMovieReservation {

    public String movieTitle;
    public LocalDateTime watchDate;
    public String seat;

    public MyMovieReservation(String movieTitle, LocalDateTime watchDate, String seat){
        this.movieTitle = movieTitle;
        this.watchDate = watchDate;
        this.seat = seat;
    }
}
