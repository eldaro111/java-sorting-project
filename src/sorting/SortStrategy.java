package sorting;

import model.Car;

public interface SortStrategy {
    void sort(Car[] cars, SortField field);
}