package springstart.ZooSimulation.Domain;
import springstart.ZooSimulation.Repository.WorkerInterface;

import java.util.*;

public class Animal {

    String[] common = {"밥 먹는중", "자는 중", "씻는 중", "놀고 있는 중", "걷는 중", "뛰는 중"};
    String[] waterAnimal = {"밥 먹는중", "자는 중", "씻는 중", "놀고 있는 중", "걷는 중", "뛰는 중", "헤엄치는 중", "잠수하는 중"};
    String[] bird = {"밥 먹는중", "자는 중", "씻는 중", "놀고 있는 중", "걷는 중", "뛰는 중", "나는 중", "날개 확 핀 상태"};

//    public void inputAnimalMap(String animal, String kind){
//
//        if(!WorkerInterface.animalBehave.containsKey(animal)){
//            WorkerInterface.animalBehave.put(animal, kind);
//        }
//    }

    public void behave(String animal){

        Random random = new Random();
        int commonLen = common.length;
        int wateraniLen = waterAnimal.length;
        int birdLen = bird.length;

        int randcommon = random.nextInt(commonLen);
        int randwater = random.nextInt(wateraniLen);
        int randbird = random.nextInt(birdLen);

        if(animal.equals("포유류")){

            System.out.println(common[randcommon]);

        }else if(animal.equals("바다 동물")){

            System.out.println(waterAnimal[randwater]);

        }else if(animal.equals("조류")){
            System.out.println(bird[randbird]);
        }

    }
}
