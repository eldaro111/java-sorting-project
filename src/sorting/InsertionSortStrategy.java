package sorting;

import model.Car;

public class InsertionSortStrategy implements SortStrategy {

    @Override
    public void sort(Car[] cars, SortField field) {
        for (int i = 1; i < cars.length; i++) {
            Car current = cars[i];
            int j = i - 1;

            while (j >= 0 &&
                    compare(cars[j], current, field) > 0) {

                cars[j + 1] = cars[j];
                j--;
            }

            cars[j + 1] = current;
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