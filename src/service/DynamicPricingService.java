
package service;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DynamicPricingService {

    private double peakHourMultiplier;
    private double offPeakMultiplier;
    private double highDemandMultiplier;

    public DynamicPricingService() {
        peakHourMultiplier = 1.5;
        offPeakMultiplier = 0.8;
        highDemandMultiplier = 1.25;
    }

    public double calculateDynamicRate(
            double baseRate,
            int occupiedSlots,
            int totalSlots,
            LocalDateTime currentTime) {

        if (Double.isNaN(baseRate)
                || Double.isInfinite(baseRate)
                || baseRate <= 0
                || totalSlots <= 0
                || occupiedSlots < 0
                || occupiedSlots > totalSlots
                || currentTime == null) {

            throw new IllegalArgumentException(
                    "Invalid pricing details."
            );
        }

        LocalTime time = currentTime.toLocalTime();

        boolean peakHour =
                (!time.isBefore(LocalTime.of(8, 0))
                        && time.isBefore(LocalTime.of(11, 0)))
                || (!time.isBefore(LocalTime.of(17, 0))
                        && time.isBefore(LocalTime.of(21, 0)));

        double finalRate = baseRate;

        if (peakHour) {
            finalRate = finalRate * peakHourMultiplier;
        } else {
            finalRate = finalRate * offPeakMultiplier;
        }

        double occupancyPercentage =
                (occupiedSlots * 100.0) / totalSlots;

        if (occupancyPercentage >= 80) {
            finalRate = finalRate * highDemandMultiplier;
        }

        return finalRate;
    }

    public void displayDynamicRate(
            double baseRate,
            int occupiedSlots,
            int totalSlots,
            LocalDateTime currentTime) {

        try {
            double dynamicRate = calculateDynamicRate(
                    baseRate,
                    occupiedSlots,
                    totalSlots,
                    currentTime
            );

            System.out.println("\n----- DYNAMIC PARKING PRICING -----");
            System.out.println("Base Rate: Rs. " + baseRate);
            System.out.println("Occupied Slots: " + occupiedSlots);
            System.out.println("Total Slots: " + totalSlots);
            System.out.println("Dynamic Rate: Rs. " + dynamicRate);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMultipliers(
            double peakMultiplier,
            double offPeakMultiplier,
            double demandMultiplier) {

        if (!isValidMultiplier(peakMultiplier)
                || !isValidMultiplier(offPeakMultiplier)
                || !isValidMultiplier(demandMultiplier)) {

            System.out.println("Invalid multiplier values!");
            return;
        }

        peakHourMultiplier = peakMultiplier;
        offPeakMultiplier = offPeakMultiplier;
        highDemandMultiplier = demandMultiplier;

        System.out.println(
                "Pricing multipliers updated successfully!"
        );
    }

    private boolean isValidMultiplier(double value) {
        return value > 0
                && !Double.isNaN(value)
                && !Double.isInfinite(value);
    }

    public double getPeakHourMultiplier() {
        return peakHourMultiplier;
    }

    public double getOffPeakMultiplier() {
        return offPeakMultiplier;
    }

    public double getHighDemandMultiplier() {
        return highDemandMultiplier;
    }
}
