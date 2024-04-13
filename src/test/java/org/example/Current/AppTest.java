package org.example.Current;

import org.example.Current.BusinessObjects.JsonConverter;
import org.example.Current.DAOs.MySqlCarDao;
import org.example.Current.DTOs.Car;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AppTest {
    MySqlCarDao ICarDao = new MySqlCarDao();

    public static void main(String[] args) {
        //ResetCarTable resetCarTable = new ResetCarTable();
        //resetCarTable.resetTable();
    }

    // Change id to the id that it will be when added to the database for the test to succeed
    /* TODO
        Create a MySqlCarDao that has a function to reset the database to remove manually changing tests
     */
    @Test
    public void insertTest1() throws SQLException {
        assertEquals(new Car(16, "model", "brand", "colour", 2000, 10000), ICarDao.insertCar(new Car(1, "model", "brand", "colour", 2000, 10000)));
    }

    @Test
    public void insertTest2() throws SQLException {
        assertEquals(new Car(17, "model", "brand", "red", 2001, 11000), ICarDao.insertCar(new Car(1, "model", "brand", "red", 2001, 11000)));
    }

    @Test
    public void insertTest3() throws SQLException {
        assertNull(ICarDao.insertCar(new Car(1, "model", "brand", "red", 2001, 11000)));
    }

    @Test
    public void carToJSONAndBack() {
        JsonConverter jsonConverter = new JsonConverter();
        Car car1 = new Car(1, "model", "brand", "red", 2001, 11000);
        String carJSON = jsonConverter.carObjectToJson(car1);
        Car car2 = jsonConverter.fromJson(carJSON);
        assertEquals(car1, car2);
    }
}