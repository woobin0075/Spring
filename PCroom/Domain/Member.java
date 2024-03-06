package PCroom.Domain;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

@Data
public class Member implements PCroomInterface{
    String name;
    String ID;
    String pwd;
    String phone_number;
    int usingTime; //피시방 이용시간 (컴퓨터 시간 5초에 1시간 지났다고 가정하기)
    boolean isVIP = false; //vip인지 아닌지 확인용, 처음엔 false이고 추후에 조건이 갖춰지면 true로 변경

    @Override
    public void createMember() {
        Scanner scStr = new Scanner(System.in);

        System.out.printf("이름을 입력하세요. : ");
        name = scStr.nextLine();
        System.out.printf("사용하고 싶은 ID를 입력하세요. : ");
        ID = scStr.nextLine();
        System.out.printf("비밀번호를 입력하세요. : ");
        pwd = scStr.nextLine();
        System.out.printf("핸드폰 번호를 입력하세요.('-'없이 입력) : ");
        phone_number = scStr.nextLine();

    }

    @Override
    public void measureUsingTime(LocalDateTime startTime) { //로그인시 바로 측정 시작
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long time = duration.getSeconds();

        usingTime = (int) Math.ceil((int)(time/5));
    }
}
