package org.example.Current.BusinessObjects;

import org.example.Current.Comparators.carYearComparatorDesc;
import org.example.Current.DAOs.CarDaoInterface;
import org.example.Current.DAOs.MySqlCarDao;
import org.example.Current.DTOs.Car;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;


public class App {
    public static void main(String[] args) {
        CarDaoInterface ICarDao = new MySqlCarDao();

        try {
            System.out.println("\nCall findAllUser()");
            List<Car> cars = ICarDao.findAllCars();

            if (cars.isEmpty()) {
                System.out.println("There are no cars in the database");
            } else {
                for (Car car : cars) {
                    System.out.println(car.toString());
                }
            }

            System.out.println("\nCall insertCar()");
            //  For now the Car entity will be returned as NULL if it already exists in the database
            Car newCar = ICarDao.insertCar(new Car(1,"Huracan", "Lamborghini", "Silver", 2023, 250000));
            if(newCar != null){
                System.out.println("New entity added: " + newCar);
            } else {
                System.out.println("Entity was not added. Entity Already exists within the database");
            }

            /* TODO
                Consider returning int to determine if the delete was successful
             */
            System.out.println("\nCall deleteById() where id = 14");
            ICarDao.deleteCarById(14);

            System.out.println("\nCall findCarById()");
            Car car = ICarDao.findCarById(2);
            if(car != null){ // null is returned if in is not valid
                System.out.println("Car found: " + car);
            } else {
                System.out.println("Car with this id not found in database");
            }

            System.out.println("\nCall findCarsUsingFilter()");
            List<Car> sortedCars = ICarDao.findCarsUsingFilter((c1, c2) -> Integer.compare(c1.getProductionYear(), c2.getProductionYear()));

            if (sortedCars.isEmpty()) {
                System.out.println("There are no cars in the database");
            } else {
                for (Car sortedcar : sortedCars) {
                    System.out.println(sortedcar.toString());
                }
            }

            System.out.println("\nCall findCarsUsingFilter(carYearComparatorDesc)");
            sortedCars = ICarDao.findCarsUsingFilter(new carYearComparatorDesc());

            if (sortedCars.isEmpty()) {
                System.out.println("There are no cars in the database");
            } else {
                for (Car sortedcar : sortedCars) {
                    System.out.println(sortedcar.toString());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


/* TODO
    Feature 1 - Dominik - done
    Feature 2 - Dominik - done
    Feature 3 -   Ida   - done
    Feature 4 -  Logan  - done
    Feature 5 -   Ida   - done
    Feature 6 -  Logan  - done
 */