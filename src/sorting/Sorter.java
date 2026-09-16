package sorting;

import java.util.Comparator;
import java.util.List;

public class Sorter<T> {

    private SortStrategy<T> strategy;

    public Sorter(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(
            SortStrategy<T> strategy
    ) {
        this.strategy = strategy;
    }

    public void sort(
            List<T> items,
            Comparator<T> comparator
    ) {
        strategy.sort(
                items,
                comparator
        );
    }
}