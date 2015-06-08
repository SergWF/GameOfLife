package my.outfittery;

import my.outfittery.life.logic.entity.checks.CyclingStateChecker;
import my.outfittery.life.logic.entity.checks.MinumumLiveAmountChecker;
import my.outfittery.life.logic.entity.rules.TooMuchNeighboursRule;
import my.outfittery.life.logic.entity.rules.ExpansionRule;
import my.outfittery.life.logic.entity.rules.LowNeighboursRule;
import my.outfittery.life.logic.handling.GameHandler;
import my.outfittery.life.logic.handling.StateStorage;
import my.outfittery.life.storage.StateStorageImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@SpringBootApplication
@Configuration
public class GameOfLifeApplication {

    @Bean
    public StateStorage stateStorage(){
        return new StateStorageImpl();
    }


    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GameHandler gameHandler(){
        GameHandler gameHandler = new GameHandler();
        gameHandler.addRule(lowNeighboursRule());
        gameHandler.addRule(aLotNeighboursRule());
        gameHandler.addRule(expansionRule());
        gameHandler.addChecker(cyclingStateChecker());
        gameHandler.addChecker(minumumLiveAmountChecker());
        return gameHandler;
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    LowNeighboursRule lowNeighboursRule(){
        return new LowNeighboursRule(2);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    TooMuchNeighboursRule aLotNeighboursRule(){
        return new TooMuchNeighboursRule(3);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    ExpansionRule expansionRule(){
        return new ExpansionRule(3);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    MinumumLiveAmountChecker minumumLiveAmountChecker(){
        return new MinumumLiveAmountChecker(2);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    CyclingStateChecker cyclingStateChecker(){
        return new CyclingStateChecker(stateStorage());
    }



    public static void main(String[] args) {
        SpringApplication.run(GameOfLifeApplication.class, args);
    }
}
