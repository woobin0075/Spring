package springstart.ZooSimulation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springstart.ZooSimulation.Domain.Animal;

import java.util.*;

@Component
public class MainWhile {

    WorkerService workerService;
    VisitorService visitorService;

    @Autowired
    public MainWhile(WorkerService workerService, VisitorService visitorService){
        this.workerService = workerService;
        this.visitorService = visitorService;
    }

    public void whileSelect(){
        Promotion promotion = new Promotion();
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        int select, selectImprovementNum, exhibitionSelect;
        int giveFood = 0; //3번까지 줄 수 있도록 설정
        boolean cleaning = false;
        String inputName, inputID, inputPwd, inputKindOf;
        String inputKindof2;
        int inputAge;
        String inputBranchName, inputCageName;
        String inputImprovement;
        int branchId, cageId;
        int fare = 0;
        Map<Integer, String> peopleWantAnimalMap = new HashMap<>();
        int animalId;
        double[] feedbackScore = new double[3];
        String feedbackText = "";
        String exhibitionTopic = "", factor, bringFactor;
        int exhibitionOpendays, untilDays = 0;
        int exhibitionStart = 0;
        boolean exhibitionFlag = false;
        int questSelect;
        boolean questFlag = true;
        boolean fourDaysAnimalsHealthFlag = true;
        int days = 0;
        //Animal animal = new Animal();

        System.out.println("건강 상태 기준");
        System.out.println("8 ~ 10 : 매우 양호");
        System.out.println("4 ~ 7 : 양호");
        System.out.println("1 ~ 3 : 건강 악화");
        System.out.println("0 : 사망");
        System.out.println();
        System.out.println("구역 하나당 우리 최대 3개까지 추가 가능");
        System.out.println("우리 하나당 동물 4마리 입주 가능");
        System.out.println("먹이는 3번까지, 청소는 1번만 가능합니다.");
        System.out.println();

        peopleWantAnimalMap.put(0, "호랑이");
        peopleWantAnimalMap.put(1, "사자");
        peopleWantAnimalMap.put(2, "판다");
        peopleWantAnimalMap.put(3, "코끼리");
        peopleWantAnimalMap.put(4, "독수리");
        peopleWantAnimalMap.put(5, "물개");
        peopleWantAnimalMap.put(6, "바다표범");
        peopleWantAnimalMap.put(7, "타조");

        while(true){ //등록, 로그인용
            System.out.printf("1.직원 등록, 2.직원 로그인, 3.방문객 전용 피드백 남기기, 4.종료 => ");
            select = sc.nextInt();

            if(select == 1){

                System.out.printf("이름을 입력하세요. : ");
                inputName = sc.next();
                System.out.printf("사용할 아이디를 입력하세요. : ");
                inputID = sc.next();
                System.out.printf("사용할 비밀번호를 입력하세요. : ");
                inputPwd = sc.next();

                workerService.makeWorkerAccount(inputName, inputID, inputPwd);

            }else if(select == 2){

                System.out.printf("아이디를 입력하세요. : ");
                inputID = sc.next();
                System.out.printf("비밀번호를 입력하세요. : ");
                inputPwd = sc.next();

                boolean login = workerService.canWorkerLogin(inputID, inputPwd);
                if(login){

                    System.out.println("로그인에 성공하셨습니다.");
                    break;
                }else{
                    System.out.println("다시 입력해주세요.");
                }
            }else if(select == 3){

                System.out.println("다음 항목에 대해서 점수를 부여해주세요.(ex.점수 : 1.0, 2.5, 5.0)");
                System.out.println("점수는 1.0에서 5.0까지만 가능합니다.");


                while(true){

                    System.out.printf("1. 동물들의 상태 : ");
                    feedbackScore[0] = sc.nextDouble();

                    if(feedbackScore[0] < 1.0 || feedbackScore[0] > 5.0){
                        System.out.println("처음부터 다시 입력해주세요.");
                        continue;
                    }

                    System.out.printf("2. 시설 청결도 : ");
                    feedbackScore[1] = sc.nextDouble();

                    if(feedbackScore[1] < 1.0 || feedbackScore[1] > 5.0){
                        System.out.println("처음부터 다시 입력해주세요.");
                        continue;
                    }

                    System.out.printf("3. 서비스 : ");
                    feedbackScore[2] = sc.nextDouble();

                    if(feedbackScore[2] < 1.0 || feedbackScore[2] > 5.0){
                        System.out.println("처음부터 다시 입력해주세요.");
                        continue;
                    }

                    System.out.printf("4. 남기고 싶은 말 : ");
                    feedbackText = scStr.nextLine();

                    visitorService.addNewFeedback(feedbackScore[0], feedbackScore[1], feedbackScore[2], feedbackText);

                    break;
                }


            }else if(select == 4){
                return;
            }
        }

        printPeopleWantAnimal(peopleWantAnimalMap);
        while(true){

            /*벌금 낼지 안 낼지 검사*/
            ArrayList<Integer> animalsList = workerService.selectHealthStateZeroAnimalsDBid();

            if(animalsList.size() > 0){

                fare = animalsList.size() * 5000;
                System.out.println("죽은 동물 발생으로 벌금 "+fare+"원이 부과되었습니다.");
                workerService.deleteDiedAnimals(animalsList);
            }
            printZooState();
            System.out.println();

            if(exhibitionFlag){
                System.out.println(untilDays-exhibitionStart+"일 동안 "+exhibitionTopic+"행사를 합니다.");
                bringFactor = visitorService.bringFactor(exhibitionTopic);

                switch (bringFactor) {
                    case "입장권 할인":

                        promotion.onSale();
                        break;
                    case "특별 공연":

                        promotion.onConcert();
                        break;
                    case "스탬프 모으기":

                        promotion.collectStamp();
                        break;
                }
            }

            if(workerService.inspectAllAnimalsHealth()){
                days++;
            }

            System.out.printf("1.동물원 구역 추가, 2.동물원 우리 추가, 3.동물원에 데려올 동물 추가," +
                    " 4.동물들 건강상태 조회, 5.동물들 먹이 공급, " +
                    "6.청결 청소, 7.다음날, 8.개선점 작성, 9.개선완료 체크, 10.전시회 개최, 11.퀘스트 => "); //개선점 작성 선택,
            select = sc.nextInt();

            if(select == 1){
                System.out.printf("구역 이름 입력 : ");
                inputBranchName = sc.next();

                workerService.addNewBranch(inputBranchName);

            }else if(select == 2){
                
                List<Integer> branchList = workerService.selectAllBranchId();
                
                if(branchList.size() > 0){
                    System.out.println("현재 지어진 구역 이름");
                    for(int i=0; i<branchList.size(); i++){
                        System.out.println(workerService.selectBranchNamebyId(branchList.get(i)));
                    }

                    System.out.printf("우리를 추가할 구역 이름을 입력하세요. : ");
                    inputBranchName = sc.next();
                    branchId = workerService.bringBranchDBid(inputBranchName);
                    
                    if(branchId > 0){

                        List<Integer> cageList = workerService.selectAllCagesIdinBranch(branchId);
                        
                        if(cageList.size() < 3){
                            System.out.printf("우리 이름 추가 : ");
                            inputCageName = sc.next();
                            
                            workerService.addNewCage(inputCageName);
                            cageId = workerService.bringCageDBid(inputCageName);
                            
                            workerService.addCageInBranch(branchId, cageId);
                                    
                        }else{
                            System.out.println("지어진 우리가 3개라 지을 수 없습니다.");
                        }
                        
                    }else{
                        System.out.println("해당하는 구역이 없습니다.");
                    }
                    
                }else{
                    System.out.println("아직 구역이 안 만들어졌습니다.");
                }

            }else if(select == 3){

                System.out.printf("입주할 동물원 구역 이름을 입력하세요. :");
                inputBranchName = sc.next();
                System.out.printf("입주시킬 우리 이름을 입력하세요. : ");
                inputCageName = sc.next();

                cageId = workerService.bringCageDBid(inputCageName);
                branchId = workerService.bringBranchDBid(inputBranchName);

                if(!workerService.selectCorrectBranchandCage(branchId, cageId)){
                    System.out.println("해당하는 동물원 지역이 없습니다.");
                    continue;
                }

                if(cageId > 0){

                    List<Integer> animalList = workerService.selectAllanimalsIdinCage(cageId, branchId);

                    if(animalList.size() < 4){

                        System.out.printf("동물 이름 입력 : ");
                        inputName = sc.next();
                        System.out.printf("품종 입력(ex.포유류, 조류) : ");
                        inputKindof2 = sc.next();
                        System.out.printf("종류 입력 : ");
                        inputKindOf = sc.next();

                        System.out.printf("나이 입력 : ");
                        inputAge = sc.nextInt();
                        workerService.addNewAnimal(inputKindOf, inputKindof2, inputName, inputAge);
                        animalId = workerService.bringAnimalDBid(inputKindOf, inputName);

                        workerService.insertAnimalinCageinBranch(cageId, branchId, animalId);

                    }else{
                        System.out.println("우리에 동물들이 꽉차서 입주시킬 수 없습니다.");
                    }

                }else{
                    System.out.println("해당하는 우리가 없습니다.");
                }


            }else if(select == 4){

                System.out.printf("1.모든 동물 건강 상태 조회, 2.특정 동물 건강 상태 조회");
                select = sc.nextInt();

                if(select == 1){

                    workerService.printAllAnimalsHealthstate();

                }else if(select == 2){
                    System.out.printf("동물 이름 입력 : ");
                    inputName = sc.next();
                    System.out.printf("품종 입력 : ");
                    inputKindOf = sc.next();

                    System.out.println("건강 상태 : "+workerService.bringAnimalHealthState(inputKindOf, inputName));
                }

            }else if(select == 5){

                giveFood++;

                if(giveFood < 4){
                    System.out.printf(giveFood+"번 먹이를 주었습니다.");
                }else{
                    System.out.println("먹이를 충분히 주어서 더 줄 필요가 없습니다.");
                }
                System.out.println();

            }else if(select == 6){

                if(!cleaning){
                    System.out.println("청소 시행.");
                    cleaning = true;

                }else{
                    System.out.println("청소를 시작했습니다.");
                }
                System.out.println();

            }else if(select == 7){

                if(exhibitionFlag){
                    exhibitionStart++;
                }

                if(exhibitionFlag && exhibitionStart >= untilDays){
                    exhibitionStart = 0;
                    exhibitionFlag = false;
                }

                if(cleaning){
                    cleaning = false;

                }else{
                    //건강 악화 시작
                    workerService.updateRandomAnimalsHealthDown(workerService.selectAllAnimalsDBidAndHealthDown());
                }

                if(giveFood < 1){
                    //건강 악화 시작
                    workerService.updateRandomAnimalsHealthDown(workerService.selectAllAnimalsDBidAndHealthDown());
                }

                giveFood = 0;
                questFlag = true;
                printPeopleWantAnimal(peopleWantAnimalMap);

            }else if(select == 8){

                //지금가지 작성된 개선점과 실행완료 여부 프린트
                workerService.printAllImprovements();

                System.out.printf("동물원 개선을 위한 개선점을 입력하세요. : ");
                inputImprovement = scStr.nextLine();
                workerService.writeNewImProvement(inputImprovement);

            }else if(select == 9){

                //지금가지 작성된 개선점과 실행완료 여부 프린트
                workerService.printAllImprovements();
                //개선한 개선점 번호로 선택 -> 완료 설정
                System.out.printf("미실행한 개선점들 중 실행 완료한 개선점 번호를 입력하세요. : ");
                selectImprovementNum = sc.nextInt();

                workerService.updateFinishImprovement(selectImprovementNum);

            }else if(select == 10){

                System.out.printf("개최할 전시회 주제 작성 : ");
                exhibitionTopic = scStr.nextLine();

                System.out.printf("전시회 기간일(일수로 작성) : ");
                exhibitionOpendays = sc.nextInt();

                System.out.println("전시회에서 할 행사");
                System.out.printf("1.입장권 할인, 2.특별 공연, 3.스탬프 모으기 => ");
                exhibitionSelect = sc.nextInt();
                factor = promotion.factors(exhibitionSelect);

                untilDays = exhibitionOpendays;
                exhibitionFlag = true;
                visitorService.addNewExhibition(exhibitionTopic, exhibitionOpendays, factor);

            }else if(select == 11){

                if(!questFlag){
                    System.out.println("퀘스트 하루에 한번만 가능합니다.");
                    continue;
                }

                System.out.println("다음 퀘스트 중 하나를 고르세요.");
                System.out.printf("1. 동물 구조, 2. 4일이상 동물들 건강 상태 양호 : ");
                questSelect = sc.nextInt();

                if(questSelect == 1){

                    if(animalSave()){
                        System.out.println("동물 구조에 성공하셨습니다. 상여금 10000원이 주어집니다.");
                    }else{
                        System.out.println("아쉽네요. 다음 기회에 다시 도전하세요.");
                    }

                }else if(questSelect == 2){

                    if(days >= 4){
                        System.out.println("동물들 상태가 모두 양호합니다. 상여금 15000원이 주어집니다.");
                        days = 0;

                    }else{
                        System.out.println("아직 4일 이상이 지나지 않았습니다.");
                    }

                }

                questFlag = false;
            }
        }
    }

    private boolean animalSave(){ //가챠 형식으로 진행
        Random random = new Random();
        boolean flag = true;

        int r = random.nextInt(50);

        flag = r < 20;

        return flag;
    }

    private void printPeopleWantAnimal(Map<Integer, String> peopleWantAnimalMap){
        Random random = new Random();
        int wantRandNum = random.nextInt(peopleWantAnimalMap.size());

        System.out.println("오늘 손님들이 보고 싶은 동물은 "+peopleWantAnimalMap.get(wantRandNum)+"입니다.");
    }
    
    private void printZooState(){
        List<Integer> branchList = workerService.selectAllBranchId();
        
        for(int i=0; i<branchList.size(); i++){
            System.out.println("구역 이름 : " + workerService.selectBranchNamebyId(branchList.get(i)));
            List<Integer> cageList = workerService.selectAllCagesIdinBranch(branchList.get(i));
            
            for(int j=0; j<cageList.size(); j++){
                System.out.println("우리 이름 : "+workerService.selectCageNameByid(cageList.get(j)));
                List<Integer> animalList = workerService.selectAllanimalsIdinCage(cageList.get(j), branchList.get(i));
                
                for(int k=0; k<animalList.size(); k++){
                    workerService.selectAnimalInfoByid(animalList.get(k));
                }
                System.out.println();
            }
        }
    }
}
