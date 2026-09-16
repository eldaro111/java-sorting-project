package sorting;

import java.util.Comparator;
import java.util.List;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(
            List<T> items,
            Comparator<T> comparator
    ) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = 0; j < items.size() - i - 1; j++) {

                if (comparator.compare(
                        items.get(j),
                        items.get(j + 1)
                ) > 0) {
                    T temp = items.get(j);

                    items.set(
                            j,
                            items.get(j + 1)
                    );

                    items.set(
                            j + 1,
                            temp
                    );
                }
            }
        }
    }
}