package MODELS;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Admin {
    public String username;
    public List<Employee> unapprovedEmployees;
    public String password;
    public List<Employee> employees;
    public List<String> projects;
    public Map<String, List<String>> attendance;

    public Map<String, String> leaveRequests;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        employees = new ArrayList<>();
        projects = new ArrayList<>();
        attendance = new HashMap<>();
        leaveRequests = new HashMap<>();
        unapprovedEmployees = new ArrayList<>();
    }
    public List<Employee> getUnapprovedEmployees() {
        return unapprovedEmployees;
    }
    public  boolean isAdminValid(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    public List<Employee> getEmployees() {
        return employees;
    }

    public List<String> getProjects() {
        return projects;
    }

    public Map<String, List<String>> getAttendance() {
        return attendance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUnapprovedEmployees(List<Employee> unapprovedEmployees) {
        this.unapprovedEmployees = unapprovedEmployees;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public void setAttendance(Map<String, List<String>> attendance) {
        this.attendance = attendance;
    }

    public Map<String, String> getLeaveRequests() {
        return leaveRequests;
    }

    public void setLeaveRequests(Map<String, String> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }
}
