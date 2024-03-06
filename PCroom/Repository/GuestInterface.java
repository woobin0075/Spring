package PCroom.Repository;

import PCroom.Domain.Guest;

public interface GuestInterface {
    boolean insertGuestInfo(String enterNumber, int table_ID);
    void insertUsingPcroomTime(String visitDate, Guest guest);

}
