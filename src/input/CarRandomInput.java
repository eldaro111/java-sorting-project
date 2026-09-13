package input;

import model.Car;
import validation.CarValidator;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarRandomInput {

    private final Random random = new Random();

    private static final String[] MODELS = {
            "BMW", "Audi", "Toyota", "Honda", "Mercedes-Benz",
            "Ford", "Hyundai", "Kia", "Nissan", "Volkswagen"
    };

    public List<Car> readCars(int length) {
        if (length <= 0) {
            return new ArrayList<>();
        }

        List<Car> cars = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            cars.add(generateRandomCar());
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
            throw new IllegalStateException(
                    String.format("Сгенерированы некорректные данные автомобиля: %s, %d г., %d л.с.",
                            model, year, power)
            );
        }

        return new Car.Builder()
                .setModel(model)
                .setYear(year)
                .setPower(power)
                .build();
    }
}

