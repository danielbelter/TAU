package pl.tau.phoneapp.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.tau.phoneapp.domain.Phone;

import java.util.List;

@Component
@Transactional
public class PhoneManagerHibernateImpl implements PhoneManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long addPhonejkk(Phone phone) {
        // Pierwsza wersja dodawania do bazy danych - korzystamy z persist - to jest dostepne w JPA
        if (phone.getId() != null)
            throw new IllegalArgumentException("the person ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(phone);
        sessionFactory.getCurrentSession().flush();
        return phone.getId();
    }

    @Override
    public List<Phone> findAllPhone() {
        return sessionFactory.getCurrentSession().getNamedQuery("phone.all")
                .list();
    }

    @Override
    public Phone findPhoneById(Long id) {
        return (Phone) sessionFactory.getCurrentSession().get(Phone.class, id);
    }

    @Override
    public void deletePhone(Phone phone) {
        phone = (Phone) sessionFactory.getCurrentSession().get(Phone.class,
                phone.getId());
        sessionFactory.getCurrentSession().delete(phone);

    }

    @Override
    public void updatePhone(Phone phone) {
        sessionFactory.getCurrentSession().update(phone);

    }
    @Override
    public List<Phone> findPhoneByName(String phoneName) {
        return (List<Phone>) sessionFactory.getCurrentSession()
                .getNamedQuery("phone.findPhone")
                .setString("modelNameFragment", "%"+phoneName+"%")
                .list();
    }

}
