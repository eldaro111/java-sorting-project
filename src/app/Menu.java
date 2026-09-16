package app;

import model.Car;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMainMenu() {
        System.out.println("""
                
                === Сортировка автомобилей ===
                1. Создать и отсортировать коллекцию
                0. Выход
                """);
    }

    public void showInputMethodMenu() {
        System.out.println("""
                
                === Способ заполнения коллекции ===
                1. Вручную
                2. Случайными значениями
                3. Из файла
                """);
    }

    public void showSortingAlgorithmMenu() {
        System.out.println("""
                
                === Алгоритм сортировки ===
                1. Bubble Sort
                2. Selection Sort
                """);
    }

    public void showSortingFieldMenu() {
        System.out.println("""
                
                === Поле сортировки ===
                1. Мощность
                2. Модель
                3. Год производства
                4. Все поля
                """);
    }

    public void showSaveResultMenu() {
        System.out.println("""
                
                === Сохранение результата ===
                1. Сохранить результат в файл
                0. Не сохранять
                """);
    }

    public int readMenuChoice(int min, int max) {
        while (true) {
            System.out.print("Выберите действие: ");

            if (!scanner.hasNextInt()) {
                System.out.println(
                        "Ошибка: необходимо ввести целое число."
                );

                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < min || choice > max) {
                System.out.println(
                        "Ошибка: выберите значение от "
                                + min
                                + " до "
                                + max
                                + "."
                );

                continue;
            }

            return choice;
        }
    }

    public int readCollectionSize() {
        while (true) {
            System.out.print(
                    "Введите размер коллекции: "
            );

            if (!scanner.hasNextInt()) {
                System.out.println(
                        "Ошибка: необходимо ввести целое число."
                );

                scanner.nextLine();
                continue;
            }

            int size = scanner.nextInt();
            scanner.nextLine();

            if (size <= 0) {
                System.out.println(
                        "Ошибка: размер коллекции должен быть больше нуля."
                );

                continue;
            }

            return size;
        }
    }

    public String readFileName() {
        while (true) {
            System.out.print(
                    "Введите путь к файлу: "
            );

            String fileName = scanner.nextLine().trim();

            if (fileName.isEmpty()) {
                System.out.println(
                        "Ошибка: путь к файлу не может быть пустым."
                );

                continue;
            }

            return fileName;
        }
    }

    public String readOutputFileName() {
        while (true) {
            System.out.print(
                    "Введите путь к файлу для сохранения результата: "
            );

            String fileName = scanner.nextLine().trim();

            if (fileName.isEmpty()) {
                System.out.println(
                        "Ошибка: путь к файлу не может быть пустым."
                );

                continue;
            }

            return fileName;
        }
    }

    public void showConfiguration(
            int collectionSize,
            int inputMethod,
            int sortingAlgorithm,
            int sortingField
    ) {
        System.out.println();
        System.out.println(
                "=== Выбранные параметры ==="
        );

        System.out.println(
                "Размер коллекции: "
                        + collectionSize
        );

        System.out.println(
                "Способ заполнения: "
                        + getInputMethodName(inputMethod)
        );

        System.out.println(
                "Алгоритм сортировки: "
                        + getSortingAlgorithmName(sortingAlgorithm)
        );

        System.out.println(
                "Поле сортировки: "
                        + getSortingFieldName(sortingField)
        );
    }

    public void showCars(
            String title,
            List<Car> cars
    ) {
        System.out.println();
        System.out.println(title);

        for (Car car : cars) {
            System.out.println(car);
        }
    }

    public void showSaveSuccess(
            String fileName
    ) {
        System.out.println(
                "Результат сохранён в файл: "
                        + fileName
        );
    }

    public void showError(String message) {
        System.out.println(
                "Ошибка: " + message
        );
    }

    public void showExitMessage() {
        System.out.println(
                "Программа завершена."
        );
    }

    private String getInputMethodName(
            int choice
    ) {
        return switch (choice) {
            case 1 -> "Вручную";
            case 2 -> "Случайные значения";
            case 3 -> "Из файла";
            default -> "Неизвестно";
        };
    }

    private String getSortingAlgorithmName(
            int choice
    ) {
        return switch (choice) {
            case 1 -> "Bubble Sort";
            case 2 -> "Selection Sort";
            default -> "Неизвестно";
        };
    }

    private String getSortingFieldName(
            int choice
    ) {
        return switch (choice) {
            case 1 -> "Мощность";
            case 2 -> "Модель";
            case 3 -> "Год производства";
            case 4 -> "Все поля";
            default -> "Неизвестно";
        };
    }
}