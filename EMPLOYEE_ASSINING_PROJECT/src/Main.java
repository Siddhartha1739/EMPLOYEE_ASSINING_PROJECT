import MODELS.*;
import Service.*;
import DAO.Database;

import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
       Admin admin = new Admin("admin", "admin123");
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        boolean go=true;
        while (go) {
            System.out.println("Choose an option:");
            System.out.println("1. Admin Login");
            System.out.println("2. Employee Register");
            System.out.println("3. Employee Login");
            System.out.println("4.Exit  ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    loggedIn =admin_service.adminLogin(admin, scanner);
                    if (loggedIn) {
                        admin_service.adminMenu(admin, scanner);
                    }
                    break;
                case 2:
                    Database.employeeRegister(admin, scanner);
                    break;
                case 3:
                   Employee employee=employee_service.employeeLogin(admin, scanner);
                    if (employee != null && employee.isApproved()) {
                        employee_service.employeeMenu(employee, scanner);
                    } else {
                        System.out.println("Employee account not approved or not found.");
                    }
                    break;
                case 4:go=false;break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

