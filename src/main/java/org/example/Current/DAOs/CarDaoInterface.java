package org.example.Current.DAOs;

import org.example.Current.DTOs.Car;
import java.sql.SQLException;
import java.util.List;

public interface CarDaoInterface {
    List<Car> findAllCars() throws SQLException;
    void deleteCarById(int id) throws SQLException;
    /**
     * Insert a given Car into the car table
     * @param car The car object to be inserted
     * @return The inserted Car if successful, or the Car will be NULL if it's already contained within the table
     * @throws SQLException if any SQL statements fail
     */
    Car  insertCar(Car car) throws SQLException;
    Car findCarById(int id) throws SQLException;
}
