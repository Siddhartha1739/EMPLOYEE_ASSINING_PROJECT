package Service;
import DAO.Database;
import MODELS.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class admin_service {

    public static void registerProject(String projectName,Admin admin) {
        Database.projects(admin);
        admin.projects.add(projectName);
    }

    public static void registerEmployee(String username, String password, String name, Admin admin) {
        admin.unapprovedEmployees.add(new Employee(username, password, name));

    }

    public static void approveEmployeeRegistration(String username,Admin admin) {
        Database.employees(admin);
       admin.unapprovedEmployees.stream()
                .filter(emp -> emp.getUsername().equals(username))
                .findFirst()
                .ifPresent(emp -> {
                    emp.setApproved(true);
                    admin.employees.add(emp);
                   admin.employees.stream().forEach(emp1->System.out.println(emp1.name));
                   admin.unapprovedEmployees.remove(emp);
                    System.out.println("Employee account approved: " + username);
                });
    }

    public static boolean adminLogin(Admin admin, Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.next();
        System.out.println("Enter admin password:");
        String password = scanner.next();

        if (admin.isAdminValid(username, password)) {
            System.out.println("Admin login successful.");
            return true;
        } else {
            System.out.println("Invalid username or password for admin.");
            return false;
        }
    }
    public static void addAttendance(String employeeName, String date,Admin admin) {
        admin.attendance.computeIfAbsent(employeeName, k -> new ArrayList<>()).add(date);
    }

    public static void approveLeave(String employeeName, String leaveRequest,Admin admin) {
        admin.leaveRequests.put(employeeName, leaveRequest);
        System.out.println("Leave approved for " + employeeName);
    }

    public static void viewEmployeesByProject(String projectName,Admin admin) {
        System.out.println("Employees working on project " + projectName + ":");
        admin.employees.stream()
                .filter(employee -> employee.getProjects().contains(projectName))
                .forEach(employee -> System.out.println(employee.getName()));
    }

    public static void viewLeaveInCurrentProject(String projectName,Admin admin) {
        System.out.println("Employees on leave in project " + projectName + " :");
       admin.employees.stream()
                .filter(employee -> employee.getProjects().contains(projectName))
                .forEach(employee -> employee.getApprovedRequests().stream()
                        .map(leave -> leave.split(", "))
                        .forEach(parts -> {
                            String fromDate = parts[0].split(": ")[1];
                            String toDate = parts[1].split(": ")[1];
                            LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            LocalDate to = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            LocalDate now = LocalDate.now();
                            if ((from.getMonth() == now.getMonth() && from.getYear() == now.getYear()) ||
                                    (to.getMonth() == now.getMonth() && to.getYear() == now.getYear())) {
                                System.out.println(employee.getName() + " - Leave from " + fromDate + " to " + toDate);
                            }
                        }));
    }


    static boolean isEmployee(String emp, Admin admin) {
        return admin.getEmployees().stream()
                .anyMatch(employee -> employee.getName().equals(emp));
    }


    public static void assignProjectToEmployee(String employeeName, String projectName,Admin admin) {
        admin.employees.stream()
                .filter(employee -> employee.getName().equals(employeeName))
                .findFirst()
                .ifPresent(employee -> {
                    employee_service.addProject(projectName,employee);
                    System.out.println("Project " + projectName + " assigned to employee " + employeeName);
                });
    }

    public static void adminMenu(Admin admin, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Admin Menu:");
            System.out.println("1. Register Project");
            System.out.println("2. Approve an Employee");
            System.out.println("3. Add Attendance");
            System.out.println("4. Leave approval");
            System.out.println("5. View Employees based on project");
            System.out.println("6. View who are in leave in current project:");
            System.out.println("7. View project details which worked/ongoing for an employee");
            System.out.println("8. Log out");
            System.out.println("9. Assign projects");
            System.out.println("10.Display projects");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Register Project
                    System.out.println("Enter project name:");
                    String projectName = scanner.next();
                    admin_service.registerProject(projectName,admin);
                    System.out.println("Project registered: " + projectName);
                    break;
                case 2:
                    // Approve an Employee
                    System.out.println("Unapproved Employees:");
                    admin.getUnapprovedEmployees().forEach(employee -> System.out.println(employee.getName()));
                    System.out.println("Enter employee name to approve:");
                    String empName = scanner.next();
                    admin_service.approveEmployeeRegistration(empName,admin);
                    break;
                case 3:
                    //Attendance
                    System.out.println("Enter employee name:");
                    String empNameForAttendance = scanner.next();
                    if (isEmployee(empNameForAttendance, admin)) {
                        System.out.println("Enter date (format: dd-mm-yyyy):");
                        String date = scanner.next();
                        admin_service.addAttendance(empNameForAttendance, date,admin);
                        System.out.println("Attendance for " + empNameForAttendance + " is added on " + date);
                    } else {
                        System.out.println("Employee Not Found !!!");
                    }
                    break;
                case 4:
                    System.out.println("Leave Requests:");
                    int flag = 0;
                    for (Employee employee : admin.getEmployees()) {
                        if (employee.getLeaveRequests().isEmpty()) continue;
                        flag = 1;
                        System.out.println("Employee: " + employee.getName());
                        employee.getLeaveRequests().forEach(request -> System.out.println("  Leave request: " + request));
                    }
                    if (flag == 1) {
                        System.out.println("Enter employee name to approve leave:");
                        String empNameForLeave = scanner.next();
                        Employee selectedEmployee = admin.getEmployees().stream()
                                .filter(employee -> employee.getName().equals(empNameForLeave))
                                .findFirst()
                                .orElse(null);
                        if (selectedEmployee != null) {
                            List<String> leaveRequests = selectedEmployee.getLeaveRequests();
                            for (int i = 0; i < leaveRequests.size(); i++) {
                                System.out.println((i + 1) + ". " + leaveRequests.get(i));
                            }
                            System.out.println("Enter the number of the leave request to approve:");
                            int leaveIndex = scanner.nextInt();
                            if (leaveIndex > 0 && leaveIndex <= leaveRequests.size()) {
                                String leaveRequest = leaveRequests.get(leaveIndex - 1);
                                employee_service.addleaveRequest(leaveRequest,selectedEmployee);
                                admin_service.approveLeave(empNameForLeave, leaveRequest,admin);
                            } else {
                                System.out.println("Invalid leave request number.");
                            }
                        } else {
                            System.out.println("Employee not found.");
                        }
                    } else {
                        System.out.println("No Leave Requests Found");
                    }
                    break;
                case 5:
                    System.out.println("Enter project name:");
                    projectName = scanner.next();
                    admin_service.viewEmployeesByProject(projectName,admin);
                    break;
                case 6:
                    System.out.println("Enter project name:");
                    String currentProject = scanner.next();
                    if (admin.getProjects().contains(currentProject)) {
                        admin_service.viewLeaveInCurrentProject(currentProject,admin);
                    } else {
                        System.out.println("Project is not found");
                    }
                    break;
                case 7:
                    System.out.println("Enter employee name:");
                    String empNameForProjectDetails = scanner.next();
                    admin.getEmployees().stream()
                            .filter(employee -> employee.getName().equals(empNameForProjectDetails))
                            .findFirst()
                            .ifPresentOrElse(employee -> {
                                System.out.println("Projects for " + empNameForProjectDetails + ":");
                                employee.getProjects().forEach(System.out::println);
                            }, () -> System.out.println("Employee not found."));
                    break;
                case 8:
                    // Log out
                    exit = true;
                    System.out.println("Logged out.");
                    break;
                case 9:
                    System.out.println("Registered Projects:");
                    admin.getProjects().forEach(System.out::println);
                    System.out.println("Approved Employees:");
                    admin.getEmployees().stream()
                            .filter(Employee::isApproved)
                            .map(Employee::getName)
                            .findFirst()
                            .ifPresentOrElse(System.out::println, () -> System.out.println("No approved employees found."));

                    System.out.println("Enter project name:");
                    projectName = scanner.next();
                    System.out.println("Enter employee name:");
                    String employeeName = scanner.next();
                    admin_service.assignProjectToEmployee(employeeName, projectName,admin);
                    break;
                case 10:
                    admin.getProjects().stream().forEach(System.out::println);
                    admin.getEmployees().stream().forEach(emp->System.out.println(emp.name+" "+emp.password+" "+emp.username));
                break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}

