package output;

import model.Car;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResultFileWriter {

    public void appendCars(
            String fileName,
            List<Car> cars
    ) throws IOException {
        try (
                FileWriter fileWriter =
                        new FileWriter(fileName, true);

                PrintWriter writer =
                        new PrintWriter(fileWriter)
        ) {
            writer.println("=== Sorting result ===");

            for (Car car : cars) {
                writer.println(car);
            }

            writer.println();
        }
    }
}