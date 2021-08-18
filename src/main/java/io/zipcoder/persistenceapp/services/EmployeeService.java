package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.entities.Department;
import io.zipcoder.persistenceapp.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public Employee findById(Long id) {
        return repository.findOne(id);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateManager(Long id, Employee newManager) {
        Employee employee = findById(id);
        employee.setManager(newManager);
        return repository.save(employee);
    }

    public Employee update(Long id, Employee newEmployeeData) {
        Employee employee = findById(id);
        employee.setFirstName(newEmployeeData.getFirstName());
        employee.setLastName(newEmployeeData.getLastName());
        employee.setTitle(newEmployeeData.getTitle());
        employee.setPhoneNumber(newEmployeeData.getPhoneNumber());
        employee.setEmail(newEmployeeData.getEmail());
        employee.setHireDate(newEmployeeData.getHireDate());
        employee.setManager(newEmployeeData.getManager());
        employee.setDepartmentNumber(newEmployeeData.getDepartmentNumber());
        return repository.save(employee);
    }

    public List<Employee> findEmployeesByManager(Employee manager) {
        List<Employee> listOfEmployees = new ArrayList<>();
        findAll()
                .stream()
                .forEach(employee -> {
                    if (employee.getManager().equals(manager)) {
                        listOfEmployees.add(employee);
                    }
                });
        return listOfEmployees;
    }

    public List<Employee> findHierarchyOfEmployee (Employee employee) {
        List<Employee> hierarchy = new ArrayList<>();
        while (employee.getManager() != null) {
            hierarchy.add(employee.getManager());
        }
        return hierarchy;
    }

    public List<Employee> findEmployeesWithNoManager() {
        List<Employee> listOfEmployees = new ArrayList<>();
        findAll()
                .stream()
                .forEach(employee -> {
                    if (employee.getManager() == null) {
                        listOfEmployees.add(employee);
                    }
                });
        return listOfEmployees;
    }

    public List<Employee> findEmployeesOfDepartment(Integer departmentNumber) {
        List<Employee> listOfEmployees = new ArrayList<>();
        findAll()
                .stream()
                .forEach(employee -> {
                    if (employee.getDepartmentNumber() == departmentNumber) {
                        listOfEmployees.add(employee);
                    }
                });
        return listOfEmployees;
    }

    public List<Employee> findAllEmployeesByManager(Employee manager) {
        List<Employee> listOfEmployees = new ArrayList<>();
        return findAll()
                .stream()
                .filter(employee -> findHierarchyOfEmployee(employee).contains(manager))
                .collect(Collectors.toList());
    }

    public Employee delete(Employee employee) {
        repository.delete(employee);
        return employee;
    }

    public List<Employee> deleteEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            delete(employee);
        }
        return employees;
    }

    public List<Employee> deleteByDepartment(Integer departmentNumber) {
        List<Employee> listOfEmployees = new ArrayList<>();
        for (Employee employee : findAll()) {
            if (employee.getDepartmentNumber() == departmentNumber) {
                listOfEmployees.add(employee);
                delete(employee);
            }
        }
        return listOfEmployees;
    }

    public List<Employee> deleteAllEmployeesByManager(Employee manager) {
        List<Employee> listOfEmployees = new ArrayList<>();
        for (Employee employee : findAllEmployeesByManager(manager)) {
            listOfEmployees.add(employee);
            delete(employee);
        }
        return listOfEmployees;
    }

    public List<Employee> deleteDirectEmployeesByManager(Employee manager) {
        List<Employee> listOfEmployees = new ArrayList<>();
        for (Employee employee : findEmployeesByManager(manager)) {
            listOfEmployees.add(employee);
            for (Employee employeeWithNoManager : findEmployeesByManager(employee)) {
                employeeWithNoManager.setManager(employee.getManager());
            }
            delete(employee);

        }
        return listOfEmployees;
    }

    public String findAttribute(Employee employee, String attribute) {
        switch (attribute) {
            case "id":
                return employee.getId().toString();
            case "firstName":
                return employee.getFirstName();
            case "lastName":
                return employee.getLastName();
            case "title":
                return employee.getTitle();
            case "phoneNumber":
                return employee.getPhoneNumber();
            case "email":
                return employee.getEmail();
            case "hireDate":
                return employee.getHireDate();
            case "manager":
                return employee.getManager().toString();
            case "departmentNumber":
                return employee.getDepartmentNumber().toString();
            default:
                return "Attribute not found";
        }
    }

    public String mergeDepartments(Department departmentBeingMergedInto, Department departmentBeingMerged) {
        Employee juniorManager = departmentBeingMerged.getManager();
        Employee seniorManager = departmentBeingMergedInto.getManager();
        juniorManager.setManager(seniorManager);
        findAllEmployeesByManager(juniorManager)
                .stream()
                .forEach(employee -> employee.setDepartmentNumber(departmentBeingMergedInto.getId().intValue()));
        return String.format("Department %s successfully merged into Department %s", departmentBeingMerged.getName(), departmentBeingMergedInto.getName());
    }
}
