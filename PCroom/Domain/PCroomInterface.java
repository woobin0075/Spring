package PCroom.Domain;

import java.time.LocalDateTime;

public interface PCroomInterface {
    int secretNum = 1111; //관리자 비밀번호
    void createMember(); //회원가입
    void measureUsingTime(LocalDateTime startTime); //피시방 이용시간 측정
}
