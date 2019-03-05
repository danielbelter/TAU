package pl.belter.tau.domain;

public class Phone {
    private Long id;
    private String model;
    private int serialNumber;

    public Phone() {
    }

    public Phone(Long id, String model, int serialNumber) {
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

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}
