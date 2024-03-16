package org.example.Logan;

import java.sql.SQLException;
import java.util.List;

public interface CarDaoInterface {
    int insertCar(Car car) throws  SQLException;

    /**
     * Returns a sorted list of Cars from the database using the Car's natural order
     * @return The sorted Car List
     * @throws SQLException if any SQL statements fail
     */
    List<Car> findCarsUsingNaturalOrder() throws SQLException;

    List<Car> findAllCars() throws SQLException;
}

