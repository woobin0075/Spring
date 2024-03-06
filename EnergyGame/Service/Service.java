package EnergyGame.Service;

import EnergyGame.Domain.Player;
import EnergyGame.Repository.Account;
import EnergyGame.Repository.Game;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

public class Service {
    Account account;
    Game game;

    public Service(Account account, Game game){
        this.account = account;
        this.game = game;
    }

    public void makeAccountService(String id, String pwd){
        account.insertAccount(id, pwd);
    }

    public boolean loginService(String id, String pwd){
        return account.selectAccount(id, pwd);
    }

    public void resultGameStateService(String date, String nickname, int penalty, String userID){
        game.insertGameState(date, nickname, penalty, userID);
    }

    public void printGameStateService(String date, String userID){
        game.selectGameState(date, userID);
    }

    public void whileSelect(){
        Scanner sc = new Scanner(System.in);
        Player[] players = new Player[4];
        final int lastTurn = 12;
        int presentTurn = 1;
        final int totalParticipants = 4;
        LocalDateTime today = LocalDateTime.now();
        String todayDate;
        String inputID, inputPwd, inputNickname;
        int cityNumber, zoneNumber;
        int inputPeople, inputTransferEnergy;
        int totalProduceEnergyByZones, totalSpendEnergyByZones, energyDifference;
        int penalty;
        int select, accept;
        int idx;

        todayDate = dateToString(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        System.out.println("게임 시행 날짜 : "+todayDate);
        System.out.println();

        while(true){
            System.out.printf("1.회원 가입, 2.로그인 => ");
            select = sc.nextInt();

            System.out.printf("사용할 아이디 입력 : ");
            inputID = sc.next();
            System.out.printf("사용할 비밀번호 입력 : ");
            inputPwd = sc.next();

            if(select == 1){

                makeAccountService(inputID, inputPwd);

            }else if(select == 2){

                if(loginService(inputID, inputPwd)){
                    break;

                }else{
                    System.out.println("아이디 또는 비밀번호를 잘못 입력하셨습니다.");
                    System.out.println();
                }
            }
        }

        /*---------------------------------------------------------------*/
        System.out.println();
        System.out.println("각 플레이어 1명당 닉네임을 입력해주세요.");
        for(int i=0; i<totalParticipants; i++){
            players[i] = new Player();
            System.out.printf((i+1)+"번째 플레이어 닉네임 입력 : ");
            players[i].nickname = sc.next();
        }

        System.out.println();
        System.out.println("당신은 도시의 에너지 관리자입니다. 도시는 여러 구역으로 나뉘어 있으며, 각 구역은 에너지를 생산하거나 소비합니다");
        System.out.println("도시 하나당 4개의 구역이 자동으로 할당 됩니다.");
        System.out.println();

        while (presentTurn != lastTurn){

            for(int i=0; i<totalParticipants; i++){
                System.out.println(players[i].nickname+"차례 입니다.");
                System.out.println(players[i].nickname+"님 턴 : "+players[i].turn);
                //플레이어 상태 출력
                players[i].printPlayerState();

                //도시 내 구역 에너지 랜덤 생산
                System.out.println("에너지를 생산합니다.");
                totalProduceEnergyByZones = players[i].allZonesProduceEnergy();
                System.out.println("생산한 에너지 : "+totalProduceEnergyByZones);

                //도시 내 구역 에너지 랜덤 소비
                System.out.println("에너지를 소비합니다.");
                totalSpendEnergyByZones = players[i].allZonesSpendEnergy();
                System.out.println("소비한 에너지 : "+totalSpendEnergyByZones);

                if(totalProduceEnergyByZones - totalSpendEnergyByZones >= 0){

                    energyDifference = totalProduceEnergyByZones - totalSpendEnergyByZones;

                    //갚을 거 있으면 갚기
                    if(players[i].rentsMap.size() > 0){
                        Iterator keySetIterator = players[i].rentsMap.keySet().iterator();

                        while (keySetIterator.hasNext()){ //rentMap 순환
                            String name = keySetIterator.next().toString();

                            if(players[i].rentsMap.get(name) >= energyDifference){
                                players[i].rentsMap.put(name, players[i].rentsMap.get(name) - energyDifference);


                                if(players[i].rentsMap.get(name) == 0){ //완전히 갚았으면 목록에서 제거
                                    players[i].rentsMap.remove(name);
                                }

                            }else if(players[i].rentsMap.get(name) < energyDifference){
                                energyDifference = energyDifference - players[i].rentsMap.get(name);
                                players[i].rentsMap.remove(name);
                            }

                        }
                    }

                }else{

                    //대출하기, 대출 못하면 페널티 2배로 늘리기(내가 정함)
                    System.out.printf("대출하고 싶은 다른 플레이어의 닉네임을 입력하세요. : ");
                    inputNickname = sc.next();

                    energyDifference = totalSpendEnergyByZones - totalProduceEnergyByZones;

                    for(idx=0; idx<4; idx++){
                        if(players[idx].nickname.equals(inputNickname) && players[idx].getEnergyPool() >= energyDifference){

                            players[idx].setEnergyPool(players[idx].getEnergyPool() - energyDifference);
                            players[i].lendEnergy(inputNickname, energyDifference);
                            System.out.println(players[idx].nickname+"님에게 대출받았습니다.");
                            break;
                        }
                    }

                    if(idx == 4){ //대출 실패경우, 대출 실패시 페널티 5 추가로 수정함
                        System.out.println("대출에 실패하셨습니다.");
                        penalty = energyDifference;
                        penalty += 5;
                        players[i].penalty += penalty;
                    }

                    energyDifference = 0;
                }

                System.out.printf("1.도시 생성, 2.에너지 건물 구현, 3.에너지 저장, 4.이주 가능한 사람들의 수 입력, 5.동맹 요청, 6.동맹한 사람에게 에너지 받기 => ");
                select = sc.nextInt();

                    if(select == 1){

                        players[i].addCity();

                    }else if(select == 2){

                        System.out.printf("도시 번호를 입력하세요. : ");
                        cityNumber = sc.nextInt();
                        System.out.printf("구역 번호를 입력하세요. : ");
                        zoneNumber = sc.nextInt();


                        if(players[i].getEnergyPool() - 3 >= 0){
                            players[i].cities.get(cityNumber-1).zones[zoneNumber-1].makeBuilding();
                            players[i].useEnergy(3);
                        }

                    }else if(select == 3){

                        if(energyDifference > 0){

                            players[i].saveEnergy(energyDifference);
                            System.out.println(energyDifference+"만큼 에너지가 저장되었습니다.");
                        }else{
                            System.out.println("에너지를 저장할 수 없습니다.");
                        }

                    }else if(select == 4){

                        System.out.printf("도시 번호를 입력하세요. : ");
                        cityNumber = sc.nextInt();
                        System.out.printf("구역 번호를 입력하세요. : ");
                        zoneNumber = sc.nextInt();

                        System.out.printf("몇 명의 사람들을 이주하시겠습니까? : ");
                        inputPeople = sc.nextInt();

                        players[i].cities.get(cityNumber-1).zones[zoneNumber-1].plusPersonNum(inputPeople);

                    }else if(select == 5){

                       if(players[i].getCannotRequestAlliance() == 0){
                           System.out.printf("동맹을 요청하고 싶은 플레이어의 닉네임을 입력하세요. : ");
                           inputNickname = sc.next();

                           for(idx = 0; idx<4; idx++){
                               if(players[idx].nickname.equals(inputNickname)){
                                   System.out.printf(players[idx].nickname+"님 요청을 수락하면 1번을 아니면 그외 다른 숫자를 입력하세요. : ");
                                   accept = sc.nextInt();

                                   if(accept == 1){

                                       players[idx].addAlliance(players[i].nickname);
                                       players[i].addAlliance(players[idx].nickname);

                                   }else{
                                       System.out.println("동맹 요청에 거절 당하셨습니다. 5턴 동안 동맹 요청을 할 수가 없습니다.");
                                       players[i].inputCannotRequestAlliance();
                                   }
                               }
                           }
                       }

                       if(players[i].turn < players[i].getCannotRequestAlliance()) {
                           System.out.println("게임 룰 대로 아직 동맹 요청을 할 수가 없습니다.");

                       }else{
                           players[i].setCannotRequestAlliance(0); //적용 헤제
                       }

                    }else if(select == 6){
                        System.out.println("어느 플레이어에게 에너지를 넘겨 받고 싶으신가요? : ");
                        inputNickname = sc.next();

                        System.out.println("얼마만큼의 에너지를 받고 싶은신가요? : ");
                        inputTransferEnergy = sc.nextInt();

                        for(idx = 0; idx < 4; idx++){
                            if(players[idx].nickname.equals(inputNickname) && players[idx].getEnergyPool() >= inputTransferEnergy){
                                System.out.printf(players[idx].nickname+"님은 에너지를 넘기실거면 1번을 아니면 그외 숫자를 입력하세요. : ");
                                accept = sc.nextInt();

                                if(accept == 1){
                                    players[idx].setEnergyPool(players[idx].getEnergyPool() - inputTransferEnergy);
                                    players[i].setEnergyPool(players[i].getEnergyPool() + inputTransferEnergy);

                                }else{
                                    System.out.println("에너지를 받지 못했습니다.");
                                }
                            }
                        }
                    }

                    players[i].printPlayerState();
                    players[i].turn++;
                }

                presentTurn++;
            }


        //대출 못 갚은 경우 페널티 부과
        for(idx=0; idx<totalParticipants; idx++){
            if(players[idx].rentsMap.size() > 0){
                Iterator keySetIterator = players[idx].rentsMap.keySet().iterator();

                while (keySetIterator.hasNext()){ //rentMap 순환
                    String name = keySetIterator.next().toString();

                    players[idx].penalty += players[idx].rentsMap.get(name);
                }
            }
        }

        for(idx=0; idx<totalParticipants; idx++){
            resultGameStateService(todayDate, players[idx].nickname, players[idx].penalty, inputID);
        }

        System.out.println("게임 최종 결과");
        printGameStateService(todayDate, inputID);
        System.out.println();
    }


    private String dateToString(int year, int month, int day){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);

        return stringBuilder.toString();
    }
}
