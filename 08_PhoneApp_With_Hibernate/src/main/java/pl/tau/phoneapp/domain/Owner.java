package pl.tau.phoneapp.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Owner")
@NamedQuery(name = "owner.all", query = "Select o from Owner o")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "owner"
    )
    private List<Phone> phones;

    public Owner() {
    }

    public Owner(String firstName, List<Phone> phones) {
        this.firstName = firstName;
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
