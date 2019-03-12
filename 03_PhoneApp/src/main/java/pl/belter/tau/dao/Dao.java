package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao {
    Connection getConnection();

    void setConnection(Connection connection) throws SQLException;

    int addPhone(Phone phone);
}
