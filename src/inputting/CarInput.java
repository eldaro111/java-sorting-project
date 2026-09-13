package inputting;

import model.Car;
import validation.CarValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarInput {

    private final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public Car[] readCars(int length) throws IOException {
        Car[] cars = new Car[length];

        for (int i = 0; i < length; i++) {
            cars[i] = readCar(i + 1);
        }

        return cars;
    }

    private Car readCar(int number) throws IOException {
        while (true) {
            System.out.println();
            System.out.println("Автомобиль №" + number);

            System.out.print("Модель: ");
            String model = reader.readLine();

            System.out.print("Год выпуска: ");
            String yearText = reader.readLine();

            System.out.print("Мощность: ");
            String powerText = reader.readLine();

            try {
                int year = Integer.parseInt(yearText);
                int power = Integer.parseInt(powerText);

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