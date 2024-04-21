package org.example.Current.DAOs;

import org.example.Current.DTOs.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MySqlCarDao extends MySqlDao implements CarDaoInterface {
    /**
     * Main Author: Dominik Domalip
     */
    @Override
    public List<Car> findAllCars() throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Car> carsList = new ArrayList<>();
        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "SELECT * FROM car";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                String model = resultSet.getString("MODEL");
                String brand = resultSet.getString("BRAND");
                String colour = resultSet.getString("COLOUR");
                int production_year = resultSet.getInt("PRODUCTION_YEAR");
                int price = resultSet.getInt("PRICE");


                Car u = new Car(id,model,brand,colour,production_year,price);
                carsList.add(u);
            }
        } catch (SQLException e)
        {
            throw new SQLException("findAllCarResultSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new SQLException("findAllCars() " + e.getMessage());
            }
        }
        return carsList;     // may be empty
    }


    //  **** Logan's Code ****
    //  **** Fixed by Dominik as My code was returning an int instead of the Car entity inserted
    /**
     * Main Author: Logan Rushe
     * <p>
     * Other contributors: Dominik Domalip
     * <p>
     * Insert a given Car into the car table
     * @param car The car object to be inserted
     * @return The inserted Car if successful, or the Car will be NULL if it's already contained within the table
     * @throws SQLException if any SQL statements fail
     */
    public Car insertCar(Car car) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement checkStatement = null;
        ResultSet resultSet = null;
        Car newCar = null;
        // int code = 0;

        try {
            connection = this.getConnection();

            // Prepare insert statement
            String query = "INSERT INTO car VALUES (null, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // return generated keys to be able to print the entity
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getBrand());
            preparedStatement.setString(3, car.getColour());
            preparedStatement.setInt(4, car.getProductionYear());
            preparedStatement.setInt(5, car.getPrice());

            // Prepare statement to check if the car is in the table already
            String checkQuery = "SELECT * FROM car WHERE model = ? AND brand = ? AND colour = ? AND production_year = ? AND price = ?";
            checkStatement = connection.prepareStatement(checkQuery, Statement.RETURN_GENERATED_KEYS);
            checkStatement.setString(1, car.getModel());
            checkStatement.setString(2, car.getBrand());
            checkStatement.setString(3, car.getColour());
            checkStatement.setInt(4, car.getProductionYear());
            checkStatement.setInt(5, car.getPrice());
            resultSet = checkStatement.executeQuery();

            // If the car is not found in the table
            if(!resultSet.next()){
                preparedStatement.execute();
                ResultSet getKeys = preparedStatement.getGeneratedKeys(); // retrieve the keys that were generated
                if(getKeys.next()){
                    int insertedId = getKeys.getInt(1); // get id that was auto generated
                    newCar = new Car(insertedId, car.getModel(), car.getBrand(), car.getColour(), car.getProductionYear(), car.getPrice()); // Create a new Car with auto incremented id, needed for return so we see what was added
                }
            }
        } catch (SQLException e) {
            throw new SQLException("insertCar() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (checkStatement != null)
                {
                    checkStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new SQLException("insertCar() " + e.getMessage());
            }
        }
        return newCar;
    }

    /**
     * Main Author: Dominik Domalip
     */
    //    **** Dominik's code ****
    @Override
    public Car findCarById(int id) throws SQLException{
//        Declaring needed variables, setting them to null for now
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Car car = null;
        //            Everything in the try block will try to execute code inside, if not
        try {
// setting connection variable to the function that connects to our database
            connection = this.getConnection();
//            Storing our sql query into a string variable, ? gets defined below
            String query = "SELECT * FROM car WHERE id = ?";
//  Prepares the statement to be sent into the database
            preparedStatement = connection.prepareStatement(query);
//            in our query setting the ? to be an int of a value of id that's passed into the function
            preparedStatement.setInt(1, id);
// finally our sql statement gets executed by calling executeQuery();
            resultSet = preparedStatement.executeQuery();
//            if there is data in the resultSet do this
            if(resultSet.next()){
//  retrieves the data from database and stores them into our variables
                int carId = resultSet.getInt("ID");
                String model = resultSet.getString("MODEL");
                String brand = resultSet.getString("BRAND");
                String colour = resultSet.getString("COLOUR");
                int production_year = resultSet.getInt("PRODUCTION_YEAR");
                int price = resultSet.getInt("PRICE");
// then create a new object of a type CarClass and put our values into it, if it is found
                car = new Car(carId, model, brand, colour, production_year, price);
            }
//            catch if our try block does not run
        } catch (SQLException e){
            throw new SQLException("findCarById()" + e.getMessage());
//      after all this we need to make sure to close the connection
        } finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
//                call our function from mysqldao that will close the system
                if(connection != null){
                    freeConnection(connection);
                }
//                if anything goes wrong throw exception
            } catch (SQLException e){
                throw new SQLException("findCarById()" + e.getMessage());
            }
        }
//        at the end return our car object if it was found
        return car;
    }

    /**
     * Main Author: Ida Tehlarova
     * <p>
     * Other contributors: Dominik Domalip
     */
    //    **** Ida's code
    //    **** Dominik upgrade - return what car has been deleted by using function findCarById() to get the object before and then after
    //    delete print out deleted car
    public void deleteCarById(int id) throws SQLException {

        //CarDaoInterface carDaoInterface = new MySqlCarDao();

        Connection connection = null;
        PreparedStatement preparedStatement1 = null;

        try
        {
            String query1 = "DELETE FROM car_rental.car WHERE id = ?";
            connection = this.getConnection();
            preparedStatement1 = connection.prepareStatement(query1);
            //System.out.println("Building a PreparedStatement to delete a new row in database.");

            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();

            // System.out.println("Disconnected from database");

        } catch (SQLException e) {
            throw new SQLException("deleteCarById() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (preparedStatement1 != null)
                {
                    preparedStatement1.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new SQLException("deleteCarById() " + e.getMessage());
            }
        }
    }

    /**
     * Main Author: Logan Rushe
     */
    //  **** Logan's Code ****
    public List<Car> findCarsUsingFilter(Comparator<Car> carComparator) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        List<Car> carsList = new ArrayList<>();

        try {
            carsList = findAllCars();
            // Sorts the list of cars using the comparator
            carsList.sort(carComparator);

        } catch (SQLException e) {
            throw new SQLException("findCarsUsingFilter() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement1 != null)
                {
                    preparedStatement1.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new SQLException("deleteCarById() " + e.getMessage());
            }
        }
        return carsList;
    }

    /**
     * Main Author: Ida Tehlarova
     * <p>
     * Other contributors: Logan Rushe
     */
    @Override
    public void updateCar(int id, Car car) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();

            // Prepare update statement
            String query = "UPDATE car SET model =?, brand = ?, colour = ?, production_year = ?, price = ? WHERE id = ?;";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getBrand());
            preparedStatement.setString(3, car.getColour());
            preparedStatement.setInt(4, car.getProductionYear());
            preparedStatement.setInt(5, car.getPrice());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("updateCar() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new SQLException("updateCar() " + e.getMessage());
            }
        }
    }
}
