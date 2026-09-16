package test;

import app.Application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class IntegrationDemoTests {

    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        System.out.println(
                "=== ИНТЕГРАЦИОННЫЕ И ДЕМОНСТРАЦИОННЫЕ ТЕСТЫ ==="
        );

        testRandomPowerSorting();
        testManualModelSorting();
        testFileYearSorting();
        testFileAllFieldsSorting();
        testSeveralOperations();
        testCorrectExit();

        testIncorrectMenuChoice();
        testIncorrectCollectionSize();
        testInvalidPower();
        testEmptyModel();
        testIncorrectYear();
        testCorruptedFile();
        testNonExistentFile();

        System.out.println();
        System.out.println("=== РЕЗУЛЬТАТ ===");
        System.out.println("Успешно: " + passed);
        System.out.println("Ошибок: " + failed);

        if (failed > 0) {
            throw new AssertionError(
                    "Некоторые интеграционные тесты не пройдены."
            );
        }
    }

    private static void testRandomPowerSorting() {
        String input = """
                1
                5
                2
                1
                1
                0
                """;

        String output = runApplication(input);

        check(
                output.contains(
                        "=== Коллекция до сортировки ==="
                )
                        && output.contains(
                        "=== Коллекция после сортировки ==="
                )
                        && output.contains(
                        "Программа завершена."
                ),
                "Positive: Random -> Bubble -> power"
        );
    }

    private static void testManualModelSorting() {
        String input = """
                1
                3
                1
                1
                2
                Toyota
                2020
                150
                Audi
                2019
                180
                BMW
                2021
                200
                0
                """;

        String output = runApplication(input);

        String sortedOutput = getSortedOutput(output);

        int audiIndex = sortedOutput.indexOf(
                "Car{model='Audi'"
        );

        int bmwIndex = sortedOutput.indexOf(
                "Car{model='BMW'"
        );

        int toyotaIndex = sortedOutput.indexOf(
                "Car{model='Toyota'"
        );

        check(
                audiIndex >= 0
                        && bmwIndex > audiIndex
                        && toyotaIndex > bmwIndex,
                "Positive: Manual -> Bubble -> model"
        );
    }

    private static void testFileYearSorting() {
        Path file = null;

        try {
            file = createTempFile("""
                    BMW;2022;250
                    Audi;2018;180
                    Toyota;2020;150
                    """);

            String input = """
                    1
                    3
                    3
                    2
                    3
                    %s
                    0
                    """.formatted(
                    file.toAbsolutePath()
            );

            String output = runApplication(input);
            String sortedOutput = getSortedOutput(output);

            int year2018 = sortedOutput.indexOf(
                    "year=2018"
            );

            int year2020 = sortedOutput.indexOf(
                    "year=2020"
            );

            int year2022 = sortedOutput.indexOf(
                    "year=2022"
            );

            check(
                    year2018 >= 0
                            && year2020 > year2018
                            && year2022 > year2020,
                    "Positive: File -> Selection -> year"
            );

        } catch (IOException e) {
            fail(
                    "Positive: File -> Selection -> year"
            );
        } finally {
            deleteFile(file);
        }
    }

    private static void testFileAllFieldsSorting() {
        Path file = null;

        try {
            file = createTempFile("""
                    BMW;2020;200
                    Audi;2022;150
                    Toyota;2019;200
                    BMW;2018;200
                    Audi;2020;150
                    """);

            String input = """
                    1
                    5
                    3
                    2
                    4
                    %s
                    0
                    """.formatted(
                    file.toAbsolutePath()
            );

            String output = runApplication(input);
            String sortedOutput = getSortedOutput(output);

            String audi2020 =
                    "Car{model='Audi', year=2020, power=150}";

            String audi2022 =
                    "Car{model='Audi', year=2022, power=150}";

            String bmw2018 =
                    "Car{model='BMW', year=2018, power=200}";

            String bmw2020 =
                    "Car{model='BMW', year=2020, power=200}";

            String toyota2019 =
                    "Car{model='Toyota', year=2019, power=200}";

            int first = sortedOutput.indexOf(audi2020);
            int second = sortedOutput.indexOf(audi2022);
            int third = sortedOutput.indexOf(bmw2018);
            int fourth = sortedOutput.indexOf(bmw2020);
            int fifth = sortedOutput.indexOf(toyota2019);

            check(
                    first >= 0
                            && second > first
                            && third > second
                            && fourth > third
                            && fifth > fourth,
                    "Positive: File -> Selection -> all fields"
            );

        } catch (IOException e) {
            fail(
                    "Positive: File -> Selection -> all fields"
            );
        } finally {
            deleteFile(file);
        }
    }

    private static void testSeveralOperations() {
        String input = """
                1
                2
                2
                1
                1
                1
                2
                2
                2
                3
                0
                """;

        String output = runApplication(input);

        int sortingCount = countOccurrences(
                output,
                "=== Коллекция после сортировки ==="
        );

        check(
                sortingCount == 2,
                "Positive: несколько операций подряд"
        );
    }

    private static void testCorrectExit() {
        String output = runApplication(
                "0\n"
        );

        check(
                output.contains(
                        "Программа завершена."
                ),
                "Positive: корректный Exit"
        );
    }

    private static void testIncorrectMenuChoice() {
        String input = """
                9
                abc
                0
                """;

        String output = runApplication(input);

        check(
                output.contains(
                        "Ошибка: выберите значение от 0 до 1."
                )
                        && output.contains(
                        "Ошибка: необходимо ввести целое число."
                )
                        && output.contains(
                        "Программа завершена."
                ),
                "Negative: неправильный пункт меню"
        );
    }

    private static void testIncorrectCollectionSize() {
        String input = """
                1
                0
                -5
                2
                2
                1
                1
                0
                """;

        String output = runApplication(input);

        check(
                countOccurrences(
                        output,
                        "Ошибка: размер коллекции должен быть больше нуля."
                ) == 2
                        && output.contains(
                        "=== Коллекция после сортировки ==="
                ),
                "Negative: размер <= 0"
        );
    }

    private static void testInvalidPower() {
        String input = """
                1
                1
                1
                1
                1
                BMW
                2020
                0
                BMW
                2020
                250
                0
                """;

        String output = runApplication(input);

        check(
                output.contains(
                        "Ошибка: данные автомобиля некорректны."
                )
                        && output.contains(
                        "power=250"
                ),
                "Negative: некорректная мощность"
        );
    }

    private static void testEmptyModel() {
        String input =
                "1\n"
                        + "1\n"
                        + "1\n"
                        + "1\n"
                        + "2\n"
                        + "\n"
                        + "2020\n"
                        + "150\n"
                        + "Audi\n"
                        + "2020\n"
                        + "150\n"
                        + "0\n";

        String output = runApplication(input);

        check(
                output.contains(
                        "Ошибка: данные автомобиля некорректны."
                )
                        && output.contains(
                        "Car{model='Audi'"
                ),
                "Negative: пустая модель"
        );
    }

    private static void testIncorrectYear() {
        String input = """
                1
                1
                1
                2
                3
                BMW
                1885
                150
                BMW
                2020
                150
                0
                """;

        String output = runApplication(input);

        check(
                output.contains(
                        "Ошибка: данные автомобиля некорректны."
                )
                        && output.contains(
                        "year=2020"
                ),
                "Negative: плохой год"
        );
    }

    private static void testCorruptedFile() {
        Path file = null;

        try {
            file = createTempFile(
                    "BMW;2020;\n"
            );

            String input = """
                    1
                    1
                    3
                    1
                    1
                    %s
                    0
                    """.formatted(
                    file.toAbsolutePath()
            );

            String output = runApplication(input);

            check(
                    output.contains(
                            "Ошибка: Некорректные данные в строке 1"
                    )
                            && output.contains(
                            "Программа завершена."
                    ),
                    "Negative: битый файл"
            );

        } catch (IOException e) {
            fail(
                    "Negative: битый файл"
            );
        } finally {
            deleteFile(file);
        }
    }

    private static void testNonExistentFile() {
        String input = """
                1
                1
                3
                1
                1
                file_that_definitely_does_not_exist_12345.txt
                0
                """;

        String output = runApplication(input);

        check(
                output.contains(
                        "Ошибка:"
                )
                        && output.contains(
                        "Программа завершена."
                ),
                "Negative: несуществующий файл"
        );
    }

    private static String runApplication(
            String input
    ) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;

        ByteArrayInputStream testInput =
                new ByteArrayInputStream(
                        input.getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        ByteArrayOutputStream outputBuffer =
                new ByteArrayOutputStream();

        try {
            System.setIn(testInput);

            System.setOut(
                    new PrintStream(
                            outputBuffer,
                            true,
                            StandardCharsets.UTF_8
                    )
            );

            Application application =
                    new Application();

            application.run();

            return outputBuffer.toString(
                    StandardCharsets.UTF_8
            );

        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }

    private static String getSortedOutput(
            String output
    ) {
        String marker =
                "=== Коллекция после сортировки ===";

        int markerIndex =
                output.indexOf(marker);

        if (markerIndex < 0) {
            return "";
        }

        return output.substring(
                markerIndex
        );
    }

    private static Path createTempFile(
            String content
    ) throws IOException {
        Path file = Files.createTempFile(
                "cars_demo_",
                ".txt"
        );

        Files.writeString(
                file,
                content,
                StandardCharsets.UTF_8
        );

        return file;
    }

    private static void deleteFile(
            Path file
    ) {
        if (file == null) {
            return;
        }

        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
        }
    }

    private static int countOccurrences(
            String text,
            String fragment
    ) {
        int count = 0;
        int index = 0;

        while (
                (index = text.indexOf(
                        fragment,
                        index
                )) >= 0
        ) {
            count++;
            index += fragment.length();
        }

        return count;
    }

    private static void check(
            boolean condition,
            String testName
    ) {
        if (condition) {
            passed++;

            System.out.println(
                    "[OK] " + testName
            );
        } else {
            fail(testName);
        }
    }

    private static void fail(
            String testName
    ) {
        failed++;

        System.out.println(
                "[FAIL] " + testName
        );
    }
}