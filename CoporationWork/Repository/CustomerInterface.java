package CoporationWork.Repository;

public interface CustomerInterface {
    void insertNewCustomer(String customerID, String pwd, String name, String phoneNumber);
    boolean selectCustomer(String customerID, String pwd);
    int selectCustomerDBid(String customerID);
    boolean selectProjectForCustomer(String projectName);
    void insertFeedbackStart(int projectDbid);
    int selectFeedbackGood(int projectDbid);
    int selectFeedbackBad(int projectDbid);
    void updateFeedbackGood(int projectDbid, int beforeGoodNum);
    void updateFeedbackBad(int projectDbid, int beforeBadNum);
    void insertCustomerReview(int projectDbid, int customerDbid, String review);
    void insertNewOutsource(int teamLeaderDBidnum, int architectUserDBidnum, int cutomerDBidnum, String untilfinishdate, String outsourcingName);

}
