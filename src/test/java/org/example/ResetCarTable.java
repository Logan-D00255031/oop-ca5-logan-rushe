package org.example;

import org.example.Logan.MySqlCarDao;

import java.sql.SQLException;

public class ResetCarTable {
    public ResetCarTable() {
    }

    public void resetTable() {
        MySqlCarDao ICarDao = new MySqlCarDao();

        try {
            ICarDao.resetCarTable();
        } catch( SQLException e ) {
            e.printStackTrace();
        }
    }
}
