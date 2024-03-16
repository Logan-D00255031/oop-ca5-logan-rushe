package org.example.Ida.DAOs;
import java.util.List;
import org.example.Ida.Exceptions.DaoException;
import org.example.Ida.DTOs.CarClass;

import java.util.List;

public interface CarDaoInterface {
    public List<CarClass> findAllCars() throws DaoException;
    public void deleteCarById(int id) throws DaoException;
    public int  insertCar(CarClass car) throws DaoException;
    public CarClass findCarById(int id) throws DaoException;
}
