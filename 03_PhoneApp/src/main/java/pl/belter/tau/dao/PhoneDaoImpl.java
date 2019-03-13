package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PhoneDaoImpl implements Dao {
    private Connection connection;
    private PreparedStatement addMovieStmt;
    private PreparedStatement getAllMoviesStmt;
    private PreparedStatement getPhoneStmt;


    public PhoneDaoImpl() throws SQLException {
    }

    public PhoneDaoImpl(Connection connection) throws SQLException {
        this.connection = connection;
        // if (!isDatabaseReady()) {
        //    createTables();
        //}

        setConnection(connection);
    }

    public void createTables() throws SQLException {
        connection.createStatement()
                .executeUpdate("CREATE TABLE " +
                        "Phone" +
                        "(id bigint GENERATED BY DEFAULT AS IDENTITY, " +
                        "model varchar(20) NOT NULL," +
                        "serialnumber integer)");
    }

    private boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if ("Phone".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int addPhone(Phone phone) {
        int count = 0;
        try {
            addMovieStmt.setString(1, phone.getModel());
            addMovieStmt.setInt(2, phone.getSerialNumber());
            count = addMovieStmt.executeUpdate();
            ResultSet generatedKeys = addMovieStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                phone.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    @Override
    public List<Phone> getAllPhones() {
        List<Phone> phoneList = new LinkedList<>();
        try {
            ResultSet rs = getAllMoviesStmt.executeQuery();

            while (rs.next()) {
                Phone p = new Phone();
                p.setId(rs.getLong("id"));
                p.setModel(rs.getString("model"));
                p.setSerialNumber(rs.getInt("serialnumber"));
                phoneList.add(p);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return phoneList;
    }

    @Override
    public Phone getPhone(long id) throws SQLException {
        try {
            getPhoneStmt.setLong(1, id);
            ResultSet rs = getPhoneStmt.executeQuery();

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
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addMovieStmt = connection.prepareStatement(
                "INSERT INTO Phone (model, serialnumber) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        getAllMoviesStmt = connection.prepareStatement("SELECT id, model, serialnumber FROM Phone ORDER BY id");
        getPhoneStmt = connection.prepareStatement("SELECT id, model, serialnumber FROM Phone WHERE id = ?");
    }
}
