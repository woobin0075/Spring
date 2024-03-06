package EnergyGame.WebConfig;

import EnergyGame.Repository.Account;
import EnergyGame.Repository.AccountRepository;
import EnergyGame.Repository.Game;
import EnergyGame.Repository.GameRepository;
import EnergyGame.Service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public Service service(){
        AccountRepository accountRepository = new AccountRepository();
        GameRepository gameRepository = new GameRepository();
        Account account = new Account(accountRepository);
        Game game = new Game(gameRepository);

        return new Service(account, game);
    }
}
