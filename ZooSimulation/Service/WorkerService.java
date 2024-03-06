package springstart.ZooSimulation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springstart.ZooSimulation.Repository.WorkerRepo;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class WorkerService {

    WorkerRepo workerRepo;

    @Autowired
    public WorkerService(WorkerRepo workerRepo){
        this.workerRepo = workerRepo;
    }

    public void makeWorkerAccount(String name, String id, String pwd){
        workerRepo.insertWorkerInfo(name, id, pwd);
    }

    public boolean canWorkerLogin(String id, String pwd){
        return workerRepo.selectLoginWorker(id, pwd);
    }

    public void addNewAnimal(String kindof, String kindof2,  String name, int age){
        workerRepo.insertNewAnimal(kindof, kindof2, name, age);
    }

    public int bringAnimalDBid(String kindOf, String name){
        return workerRepo.selectAnimalDBid(kindOf, name);
    }

    public void addNewCage(String cageName){
        workerRepo.insertNewCage(cageName);
    }

    public int bringCageDBid(String cageName){
        return workerRepo.selectCageDbid(cageName);
    }

    public void addCageInBranch(int branchID, int cageID){
        workerRepo.insertCageInBranch(branchID, cageID);
    }

    public void addNewBranch(String branchName){
        workerRepo.insertNewZooBranch(branchName);
    }

    public int bringBranchDBid(String branchName){
        return workerRepo.selectZooBranchid(branchName);
    }

    public int bringAnimalHealthState(String kindOf, String name){
        return workerRepo.selectAnimalHealthState(kindOf, name);
    }

    public void selectAnimalInfoByid(int animalDBid){
        workerRepo.selectAnimalInfoByid(animalDBid);
    }

    public ArrayList<Integer> selectAllBranchId(){
        return workerRepo.selectAllBranchId();
    }

    public ArrayList<Integer> selectAllCagesIdinBranch(int branchID){
        return workerRepo.selectAllCagesIdinBranch(branchID);
    }

    public ArrayList<Integer> selectAllanimalsIdinCage(int cageId, int branchId){
        return workerRepo.selectAllanimalsIdinCage(cageId, branchId);
    }

    public String selectBranchNamebyId(int branchId){
        return workerRepo.selectBranchNamebyId(branchId);
    }

    public String selectCageNameByid(int cageid){
        return workerRepo.selectCageNameByid(cageid);
    }

    public void printAllAnimalsHealthstate(){
        workerRepo.selectAllHealthState();
    }

    public HashMap<Integer, Integer> selectAllAnimalsDBidAndHealthDown(){
        return workerRepo.selectAllAnimalsDBidAndHealthDown();
    }

    public void updateRandomAnimalsHealthDown(HashMap<Integer, Integer> map){
        workerRepo.updateRandomAnimalsHealthDown(map);
    }

    public ArrayList<Integer> selectHealthStateZeroAnimalsDBid(){
        return workerRepo.selectHealthStateZeroAnimalsDBid();
    }

    public void deleteDiedAnimals(ArrayList<Integer> listDBids){
        workerRepo.deleteDiedAnimals(listDBids);
    }

    public boolean selectCorrectBranchandCage(int branchId, int cageId){
        return workerRepo.selectCorrectBranchandCage(branchId, cageId);
    }

    public void insertAnimalinCageinBranch(int cageId, int branchId, int animalId){
        workerRepo.insertAnimalinCageinBranch(cageId, branchId, animalId);
    }

    public void writeNewImProvement(String improve){
        workerRepo.insertNewImprovement(improve);
    }

    public void printAllImprovements(){
        workerRepo.selectAllImprovements();
    }

    public void updateFinishImprovement(int num){
        workerRepo.updateFinishImprovement(num);
    }

    public boolean inspectAllAnimalsHealth(){
        return workerRepo.selectAllAnimalsHealth();
    }
}
