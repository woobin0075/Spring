package ResutuantOrguugdanOrCalclation.Repository;

public interface CustomerInterface {
    void priceSave();
    void insert(Object customer, int idx);
    void find(String customerName);
    void delete(String name);

}
