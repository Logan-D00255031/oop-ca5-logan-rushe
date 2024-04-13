package org.example.Current.Comparators;

import org.example.Current.DTOs.Car;

import java.util.Comparator;

public class carYearComparatorDesc implements Comparator<Car> {
    @Override
    public int compare(Car c1, Car c2) {
        int a = Integer.compare(c1.getProductionYear(), c2.getProductionYear());
        return a * -1;
    }
}
