package HotelReservationSystem.Service;

import HotelReservationSystem.Repository.CustomerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Scanner;

public class CustomerService implements CustomerInterface{

    public void reservationService(CustomerRepository customerRepository){ //예약 되는지 검사하고 예약 가능하면 db에 저장해주기
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        String roomType;
        int roomNumber, roomID;
        String inputName, inputCheckinDate, inputCheckOutDate, reservateDateToString; //날짜는 6자리 숫자로 입력 ex.20231031, 20240106
        int visitPeople; //사용할 인원
        int pay;
        LocalDateTime reservateDate = null;

        System.out.printf("방 번호를 입력하세요. : ");
        roomNumber = sc.nextInt();

        System.out.printf("룸 타입을 입력하세요. : ");
        roomType = scStr.nextLine();

        if(customerRepository.canReservate(roomNumber, roomType)){

            roomID = customerRepository.findRoomID(roomNumber, roomType);

            if(roomID != 0){
                reservateDate = LocalDateTime.now();
                reservateDateToString = visitDatetoString(reservateDate);

                System.out.printf("이름을 입력하세요. : ");
                inputName = scStr.nextLine();
                System.out.printf("체크인 날짜를 입력하세요.(6자리 숫자로 입력, ex.20231031, 20240106) : ");
                inputCheckinDate = scStr.nextLine();
                System.out.printf("체크아웃 날짜를 입력하세요.(체크인 날짜 입력과 마찬가지로)");
                inputCheckOutDate = scStr.nextLine();
                System.out.printf("방문할 인원을 입력하세요. : ");
                visitPeople = sc.nextInt();

                pay = calcDateDifferce(inputCheckinDate, inputCheckOutDate) * customerRepository.findRoomPrice(roomID);

                customerRepository.insertReservationInfo(inputName, reservateDateToString, inputCheckinDate, inputCheckOutDate, visitPeople, pay, roomID);
                System.out.println("지불할 금액 : "+pay+"원입니다.");

                customerRepository.updateIsResevateFalse(roomID);

            }else{
                System.out.println("해당하는 방이 없습니다.");
            }

        }else{
            System.out.println("예약이 불가합니다.");
        }


    }

    public void cancelReservationService(CustomerRepository customerRepository){
        Scanner scStr = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        String inputName;
        int roomNumber, roomID;
        String roomType;

        System.out.printf("이름을 입력하세요. : ");
        inputName = scStr.nextLine();

        System.out.printf("취소할 방 번호를 입력하세요. : ");
        roomNumber = sc.nextInt();

        System.out.printf("취소할 룸 타입을 입력하세요. : ");
        roomType = scStr.nextLine();

        roomID = customerRepository.findRoomID(roomNumber, roomType);

        customerRepository.deleteReservationInfo(roomID, inputName);
        customerRepository.updateIsResevateTrue(roomID); //예약 가능 true로 바꿔주기
    }

    public void printRoomStates(CustomerRepository customerRepository){
        System.out.println("이용 현황은 다음과 같습니다.");
        customerRepository.selectAllRoomStates();
    }

    private String visitDatetoString(LocalDateTime time){ //날짜를 8자리 숫자문자열로 전환
        StringBuilder stringBuilder = new StringBuilder();
        int year = time.getYear();
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();

        stringBuilder.append(year);
        stringBuilder.append(month);
        stringBuilder.append(day);

        return stringBuilder.toString();
    }

    private LocalDate StringToDate(String date){ //8자리 숫자를 LocalDate 형태로
        LocalDate time;
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));

        time = LocalDate.of(year, month, day);

        return time;
    }

    private int calcDateDifferce(String start, String end){ //두 날짜 사이의 간격 계산

        LocalDate startDate = StringToDate(start);
        LocalDate endDate = StringToDate(end);

        Period period = Period.between(startDate, endDate);

        return period.getDays();
    }
}
