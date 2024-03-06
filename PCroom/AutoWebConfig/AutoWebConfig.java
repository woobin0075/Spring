package PCroom.AutoWebConfig;

import PCroom.Service.GuestService;
import PCroom.Service.MemberService;
import PCroom.Service.SuperVisorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoWebConfig {

    @Bean
    GuestService guestService(){
        return new GuestService();
    }

    @Bean
    MemberService memberService(){
        return new MemberService();
    }

    @Bean
    SuperVisorService superVisorService(){
        return new SuperVisorService();
    }
}
