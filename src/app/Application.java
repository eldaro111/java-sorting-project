package app;

import input.CarFileInput;
import input.CarManualInput;
import input.CarRandomInput;
import model.Car;
import sorting.BubbleSortStrategy;
import sorting.CarComparators;
import sorting.SelectionSortStrategy;
import sorting.Sorter;

import java.io.IOException;
import java.util.Comparator;
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
                "=== Коллекция до сортировки ===",
                cars
        );

        sortCars(
                cars,
                sortingAlgorithm,
                sortingField
        );

        menu.showCars(
                "=== Коллекция после сортировки ===",
                cars
        );
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

    private void sortCars(
            List<Car> cars,
            int sortingAlgorithm,
            int sortingField
    ) {
        Sorter<Car> sorter = createSorter(
                sortingAlgorithm
        );

        Comparator<Car> comparator = createComparator(
                sortingField
        );

        sorter.sort(
                cars,
                comparator
        );
    }

    private Sorter<Car> createSorter(
            int sortingAlgorithm
    ) {
        return switch (sortingAlgorithm) {
            case 1 -> new Sorter<>(
                    new BubbleSortStrategy<>()
            );

            case 2 -> new Sorter<>(
                    new SelectionSortStrategy<>()
            );

            default -> throw new IllegalArgumentException(
                    "Неизвестный алгоритм сортировки."
            );
        };
    }

    private Comparator<Car> createComparator(
            int sortingField
    ) {
        return switch (sortingField) {
            case 1 -> CarComparators.byPower();
            case 2 -> CarComparators.byModel();
            case 3 -> CarComparators.byYear();
            case 4 -> CarComparators.byAllFields();

            default -> throw new IllegalArgumentException(
                    "Неизвестное поле сортировки."
            );
        };
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