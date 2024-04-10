package org.example.Dominik.DAOs;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import org.example.Dominik.Exception.DaoException;
import org.example.Dominik.DTOs.CarClass;
import org.example.Dominik.DAOs.JsonConverter;
public interface CarDaoInterface {
    public List<CarClass> findAllCars() throws DaoException;
    public CarClass findCarById(int id) throws DaoException;
    public CarClass insertCar(String model, String brand, String colour, int year, int price) throws DaoException;
    public void deleteCarById(int id) throws DaoException;

    public List<CarClass> findCarsUsingFilter(Comparator<CarClass> carComparator) throws SQLException;

}
