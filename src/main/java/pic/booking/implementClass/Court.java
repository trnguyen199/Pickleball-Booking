package pic.booking.implementClass;

public class Court {
    private Long id;
    private String name;
    private String location;
    private double pricePerHour;
    private boolean isAvailable;

    public Court(Long id, String name, String location, double pricePerHour, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.pricePerHour = pricePerHour;
        this.isAvailable = isAvailable;
    }

    public Court(Long id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}
