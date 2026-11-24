
package model;

public class ParkingRate {

    private String vehicleType;
    private double firstHourRate;
    private double additionalHourRate;

    // Constructor
    public ParkingRate(String vehicleType,
                       double firstHourRate,
                       double additionalHourRate) {

        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Vehicle type cannot be empty.");
        }

        if (firstHourRate < 0 || additionalHourRate < 0
                || Double.isNaN(firstHourRate)
                || Double.isNaN(additionalHourRate)
                || Double.isInfinite(firstHourRate)
                || Double.isInfinite(additionalHourRate)) {

            throw new IllegalArgumentException(
                    "Invalid parking rates.");
        }

        this.vehicleType = vehicleType.trim();
        this.firstHourRate = firstHourRate;
        this.additionalHourRate = additionalHourRate;
    }

    // Getters
    public String getVehicleType() {
        return vehicleType;
    }

    public double getFirstHourRate() {
        return firstHourRate;
    }

    public double getAdditionalHourRate() {
        return additionalHourRate;
    }

    // Calculate Parking Fee
    public double calculateFee(int startedHours) {

        if (startedHours <= 0) {
            return 0;
        }

        return firstHourRate
                + (startedHours - 1) * additionalHourRate;
    }

    // Display Parking Rate
    @Override
    public String toString() {

        return "Vehicle Type: " + vehicleType
                + " | First Hour: " + firstHourRate
                + " | Additional Hour: " + additionalHourRate;
    }
}
