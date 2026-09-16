package sorting;

import java.util.Comparator;
import java.util.List;

public interface SortStrategy<T> {

    void sort(
            List<T> items,
            Comparator<T> comparator
    );
}