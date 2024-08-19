package work.worklist.domain.work;

import lombok.Data;

@Data
public class MoviePromotionInfo{

    private String code;
    private boolean isUsed;
    private int sale;

    public MoviePromotionInfo(String code, boolean isUsed, int sale){
        this.code = code;
        this.isUsed = isUsed;
        this.sale = sale;
    }

}
