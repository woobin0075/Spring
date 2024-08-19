package work.worklist.domain.work;

import java.sql.Time;
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Review {

    public String star;
    public String reviewContent;
    public LocalDateTime reviewtime;

    public Review(String star, String reviewContent, LocalDateTime reviewtime){
        this.star = star;
        this.reviewContent = reviewContent;
        this.reviewtime = reviewtime;
    }
}
