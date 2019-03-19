package pl.belter.phone.app.dao;

import pl.belter.phone.app.domain.Phone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PhoneDaoImpl implements PhoneDao {
    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {

    }

    @Override
    public List<Phone> getAllPersons() {
        return null;
    }
}
