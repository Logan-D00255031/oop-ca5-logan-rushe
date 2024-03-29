package org.example.Dominik.BusinessObjects;

import org.example.Dominik.DAOs.CarDaoInterface;
import org.example.Dominik.DAOs.MySqlCarDao;
import org.example.Dominik.DAOs.MySqlDao;
import org.example.Dominik.DTOs.CarClass;
import org.example.Dominik.Exception.DaoException;

import java.util.List;

public class App {
    public static void main(String[] args) throws DaoException {
        CarDaoInterface IUserDao = new MySqlCarDao();
        try {
            System.out.println("\nCall: findAllCars()");
            List<CarClass> cars = IUserDao.findAllCars();

            if(cars.isEmpty()){
                System.out.println("No cars in the system");
            } else {
                for(CarClass car : cars){
                    System.out.println(car.toString());
                }
            }

            System.out.println("\n Call: findCarById()");
            CarClass car = IUserDao.findCarById(2);
            if(car != null){ //null is returned if in is not valid
                System.out.println("Car found: " + car);
            } else {
                System.out.println("Car with this id not found in database");
            }

        } catch (DaoException e){
            e.printStackTrace();
        }

        System.out.println("Calling inserCar(): ");
//       For now no new entity will be created as it already exists because i was testing
        CarClass newCar = IUserDao.insertCar("Huracan", "Lamborghini", "Silver", 2023, 250000);
        if(newCar != null){
            System.out.println("New entity added: " + newCar);
        } else {
            System.out.println("Entity was not added.");
        }

        System.out.println("Deleting an entity by id ");
        IUserDao.deleteCarById(16);


    }


}
