package com.hospital;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hms";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static Connection conn = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            boolean exit = false;
            while (!exit) {
                System.out.println("Welcome to Hospital Management System!!!!!");
                System.out.println("1. Handle Hospitals");
                System.out.println("2. Handle Patients");
                System.out.println("3. Handle Doctors");
                System.out.println("4. Schedule Appointments");
                System.out.println("5. Exit");
              
             
                System.out.print("Select an operation you want to perform: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        HandleHospitals();
                        break;
                    case 2:
                        HandlePatients();
                        break;
                    case 3:
                        HandleDoctors();
                        break;
                    case 4:
                        HandleAppointments();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            System.out.println("Thank you for visiting.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void HandleHospitals() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Handle Hospitals");
            System.out.println("1. Add Hospital");
            System.out.println("2. View Hospitals");
            System.out.println("3. Update Hospital");
            System.out.println("4. Delete Hospital");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addHospital();
                    break;
                case 2:
                    viewHospitals();
                    break;
                case 3:
                    updateHospital();
                    break;
                case 4:
                    deleteHospital();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addHospital() throws SQLException {
        System.out.print("Enter hospital name: ");
        String name = scanner.nextLine();
        System.out.print("Enter hospital address: ");
        String address = scanner.nextLine();

        String query = "INSERT INTO Hospital (Name, Address) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.executeUpdate();
            System.out.println("Hospital added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding hospital: " + e.getMessage());
        }
    }

    private static void viewHospitals() throws SQLException {
        String query = "SELECT * FROM Hospital";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Hospitals List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Hospital_ID") + ", Name: " + rs.getString("Name") +
                        ", Address: " + rs.getString("Address"));
            }
        }
    }

    private static void updateHospital() throws SQLException {
        System.out.print("Enter hospital ID to update: ");
        int hospitalId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new hospital name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new hospital address: ");
        String address = scanner.nextLine();

        String query = "UPDATE Hospital SET Name = ?, Address = ? WHERE Hospital_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setInt(3, hospitalId);
            pstmt.executeUpdate();
            System.out.println("Hospital updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating hospital: " + e.getMessage());
        }
    }

    private static void deleteHospital() throws SQLException {
        System.out.print("Enter hospital ID to delete: ");
        int hospitalId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Hospital WHERE Hospital_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hospitalId);
            pstmt.executeUpdate();
            System.out.println("Hospital deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting hospital: " + e.getMessage());
        }
    }

    // Existing managePatients, manageDoctors, and manageAppointments methods go here



    private static void HandlePatients() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Handle Patients");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addPatient() throws SQLException {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter patient gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter patient contact number: ");
        String contactNumber = scanner.nextLine();

        String query = "INSERT INTO Patient (Name, Age, Gender, Contact_Number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, contactNumber);
            pstmt.executeUpdate();

            // Retrieve the auto-generated patient ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int patientId = -1;
            if (generatedKeys.next()) {
                patientId = generatedKeys.getInt(1);
            }

            // Assign a doctor to the patient
            assignDoctorToPatient(patientId);
            
            System.out.println("Patient added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    private static void assignDoctorToPatient(int patientId) throws SQLException {
        System.out.print("Assign a doctor to the patient (Enter doctor ID): ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "UPDATE Patient SET Assigned_Doctor_ID = ? WHERE Patient_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, doctorId);
            pstmt.setInt(2, patientId);
            pstmt.executeUpdate();
            System.out.println("Doctor assigned to patient successfully.");
        } catch (SQLException e) {
            System.out.println("Error assigning doctor to patient: " + e.getMessage());
        }
    }

    private static void updatePatient() throws SQLException {
        System.out.print("Enter patient ID to update: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Prompt for new patient details
        System.out.print("Enter new patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new patient gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter new patient contact number: ");
        String contactNumber = scanner.nextLine();

        String query = "UPDATE Patient SET Name = ?, Age = ?, Gender = ?, Contact_Number = ? WHERE Patient_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, contactNumber);
            pstmt.setInt(5, patientId);
            pstmt.executeUpdate();

            // After updating patient details, also update assigned doctor
            assignDoctorToPatient(patientId);

            System.out.println("Patient updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    private static void viewPatients() throws SQLException {
        String query = "SELECT p.Patient_ID, p.Name, p.Age, p.Gender, p.Contact_Number, d.Doctor_ID, d.Name AS Doctor_Name, d.Specialty " +
                       "FROM Patient p LEFT JOIN Doctor d ON p.Assigned_Doctor_ID = d.Doctor_ID";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Patients List:");
            while (rs.next()) {
                System.out.println("Patient ID: " + rs.getInt("Patient_ID") +
                                   ", Name: " + rs.getString("Name") +
                                   ", Age: " + rs.getInt("Age") +
                                   ", Gender: " + rs.getString("Gender") +
                                   ", Contact Number: " + rs.getString("Contact_Number"));

                // Display assigned doctor information
                System.out.println("Assigned Doctor: " +
                                   "ID: " + rs.getInt("Doctor_ID") +
                                   ", Name: " + rs.getString("Doctor_Name") +
                                   ", Specialty: " + rs.getString("Specialty"));
                System.out.println("--------------------");
            }
        }
    }


    private static int selectDoctor() throws SQLException {
        System.out.println("Available Doctors:");
        viewDoctors(); // This method should display a list of doctors

        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        // You can add additional validation here if needed

        return doctorId;
    }

    private static void viewPatients1() throws SQLException {
        String query = "SELECT * FROM Patient";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Patients List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Patient_ID") + ", Name: " + rs.getString("Name") +
                        ", Age: " + rs.getInt("Age") + ", Gender: " + rs.getString("Gender") +
                        ", Contact Number: " + rs.getString("Contact_Number"));
            }
        }
    }

    private static void updatePatient1() throws SQLException {
        System.out.print("Enter patient ID to update: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new patient gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter new patient contact number: ");
        String contactNumber = scanner.nextLine();

        String query = "UPDATE Patient SET Name = ?, Age = ?, Gender = ?, Contact_Number = ? WHERE Patient_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, contactNumber);
            pstmt.setInt(5, patientId);
            pstmt.executeUpdate();
            System.out.println("Patient updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    private static void deletePatient() throws SQLException {
        System.out.print("Enter patient ID to delete: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Patient WHERE Patient_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.executeUpdate();
            System.out.println("Patient deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting patient: " + e.getMessage());
        }
    }

    private static void HandleDoctors() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Handle Doctors");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    viewDoctors();
                    break;
                case 3:
                    updateDoctor();
                    break;
                case 4:
                    deleteDoctor();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addDoctor() throws SQLException {
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter doctor specialty: ");
        String specialty = scanner.nextLine();
        System.out.print("Enter doctor contact number: ");
        String contactNumber = scanner.nextLine();

        String query = "INSERT INTO Doctor (Name, Specialty, Contact_Number) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialty);
            pstmt.setString(3, contactNumber);
            pstmt.executeUpdate();
            System.out.println("Doctor added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding doctor: " + e.getMessage());
        }
    }

    private static void viewDoctors() throws SQLException {
        String query = "SELECT * FROM Doctor";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Doctors List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Doctor_ID") + ", Name: " + rs.getString("Name") +
                        ", Specialty: " + rs.getString("Specialty") + ", Contact Number: " + rs.getString("Contact_Number"));
            }
        }
    }

    private static void updateDoctor() throws SQLException {
        System.out.print("Enter doctor ID to update: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new doctor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new doctor specialty: ");
        String specialty = scanner.nextLine();
        System.out.print("Enter new doctor contact number: ");
        String contactNumber = scanner.nextLine();

        String query = "UPDATE Doctor SET Name = ?, Specialty = ?, Contact_Number = ? WHERE Doctor_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialty);
            pstmt.setString(3, contactNumber);
            pstmt.setInt(4, doctorId);
            pstmt.executeUpdate();
            System.out.println("Doctor updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating doctor: " + e.getMessage());
        }
    }

    private static void deleteDoctor() throws SQLException {
        System.out.print("Enter doctor ID to delete: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Doctor WHERE Doctor_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, doctorId);
            pstmt.executeUpdate();
            System.out.println("Doctor deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting doctor: " + e.getMessage());
        }
    }

    private static void HandleAppointments() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Handle Appointments");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Update Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addAppointment();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    updateAppointment();
                    break;
                case 4:
                    deleteAppointment();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addAppointment1() {
		// TODO Auto-generated method stub
		
	}

	private static void addAppointment() throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter appointment time (HH:MM): ");
        String time = scanner.nextLine();

        // Check if doctorId exists in Doctor table
        if (!isDoctorExists(doctorId)) {
            System.out.println("Error adding appointment: Doctor with ID " + doctorId + " does not exist.");
            return;
        }

        String query = "INSERT INTO Appointment (Patient_ID, Doctor_ID, Date, Time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setString(3, date);
            pstmt.setString(4, time);
            pstmt.executeUpdate();
            System.out.println("Appointment added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding appointment: " + e.getMessage());
        }
    }

    private static boolean isDoctorExists(int doctorId) throws SQLException {
        String query = "SELECT 1 FROM Doctor WHERE Doctor_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If there's any row, doctor exists
            }
        }
    }


    private static void viewAppointments() throws SQLException {
        String query = "SELECT * FROM Appointment";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Appointments List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Appointment_ID") + ", Patient ID: " + rs.getInt("Patient_ID") +
                        ", Doctor ID: " + rs.getInt("Doctor_ID") + ", Date: " + rs.getString("Date") +
                        ", Time: " + rs.getString("Time"));
            }
        }
    }

    private static void updateAppointment() throws SQLException {
        System.out.print("Enter appointment ID to update: ");
        int appointmentId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter new appointment time (HH:MM): ");
        String time = scanner.nextLine();

        String query = "UPDATE Appointment SET Patient_ID = ?, Doctor_ID = ?, Date = ?, Time = ? WHERE Appointment_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setString(3, date);
            pstmt.setString(4, time);
            pstmt.setInt(5, appointmentId);
            pstmt.executeUpdate();
            System.out.println("Appointment updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        }
    }

    private static void deleteAppointment() throws SQLException {
        System.out.print("Enter appointment ID to delete: ");
        int appointmentId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Appointment WHERE Appointment_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, appointmentId);
            pstmt.executeUpdate();
            System.out.println("Appointment deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
            
        }
    }
}