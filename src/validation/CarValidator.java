package validation;

public class CarValidator {

    private static final int MIN_YEAR = 1886;
    private static final int MAX_YEAR = 2027;

    private static final int MIN_POWER = 1;
    private static final int MAX_POWER = 1500;

    public static boolean isValid(String model, int year, int power) {
        if (model == null || model.trim().isEmpty()) {
            return false;
        }
        if (year < MIN_YEAR || year > MAX_YEAR) {
            return false;
        }
        if (power < MIN_POWER || power > MAX_POWER) {
            return false;
        }
        return true;
    }
}
