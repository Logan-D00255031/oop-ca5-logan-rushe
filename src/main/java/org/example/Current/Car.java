package org.example.Current;

import java.util.Objects;

public class Car {
    private int id;
    private String model;
    private String brand;
    private String colour;
    private int productionYear;
    private int price;

    public Car(int id, String model, String brand, String colour, int productionYear, int price) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.colour = colour;
        this.productionYear = productionYear;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", colour='" + colour + '\'' +
                ", productionYear=" + productionYear +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && productionYear == car.productionYear && price == car.price && Objects.equals(model, car.model) && Objects.equals(brand, car.brand) && Objects.equals(colour, car.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, brand, colour, productionYear, price);
    }
}
