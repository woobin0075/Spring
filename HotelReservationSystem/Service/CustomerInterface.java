package HotelReservationSystem.Service;

import HotelReservationSystem.Repository.CustomerRepository;

public interface CustomerInterface {
    void reservationService(CustomerRepository customerRepository);
    void cancelReservationService(CustomerRepository customerRepository);
    void printRoomStates(CustomerRepository customerRepository);

}
