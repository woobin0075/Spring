package work.worklist;

import work.worklist.domain.repowitory.WorkRepository;
import work.worklist.domain.work.Work;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorkRepository workRepository = new WorkRepository();
        saveWork(workRepository);
        findAll(workRepository);
        updateWork(workRepository);
    }

    public static void saveWork(WorkRepository workRepository){
        Work work1 = new Work("GS25", "경기도청역 어디어디", 1500000);
        workRepository.save(work1);
        Work work2 = new Work("CU", "수원시 어디어디", 3000000);
        workRepository.save(work2);
        Work work3 = new Work("세븐일레븐", "고양시 어디어디", 1200000);
        workRepository.save(work3);
    }
    public static void findAll(WorkRepository workRepository){
        List<Work> works = workRepository.findAll();
        for (Work work : works) {
            System.out.println("work.toString() = " + work.toString());
        }
    }
    public static void updateWork(WorkRepository workRepository){
        System.out.println("\n\n\n\n");
        System.out.println("----now Data-----");
        findAll(workRepository);

        Work updateData = new Work(null, null, null);
        workRepository.update(2L, updateData);
        System.out.println("----update Data----");
        findAll(workRepository);
    }
}
