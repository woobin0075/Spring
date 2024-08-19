package work.worklist.domain.work;

import java.sql.Date;
import java.time.LocalDateTime;

public class Movie {

    public int advisor_id;
    public String movie_title;
    public Date opendate;
    public LocalDateTime movie_start;
    public int totalsize;
    public int price;
    public Date closedate;
    public int time;

    public Movie(int advisor_id, String movie_title, Date opendate,
                 LocalDateTime movie_start, int totalsize, int price,
                 Date closedate, int time){

        this.advisor_id = advisor_id;
        this.movie_title = movie_title;
        this.opendate = opendate;
        this.movie_start = movie_start;
        this.totalsize = totalsize;
        this.price = price;
        this.closedate = closedate;
        this.time = time;
    }
}
