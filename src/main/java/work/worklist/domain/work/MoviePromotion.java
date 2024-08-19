package work.worklist.domain.work;

import java.util.Random;

public class MoviePromotion {

    private String code;

    public MoviePromotion(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int r=0;
        for(int i=0; i<5; i++){
            r = random.nextInt(10);
            sb.append(r);
        }
        code = sb.toString();
    }

    public String getCode(){
        return code;
    }
}
