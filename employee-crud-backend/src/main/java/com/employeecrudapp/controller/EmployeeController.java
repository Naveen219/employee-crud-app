package com.employeecrudapp.controller;

import com.employeecrudapp.entity.Employee;
import com.employeecrudapp.exception.ResourceNotFoundException;
import com.employeecrudapp.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        "http://localhost:4200"
)
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }
    // add an employee
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepo.save(employee);
    }

    // get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Employee doesn't exist with the id" + id));
        return ResponseEntity.ok(employee);
    }

    //  update the employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Employee doesn't exist with the id" + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updatedEmployee = this.employeeRepo.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee doesn't exist with the id" + id));
        employeeRepo.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
