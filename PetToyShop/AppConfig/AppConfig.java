package springstart.PetToyShop.AppConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import springstart.PetToyShop.Repository.OwnerRepo;
import springstart.PetToyShop.Repository.UserRepo;
import springstart.PetToyShop.Repository.UserRepository;
import springstart.PetToyShop.Service.*;

import java.util.Random;

@Configuration
@ComponentScan(
        basePackageClasses = AppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AppConfig {

    @Bean
    springstart.PetToyShop.Service.PuppyDayPromotion puppyDayPromotion(){
        return new springstart.PetToyShop.Service.PuppyDayPromotion();
    }

    @Bean
    WeekendPromotion weekendPromotion(){
        return new WeekendPromotion();
    }

    @Bean
    HolidayPromotion holidayPromotion(){
        return new HolidayPromotion();
    }

    @Bean
    CatDayPromotion catDayPromotion(){
        return new CatDayPromotion();
    }

    @Bean
    FeedPromotion feedPromotion(){
        return new FeedPromotion();
    }

    @Bean
    ToyPromotion toyPromotion(){
        return new ToyPromotion();
    }

    @Bean
    CelebrateOpenPromotion celebrateOpenPromotion(){
        return new CelebrateOpenPromotion();
    }

    @Bean
    PromotionManager selectRandomPromotionStrategy(){
        Random random = new Random();
        int n = random.nextInt(7) + 1;

        if(n == 1){
            System.out.println("강아지의 날 기념으로 할인합니다.");
            return PromotionManager.getManager(puppyDayPromotion());
        }else if(n == 2){
            System.out.println("공휴일 기념으로 할인합니다.");
            return PromotionManager.getManager(holidayPromotion());
        }else if(n == 3){
            System.out.println("주말 기념으로 할인합니다.");
            return PromotionManager.getManager(weekendPromotion());
        }else if(n == 4){
            System.out.println("고양이의 날 기념으로 할인합니다.");
            return PromotionManager.getManager(catDayPromotion());
        }else if(n == 5){
            System.out.println("사료 베포의 날 기념으로 할인합니다.");
            return PromotionManager.getManager(feedPromotion());
        }else if(n == 6){
            System.out.println("반려동물 장난감 베포 기념으로 할인합니다.");
            return PromotionManager.getManager(toyPromotion());

        }else if(n == 7){
            System.out.println("오픈 창린 기념으로 할인합니다.");
            return PromotionManager.getManager(celebrateOpenPromotion());
        }

        return null;
    }

    @Bean
    Service service(){
        UserRepository userRepository = new UserRepository();
        UserRepo userRepo = new UserRepo(userRepository);
        OwnerRepo ownerRepo = OwnerRepo.getOwnerRepo();

        return new Service(userRepo, ownerRepo);
    }
}
