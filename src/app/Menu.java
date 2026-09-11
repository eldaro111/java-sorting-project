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

    public void showExitMessage() {
        System.out.println("Программа завершена.");
    }
}