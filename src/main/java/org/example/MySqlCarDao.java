package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlCarDao extends MySqlDao implements CarDaoInterface {
    // Logan Rushe
    /**
     * Insert a given Car into the car table
     * @param car The car object to be inserted
     * @return Code 1 if successful, or 0 if already contained within the table
     * @throws SQLException
     */
    public int insertCar(Car car) throws SQLException {
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
            preparedStatement.setInt(4, car.getProductionYear());
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

