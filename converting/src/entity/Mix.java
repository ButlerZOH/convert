package entity;

public class Mix {
    private String id;
    private String name;

    public Mix() {
    }

    public Mix(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Mix{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
