package org.example.Current.Comparators;

import org.example.Current.DTOs.Car;

import java.util.Comparator;

public class carModelWithinBrandComparator implements Comparator<Car> {
    @Override
    public int compare(Car c1, Car c2) {
        if(c1.getBrand().equals(c2.getBrand()))
            return c1.getModel().compareTo(c2.getModel());
        else
            return c1.getBrand().compareTo(c2.getBrand());
    }
}
