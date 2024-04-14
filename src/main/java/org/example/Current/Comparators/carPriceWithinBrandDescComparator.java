package org.example.Current.Comparators;

import org.example.Current.DTOs.Car;

import java.util.Comparator;

public class carPriceWithinBrandDescComparator implements Comparator<Car> {
    @Override
    public int compare(Car c1, Car c2) {
        int a;
        if(c1.getBrand().equals(c2.getBrand()))
            a = Integer.compare(c1.getPrice(), c2.getPrice());
        else
            a = c1.getBrand().compareTo(c2.getBrand());
        return a * -1;
    }
}
