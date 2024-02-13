package DAO;

import MODELS.Admin;
import MODELS.Employee;
import Service.admin_service;

import java.util.Scanner;

public class Database {
    public static void employeeRegister(Admin admin, Scanner scanner) {

        admin_service.registerEmployee("sid", "123", "sid",admin);
        admin_service.registerEmployee("anil","123","anil",admin);
        admin_service.registerEmployee("naveen","123","naveen",admin);
        admin_service.registerEmployee("rahul","123","rahul",admin);
        System.out.println("Employee registration submitted for approval.");
    }
    public static void projects(Admin admin){
      admin.projects.add("Project_K");
        admin.projects.add("Project_L");
        admin.projects.add("Project_M");
        admin.projects.add("Project_N");
        admin.projects.add("Project_P");
    }
    public static void employees(Admin admin){

        admin.employees.add(new Employee("rahul","123","rahul"));
        admin.employees.add(new Employee("ajay","123","ajay"));
        admin.employees.add(new Employee("ram","123","ram"));
        admin.employees.add(new Employee("shiva","123","shiva"));
        admin.employees.add(new Employee("anil","123","anil"));
        admin.employees.add(new Employee("naveen","123","naveen"));
        admin.employees.add(new Employee("Bhanu","123","Bhanu"));
        admin.employees.add(new Employee("Sony","123","Sony"));
        admin.employees.add(new Employee("Sohail","123","Sohail"));
        admin.employees.add(new Employee("arjun","123","arjun"));
    }
}
