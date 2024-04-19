package org.example.Ida.Comparators;

import org.example.Ida.DTOs.Car;

import java.util.Comparator;

public class carYearComparatorDes implements Comparator<Car> {
    @Override
    public int compare(Car c1, Car c2) {
        int a = Integer.compare(c1.getProductionYear(), c2.getProductionYear());
        if (a == -1) return 1;
        else if (a == 1) return -1;
        else return 0;
    }
}
// **** Part of logan's code
