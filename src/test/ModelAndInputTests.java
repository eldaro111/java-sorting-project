package test;

import input.CarFileInput;
import input.CarManualInput;
import input.CarRandomInput;
import model.Car;
import validation.CarValidator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class ModelAndInputTests {

    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК РУЧНЫХ ТЕСТОВ ===");

        // --- ПОЗИТИВНЫЕ ТЕСТЫ ---
        testCorrectBuilderAndValidCar();
        testCorrectManualInput();
        testCorrectFileRead();
        testRandomInputCreatesCorrectSize();

        // --- НЕГАТИВНЫЕ ТЕСТЫ ---
        testInvalidPower();
        testEmptyModel();
        testIncorrectYear();
        testCorruptedFile();
        testNonExistentFile();

        System.out.println("\n=== ТЕСТИРОВАНИЕ ЗАВЕРШЕНО ===");
    }

    // ==========================================
    // ПОЗИТИВНЫЕ СЦЕНАРИИ
    // ==========================================

    private static void testCorrectBuilderAndValidCar() {
        Car car = new Car.Builder().setModel("BMW").setYear(2020).setPower(250).build();
        if (car != null && car.getModel().equals("BMW") && car.getYear() == 2020 && car.getPower() == 250) {
            if (CarValidator.isValid(car.getModel(), car.getYear(), car.getPower())) {
                System.out.println("[OK] Позитивный: Корректный Builder и валидный автомобиль");
                return;
            }
        }
        System.out.println("[FAIL] Позитивный: Ошибка сборки или валидации правильного авто");
    }

    private static void testCorrectManualInput() {
        // Имитируем ввод пользователя через строку с переводами строк \n
        String simulatedInput = "Honda\n2019\n140\n";
        Scanner testScanner = new Scanner(simulatedInput);
        CarManualInput manualInput = new CarManualInput(testScanner);

        List<Car> cars = manualInput.readCars(1);
        if (cars.size() == 1 && cars.get(0).getModel().equals("Honda") && cars.get(0).getYear() == 2019) {
            System.out.println("[OK] Позитивный: Корректный manual input");
        } else {
            System.out.println("[FAIL] Позитивный: Ошибка ручного ввода");
        }
    }

    private static void testCorrectFileRead() {
        String testFileName = "test_valid_cars.txt";
        // Создаем временный файл
        try (FileWriter writer = new FileWriter(testFileName)) {
            writer.write("BMW;2020;250\nAudi;2018;180\n");
        } catch (IOException e) {
            System.out.println("[FAIL] Не удалось подготовить файл для теста");
            return;
        }

        try {
            CarFileInput fileInput = new CarFileInput();
            List<Car> cars = fileInput.readCars(testFileName, 2);
            if (cars.size() == 2 && cars.get(0).getModel().equals("BMW")) {
                System.out.println("[OK] Позитивный: Корректный файл");
            } else {
                System.out.println("[FAIL] Позитивный: Файл прочитан неверно");
            }
        } catch (IOException e) {
            System.out.println("[FAIL] Позитивный: Ошибка при чтении корректного файла: " + e.getMessage());
        } finally {
            new File(testFileName).delete(); // Удаляем файл за собой
        }
    }

    private static void testRandomInputCreatesCorrectSize() {
        CarRandomInput randomInput = new CarRandomInput();
        int expectedSize = 5;
        try {
            List<Car> cars = randomInput.readCars(expectedSize);
            if (cars.size() == expectedSize) {
                System.out.println("[OK] Позитивный: random создаёт нужное число объектов");
            } else {
                System.out.println("[FAIL] Позитивный: random создал " + cars.size() + " вместо " + expectedSize);
            }
        } catch (Exception e) {
            System.out.println("[FAIL] Позитивный: random выбросил ошибку: " + e.getMessage());
        }
    }

    // ==========================================
    // НЕГАТИВНЫЕ СЦЕНАРИИ
    // ==========================================

    private static void testInvalidPower() {
        boolean validZero = CarValidator.isValid("Audi", 2018, 0);
        boolean validNegative = CarValidator.isValid("Audi", 2018, -50);

        if (!validZero && !validNegative) {
            System.out.println("[OK] Негативный: power <= 0 отклоняется");
        } else {
            System.out.println("[FAIL] Негативный: Валидатор пропустил отрицательную мощность");
        }
    }

    private static void testEmptyModel() {
        boolean validNull = CarValidator.isValid(null, 2022, 150);
        boolean validEmpty = CarValidator.isValid("", 2022, 150);
        boolean validSpaces = CarValidator.isValid("   ", 2022, 150);

        if (!validNull && !validEmpty && !validSpaces) {
            System.out.println("[OK] Негативный: пустая model отклоняется");
        } else {
            System.out.println("[FAIL] Негативный: Валидатор пропустил пустую модель");
        }
    }

    private static void testIncorrectYear() {
        int currentYear = Year.now().getValue();
        boolean validPast = CarValidator.isValid("Toyota", 1885, 150);
        boolean validFuture = CarValidator.isValid("Toyota", currentYear + 1, 150);

        if (!validPast && !validFuture) {
            System.out.println("[OK] Негативный: некорректный year отклоняется");
        } else {
            System.out.println("[FAIL] Негативный: Валидатор пропустил некорректный год");
        }
    }

    private static void testCorruptedFile() {
        String testFileName = "test_corrupted_cars.txt";
        // Записываем битую строку (не хватает параметров из-за split)
        try (FileWriter writer = new FileWriter(testFileName)) {
            writer.write("BMW;2020;\n");
        } catch (IOException e) {
            return;
        }

        try {
            CarFileInput fileInput = new CarFileInput();
            fileInput.readCars(testFileName, 1);
            System.out.println("[FAIL] Негативный: Класс пропустил битую строку файла");
        } catch (IOException e) {
            System.out.println("[OK] Негативный: битая строка файла вызывает IOException");
        } finally {
            new File(testFileName).delete();
        }
    }

    private static void testNonExistentFile() {
        try {
            CarFileInput fileInput = new CarFileInput();
            fileInput.readCars("file_does_not_exist_12345.txt", 1);
            System.out.println("[FAIL] Негативный: Класс успешно прочитал несуществующий файл?!");
        } catch (IOException e) {
            System.out.println("[OK] Негативный: несуществующий файл вызывает IOException");
        }
    }
}
