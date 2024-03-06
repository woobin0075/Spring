package Delivery.Domain;

public class Master {
    private int tableID;
    private String password;
    public String restaurant_name;

    public void setTableID(int tableID){
        this.tableID = tableID;
    }

    public int getTableID(){
        return tableID;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
