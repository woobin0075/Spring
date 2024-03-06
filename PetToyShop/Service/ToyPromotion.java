package springstart.PetToyShop.Service;

public class ToyPromotion implements PromotionStrategy{
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice*(1 - 0.04);
    }
}
