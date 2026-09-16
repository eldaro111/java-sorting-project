package sorting;

import java.util.Comparator;
import java.util.List;

public class SelectionSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(
            List<T> items,
            Comparator<T> comparator
    ) {
        for (int i = 0; i < items.size() - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < items.size(); j++) {
                if (comparator.compare(
                        items.get(j),
                        items.get(minIndex)
                ) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = items.get(i);

                items.set(
                        i,
                        items.get(minIndex)
                );

                items.set(
                        minIndex,
                        temp
                );
            }
        }
    }
}