package by.bsu.practice.jdbc.entity;

public class Galaxy {
    private int id;
    private String name;

    public Galaxy(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "Galaxy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
