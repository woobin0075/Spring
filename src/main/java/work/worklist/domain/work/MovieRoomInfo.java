package work.worklist.domain.work;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieRoomInfo {
    int advisorDBid;
    String movieTitle;
    int roomNumber;
    LocalDateTime watchDate;

    public MovieRoomInfo(int advisorDBid, String movieTitle, int roomNumber, LocalDateTime watchDate){
        this.advisorDBid = advisorDBid;
        this.movieTitle = movieTitle;
        this.roomNumber = roomNumber;
        this.watchDate = watchDate;
    }
}
