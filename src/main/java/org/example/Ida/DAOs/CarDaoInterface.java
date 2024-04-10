package org.example.Ida.DAOs;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import org.example.Ida.Exception.DaoException;
import org.example.Ida.DTOs.CarClass;
import org.example.Ida.DAOs.JsonConverter;
public interface CarDaoInterface {
    public List<CarClass> findAllCars() throws DaoException;
    public CarClass findCarById(int id) throws DaoException;
    public CarClass insertCar(String model, String brand, String colour, int year, int price) throws DaoException;
    public void deleteCarById(int id) throws DaoException;
    public void updateCar(int id, CarClass car) throws DaoException;

    public List<CarClass> findCarsUsingFilter(Comparator<CarClass> carComparator) throws SQLException;

}
