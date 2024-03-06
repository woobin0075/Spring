package HotelReservationSystem.Service;

import HotelReservationSystem.Repository.CustomerRepository;
import HotelReservationSystem.Repository.MasterRepository;

import java.util.Scanner;

public class MainService {

    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        int select, selectCustomerOrMaster;
        CustomerRepository customerRepository = new CustomerRepository();
        MasterRepository masterRepository = new MasterRepository();
        Service service = new Service();


        while (true){
            System.out.printf("1.손님, 2.주인 => ");
            selectCustomerOrMaster = sc.nextInt();

            if(selectCustomerOrMaster == 1){

                while (true){
                    System.out.printf("1.예약, 2.예약 취소, 3.객실 이용 현황 조회, 4.뒤로 => ");
                    select = sc.nextInt();

                    if(select == 1){
                        service.customerService.reservationService(customerRepository);
                        //customerService.reservationService(customerRepository);

                    }else if(select == 2){
                        service.customerService.cancelReservationService(customerRepository);

                    }else if(select == 3){
                        service.customerService.printRoomStates(customerRepository);

                    }else if(select == 4){

                        break;
                    }else{
                        System.out.println("다시 입력헤주세요. ");
                    }

                }


            }else if(selectCustomerOrMaster == 2){

                while (true){
                    System.out.printf("1.객실 정보 조회, 2.객실 상태 변경, 3.고객 정보 조회, 4.뒤로 => ");
                    select = sc.nextInt();

                    if(select == 1){
                        service.masterService.printRoomInfo(masterRepository);
                        //masterService.printRoomInfo(masterRepository);

                    }else if(select == 2){
                        service.masterService.changeReservateState(masterRepository);
                        //masterService.changeReservateState(masterRepository);

                    }else if(select == 3){
                        service.masterService.printCustomersInfo(masterRepository);

                    }else if(select == 4){

                        break;
                    }else{
                        System.out.println("다시 선택해주세요.");
                    }
                }
            }else{
                System.out.println("다시 선택해주세요.");
            }
            System.out.println();
        }
    }
}
