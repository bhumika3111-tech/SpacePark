
package util;

public class InputValidator {

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }

    public static boolean isValidEmail(String email) {
        return email != null &&
                email.trim().matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                );
    }

    public static boolean isValidPhone(String phone) {
        return phone != null &&
                phone.trim().matches("[0-9]{10}");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidVehicleNumber(String vehicleNumber) {

        return vehicleNumber != null &&
                vehicleNumber.trim().toUpperCase().matches(
                        "^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$"
                );
    }

    public static boolean isValidUserId(String userId) {
        return userId != null && userId.trim().length() >= 3;
    }

    public static boolean isPositiveNumber(double number) {
        return number > 0
                && !Double.isNaN(number)
                && !Double.isInfinite(number);
    }

    public static boolean isValidSlotId(String slotId) {
        return slotId != null && slotId.trim().length() >= 2;
    }
}
