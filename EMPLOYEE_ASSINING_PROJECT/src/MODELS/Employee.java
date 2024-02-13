package MODELS;

import Service.employee_service;

import java.util.ArrayList;
import java.util.List;

public class Employee  {
    public String username;
    public String password;

    public  String name;
    public boolean approved;
    public List<String> projects;
    public List<String> leaveRequests;
    public List<String> approvedRequests;

    public Employee(String username, String password, String name) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.approved = false;
        projects = new ArrayList<>();
        leaveRequests = new ArrayList<>();
        approvedRequests = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public  String getName() {
        return name;
    }
    public List<String> getProjects() {
        return projects;
    }
    public List<String> getApprovedRequests() {
        return approvedRequests;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }


    public boolean isApproved() {
        return approved;
    }
    public List<String> getLeaveRequests() {
        return leaveRequests;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public void setLeaveRequests(List<String> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }

    public void setApprovedRequests(List<String> approvedRequests) {
        this.approvedRequests = approvedRequests;
    }
}
