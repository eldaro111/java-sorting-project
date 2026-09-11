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
                case 1 -> showDevelopmentMessage();
                case 0 -> running = false;
            }
        }

        menu.showExitMessage();
    }

    private void showDevelopmentMessage() {
        System.out.println(
                "Функционал создания и сортировки коллекции находится в разработке."
        );
    }
}