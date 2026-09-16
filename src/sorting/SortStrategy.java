package sorting;

import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(T[] items, Comparator<T> comparator);
}
