package sorting;

import java.util.Comparator;

public class SelectionSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> items, Comparator<T> comparator) {
        for (int i = 0; i < items.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < items.length; j++) {
                if (comparator.compare(items[j], items[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = items[i];
                items[i] = items[minIndex];
                items[minIndex] = temp;
            }
        }
    }
}