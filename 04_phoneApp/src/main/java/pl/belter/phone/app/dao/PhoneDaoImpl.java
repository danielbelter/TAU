package pl.belter.phone.app.dao;

import pl.belter.phone.app.domain.Phone;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PhoneDaoImpl implements PhoneDao {
    public PreparedStatement preparedStatementGetAll;
    public PreparedStatement preparedStatementInsert;
    public PreparedStatement preparedStatementGetById;
    public PreparedStatement preparedStatementupdatePhone;
    public PreparedStatement preparedStatementDelete;
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
                "SELECT id, model, serialnumber FROM Phone WHERE id = ?");
        preparedStatementupdatePhone = connection.prepareStatement(
                "UPDATE Phone SET model=?,serialnumber=? WHERE id = ?"
        );
        preparedStatementDelete = connection.prepareStatement("DELETE FROM Phone where id = ?");

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

    @Override
    public Phone getPhoneById(Long id) throws SQLException {
        try {
            preparedStatementGetById.setLong(1, id);
            ResultSet rs = preparedStatementGetById.executeQuery();

            if (rs.next()) {
                Phone p = new Phone();
                p.setId(rs.getLong("id"));
                p.setModel(rs.getString("model"));
                p.setSerialNumber(rs.getInt("serialnumber"));
                return p;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Phone with id " + id + " does not exist");
    }


    @Override
    public int updatePhone(Phone phone) throws SQLException {
        int count = 0;
        try {
            preparedStatementupdatePhone.setString(1, phone.getModel());
            preparedStatementupdatePhone.setInt(2, phone.getSerialNumber());
            if (phone.getId() != null) {
                preparedStatementupdatePhone.setLong(3, phone.getId());
            } else {
                preparedStatementupdatePhone.setLong(3, -1);
            }
            count = preparedStatementupdatePhone.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        if (count <= 0)
            throw new SQLException("Phone not found for update");
        return count;
    }

    @Override
    public int deletePhone(Phone phone) throws SQLException {
        try {
            preparedStatementDelete.setLong(1, phone.getId());
            int r = preparedStatementDelete.executeUpdate();
            return r;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }
}

