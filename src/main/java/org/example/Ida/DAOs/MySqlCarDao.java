package org.example.Ida.DAOs;
import org.example.Ida.DTOs.CarClass;
import org.example.Ida.Exceptions.DaoException;
import org.example.Logan.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MySqlCarDao extends MySqlDao implements CarDaoInterface{
    @Override
    public List<CarClass> findAllCars() throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CarClass> carsList = new ArrayList<>();
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


                CarClass u = new CarClass(id,model,brand,colour,production_year,price);
                carsList.add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllCarResultSet() " + e.getMessage());
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
                throw new DaoException("findAllCars() " + e.getMessage());
            }
        }
        return carsList;     // may be empty
    }

    // Logan Rushe
    /**
     * Insert a given Car into the car table
     * @param car The car object to be inserted
     * @return Code 1 if successful, or 0 if already contained within the table
     * @throws SQLException
     */
    public int insertCar(CarClass car) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement checkStatement = null;
        ResultSet resultSet = null;
        int code;

        try {
            connection = this.getConnection();

            // Prepare insert statement
            String query = "INSERT INTO car VALUES (null, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getBrand());
            preparedStatement.setString(3, car.getColour());
            preparedStatement.setInt(4, car.getProduction_year());
            preparedStatement.setInt(5, car.getPrice());

            // Prepare statement to check if the car is in the table already
            String checkQuery = "SELECT * FROM car WHERE model = ? AND brand = ?";
            checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, car.getModel());
            checkStatement.setString(2, car.getBrand());
            resultSet = checkStatement.executeQuery();

            // If the car is found in the table
            if(resultSet.next())
                code = 0;
            else
                code = preparedStatement.executeUpdate();
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
        return code;
    }
}
