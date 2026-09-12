package sorting;

import model.Car;

public class MergeSortStrategy implements SortStrategy {

    @Override
    public void sort(Car[] cars, SortField field) {
        if (cars.length < 2) {
            return;
        }

        Car[] temp = new Car[cars.length];

        mergeSort(cars, temp, 0, cars.length - 1, field);
    }

    private void mergeSort(
            Car[] cars,
            Car[] temp,
            int left,
            int right,
            SortField field) {

        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(cars, temp, left, middle, field);
        mergeSort(cars, temp, middle + 1, right, field);

        merge(cars, temp, left, middle, right, field);
    }

    private void merge(
            Car[] cars,
            Car[] temp,
            int left,
            int middle,
            int right,
            SortField field) {

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {

            if (compare(cars[i], cars[j], field) <= 0) {
                temp[k++] = cars[i++];
            } else {
                temp[k++] = cars[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = cars[i++];
        }

        while (j <= right) {
            temp[k++] = cars[j++];
        }

        for (i = left; i <= right; i++) {
            cars[i] = temp[i];
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