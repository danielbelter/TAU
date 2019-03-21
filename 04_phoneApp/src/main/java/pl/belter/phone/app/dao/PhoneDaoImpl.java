package pl.belter.phone.app.dao;

import pl.belter.phone.app.domain.Phone;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PhoneDaoImpl implements PhoneDao {
    public PreparedStatement preparedStatementGetAll;
    public PreparedStatement preparedStatementInsert;
    public PreparedStatement preparedStatementGetById;
    Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        preparedStatementGetAll = connection.prepareStatement(
                "SELECT id, model, serialnumber FROM Phone ORDER BY id");
        preparedStatementInsert = connection.prepareStatement(
                "INSERT INTO Phone (model, serialnumber) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatementGetById = connection.prepareStatement(
                "SELECT id, model, serialnumber FROM Phone WHERE (?, ?, ?)");
    }

    @Override
    public List<Phone> getAllPhones() {
        try {
            List<Phone> ret = new LinkedList<>();
            ResultSet result = preparedStatementGetAll.executeQuery();
            while (result.next()) {
                Phone p = new Phone();
                p.setId(result.getLong("id"));
                p.setModel(result.getString("model"));
                p.setSerialNumber(result.getInt("serialnumber"));
                ret.add(p);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addPhone(Phone phone) throws SQLException {
        preparedStatementInsert.setString(1, phone.getModel());
        preparedStatementInsert.setInt(2, phone.getSerialNumber());
        int r = preparedStatementInsert.executeUpdate();
        return r;
    }
}
