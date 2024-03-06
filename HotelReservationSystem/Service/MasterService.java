package HotelReservationSystem.Service;

import HotelReservationSystem.Repository.MasterRepository;

import java.util.Scanner;

public class MasterService implements MasterInterface {

    public void printRoomInfo(MasterRepository masterRepository){
        masterRepository.selectRoomsInfo();
    }

    public void changeReservateState(MasterRepository masterRepository){
        Scanner sc = new Scanner(System.in);
        int roomNumber;
        int select;

        System.out.printf("1.예약 불가로 만들기, 2.예약 가능으로 만들기 : ");
        select = sc.nextInt();

        System.out.printf("객실 번호를 입력하세요. : ");
        roomNumber = sc.nextInt();

        if(select == 1){
            masterRepository.updateIsResevateFalse(roomNumber);

        }else if(select == 2){
            masterRepository.updateIsResevateTrue(roomNumber);
        }
    }

    public void printCustomersInfo(MasterRepository masterRepository){
        masterRepository.selectAllCustomers();
    }
}
