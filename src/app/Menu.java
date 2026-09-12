package app;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        System.out.println();
        System.out.println("=== Сортировка автомобилей ===");
        System.out.println("1. Создать и отсортировать коллекцию");
        System.out.println("0. Выход");
    }

    public void showInputMethodMenu() {
        System.out.println();
        System.out.println("=== Способ заполнения коллекции ===");
        System.out.println("1. Вручную");
        System.out.println("2. Случайными значениями");
        System.out.println("3. Из файла");
    }

    public void showSortingAlgorithmMenu() {
        System.out.println();
        System.out.println("=== Алгоритм сортировки ===");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
    }

    public void showSortingFieldMenu() {
        System.out.println();
        System.out.println("=== Поле сортировки ===");
        System.out.println("1. Мощность");
        System.out.println("2. Модель");
        System.out.println("3. Год производства");
        System.out.println("4. Все поля");
    }

    public int readMenuChoice(int min, int max) {
        while (true) {
            System.out.print("Выберите действие: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: необходимо ввести целое число.");
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
            System.out.print("Введите размер коллекции: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: необходимо ввести целое число.");
                scanner.nextLine();
                continue;
            }

            int size = scanner.nextInt();
            scanner.nextLine();

            if (size <= 0) {
                System.out.println("Ошибка: размер коллекции должен быть больше нуля.");
                continue;
            }

            return size;
        }
    }

    public void showConfiguration(
            int collectionSize,
            int inputMethod,
            int sortingAlgorithm,
            int sortingField
    ) {
        System.out.println();
        System.out.println("=== Выбранные параметры ===");
        System.out.println("Размер коллекции: " + collectionSize);
        System.out.println("Способ заполнения: " + getInputMethodName(inputMethod));
        System.out.println("Алгоритм сортировки: "
                + getSortingAlgorithmName(sortingAlgorithm));
        System.out.println("Поле сортировки: " + getSortingFieldName(sortingField));
    }

    public void showIntegrationPendingMessage() {
        System.out.println();
        System.out.println(
                "Параметры приняты. Загрузка и сортировка будут подключены на этапе интеграции."
        );
    }

    public void showExitMessage() {
        System.out.println("Программа завершена.");
    }

    private String getInputMethodName(int choice) {
        return switch (choice) {
            case 1 -> "Вручную";
            case 2 -> "Случайные значения";
            case 3 -> "Из файла";
            default -> "Неизвестно";
        };
    }

    private String getSortingAlgorithmName(int choice) {
        return switch (choice) {
            case 1 -> "Bubble Sort";
            case 2 -> "Selection Sort";
            default -> "Неизвестно";
        };
    }

    private String getSortingFieldName(int choice) {
        return switch (choice) {
            case 1 -> "Мощность";
            case 2 -> "Модель";
            case 3 -> "Год производства";
            case 4 -> "Все поля";
            default -> "Неизвестно";
        };
    }
}