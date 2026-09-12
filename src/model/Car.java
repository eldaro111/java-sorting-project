package model;

import java.util.Objects;

public class Car {
    private final int power;
    private final String model;
    private final int year;

    private Car(Builder builder) {
        this.year = builder.year;
        this.power = builder.power;
        this.model = builder.model;
    }

    public static class Builder {
        private int year;
        private int power;
        private String model;

        public Builder setYear(int year) { this.year = year; return this; }
        public Builder setPower(int power) { this.power = power; return this; }
        public Builder setModel(String model) { this.model = model; return this; }
        public Car build() {
            return new Car(this);
        }
    }

    public int getPower() { return power; }
    public String getModel() { return model; }
    public int getYear() { return year; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return power == car.power &&
                year == car.year &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    @Override
    public String toString() {
        return "Car{model='" + model + "', year=" + year + ", power=" + power + "}";
    }
}