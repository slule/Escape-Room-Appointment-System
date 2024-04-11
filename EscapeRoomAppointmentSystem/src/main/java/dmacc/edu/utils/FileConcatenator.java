package dmacc.edu.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileConcatenator {

    private static final String PROJECT_DIRECTORY = "F:\\COLLEGE\\SEMESTER 5\\JAVA II\\GROUP PROJECT\\Escape-Room-Appointment-System\\EscapeRoomAppointmentSystem";
    private static final String OUTPUT_FILE = "F:\\COLLEGE\\SEMESTER 5\\JAVA II\\GROUP PROJECT\\concatenated_files.txt";

    private static final List<String> FILES_TO_CONCATENATE = Arrays.asList(
        "\\src\\main\\java\\dmacc\\edu\\bootstrap\\DatabaseLoader.java",
        "\\src\\main\\java\\dmacc\\edu\\config\\SecurityConfig.java",
        "\\src\\main\\java\\dmacc\\edu\\controller\\BookRoomController.java",
        "\\src\\main\\java\\dmacc\\edu\\controller\\BookingController.java",
        "\\src\\main\\java\\dmacc\\edu\\controller\\EscapeRoomController.java",
        "\\src\\main\\java\\dmacc\\edu\\controller\\UserController.java",
        "\\src\\main\\java\\dmacc\\edu\\controller\\ViewController.java",
        "\\src\\main\\java\\dmacc\\edu\\controller\\ProfileController.java",
        "\\src\\main\\java\\dmacc\\edu\\model\\Booking.java",
        "\\src\\main\\java\\dmacc\\edu\\model\\EscapeRoom.java",
        "\\src\\main\\java\\dmacc\\edu\\model\\Player.java",
        "\\src\\main\\java\\dmacc\\edu\\model\\User.java",
        "\\src\\main\\java\\dmacc\\edu\\repository\\BookingRepository.java",
        "\\src\\main\\java\\dmacc\\edu\\repository\\EscapeRoomRepository.java",
        "\\src\\main\\java\\dmacc\\edu\\repository\\PlayerRepository.java",
        "\\src\\main\\java\\dmacc\\edu\\repository\\UserRepository.java",
        "\\src\\main\\java\\dmacc\\edu\\service\\BookingService.java",
        "\\src\\main\\java\\dmacc\\edu\\service\\EscapeRoomService.java",
        "\\src\\main\\java\\dmacc\\edu\\service\\UserService.java",
        "\\src\\main\\java\\dmacc\\edu\\EscapeRoomAppointmentSystemApplication.java",
        "\\src\\main\\resources\\templates\\bookRoom.html",
        "\\src\\main\\resources\\templates\\escapeRooms.html",
        "\\src\\main\\resources\\templates\\login.html",
        "\\src\\main\\resources\\templates\\profile.html",
        "\\src\\main\\resources\\templates\\register.html",
        "\\src\\main\\resources\\templates\\updateProfile.html",
        "\\src\\main\\resources\\application.properties",
        "\\pom.xml"
    );

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, false))) {
            for (String relativePath : FILES_TO_CONCATENATE) {
                String fullPath = Paths.get(PROJECT_DIRECTORY, relativePath).toString();
                writer.write("// File: " + relativePath); // Add comment indicating the file
                writer.newLine();
                try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("Failed to read file: " + fullPath);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to write to output file.");
            e.printStackTrace();
        }
    }
}
