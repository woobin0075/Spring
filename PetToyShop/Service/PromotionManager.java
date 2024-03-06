package springstart.PetToyShop.Service;

import org.springframework.stereotype.Component;

@Component
public class PromotionManager {
    private PromotionStrategy strategy;
    private static PromotionManager manager;

    private PromotionManager(PromotionStrategy strategy){
        this.strategy = strategy;
    }

    public static PromotionManager getManager(PromotionStrategy stra){
        if(manager == null){
            manager = new PromotionManager(stra);
        }
        return manager;
    }

    public double applyDiscount(double pay){
        return strategy.applyDiscount(pay);
    }
}
