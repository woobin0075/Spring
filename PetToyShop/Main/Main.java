package springstart.PetToyShop.Main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springstart.PetToyShop.AppConfig.AppConfig;
import springstart.PetToyShop.Domain.DateManager;
import springstart.PetToyShop.Domain.User;
import springstart.PetToyShop.Service.PromotionManager;
import springstart.PetToyShop.Service.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        Service service = applicationContext.getBean("service", Service.class);
        PromotionManager manager = applicationContext.getBean(PromotionManager.class);

        Scanner sc = new Scanner(System.in);
        int select, loginSelect, selectOwnerOrCustomer, selectFemaleOrMale, selectAnimalParcel;
        LocalDateTime date = DateManager.getDate();
        final int dogfeedPrice = 20000;
        final int catFeedPrice = 18000;
        final int catToiletSandPrice = 23000;
        final int dogToiletPedPrice = 21000;
        final int dogToyPrice = 13000;
        int animalDBid;
        int dogfeedMany = 0; int catfeedmany = 0; int catToiletSandMany = 0;
        int dogToiletPedMany = 0; int dogToyMany = 0;
        int totalPay = 0;
        double realPay = 0.0;
        String inputID, inputPwd, inputName, inputPetKindOf;
        String inputOwnerID, inputOwnerPwd;
        String inputParcelAnimalKind, inputAnimalSex = "", inputAnimalName, inputAnimalSpecificKind;
        int inputAnimalAge, inputAnimalSellMoney;
        User user = null;
        int dogRandomnum = 0;
        int dogAgeRandomnum = 0;
        int dogRandIdx;
        boolean dogParcelRandomFlag = true;
        Random random = new Random();

        service.startTodaySale(dateTostring(date));

        while(true){
            System.out.printf("1.사장, 2.손님 => ");
            selectOwnerOrCustomer = sc.nextInt();

            if(selectOwnerOrCustomer == 1){

                System.out.println("사장 로그인");
                System.out.printf("아이디 입력 : ");
                inputOwnerID = sc.next();
                System.out.printf("비밀번호 입력 : ");
                inputOwnerPwd = sc.next();

                if(service.ownerLogin(inputOwnerID, inputOwnerPwd) > 0){

                    System.out.println("로그인 성공");
                    System.out.println();

                    while(true){
                        System.out.printf("1.오늘 매출 조회, 2.역대 매출 조회, 3.분양할 동물 입력, 4.데려올 애완견 랜덤 받기 ,5.뒤로 => ");
                        select = sc.nextInt();

                        if(select == 1){

                            service.printTodaySales(dateTostring(date));

                        }else if(select == 2){

                            service.printAllSales();

                        }else if(select == 3){

                            System.out.printf("1. 동물 종류 입력 : ");
                            inputParcelAnimalKind = sc.next();
                            System.out.printf("2. 세부 품종 입력 : ");
                            inputAnimalSpecificKind = sc.next();
                            System.out.printf("3. 동물 이름 입력 : ");
                            inputAnimalName = sc.next();
                            System.out.printf("4. 동물 나이 입력 : ");
                            inputAnimalAge = sc.nextInt();
                            System.out.printf("5. 동물 성별 => 1)암컷, 그외 번호)수컷 : ");
                            selectFemaleOrMale = sc.nextInt();

                            if(selectFemaleOrMale == 1){
                                inputAnimalSex = "암컷";
                            }else {
                                inputAnimalSex = "수컷";
                            }

                            System.out.printf("6. 분양가 입력 : ");
                            inputAnimalSellMoney = sc.nextInt();

                            service.addParcelAnimal(inputParcelAnimalKind, inputAnimalSpecificKind, inputAnimalName, inputAnimalAge,
                                    inputAnimalSex, inputAnimalSellMoney);

                        }else if(select == 4){

                            if(!dogParcelRandomFlag){
                                System.out.println("오늘 이미 한번 데려왔습니다.");
                                System.out.println();
                                continue;
                            }

                            dogParcelRandomFlag = false;

                            dogRandomnum = random.nextInt(3) + 1; //1~3마리 고정
                            System.out.println("데려올 애완견 수 : "+dogRandomnum);

                            dogRandIdx = 0;
                            while(dogRandIdx < dogRandomnum){

                                inputParcelAnimalKind = "개";
                                System.out.printf("세부 품종 입력 : ");
                                inputAnimalSpecificKind = sc.next();
                                System.out.printf("동물 이름 입력 : ");
                                inputAnimalName = sc.next();
                                inputAnimalAge = random.nextInt(2)+1; //1살, 2살 고정
                                System.out.println("나이 : "+inputAnimalAge);
                                System.out.printf("동물 성별 => 1)암컷, 그외 번호)수컷 : ");
                                selectFemaleOrMale = sc.nextInt();

                                if(selectFemaleOrMale == 1){
                                    inputAnimalSex = "암컷";
                                }else {
                                    inputAnimalSex = "수컷";
                                }

                                System.out.printf("분양가 입력 : ");
                                inputAnimalSellMoney = sc.nextInt();

                                service.addParcelAnimal(inputParcelAnimalKind, inputAnimalSpecificKind, inputAnimalName, inputAnimalAge,
                                        inputAnimalSex, inputAnimalSellMoney);

                                dogRandIdx++;
                            }

                        }else if(select == 5){
                            break;
                        }

                    }

                }else{
                    System.out.println("아이디 또는 비밀번호가 잘못 되었습니다.");
                }

            }else if(selectOwnerOrCustomer == 2){

                while (true){
                    System.out.printf("1.손님 회원 가입, 2.손님 로그인, 3.뒤로 => ");
                    loginSelect = sc.nextInt();

                    if(loginSelect == 1){

                        System.out.printf("이름을 입력하세요. : ");
                        inputName = sc.next();
                        System.out.printf("사용할 아이디를 입력하세요. : ");
                        inputID = sc.next();

                        if(service.isThereSameID(inputID)){
                            System.out.println("중복되는 아이디 입니다.");
                            continue;
                        }

                        System.out.printf("사용할 비밀번호를 입력하세요. : ");
                        inputPwd = sc.next();

                        service.makeNewAccount(inputID, inputPwd, inputName);

                    }else if(loginSelect == 2){

                        System.out.printf("사용할 아이디를 입력하세요. : ");
                        inputID = sc.next();
                        System.out.printf("사용할 비밀번호를 입력하세요. : ");
                        inputPwd = sc.next();

                        if(!service.isheUser(inputID, inputPwd)){

                            System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
                            continue;
                        }
                        user = new User(service.bringUserDBid(inputID), inputID);

                        while (true){
                            System.out.printf("1.반려견 사료 20000, 2.반려묘 사료 18000, 3.반려묘 화장실 모래 23000, " +
                                    "4.반려견 화장실 패드 21000, 5.반려견 장난감 13000, " +
                                    "6.구매, 7.다음날, 8.애완 동물 품종 입력, 9.분양중인 동물 조회, 10.분양하기, 11.뒤로 => ");
                            select = sc.nextInt();

                            if(select == 1){
                                dogfeedMany++;
                                System.out.println("반려견 사료 하나를 장바구니에 담았습니다.");

                            }else if(select == 2){
                                catfeedmany++;
                                System.out.println("반려묘 사료 하나를 장바구니에 담았습니다.");

                            }else if(select == 3){
                                catToiletSandMany++;
                                System.out.println("반려묘 화장실 모래 하나를 장바구니에 담았습니다.");

                            }else if(select == 4){
                                dogToiletPedMany++;
                                System.out.println("반려견 화장실 패드 하나를 장바구니에 담았습니다.");

                            }else if(select == 5){
                                dogToyMany++;
                                System.out.println("반려견 장난감 하나를 장바구니에 담았습니다.");

                            }else if(select == 6){
                                totalPay = dogfeedPrice * dogfeedMany + catFeedPrice * catfeedmany + catToiletSandPrice * catToiletSandMany+
                                        dogToiletPedMany * dogToiletPedPrice + dogToyMany * dogToyPrice;

                                System.out.println("정가 : "+totalPay);
                                realPay = manager.applyDiscount(totalPay);
                                System.out.println("실질 구매 금액 : "+(int)realPay);

                                service.addUserBuyInfo(user.getId(), dateTostring(date), catfeedmany, dogfeedMany, catToiletSandMany, dogToiletPedMany,
                                        dogToyMany, (int)realPay);

                                //메출 상승 업데이트
                                service.todaySaleRecord(dateTostring(date), catfeedmany*catFeedPrice, dogfeedPrice*dogfeedMany,
                                        catToiletSandMany*catToiletSandPrice, dogToiletPedPrice*dogToiletPedMany, dogToyMany*dogToyPrice);

                            }else if(select == 7){
                                date = DateManager.nextDate();
                                dogfeedMany = 0;
                                catfeedmany = 0;
                                dogToyMany = 0;
                                catToiletSandMany = 0;
                                dogToiletPedMany = 0;
                                applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
                                manager = applicationContext.getBean(PromotionManager.class);

                                dogParcelRandomFlag = true;

                                service.startTodaySale(dateTostring(date));

                            }else if(select == 8){
                                System.out.printf("키우고 있는 애완동물 품종을 입력하세요. : ");

                                while(true){
                                    System.out.printf("1.입력, 2.나가기 : ");
                                    select = sc.nextInt();

                                    if(select == 1){

                                        System.out.printf("품종 : ");
                                        inputPetKindOf = sc.next();

                                        service.addUserPet(user.getId(), inputPetKindOf);

                                    }else if(select == 2){
                                        break;
                                    }
                                }

                            }else if(select == 9){

                                System.out.printf("1. 전체 조회, 2.품종별 입력 조회 => ");
                                selectAnimalParcel = sc.nextInt();

                                if(selectAnimalParcel == 1){

                                    service.printAllParcelAnimals();

                                }else if(selectAnimalParcel == 2){
                                    System.out.printf("입력 : ");
                                    inputParcelAnimalKind = sc.next();

                                    service.printAnyParcelAnimals(inputParcelAnimalKind);
                                }

                            }else if(select == 10){

                                System.out.printf("1. 동물 종류 입력 : ");
                                inputParcelAnimalKind = sc.next();
                                System.out.printf("2. 세부 품종 입력 : ");
                                inputAnimalSpecificKind = sc.next();
                                System.out.printf("3. 동물 이름 입력 : ");
                                inputAnimalName = sc.next();
                                System.out.printf("4. 동물 나이 입력 : ");
                                inputAnimalAge = sc.nextInt();
                                System.out.printf("5. 동물 성별 => 1)암컷, 그외 번호)수컷 : ");
                                selectFemaleOrMale = sc.nextInt();

                                if(selectFemaleOrMale == 1){
                                    inputAnimalSex = "암컷";
                                }else {
                                    inputAnimalSex = "수컷";
                                }

                                System.out.printf("6. 분양가 입력 : ");
                                inputAnimalSellMoney = sc.nextInt();

                                animalDBid = service.bringAnimalDBid(inputParcelAnimalKind, inputAnimalSpecificKind, inputAnimalName,
                                        inputAnimalAge, inputAnimalSex, inputAnimalSellMoney);

                                if(animalDBid > 0){
                                    service.completeParcelAnimal(user.getId(), animalDBid);
                                    service.completeParcel(animalDBid);

                                }else{
                                    System.out.println("해당하는 동물이 없습니다.");
                                }

                            }else if(select == 11){
                                break;
                            }
                        }

                    }else if(loginSelect == 3){
                        break;
                    }
                }
            }


        }
    }

    private static String dateTostring(LocalDateTime date){
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        StringBuilder sb = new StringBuilder();

        sb.append(year);
        sb.append("-");
        if(month < 10){
            sb.append("0");

        }
        sb.append(month);
        sb.append("-");
        if(day < 10){
            sb.append("0");
        }
        sb.append(day);

        return sb.toString();
    }
}
