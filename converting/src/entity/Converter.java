package entity;

public class Converter {
    private int id;
    private String voltage;
    private String name;

    public Converter() {

    }

    public Converter(int id, String voltage, String name) {
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

    @Override
    public String toString() {
        return "Converter{" +
                "id=" + id +
                ", voltage='" + voltage + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
