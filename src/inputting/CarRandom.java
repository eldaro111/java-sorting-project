package inputting;

import model.Car;
import validation.CarValidator;

import java.time.Year;
import java.util.Random;

public class CarRandom {

    private final Random random = new Random();

    private static final String[] MODELS = {
            "BMW", "Audi", "Toyota", "Honda", "Mercedes-Benz",
            "Ford", "Hyundai", "Kia", "Nissan", "Volkswagen"
    };

    public Car[] readCars(int length) {
        if (length <= 0) {
            return new Car[0];
        }

        Car[] cars = new Car[length];
        for (int i = 0; i < length; i++) {
            cars[i] = generateRandomCar();
        }

        return cars;
    }

    private Car generateRandomCar() {
        String model = MODELS[random.nextInt(MODELS.length)];

        int minYear = 1886;
        int currentYear = Year.now().getValue();
        int year = minYear + random.nextInt(currentYear - minYear + 1);

        int power = 50 + random.nextInt(951);

        if (!CarValidator.isValid(model, year, power)) {
            model = "Toyota";
            year = 2020;
            power = 150;
        }

        return new Car.Builder()
                .setModel(model)
                .setYear(year)
                .setPower(power)
                .build();
    }
}
