package pl.belter.phone.app.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.belter.phone.app.domain.Phone;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PhoneDaoTest {
    Random random;
    static List<Phone> initialDatabaseState;


    /**
     * Tylko na potrzeby testów! Przygotujmy odpowiedni ResultSet.
     * <p>
     * UWAGA: Moglibyśmy zaimplementować cały ResultSet, ale wtedy musimy przygotować wszystkie metody które są w nim zadeklarowane.
     */
    abstract class AbstractResultSet implements ResultSet {
        int i; // automatycznie bedzie 0

        @Override
        public int getInt(String s) throws SQLException {
            return initialDatabaseState.get(i - 1).getSerialNumber();
        }

        @Override
        public long getLong(String s) throws SQLException {
            return initialDatabaseState.get(i - 1).getId();
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return initialDatabaseState.get(i - 1).getModel();
        }

        @Override
        public boolean next() throws SQLException {
            i++;
            if (i > initialDatabaseState.size())
                return false;
            else
                return true;
        }
    }

    // to będzie nasz Mock!!
    @Mock
    Connection connection;
    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement preparedStatementGetById;
    @Mock
    PreparedStatement updateStatementMock;
    @Mock
    PreparedStatement deleteStatementMock;


    @Before
    public void setup() throws SQLException {
        random = new Random();
        initialDatabaseState = new LinkedList<>();
        for (long i = 0; i < 10; i++) {
            Phone phone = new Phone();
            phone.setId(i);
            phone.setModel("Nokia" + random.nextInt(1000));
            phone.setSerialNumber(random.nextInt(50) + 1950);
            initialDatabaseState.add(phone);
        }
        Mockito.when(connection.prepareStatement("SELECT id, model, serialnumber FROM Phone ORDER BY id")).thenReturn(selectStatementMock);
        Mockito.when(connection.prepareStatement("INSERT INTO Phone (model, serialnumber) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        Mockito.when(connection.prepareStatement("SELECT id, model, serialnumber FROM Phone WHERE id = ?")).thenReturn(preparedStatementGetById);
        Mockito.when(connection.prepareStatement("UPDATE Phone SET model=?,serialnumber=? WHERE id = ?")).thenReturn(updateStatementMock);
        Mockito.when(connection.prepareStatement("DELETE FROM Phone where id = ?")).thenReturn(deleteStatementMock);
    }

    @Test
    public void setConnectionCheck() throws SQLException {
        PhoneDao dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Assert.assertNotNull(dao.getConnection());
        Assert.assertEquals(dao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesCheck() throws SQLException {
        // nagrajmy zachowanie mocka
        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Assert.assertNotNull(dao.preparedStatementGetAll);
        verify(connection).prepareStatement("SELECT id, model, serialnumber FROM Phone ORDER BY id");
    }

    @Test
    public void getAllCheck() throws SQLException {
        // szykujemy mocki

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(mockedResultSet.getInt("serialnumber")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        // robimy test

        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        List<Phone> retrievedPhones = dao.getAllPhones();
        Assert.assertThat(retrievedPhones, equalTo(initialDatabaseState));

        // weryfikujeymy wykonanie mockow

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("model");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("serialnumber");
        verify(mockedResultSet, times(initialDatabaseState.size() + 1)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {
        // przygotujmy mocki
        // tym razem weryfikujemy takze kolejnosc wykonania

        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        // nasza testowana metoda

        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Phone phone = new Phone();
        phone.setModel("Nokia");
        phone.setSerialNumber(1980);
        dao.addPhone(phone);

        // sprawdzamy wykonanie mocka

        inorder.verify(insertStatementMock, times(1)).setString(1, "Nokia");
        inorder.verify(insertStatementMock, times(1)).setInt(2, 1980);
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test(expected = SQLException.class)
    public void checkAddingInOrderException() throws Exception {
        // przygotujmy mocki
        // tym razem weryfikujemy takze kolejnosc wykonania

        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(0);

        // nasza testowana metoda

        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Phone phone = new Phone();
        phone.setModel("Nokia");
        phone.setSerialNumber(1980);
        dao.addPhone(phone);

        // sprawdzamy wykonanie mocka
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void checkGetPhoneById() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenReturn(initialDatabaseState.get(2).getId());
        when(mockedResultSet.getString("model")).thenReturn(initialDatabaseState.get(2).getModel());
        when(mockedResultSet.getInt("serialnumber")).thenReturn(initialDatabaseState.get(2).getSerialNumber());
        when(preparedStatementGetById.executeQuery()).thenReturn(mockedResultSet);

        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Phone retrievedPhone = dao.getPhoneById(2L);
        Assert.assertThat(retrievedPhone, equalTo(initialDatabaseState.get(2)));

        Mockito.verify(preparedStatementGetById, times(1)).setLong(1, 2);
        Mockito.verify(preparedStatementGetById, times(1)).executeQuery();
        Mockito.verify(mockedResultSet, times(1)).getLong("id");
        Mockito.verify(mockedResultSet, times(1)).getString("model");
        Mockito.verify(mockedResultSet, times(1)).getInt("serialnumber");
        Mockito.verify(mockedResultSet, times(1)).next();

    }

    @Test
    public void checkUpdatePhone() throws SQLException {
        InOrder inOrder = inOrder(updateStatementMock);
        when(updateStatementMock.executeUpdate()).thenReturn(1);
        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);

        when(updateStatementMock.executeUpdate()).thenReturn(1);
        Phone p = initialDatabaseState.get(0);
        p.setModel("iphone");
        dao.updatePhone(p);

        inOrder.verify(updateStatementMock, times(1)).setString(1, "iphone");
        inOrder.verify(updateStatementMock, times(1)).setLong(3, 0L);
        inOrder.verify(updateStatementMock).executeUpdate();
    }

    @Test(expected = SQLException.class)
    public void checkExceptionUpdate() throws SQLException {
        when(updateStatementMock.executeUpdate()).thenReturn(0);

        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Phone phone = initialDatabaseState.get(5);
        phone.setModel("BBB");
        phone.setSerialNumber(90);
        dao.updatePhone(phone);

    }

    @Test
    public void checkDelete() throws SQLException {
        InOrder inOrder = inOrder(deleteStatementMock);
        when(deleteStatementMock.executeUpdate()).thenReturn(1);

        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Phone phone = initialDatabaseState.get(5);
        dao.deletePhone(phone);

        inOrder.verify(deleteStatementMock, times(1)).setLong(1, 5);
        inOrder.verify(deleteStatementMock).executeUpdate();
    }

    @Test(expected = SQLException.class)
    public void checkDeleteException() throws SQLException {
        InOrder inOrder = inOrder(deleteStatementMock);
        when(deleteStatementMock.executeUpdate()).thenReturn(0);

        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        Phone phone = initialDatabaseState.get(5);
        dao.deletePhone(phone);

        inOrder.verify(deleteStatementMock).executeUpdate();
    }


    @Test(expected = SQLException.class)
    public void checkExceptionGettingById() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenReturn(false);
        when(preparedStatementGetById.executeQuery()).thenReturn(mockedResultSet);


        PhoneDaoImpl dao = new PhoneDaoImpl();
        dao.setConnection(connection);
        dao.getPhoneById(3L);
    }


}
