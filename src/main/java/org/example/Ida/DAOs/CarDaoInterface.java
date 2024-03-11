package org.example.Ida.DAOs;
import java.sql.SQLException;
import java.util.List;
import org.example.Ida.Exceptions.DaoException;
import org.example.Ida.DTOs.CarClass;
import org.example.Logan.Car;

import java.util.List;

public interface CarDaoInterface {
    public List<CarClass> findAllCars() throws DaoException;

    public int insertCar(CarClass car) throws SQLException;
}
