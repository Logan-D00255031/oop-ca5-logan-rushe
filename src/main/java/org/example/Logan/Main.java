package org.example.Logan;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        MySqlCarDao ICarDao = new MySqlCarDao();

        try {
            int code = ICarDao.insertCar(new Car(1, "Civic", "Honda", "Silver", 2010, 25000));
            if(code == 1) {
                System.out.println("Car added successfully");
            } else if (code == 0) {
                System.out.println("Car already exists in table");
            }
        } catch( SQLException e ) {
            e.printStackTrace();
        }
    }
}