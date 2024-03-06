package ResutuantOrguugdanOrCalclation.Service;

import ResutuantOrguugdanOrCalclation.Domain.Calculator;
import ResutuantOrguugdanOrCalclation.Domain.ChinaRestaurntPrice;
import ResutuantOrguugdanOrCalclation.Domain.Customer;
import ResutuantOrguugdanOrCalclation.Domain.NumberCheck;
import ResutuantOrguugdanOrCalclation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    private static CustomerService service;
    CustomerRepo customerRepo;
    UpdateCustomerRepo updateCustomerRepo;
    NumberCheckRepo numberCheckRepo;

    private CustomerService(){
        customerRepo = new CustomerRepo();
        updateCustomerRepo = new UpdateCustomerRepo();
        numberCheckRepo = new NumberCheckRepo();
    }

    public static CustomerService getService(){
        if(service == null){
            service = new CustomerService();
        }
        return service;
    }

    public void priceSave(){
        customerRepo.priceSave();
    }

    public void restaurantServie(int selectMenu, NumberCheck numberCheck, Customer customer){

        if(selectMenu == 1){
            numberCheck.visitRestaurant();
            customer.buyjajangmyeon();
            customer.buyFood(ChinaRestaurntPrice.jajangmyeon);

        }else if(selectMenu == 2){
            numberCheck.visitRestaurant();
            customer.buyfried_Rice();
            customer.buyFood(ChinaRestaurntPrice.fried_Rice);

        }else if(selectMenu == 3){
            numberCheck.visitRestaurant();
            customer.buytangsuyuksmall();
            customer.buyFood(ChinaRestaurntPrice.tangsuyuk_small);

        }else if(selectMenu == 4){
            numberCheck.visitRestaurant();
            customer.buytangsuyukmedium();
            customer.buyFood(ChinaRestaurntPrice.tangsuyuk_medium);

        }else if(selectMenu == 5){
            numberCheck.visitRestaurant();
            customer.buytangsuyuklarge();
            customer.buyFood(ChinaRestaurntPrice.tangsuyuk_large);

        }else{
            System.out.println("다시 선택하세요.");
        }
    }

    public void calcService(int selectCalc, NumberCheck numberCheck, Calculator calculator){

        if(selectCalc == 1){
            numberCheck.visitCalc();
            calculator.clac(1);

        }else if(selectCalc == 2){
            numberCheck.visitCalc();
            calculator.clac(2);

        }else if(selectCalc == 3){
            numberCheck.visitCalc();
            calculator.clac(3);

        }else if(selectCalc == 4){
            numberCheck.visitCalc();
            calculator.clac(4);

        }else{
            System.out.println("다시 선택하세요.");
        }
    }

    public void saveService(Customer customer, NumberCheck numberCheck, int i){

        customerRepo.insert(customer, i+1);
        numberCheckRepo.insert(numberCheck, i+1);

//        customerRepository.insert(customer, i+1);
//        numberCheckRepository.insert(numberCheck, i+1);

    }

    public void updateNameService(String beforeName, String afterName){


        updateCustomerRepo.update(beforeName, afterName);
        numberCheckRepo.update(beforeName, afterName);
        //updateCustomerRepository.update(beforeName, afterName);
        //numberCheckRepository.update(beforeName, afterName);

    }
    public void findNameService(String inputname){

        customerRepo.find(inputname);
    }

    public void printgugudan(int dan){
        for(int i=1; i<=9; i++){
            System.out.println(dan + " * "+i+" = "+dan*i);
        }
        System.out.println();
    }

    public void deleteService(String name){

        customerRepo.delete(name);
        //customerRepository.delete(name);
    }
}
