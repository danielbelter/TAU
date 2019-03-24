package pl.belter.phone.app.domain;

import java.util.Objects;

public class Phone {
    private Long id;
    private String model;
    private Integer serialNumber;

    public Phone() {
    }

    public Phone(Long id, String model, Integer serialNumber) {
        this.id = id;
        this.model = model;
        this.serialNumber = serialNumber;
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

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
