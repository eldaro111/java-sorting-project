package app;

public class Application {

    private final Menu menu;

    public Application() {
        menu = new Menu();
    }

    public void run() {
        boolean running = true;

        while (running) {
            menu.showMainMenu();

            int command = menu.readMenuChoice(0, 1);

            switch (command) {
                case 1 -> configureSorting();
                case 0 -> running = false;
            }
        }

        menu.showExitMessage();
    }

    private void configureSorting() {
        int collectionSize = menu.readCollectionSize();
        int inputMethod = chooseInputMethod();
        int sortingAlgorithm = chooseSortingAlgorithm();
        int sortingField = chooseSortingField();

        menu.showConfiguration(
                collectionSize,
                inputMethod,
                sortingAlgorithm,
                sortingField
        );

        menu.showIntegrationPendingMessage();
    }

    private int chooseInputMethod() {
        menu.showInputMethodMenu();
        return menu.readMenuChoice(1, 3);
    }

    private int chooseSortingAlgorithm() {
        menu.showSortingAlgorithmMenu();
        return menu.readMenuChoice(1, 2);
    }

    private int chooseSortingField() {
        menu.showSortingFieldMenu();
        return menu.readMenuChoice(1, 4);
    }
}