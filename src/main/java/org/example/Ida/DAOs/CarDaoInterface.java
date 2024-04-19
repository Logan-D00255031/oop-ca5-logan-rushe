package org.example.Ida.DAOs;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import org.example.Ida.Exception.DaoException;
import org.example.Ida.DTOs.Car;

public interface CarDaoInterface {
    public List<Car> findAllCars() throws DaoException;
    public Car findCarById(int id) throws DaoException;
    public Car insertCar(String model, String brand, String colour, int year, int price) throws DaoException;
    public void deleteCarById(int id) throws DaoException;
    public void updateCar(int id, Car car) throws DaoException;

    public List<Car> findCarsUsingFilter(Comparator<Car> carComparator) throws SQLException;

}
