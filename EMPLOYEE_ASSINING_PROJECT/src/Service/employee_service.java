package Service;

import MODELS.Admin;
import MODELS.Employee;


import java.util.Scanner;

public class employee_service {

    public static Employee employeeLogin(Admin admin, Scanner scanner) {
        System.out.println("Enter employee username:");
        String username = scanner.next();
        System.out.println("Enter employee password:");
        String password = scanner.next();


        return admin.getEmployees().stream()
                .filter(emp -> username.equals(emp.getUsername()) && password.equals(emp.getPassword()) )
                .findFirst()
                .orElse(null);
    }

    public static void addProject(String project,Employee employee) {

        employee.projects.add(project);
    }


    public static void applyForLeave(String fromDate, String toDate, String reason,Employee employee) {
        String leaveRequest = "From: " + fromDate + ", To: " + toDate + ", Reason: " + reason;
        employee.leaveRequests.add(leaveRequest);
        System.out.println("Leave request submitted for approval.");
    }

    public static void viewAssignedProjects(Employee employee) {
        if (!employee.projects.isEmpty()) {
            System.out.println("Assigned Projects:");
            employee.projects.forEach(System.out::println);
        } else {
            System.out.println("No projects assigned.");
        }
    }
    public static void addleaveRequest(String request,Employee employee) {
        employee.approvedRequests.add(request);
        employee.leaveRequests.remove(request);
    }

    public static void employeeMenu(Employee employee, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Employee Menu:");
            System.out.println("1. Apply for Leave");
            System.out.println("2. View Assigned Projects");
            System.out.println("3. Log out");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter leave start date (format: dd-mm-yyyy):");
                    String fromDate = scanner.next();
                    System.out.println("Enter leave end date (format: dd-mm-yyyy):");
                    String toDate = scanner.next();
                    System.out.println("Enter reason for leave:");
                    scanner.nextLine();  // consume newline left-over
                    String reason = scanner.nextLine();
                    employee_service.applyForLeave(fromDate, toDate, reason,employee);
                    break;
                case 2:
                    employee_service.viewAssignedProjects(employee);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

}
