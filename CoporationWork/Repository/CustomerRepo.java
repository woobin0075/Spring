package CoporationWork.Repository;

public class CustomerRepo {

    CustomerInterface repo;

    public CustomerRepo(){
        repo = new CustomerRepository();
    }

    public void insertNewCustomer(String customerID, String pwd, String name, String phoneNumber){
        repo.insertNewCustomer(customerID, pwd, name, phoneNumber);
    }

    public boolean selectCustomer(String customerID, String pwd){
        return repo.selectCustomer(customerID, pwd);
    }

    public int selectCustomerDBid(String customerID){
        return repo.selectCustomerDBid(customerID);
    }

    public boolean selectProjectForCustomer(String projectName){
        return repo.selectProjectForCustomer(projectName);
    }

    public void insertFeedbackStart(int projectDbid){
        repo.insertFeedbackStart(projectDbid);
    }

    public int selectFeedbackGood(int projectDbid){
        return repo.selectFeedbackGood(projectDbid);
    }

    public int selectFeedbackBad(int projectDbid){
        return repo.selectFeedbackBad(projectDbid);
    }


    public void updateFeedbackGood(int projectDbid, int beforeGoodNum){
        repo.updateFeedbackGood(projectDbid, beforeGoodNum);
    }

    public void updateFeedbackBad(int projectDbid, int beforeBadNum){
        repo.updateFeedbackBad(projectDbid, beforeBadNum);
    }

    public void insertCustomerReview(int projectDbid, int customerDbid, String review){
        repo.insertCustomerReview(projectDbid, customerDbid, review);
    }

    public void insertNewOutsource(int teamLeaderDBidnum, int architectUserDBidnum, int cutomerDBidnum, String untilfinishdate, String outsourcingName){
        repo.insertNewOutsource(teamLeaderDBidnum, architectUserDBidnum, cutomerDBidnum, untilfinishdate, outsourcingName);
    }
}
