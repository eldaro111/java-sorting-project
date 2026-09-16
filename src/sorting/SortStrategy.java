package sorting;

import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(List<T> items, Comparator<T> comparator);
}