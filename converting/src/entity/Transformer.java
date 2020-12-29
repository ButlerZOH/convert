package entity;

public class Transformer {

    private int id;
    private String voltage;
    private String name;

    public Transformer() {
    }

    public Transformer(int id, String voltage, String name) {
        this.id = id;
        this.voltage = voltage;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
