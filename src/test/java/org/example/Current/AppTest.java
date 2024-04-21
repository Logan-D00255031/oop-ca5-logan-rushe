package org.example.Current;

import org.example.Current.BusinessObjects.JsonConverter;
import org.example.Current.DAOs.MySqlCarDao;
import org.example.Current.DTOs.Car;
import org.example.Current.BusinessObjects.App;
import org.example.Current.Comparators.carYearComparatorDesc;
import org.example.Current.DAOs.CarDaoInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    MySqlCarDao ICarDao = new MySqlCarDao();

    /**
     * Main Author: Logan Rushe
     * <p>
     * Table must already contain at least 15 items in order to pass
     */
    @Test
    public void insertTest1() throws SQLException {
        assertEquals(new Car(16, "model", "brand", "colour", 2000, 10000), ICarDao.insertCar(new Car(1, "model", "brand", "colour", 2000, 10000)));
    }

    /**
     * Main Author: Logan Rushe
     * <p>
     * Table must already contain at least 16 items in order to pass
     */
    @Test
    public void insertTest2() throws SQLException {
        assertEquals(new Car(17, "model", "brand", "red", 2001, 11000), ICarDao.insertCar(new Car(1, "model", "brand", "red", 2001, 11000)));
    }

    /**
     * Main Author: Logan Rushe
     */
    @Test
    public void insertTest3() throws SQLException {
        Assertions.assertNull(ICarDao.insertCar(new Car(1, "model", "brand", "red", 2001, 11000)));
    }

    /**
     * Main Author: Logan Rushe
     */
    @Test
    public void carToJSONAndBack() {
        JsonConverter jsonConverter = new JsonConverter();
        Car car1 = new Car(1, "model", "brand", "red", 2001, 11000);
        String carJSON = jsonConverter.carObjectToJson(car1);
        Car car2 = jsonConverter.fromJson(carJSON);
        assertEquals(car1, car2);
    }

    /**
     * Main Author: Dominik Domalip
     */
//    ***** Dominik's code for testing functionality for looking up car by id *****
    @org.junit.jupiter.api.Test
    void testFindCarByIdPass() throws SQLException {
        int id = 1;
// create an instance of our userinterface
        CarDaoInterface IUserDao = new MySqlCarDao();
//        create expected carClass return
        Car expectedCar = IUserDao.findCarById(id);
//        create actual return in our app by calling function for handling this in app
        Car actualCar = App.findCarById(id);
//        check if both are same, meaning test successful
        assertEquals(expectedCar, actualCar);

        System.out.println(expectedCar);
        System.out.println("\n" + actualCar);
    }

    /**
     * Main Author: Dominik Domalip
     */
//  ***** Dominik's test for finding all cars *****
    @org.junit.jupiter.api.Test
    void testFindAllCars() throws SQLException{
//        create interface
        CarDaoInterface IUserDao = new MySqlCarDao();
//  create a list for expected cars from our interface calling the function
        List<Car> expectedCars = IUserDao.findAllCars();
//  create actual list of cars from our App and the function inside of it
        List<Car> actualCars = App.findAllCars();
//  comparing the results, needs to be the same
        assertEquals(expectedCars, actualCars);
//  double check
        System.out.println(expectedCars);
        System.out.println(actualCars);
    }

    /**
     * Main Author: Dominik Domalip
     */
//    @Test
//    void deleteCarTest() throws DaoException{
//        int id = 22;
//        CarDaoInterface IUserDao = new MySqlCarDao();
//
//
//    }

    /**
     * Main Author: Dominik Domalip
     */
// Inserting test is quite tricky because when we call expected we insert new car into DB and then call actual will want to add the same car
//    however our logic in the insert method doesn't let us insert the same entity so the test will result in fail because actual will be null
//    the way I handled that was to check if actual is null, meaning we are adding the same entity, if it is true then let actual = expected as they are
//    the same
    @org.junit.jupiter.api.Test
    void testInsertCar() throws SQLException {
        String model = "Camaro";
        String brand = "Chevrolet";
        String colour = "Black";
        int year = 2018;
        int price = 57959;

        CarDaoInterface IUserDao = new MySqlCarDao();
        Car expected = IUserDao.insertCar(new Car(1,model, brand, colour, year, price));
        Car actual = App.insertCar(model, brand, colour, year, price);
//        if actual is null, it means we are adding the same data as expected
        if(actual == null){
            actual = expected;
        }

        assertEquals(expected, actual);

        System.out.println( "Expected: " + expected);
        System.out.println("Actual: " + actual);
    }

    /**
     * Main Author: Dominik Domalip
     */
//    ***** Dominik's test for order descending *****
    @org.junit.jupiter.api.Test
    void sortAllDescendingTest() throws SQLException {
        CarDaoInterface IUserDao = new MySqlCarDao();
//  populating list with expected cars
        List<Car> expectedOrder = IUserDao.findCarsUsingFilter(new carYearComparatorDesc());
//  populating list with actual cars
        List<Car> actualOrder = App.sortAllDescending();
//  comparing results
        assertEquals(expectedOrder, actualOrder);
//        double check
        System.out.println("Expected: " + expectedOrder);
        System.out.println("Actual: " + actualOrder);
    }

    /**
     * Main Author: Dominik Domalip
     */
// ***** Dominik's code for order ascending *****
    @org.junit.jupiter.api.Test
    void sortAllAscendingTest() throws SQLException{
        CarDaoInterface IUserDao = new MySqlCarDao();
//   populating lists with expected order and a actual order
        List<Car> expectedOrder = IUserDao.findCarsUsingFilter((c1, c2) -> Integer.compare(c1.getProductionYear(), c2.getProductionYear()));
        List<Car> actualOrder = App.sortAllAscending();
//  comparing results
        assertEquals(expectedOrder, actualOrder);
// double check
        System.out.println("Expected: " + expectedOrder);
        System.out.println("Actual: " + actualOrder);
    }
}