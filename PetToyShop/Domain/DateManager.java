package springstart.PetToyShop.Domain;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateManager {
    private static LocalDateTime date;

    private DateManager(){
        date = LocalDateTime.now();
    }

    public static LocalDateTime getDate(){
        if(date == null){
            date = LocalDateTime.now();
        }

        return date;
    }

    public static LocalDateTime nextDate(){ //다음날 날짜 계산
        date = date.plusDays(1);
        return date;
    }
}
