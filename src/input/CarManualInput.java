package input;

import model.Car;
import validation.CarValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarManualInput {

    private final Scanner scanner;

    public CarManualInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public List<Car> readCars(int length) {
        if (length <= 0) {
            return new ArrayList<>();
        }

        List<Car> cars = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            cars.add(readCar(i + 1));
        }

        return cars;
    }

    private Car readCar(int number) {
        while (true) {
            System.out.println();
            System.out.println("Автомобиль №" + number);

            System.out.print("Модель: ");
            String model = scanner.nextLine();

            System.out.print("Год выпуска: ");
            String yearText = scanner.nextLine();

            System.out.print("Мощность: ");
            String powerText = scanner.nextLine();

            try {
                int year = Integer.parseInt(yearText.trim());
                int power = Integer.parseInt(powerText.trim());

                if (!CarValidator.isValid(model, year, power)) {
                    System.out.println(
                            "Ошибка: данные автомобиля некорректны."
                    );
                    continue;
                }

                return new Car.Builder()
                        .setModel(model.trim())
                        .setYear(year)
                        .setPower(power)
                        .build();

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ошибка: год и мощность должны быть целыми числами."
                );
            }
        }
    }
}
