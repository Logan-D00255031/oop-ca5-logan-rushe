package org.example.Ida.BusinessObjects;

import org.example.Ida.DTOs.Car;
import org.example.Ida.DAOs.MySqlCarDao;
import org.example.Ida.Exception.DaoException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {
    MySqlCarDao ICarDao = new MySqlCarDao();

    @Test
    public void insertTest1() throws DaoException {
        assertEquals(1, ICarDao.insertCar("model", "brand", "colour", 2000, 10000));
    }

    @Test
    public void insertTest2() throws DaoException {
        assertEquals(0, ICarDao.insertCar("model", "brand", "red", 2001, 11000));
    }
}