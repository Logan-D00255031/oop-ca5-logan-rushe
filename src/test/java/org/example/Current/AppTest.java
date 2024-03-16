package org.example.Current;

import org.example.Current.DAOs.MySqlCarDao;
import org.example.Current.DTOs.Car;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AppTest {
    MySqlCarDao ICarDao = new MySqlCarDao();

    @Test
    public void insertTest1() throws SQLException {
        assertEquals(1, ICarDao.insertCar(new Car(1, "model", "brand", "colour", 2000, 10000)));
    }

    @Test
    public void insertTest2() throws SQLException {
        assertEquals(0, ICarDao.insertCar(new Car(1, "model", "brand", "red", 2001, 11000)));
    }
}