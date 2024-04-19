package org.example.Ida.BusinessObjects;
import org.example.Ida.Comparators.carYearComparatorDes;
import org.example.Ida.DAOs.CarDaoInterface;
import org.example.Ida.DAOs.JsonConverter;
import org.example.Ida.DAOs.MySqlCarDao;
import org.example.Ida.DTOs.Car;
import org.example.Ida.Exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Main Author: Dominik Domalip
 *  - Creating the menu interface and putting everything in separate functions
 */
// ********* Dominik interface menu ************
public class App {
    public static void main(String[] args) throws DaoException, SQLException {
        String command = "";

        Scanner in = new Scanner(System.in);
        do {
            System.out.println("\n1. > Find all cars inside table\n2. > Find car by id\n3. > Insert car\n4. > Delete car by id\n5. > " +
                    "Sort cars ASC\n6. > Sort cars DESC\n7. > Update car by id\nQuit > Close application");
            command = in.next();
            if(command.equals("1")){
                fincAllCars();
            } else if(command.equals("2")){
               String id = in.next();
                try {
                    findCarById(Integer.parseInt(id));
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
            } else if(command.equals("3")){
                System.out.println("Model: Brand: Colour: Year: Price");
                String model = in.next();
                String brand = in.next();
                String colour = in.next();
                int year = in.nextInt();
                int price = in.nextInt();
                try {
                    insertCar(model, brand, colour, year, price);
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
            } else if(command.equals("4")){
                System.out.println("Enter id: ");
                String id = in.next();
                deleteCar(Integer.parseInt(id));
            } else if(command.equals("5")){
                sortAllAscending();
            } else if(command.equals("6")){
                sortAllDescending();
            } else if(command.equals("7")){
                System.out.println("Enter id: ");
                int id = in.nextInt();
                System.out.println("Model: Brand: Colour: Year: Price");
                String model = in.next();
                String brand = in.next();
                String colour = in.next();
                int year = in.nextInt();
                int price = in.nextInt();
                try {
                    updateCar(id, new Car(id,model,brand,colour,year,price));
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }

            }
            }while(!command.equalsIgnoreCase("quit"));
        }

       public static List<Car> fincAllCars() {
           CarDaoInterface IUserDao = new MySqlCarDao();
           JsonConverter JsonConverter = new JsonConverter();
           List<Car> cars;
           try {
               System.out.println("\n **** Call: findAllCars() ***");
               cars = IUserDao.findAllCars();

               if (cars.isEmpty()) {
                   System.out.println("No cars in the system");
               } else {
                   for (Car car : cars) {
                       System.out.println(car.toString());
                   }
                   String carList = JsonConverter.carListToJson(cars);
                   System.out.println("*** findAllCars() into Json: ***\n" + carList);
//                   From Json to List
                   List<Car> carJson = JsonConverter.jsonToCarList(carList);
                   for(Car car : carJson){
                       System.out.println(car);
                   }
               }
           } catch (DaoException e) {
               throw new RuntimeException(e);
           }
           return cars;
       }

        public static Car findCarById(int id) throws DaoException {
            CarDaoInterface IUserDao = new MySqlCarDao();
            JsonConverter JsonConverter = new JsonConverter();
            System.out.println("\n *** Call: findCarById() ***");
            Car car = IUserDao.findCarById(id);
            String carJson = JsonConverter.carObjectToJson(car);
            if (car != null) { //null is returned if in is not valid
                System.out.println("Car found: " + car);
                System.out.println("Car found Json: " + carJson + "\n");
            } else {
                System.out.println("Car with this id not found in database");
            }
            return car;
        }

        public static Car insertCar(String model, String brand, String colour, int year, int price) throws DaoException {
            CarDaoInterface IUserDao = new MySqlCarDao();
            JsonConverter JsonConverter = new JsonConverter();
            System.out.println("*** Calling insertCar(): ***");
        Car newCar = IUserDao.insertCar(model, brand, colour, year, price);
        if (newCar != null) {
            System.out.println("New entity added: " + newCar);
            String jsonCar = JsonConverter.carObjectToJson(newCar);
            System.out.println("Entity in Json string:\n" + jsonCar);

        } else {
            System.out.println("Entity was not added.");
             }
            return newCar;
        }


        static void updateCar(int id, Car car) throws DaoException{
            CarDaoInterface IUserDao = new MySqlCarDao();
            System.out.println("*** Updating car by id ***");
            IUserDao.updateCar(id, car);
        }


    static void deleteCar(int id) throws DaoException {
            CarDaoInterface IUserDao = new MySqlCarDao();
            System.out.println("*** Deleting an entity by id ***");
            IUserDao.deleteCarById(id);
        }

       public static List<Car> sortAllAscending() throws SQLException {
            CarDaoInterface IUserDao = new MySqlCarDao();
            JsonConverter JsonConverter = new JsonConverter();
            System.out.println("\n*** Call findCarsUsingFilter(), sorting list by production year ***");
//      sorting our car list with our finCarUsingFilter(comparator) function where our comparator is a lamba expression that
//      takes in two objects c1 and c2 and then compares their production year giving us list sorted in ascending order by year
        List<Car> sortedCars = IUserDao.findCarsUsingFilter((c1, c2) -> Integer.compare(c1.getProductionYear(), c2.getProductionYear()));


        if (sortedCars.isEmpty()) {
            System.out.println("There are no cars in the database");
        } else {
            for (Car sortedcar : sortedCars) {
                System.out.println(sortedcar.toString());
                }
            }
           return sortedCars;
       }

       public static List<Car> sortAllDescending() throws SQLException {
            CarDaoInterface IUserDao = new MySqlCarDao();
            JsonConverter JsonConverter = new JsonConverter();
            System.out.println("\n*** Call findCarUsingFilter(carYearComparatorDesc ***");
//        using our carYearComparator for descending order
            List<Car> sortedCars;
        sortedCars = IUserDao.findCarsUsingFilter(new carYearComparatorDes());

        if (sortedCars.isEmpty()) {
            System.out.println("There are no cars in the database");
        } else {
            for (Car sortedcar : sortedCars) {
                System.out.println(sortedcar.toString());
            }
        }
           return sortedCars;
       }
}

