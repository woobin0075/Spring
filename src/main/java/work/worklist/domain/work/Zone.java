package work.worklist.domain.work;
import java.util.Random;
import lombok.Data;

@Data
public class Zone {

    int personNum = 0; //수용되어있는 사람 수, 기본적으로 최대 4까지 수용 가능
    int residantArea = 0; //주거지역 1 늘수록 최대 인구 수용 2씩 증가

    public int getPersonNum() {
        return personNum;
    }


    public void plusPersonNum(int num){

        personNum += num;
    }

    public int getResidantArea() {
        return residantArea;
    }

    public void addResidentArea(){
        residantArea++;
    }
}
