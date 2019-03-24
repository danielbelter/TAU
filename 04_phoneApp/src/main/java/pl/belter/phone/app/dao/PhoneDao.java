package pl.belter.phone.app.dao;

import pl.belter.phone.app.domain.Phone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PhoneDao {
    public Connection getConnection();

    public void setConnection(Connection connection) throws SQLException;

    public List<Phone> getAllPhones();

    public int addPhone(Phone phone) throws SQLException;

    public Phone getPhoneById(Long id) throws SQLException;

    public int updatePhone(Phone phone) throws SQLException;

    public int deletePhone(Phone phone) throws SQLException;
}
