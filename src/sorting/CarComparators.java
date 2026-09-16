package sorting;

import java.util.Comparator;
import model.Car;

public final class CarComparators {

    private CarComparators() {
    }

    public static Comparator<Car> byPower() {
        return Comparator.comparingInt(Car::getPower);
    }

    public static Comparator<Car> byModel() {
        return Comparator.comparing(
                Car::getModel,
                String.CASE_INSENSITIVE_ORDER
        );
    }

    public static Comparator<Car> byYear() {
        return Comparator.comparingInt(Car::getYear);
    }

    public static Comparator<Car> byAllFields() {
        return byPower()
                .thenComparing(byModel())
                .thenComparing(byYear());
    }
}
