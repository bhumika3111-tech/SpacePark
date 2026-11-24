
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_NAME = "spacepark_records.txt";

    // SAVE RECORD
    public static void saveRecord(String record) {

        if (record == null) {
            System.out.println("Record cannot be null.");
            return;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME, true))) {

            writer.write(record);
            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Error saving record: " + e.getMessage()
            );
        }
    }

    // READ RECORDS
    public static List<String> readRecords() {

        List<String> records = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                records.add(line);
            }

        } catch (FileNotFoundException e) {

            System.out.println("No previous records found.");

        } catch (IOException e) {

            System.out.println(
                    "Error reading records: " + e.getMessage()
            );
        }

        return records;
    }

    // DISPLAY RECORDS
    public static void displayRecords() {

        List<String> records = readRecords();

        System.out.println("\n------ SAVED PARKING RECORDS ------");

        if (records.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        for (String record : records) {
            System.out.println(record);
        }
    }

    // CLEAR RECORDS
    public static void clearRecords() {

        try (FileWriter writer =
                     new FileWriter(FILE_NAME, false)) {

            System.out.println(
                    "All records cleared successfully."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error clearing records: " + e.getMessage()
            );
        }
    }
}
