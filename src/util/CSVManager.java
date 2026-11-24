
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVManager {

    // WRITE DATA TO CSV
    public static void writeToCSV(String fileName, String data) {

        if (fileName == null || fileName.trim().isEmpty()
                || data == null) {

            System.out.println("Invalid filename or data.");
            return;
        }

        try (FileWriter writer =
                     new FileWriter(fileName, true)) {

            writer.write(data);
            writer.write(System.lineSeparator());

            System.out.println("Data saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error while saving data: " + e.getMessage()
            );
        }
    }

    // READ DATA FROM CSV
    public static void readFromCSV(String fileName) {

        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("Invalid filename.");
            return;
        }

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {

            System.out.println(
                    "Error while reading data: " + e.getMessage()
            );
        }
    }

    // CLEAR CSV FILE
    public static void clearCSV(String fileName) {

        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("Invalid filename.");
            return;
        }

        try (FileWriter writer =
                     new FileWriter(fileName, false)) {

            System.out.println("File cleared successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error while clearing file: " + e.getMessage()
            );
        }
    }
}
