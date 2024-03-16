package org.example.Ida.BusinessObjects;
import org.example.Ida.DAOs.CarDaoInterface;
import org.example.Ida.DAOs.MySqlCarDao;
import org.example.Ida.DAOs.MySqlDao;
import org.example.Ida.DTOs.CarClass;
import org.example.Ida.Exceptions.DaoException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        CarDaoInterface IUserDao = new MySqlCarDao();

        try {
            System.out.println("\nCall findAllUser()");
            List<CarClass> cars = IUserDao.findAllCars();

            if (cars.isEmpty()) {
                System.out.println("There are no cars");
            } else {
                for (CarClass car : cars) {
                    System.out.println(car.toString());
                }
            }
            int code = IUserDao.insertCar(new CarClass(1, "Civic", "Honda", "Silver", 2010, 25000));
            if(code == 1) {
                System.out.println("\nCar added successfully");
            } else if (code == 0) {
                System.out.println("\nCar already exists in table");
                }

            System.out.println("\nCall deleteById() where id = 14");
            IUserDao.deleteCarById(14);

            System.out.println("\n Call: findCarById()");
            CarClass car = IUserDao.findCarById(2);
            if(car != null){ //null is returned if in is not valid
                System.out.println("Car found: " + car);
            } else {
                System.out.println("Car with this id not found in database");
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}


/* TODO ask dermot to help with the output cause why is there '
    Features 1 - Dominik - done
    Feature 2 - Dominik - done
    Feature 3 - Ida - done
    Feature 4 - Logan - done
 */














