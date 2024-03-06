package springstart.ZooSimulation.AppConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import springstart.ZooSimulation.Repository.VisitorRepo;
import springstart.ZooSimulation.Repository.VisitorRepository;
import springstart.ZooSimulation.Repository.WorkerRepo;
import springstart.ZooSimulation.Repository.WorkerRepository;
import springstart.ZooSimulation.Service.MainWhile;
import springstart.ZooSimulation.Service.VisitorService;
import springstart.ZooSimulation.Service.WorkerService;

@Configuration
@ComponentScan(
        basePackageClasses = AppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AppConfig {

    @Bean
    WorkerService workerService(){
        WorkerRepository workerRepository = new WorkerRepository();
        WorkerRepo workerRepo = new WorkerRepo(workerRepository);

        return new WorkerService(workerRepo);
    }

    @Bean
    VisitorService visitorService(){
        VisitorRepository visitorRepository = new VisitorRepository();
        VisitorRepo visitorRepo = new VisitorRepo(visitorRepository);

        return new VisitorService(visitorRepo);
    }

    @Bean
    MainWhile mainWhile(){

        return new MainWhile(workerService(), visitorService());
    }
}
