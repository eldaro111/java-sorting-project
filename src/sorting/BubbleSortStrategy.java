package sorting;

import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> items, Comparator<T> comparator) {
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {
                if (comparator.compare(items[j], items[j + 1]) > 0) {
                    T temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                }
            }
        }
    }
}