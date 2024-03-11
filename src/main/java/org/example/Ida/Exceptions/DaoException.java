package org.example.Ida.Exceptions;
import java.sql.SQLException;

public class DaoException extends SQLException {
    public DaoException() {
        // not used
    }

    public DaoException(String message) {super(message);
    }
}
