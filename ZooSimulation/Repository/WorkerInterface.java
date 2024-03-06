package springstart.ZooSimulation.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface WorkerInterface {

   // Map<String, String> animalBehave = new HashMap<>(); //key : 동물, value : 품종
    void insertWorkerInfo(String name, String id, String pwd);
    boolean selectLoginWorker(String id, String pwd);
    void insertNewAnimal(String kindof, String kindof2, String name, int age);
    int selectAnimalDBid(String kindOf, String name);
    void insertNewZooBranch(String branchName);
    int selectAnimalHealthState(String kindOf, String name);
    void insertNewCage(String cageName);
    int selectCageDbid(String cageName);
    void insertCageInBranch(int branchID, int cageID);
    int selectZooBranchid(String branchName);
    int selectBranchNum();
    int selectCageNum();
    void selectAnimalInfoByid(int animalDBid);
    ArrayList<Integer> selectAllBranchId();
    ArrayList<Integer> selectAllCagesIdinBranch(int branchID);
    ArrayList<Integer> selectAllanimalsIdinCage(int cageId, int branchId);
    String selectBranchNamebyId(int branchId);
    String selectCageNameByid(int cageid);
    void selectAllHealthState();
    HashMap<Integer, Integer> selectAllAnimalsDBidAndHealthDown();
    void updateRandomAnimalsHealthDown(HashMap<Integer, Integer> map);
    ArrayList<Integer> selectHealthStateZeroAnimalsDBid();
    void deleteDiedAnimals(ArrayList<Integer> listDBids);
    boolean selectCorrectBranchandCage(int branchId, int cageId);
    void insertAnimalinCageinBranch(int cageId, int branchId, int animalId);
    void insertNewImprovement(String improve);
    void selectAllImprovements();
    void updateFinishImprovement(int num);
    boolean selectAllAnimalsHealth();
}
