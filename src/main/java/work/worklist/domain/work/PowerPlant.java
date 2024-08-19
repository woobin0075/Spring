package work.worklist.domain.work;

public interface PowerPlant {
    int fossilFuelEfficient = 3;
    int fossilFuelEnvironmentEffect = 2;
    int fossilFuelStayCost = 2;

    int sunshineEffienct = 1;
    int sunshineEnvironmentEffect = 0;
    int sunshineStayCost = 0;

    int windEfficient = 2;
    int windEnvironmentEffect = 1;
    int windStayCost = 1;

    int waterEfficient = 2;
    int waterEnvironmentEffect = 1;
    int waterStayCost = 1;
}
