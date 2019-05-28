package pl.tau.phoneapp.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Phone")
@Table(name = "phone")
@NamedQueries({
        @NamedQuery(name = "phone.all", query = "Select p from Phone p"),
        //@NamedQuery(name = "car.unsold", query = "Select c from Book c where c.sold = false")
        @NamedQuery(name = "phone.findPhone", query = "Select c from Phone c where c.model like :modelNameFragment"),
        @NamedQuery(name = "phone.findPhonesByOwner", query = "Select p from Phone p where p.owner= :owner")
})
public class Phone {
    @Id
    @GeneratedValue
    private Long id;
    private String model;
    private Integer serialNumber;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;


    public Phone() {
    }

    public Phone(String model, Integer serialNumber) {
        this.model = model;
        this.serialNumber = serialNumber;
    }

    public Phone(Long id, String model, Integer serialNumber) {
        this.id = id;
        this.model = model;
        this.serialNumber = serialNumber;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) &&
                Objects.equals(model, phone.model) &&
                Objects.equals(serialNumber, phone.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, serialNumber);
    }
    public Phone clone() {
        Phone p = new Phone();
        p.owner = null;
        p.id = id;
        p.model = model;
        p.serialNumber = serialNumber;
        return p;
    }
    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
