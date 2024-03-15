package org.example.Current;

import java.sql.SQLException;
import java.util.List;


public class App {
    public static void main(String[] args) {
        CarDaoInterface IUserDao = new MySqlCarDao();

        try{
            System.out.println("\nCall findAllCars()");
            List<Car> cars = IUserDao.findAllCars();

            if(cars.isEmpty()){
                System.out.println("There are no cars");
            }else{
                for (Car car: cars){
                    System.out.println(car.toString());
                }
            }

            System.out.println("\nCall insertCar()");
            int code = IUserDao.insertCar(new Car(1, "Civic", "Honda", "Silver", 2010, 25000));
            if(code == 1) {
                System.out.println("Car added successfully");
            } else if (code == 0) {
                System.out.println("Car already exists in table");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}

/* TODO ask dermot to help with the output cause why is there '
    Features 1 - Dominik - done
    Feature 2 - Dominik
    Feature 3 - Ida
    Feature 4 - Logan - done
 */