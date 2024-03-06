package PCroom.Domain;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Data
public class Guest {

    String name;
    String enterNumber; //등록 번호
    int usingTime; //피시방 이용시간

    public void makeEnterNumber(){ //등록 번호 만들어서 저장하기
        Random r = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int num;

        stringBuilder.append("P");

        for(int i=0; i<6; i++){
            num = r.nextInt(10);
            stringBuilder.append(num);
        }

        enterNumber = stringBuilder.toString();
    }

    public void measureUsingTime(LocalDateTime startTime) { //계산할때 바로 측정 시작
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long time = duration.getSeconds();

        usingTime = (int) Math.ceil((int)(time/5));
    }
}
