package work.worklist.domain.work;

import lombok.Data;

@Data
public class Work {
    private Long id;
    private String workName;
    private String workAddress;
    private Integer pay;

    public Work(){

    }

    public Work(String workName, String workAddress, Integer pay) {
        this.workName = workName;
        this.workAddress = workAddress;
        this.pay = pay;
    }
}
