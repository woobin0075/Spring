package HotelReservationSystem.Service;

import HotelReservationSystem.Repository.MasterRepository;

public interface MasterInterface {
    void printRoomInfo(MasterRepository masterRepository);
    void changeReservateState(MasterRepository masterRepository);
    void printCustomersInfo(MasterRepository masterRepository);
}
