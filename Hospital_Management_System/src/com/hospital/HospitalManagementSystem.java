package com.hospital;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_management_systemdb";
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
                System.out.println("5. Handle Admitted In");
                System.out.println("6. Exit");

                System.out.print("Select an operation you want to perform: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        handleHospitals();
                        break;
                    case 2:
                        handlePatients();
                        break;
                    case 3:
                        handleDoctors();
                        break;
                    case 4:
                        handleAppointments();
                        break;
                    case 5:
                        handleAdmittedIn();
                        break;
                    case 6:
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

    private static void handleHospitals() throws SQLException {
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

        String query = "INSERT INTO Hospital (Hospital_name, Hospital_Add) VALUES (?, ?)";
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
                System.out.println("ID: " + rs.getInt("Hospital_Id") + ", Name: " + rs.getString("Hospital_name") +
                        ", Address: " + rs.getString("Hospital_Add"));
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

        String query = "UPDATE Hospital SET Hospital_name = ?, Hospital_Add = ? WHERE Hospital_Id = ?";
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

        String query = "DELETE FROM Hospital WHERE Hospital_Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hospitalId);
            pstmt.executeUpdate();
            System.out.println("Hospital deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting hospital: " + e.getMessage());
        }
    }

    private static void handlePatients() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Handle Patients");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
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
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();

        String query = "INSERT INTO Patients (P_Name, Contact_No, Email, Age, Gender) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactNumber);
            pstmt.setString(3, email);
            pstmt.setInt(4, age);
            pstmt.setString(5, gender);
            pstmt.executeUpdate();
            System.out.println("Patient added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    private static void viewPatients() throws SQLException {
        String query = "SELECT * FROM Patients";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Patients List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Patient_ID") + ", Name: " + rs.getString("P_Name") +
                        ", Contact Number: " + rs.getString("Contact_No") + ", Email: " + rs.getString("Email") +
                        ", Age: " + rs.getInt("Age") + ", Gender: " + rs.getString("Gender"));
            }
        }
    }


    private static void updatePatient() throws SQLException {
        System.out.print("Enter patient ID to update: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new gender: ");
        String gender = scanner.nextLine();

        String query = "UPDATE Patients SET P_Name = ?, Contact_No = ?, Email = ?, Age = ?, Gender = ? WHERE Patient_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactNumber);
            pstmt.setString(3, email);
            pstmt.setInt(4, age);
            pstmt.setString(5, gender);
            pstmt.setInt(6, patientId);
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

        String query = "SELECT P_Name, Age, Gender FROM Patients WHERE Patient_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String patientName = rs.getString("P_Name");
                int age = rs.getInt("Age");
                String gender = rs.getString("Gender");

                String deleteQuery = "DELETE FROM Patients WHERE Patient_ID = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                    deleteStmt.setInt(1, patientId);
                    deleteStmt.executeUpdate();
                    System.out.println("Patient deleted successfully:");
                    System.out.println("Name: " + patientName + ", Age: " + age + ", Gender: " + gender);
                } catch (SQLException e) {
                    System.out.println("Error deleting patient: " + e.getMessage());
                }
            } else {
                System.out.println("Patient with ID " + patientId + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving patient details: " + e.getMessage());
        }
    }


    private static void handleDoctors() throws SQLException {
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
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Enter contact details: ");
        String contactDetails = scanner.nextLine();

        String query = "INSERT INTO Doctors (D_name, D_Specialization, Contact_Details) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialization);
            pstmt.setString(3, contactDetails);
            pstmt.executeUpdate();
            System.out.println("Doctor added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding doctor: " + e.getMessage());
        }
    }

    private static void viewDoctors() throws SQLException {
        String query = "SELECT * FROM Doctors";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Doctors List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Doctor_ID") + ", Name: " + rs.getString("D_name") +
                        ", Specialization: " + rs.getString("D_Specialization") + ", Contact Details: " + rs.getString("Contact_Details"));
            }
        }
    }

    private static void updateDoctor() throws SQLException {
        System.out.print("Enter doctor ID to update: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new doctor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Enter new contact details: ");
        String contactDetails = scanner.nextLine();

        String query = "UPDATE Doctors SET D_name = ?, D_Specialization = ?, Contact_Details = ? WHERE Doctor_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialization);
            pstmt.setString(3, contactDetails);
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

        String query = "DELETE FROM Doctors WHERE Doctor_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, doctorId);
            pstmt.executeUpdate();
            System.out.println("Doctor deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting doctor: " + e.getMessage());
        }
    }

    private static void handleAppointments() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Handle Appointments");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Update Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    scheduleAppointment();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    updateAppointment();
                    break;
                case 4:
                    cancelAppointment();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void scheduleAppointment() throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter appointment time (HH:MM:SS): ");
        String time = scanner.nextLine();
        System.out.print("Enter status: ");
        String status = scanner.nextLine();

        String query = "INSERT INTO Appointments (Patient_ID, Doctor_ID, Appointment_Time, Appointment_Date, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setTime(3, Time.valueOf(time));
            pstmt.setDate(4, Date.valueOf(date));
            pstmt.setString(5, status);
            pstmt.executeUpdate();
            System.out.println("Appointment scheduled successfully.");
        } catch (SQLException e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    private static void viewAppointments() throws SQLException {
        String query = "SELECT * FROM Appointments";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Appointments List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Appointment_ID") + ", Patient ID: " + rs.getInt("Patient_ID") +
                        ", Doctor ID: " + rs.getInt("Doctor_ID") + ", Date: " + rs.getDate("Appointment_Date") +
                        ", Time: " + rs.getTime("Appointment_Time") + ", Status: " + rs.getString("Status"));
            }
        }
    }

    private static void updateAppointment() throws SQLException {
        System.out.print("Enter appointment ID to update: ");
        int appointmentId = scanner.nextInt();
        System.out.print("Enter new patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter new doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter new appointment time (HH:MM:SS): ");
        String time = scanner.nextLine();
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();

        String query = "UPDATE Appointments SET Patient_ID = ?, Doctor_ID = ?, Appointment_Time = ?, Appointment_Date = ?, Status = ? WHERE Appointment_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setTime(3, Time.valueOf(time));
            pstmt.setDate(4, Date.valueOf(date));
            pstmt.setString(5, status);
            pstmt.setInt(6, appointmentId);
            pstmt.executeUpdate();
            System.out.println("Appointment updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        }
    }

    private static void cancelAppointment() throws SQLException {
        System.out.print("Enter appointment ID to cancel: ");
        int appointmentId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, appointmentId);
            pstmt.executeUpdate();
            System.out.println("Appointment canceled successfully.");
        } catch (SQLException e) {
            System.out.println("Error canceling appointment: " + e.getMessage());
        }
    }

    private static void handleAdmittedIn() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Handle AdmittedIn");
            System.out.println("1. Admit Patient");
            System.out.println("2. View Admitted Patients");
            System.out.println("3. Discharge Patient");
            System.out.println("4. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    admitPatient();
                    break;
                case 2:
                    viewAdmittedPatients();
                    break;
                case 3:
                    dischargePatient();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void admitPatient() throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter hospital ID: ");
        int hospitalId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "INSERT INTO AdmittedIn (Patient_ID, Hospital_Id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, hospitalId);
            pstmt.executeUpdate();
            System.out.println("Patient admitted successfully.");
        } catch (SQLException e) {
            System.out.println("Error admitting patient: " + e.getMessage());
        }
    }

    private static void viewAdmittedPatients() throws SQLException {
        String query = "SELECT * FROM AdmittedIn";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Admitted Patients List:");
            while (rs.next()) {
                System.out.println("Patient ID: " + rs.getInt("Patient_ID") + ", Hospital ID: " + rs.getInt("Hospital_Id"));
            }
        }
    }

    private static void dischargePatient() throws SQLException {
        System.out.print("Enter patient ID to discharge: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter hospital ID: ");
        int hospitalId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM AdmittedIn WHERE Patient_ID = ? AND Hospital_Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, hospitalId);
            pstmt.executeUpdate();
            System.out.println("Patient discharged successfully.");
        } catch (SQLException e) {
            System.out.println("Error discharging patient: " + e.getMessage());
        }
    }
}
