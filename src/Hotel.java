//represent hotel metadata


public class Hotel {
    private int id;
    private String name;
    private String location;

    public Hotel(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s)", id, name, location);
    }
}