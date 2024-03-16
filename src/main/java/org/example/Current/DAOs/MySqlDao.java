package org.example.Current.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDao {
    public Connection getConnection() throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        /* if driver issue:
        go to:
        file -> Project Structure -> Libraries -> Click plus sign -> From Maven ->
        type in: mysql-connector-java, click on the search icon and choose the 8.0.30 ->
        when window pops up click ok -> click apply and then ok again and it should be done
         */
        String url = "jdbc:mysql://localhost:3306/car_rental";
        String username = "root";
        String password = "";
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to find driver class " + e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Connection failed " + e.getMessage());
            System.exit(2);
        }
        return connection;
    }

    //    This method is important to make sure connection is closed after use
    public void freeConnection(Connection connection) throws SQLException {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }
}