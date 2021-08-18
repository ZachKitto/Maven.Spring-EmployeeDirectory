package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.entities.Department;
import io.zipcoder.persistenceapp.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/API")
public class Controller {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(value = "/create/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @PostMapping(value = "/create/department")
    public ResponseEntity<Department> createEmployee(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.create(department), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/employee{id}/manager")
    public ResponseEntity<Employee> updateEmployeeManager(@PathVariable Long id, @RequestBody Employee newManager) {
        return new ResponseEntity<>(employeeService.updateManager(id, newManager), HttpStatus.OK);
    }

    @PutMapping(value = "/update/employee{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee newEmployeeData) {
        return new ResponseEntity<>(employeeService.update(id, newEmployeeData), HttpStatus.OK);
    }

    @PutMapping(value = "/update/department{id}/{name}")
    public ResponseEntity<Department> updateDepartmentName(@PathVariable Long id, @PathVariable String name) {
        return new ResponseEntity<>(departmentService.updateName(id, name), HttpStatus.OK);
    }

    @GetMapping(value = "/find/employeesUnderManager")
    public ResponseEntity<List<Employee>> findEmployeesUnderManager(@RequestBody Employee manager) {
        return new ResponseEntity<>(employeeService.findEmployeesByManager(manager), HttpStatus.OK);
    }

    @GetMapping(value = "/find/employeeHierarchy")
    public ResponseEntity<List<Employee>> findEmployeeHierarchy(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.findHierarchyOfEmployee(employee), HttpStatus.OK);
    }

    @GetMapping(value = "/find/employeesWithNoManager")
    public ResponseEntity<List<Employee>> findEmployeesWithNoManager() {
        return new ResponseEntity<>(employeeService.findEmployeesWithNoManager(), HttpStatus.OK);
    }

    @GetMapping(value = "/find/employeesByDepartment")
    public ResponseEntity<List<Employee>> findEmployeesByDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(employeeService.findEmployeesOfDepartment(department.getId().intValue()), HttpStatus.OK);
    }

    @GetMapping(value = "/find/all/employeesByManager")
    public ResponseEntity<List<Employee>> findAllEmployeesByManager(@RequestBody Employee manager) {
        return new ResponseEntity<>(employeeService.findAllEmployeesByManager(manager), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/employee")
    public ResponseEntity<Employee> deleteEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.delete(employee), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/employees")
    public ResponseEntity<List<Employee>> deleteEmployees(@RequestBody List<Employee> employees) {
        return new ResponseEntity<>(employeeService.deleteEmployees(employees), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/employeesByDepartment")
    public ResponseEntity<List<Employee>> deleteEmployeesByDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(employeeService.deleteByDepartment(department.getId().intValue()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/employeesByManager/all")
    public ResponseEntity<List<Employee>> deleteAllEmployeesByManager(@RequestBody Employee manager) {
        return new ResponseEntity<>(employeeService.deleteAllEmployeesByManager(manager), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/employeesByManager")
    public ResponseEntity<List<Employee>> deleteEmployeesByManager(@RequestBody Employee manager) {
        return new ResponseEntity<>(employeeService.deleteDirectEmployeesByManager(manager), HttpStatus.OK);
    }

    @GetMapping(value = "/find/employee{attribute}")
    public ResponseEntity<String> findAttribute(@RequestBody Employee employee, @PathVariable String attribute) {
        return new ResponseEntity<>(employeeService.findAttribute(employee, attribute), HttpStatus.OK);
    }

    @PutMapping(value = "/merge/department")
    public ResponseEntity<String> mergeDepartment(@RequestBody Department departmentBeingMergedInto, @RequestBody Department departmentToMerge) {
        return new ResponseEntity<>(employeeService.mergeDepartments(departmentBeingMergedInto, departmentToMerge), HttpStatus.OK);
    }
}
