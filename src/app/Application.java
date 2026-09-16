package app;

import input.CarFileInput;
import input.CarManualInput;
import input.CarRandomInput;
import model.Car;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Application {

    private final Menu menu;
    private final CarManualInput manualInput;
    private final CarRandomInput randomInput;
    private final CarFileInput fileInput;

    public Application() {
        Scanner scanner = new Scanner(System.in);

        menu = new Menu(scanner);
        manualInput = new CarManualInput(scanner);
        randomInput = new CarRandomInput();
        fileInput = new CarFileInput();
    }

    public void run() {
        boolean running = true;

        while (running) {
            menu.showMainMenu();

            int command = menu.readMenuChoice(0, 1);

            switch (command) {
                case 1 -> configureSorting();
                case 0 -> running = false;
            }
        }

        menu.showExitMessage();
    }

    private void configureSorting() {
        int collectionSize = menu.readCollectionSize();

        int inputMethod = chooseInputMethod();

        int sortingAlgorithm = chooseSortingAlgorithm();

        int sortingField = chooseSortingField();

        menu.showConfiguration(
                collectionSize,
                inputMethod,
                sortingAlgorithm,
                sortingField
        );

        List<Car> cars = loadCars(
                collectionSize,
                inputMethod
        );

        if (cars == null) {
            return;
        }

        menu.showCars(
                "=== Полученная коллекция ===",
                cars
        );

        menu.showSortingPendingMessage();
    }

    private List<Car> loadCars(
            int collectionSize,
            int inputMethod
    ) {
        return switch (inputMethod) {
            case 1 -> manualInput.readCars(
                    collectionSize
            );

            case 2 -> randomInput.readCars(
                    collectionSize
            );

            case 3 -> loadCarsFromFile(
                    collectionSize
            );

            default -> throw new IllegalArgumentException(
                    "Неизвестный способ заполнения коллекции."
            );
        };
    }

    private List<Car> loadCarsFromFile(
            int collectionSize
    ) {
        String fileName = menu.readFileName();

        try {
            return fileInput.readCars(
                    fileName,
                    collectionSize
            );
        } catch (IOException | IllegalArgumentException e) {
            menu.showError(
                    e.getMessage()
            );

            return null;
        }
    }

    private int chooseInputMethod() {
        menu.showInputMethodMenu();

        return menu.readMenuChoice(
                1,
                3
        );
    }

    private int chooseSortingAlgorithm() {
        menu.showSortingAlgorithmMenu();

        return menu.readMenuChoice(
                1,
                2
        );
    }

    private int chooseSortingField() {
        menu.showSortingFieldMenu();

        return menu.readMenuChoice(
                1,
                4
        );
    }
}