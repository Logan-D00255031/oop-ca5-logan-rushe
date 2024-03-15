package org.example.Current;

import java.sql.SQLException;
import java.util.List;

public interface CarDaoInterface {
    List<Car> findAllCars() throws SQLException;

    int insertCar(Car car) throws SQLException;
}
