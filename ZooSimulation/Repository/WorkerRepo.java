package springstart.ZooSimulation.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class WorkerRepo {
    WorkerInterface worker;

    @Autowired
    public WorkerRepo(WorkerRepository workerRepository){
        worker = workerRepository;
    }

    public void insertWorkerInfo(String name, String id, String pwd){
        worker.insertWorkerInfo(name, id, pwd);
    }

    public boolean selectLoginWorker(String id, String pwd){
        return worker.selectLoginWorker(id, pwd);
    }

    public void insertNewAnimal(String kindof, String kindof2,  String name, int age){
        worker.insertNewAnimal(kindof, kindof2, name, age);
    }

    public int selectAnimalDBid(String kindOf, String name){
        return worker.selectAnimalDBid(kindOf, name);
    }

    public void insertNewCage(String cageName){
        worker.insertNewCage(cageName);
    }

    public int selectCageDbid(String cageName){
        return worker.selectCageDbid(cageName);
    }

    public void insertCageInBranch(int branchID, int cageID){
        worker.insertCageInBranch(branchID, cageID);
    }

    public void insertNewZooBranch(String branchName){
        worker.insertNewZooBranch(branchName);
    }

    public int selectZooBranchid(String branchName){
        return worker.selectZooBranchid(branchName);
    }

    public int selectAnimalHealthState(String kindOf, String name){
        return worker.selectAnimalHealthState(kindOf, name);
    }

    public void selectAnimalInfoByid(int animalDBid){
        worker.selectAnimalInfoByid(animalDBid);
    }

    public ArrayList<Integer> selectAllBranchId(){
        return worker.selectAllBranchId();
    }

    public ArrayList<Integer> selectAllCagesIdinBranch(int branchID){
        return worker.selectAllCagesIdinBranch(branchID);
    }

    public ArrayList<Integer> selectAllanimalsIdinCage(int cageId, int branchId){
        return worker.selectAllanimalsIdinCage(cageId, branchId);
    }

    public String selectBranchNamebyId(int branchId){
        return worker.selectBranchNamebyId(branchId);
    }

    public String selectCageNameByid(int cageid){
        return worker.selectCageNameByid(cageid);
    }

    public void selectAllHealthState(){
        worker.selectAllHealthState();
    }

    public HashMap<Integer, Integer> selectAllAnimalsDBidAndHealthDown(){
        return worker.selectAllAnimalsDBidAndHealthDown();
    }

    public void updateRandomAnimalsHealthDown(HashMap<Integer, Integer> map){
        worker.updateRandomAnimalsHealthDown(map);
    }

    public ArrayList<Integer> selectHealthStateZeroAnimalsDBid(){
        return worker.selectHealthStateZeroAnimalsDBid();
    }

    public void deleteDiedAnimals(ArrayList<Integer> listDBids){
        worker.deleteDiedAnimals(listDBids);
    }

    public boolean selectCorrectBranchandCage(int branchId, int cageId){
        return worker.selectCorrectBranchandCage(branchId, cageId);
    }

    public void insertAnimalinCageinBranch(int cageId, int branchId, int animalId){
        worker.insertAnimalinCageinBranch(cageId, branchId, animalId);
    }

    public void insertNewImprovement(String improve){
        worker.insertNewImprovement(improve);
    }

    public void selectAllImprovements(){
        worker.selectAllImprovements();
    }

    public void updateFinishImprovement(int num){
        worker.updateFinishImprovement(num);
    }

    public boolean selectAllAnimalsHealth(){
        return worker.selectAllAnimalsHealth();
    }
}
