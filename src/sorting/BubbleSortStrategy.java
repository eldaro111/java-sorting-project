package sorting;

import model.Car;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public void sort(Car[] cars, SortField field) {
        for (int i = 0; i < cars.length - 1; i++) {
            for (int j = 0; j < cars.length - i - 1; j++) {

                if (compare(cars[j], cars[j + 1], field) > 0) {
                    Car temp = cars[j];
                    cars[j] = cars[j + 1];
                    cars[j + 1] = temp;
                }
            }
        }
    }

    private int compare(Car first, Car second, SortField field) {
        return switch (field) {
            case POWER ->
                    Integer.compare(
                            first.getPower(),
                            second.getPower()
                    );

            case MODEL ->
                    first.getModel()
                            .compareToIgnoreCase(second.getModel());

            case YEAR ->
                    Integer.compare(
                            first.getYear(),
                            second.getYear()
                    );
        };
    }
}