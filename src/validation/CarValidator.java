package validation;

import java.time.Year;

public class CarValidator {

    private static final int MIN_YEAR = 1886;

    private CarValidator() {
        throw new UnsupportedOperationException("Это утилитный класс и не может создавать объекты");
    }

    public static boolean isValid(String model, int year, int power) {
        return isValidModel(model) && isValidYear(year) && isValidPower(power);
    }

    public static boolean isValidModel(String model) {
        return model != null && !model.trim().isEmpty();
    }

    public static boolean isValidYear(int year) {
        int currentYear = Year.now().getValue();
        return year >= MIN_YEAR && year <= currentYear;
    }

    public static boolean isValidPower(int power) {
        return power > 0;
    }
}
