package by.bsu.practice.jdbc.entity;

public class Planet {
    private int id;
    private String name;
    private int radius;
    private int temperature;
    private boolean isAtmosphere;
    private boolean isLife;
    private int galaxyId;

    public Planet(){}

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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isAtmosphere() {
        return isAtmosphere;
    }

    public void setAtmosphere(boolean atmosphere) {
        isAtmosphere = atmosphere;
    }

    public boolean isLife() {
        return isLife;
    }

    public void setLife(boolean life) {
        isLife = life;
    }

    public int getGalaxyId() {
        return galaxyId;
    }

    public void setGalaxyId(int galaxyId) {
        this.galaxyId = galaxyId;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", radius=" + radius +
                ", temperature=" + temperature +
                ", isAtmosphere=" + isAtmosphere +
                ", isLife=" + isLife +
                ", galaxyId=" + galaxyId +
                '}';
    }
}
