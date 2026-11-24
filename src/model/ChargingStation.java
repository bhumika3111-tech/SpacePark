
package model;

public class ChargingStation {

    private String stationId;
    private String stationName;
    private String location;
    private String chargingType;
    private double powerRating;
    private boolean available;

    public ChargingStation() {
        this.available = true;
    }

    public ChargingStation(String stationId, String stationName,
            String location, String chargingType, double powerRating) {

        setStationId(stationId);
        setStationName(stationName);
        setLocation(location);
        setChargingType(chargingType);
        setPowerRating(powerRating);

        this.available = true;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        if (stationId == null || stationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Station ID cannot be empty.");
        }
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        if (stationName == null || stationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name cannot be empty.");
        }
        this.stationName = stationName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        this.location = location;
    }

    public String getChargingType() {
        return chargingType;
    }

    public void setChargingType(String chargingType) {
        if (chargingType == null || chargingType.trim().isEmpty()) {
            throw new IllegalArgumentException("Charging type cannot be empty.");
        }
        this.chargingType = chargingType;
    }

    public double getPowerRating() {
        return powerRating;
    }

    public void setPowerRating(double powerRating) {
        if (powerRating <= 0 || Double.isNaN(powerRating)
                || Double.isInfinite(powerRating)) {
            throw new IllegalArgumentException("Invalid power rating.");
        }
        this.powerRating = powerRating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayStationDetails() {
        System.out.println("\n----- CHARGING STATION -----");
        System.out.println("Station ID: " + stationId);
        System.out.println("Station Name: " + stationName);
        System.out.println("Location: " + location);
        System.out.println("Charging Type: " + chargingType);
        System.out.println("Power Rating: " + powerRating + " kW");
        System.out.println("Available: " + available);
    }
}
