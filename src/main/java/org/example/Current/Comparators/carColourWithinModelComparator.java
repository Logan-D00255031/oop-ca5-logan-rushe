package org.example.Current.Comparators;

import org.example.Current.DTOs.Car;

import java.util.Comparator;

public class carColourWithinModelComparator implements Comparator<Car> {
    @Override
    public int compare(Car c1, Car c2) {
        if(c1.getModel().equals(c2.getModel()))
            return c1.getColour().compareTo(c2.getColour());
        else
            return c1.getModel().compareTo(c2.getModel());
    }
}
