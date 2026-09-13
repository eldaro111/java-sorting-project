package inputting;

import model.Car;
import validation.CarValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarFile {

    /*
        Пример данных:
        BMW;2020;250
        Audi;2018;180
        Toyota;2022;150
        Honda;2019;140
    */

    public Car[] readCars(String fileName, int expectedLength)
            throws IOException {

        List<Car> cars = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                Car car = parseCar(line, lineNumber);

                if (car != null) {
                    cars.add(car);
                } else {
                    throw new IOException(
                            "Некорректные данные в строке "
                                    + lineNumber
                    );
                }
            }
        }

        if (cars.size() != expectedLength) {
            throw new IOException(
                    "В файле должно быть "
                            + expectedLength
                            + " автомобилей, найдено: "
                            + cars.size()
            );
        }

        return cars.toArray(new Car[0]);
    }

    private Car parseCar(String line, int lineNumber) {
        String[] parts = line.split(";");

        if (parts.length != 3) {
            return null;
        }

        String model = parts[0].trim();

        if (!isInteger(parts[1]) || !isInteger(parts[2])) {
            return null;
        }

        int year = Integer.parseInt(parts[1].trim());
        int power = Integer.parseInt(parts[2].trim());

        if (!CarValidator.isValid(model, year, power)) {
            return null;
        }

        return new Car.Builder()
                .setModel(model)
                .setYear(year)
                .setPower(power)
                .build();
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}