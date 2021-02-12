package spring.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.entities.Employee;
import spring.rest.exception_handling.EmployeeIncorrectData;
import spring.rest.exception_handling.NoSuchEmployeeException;
import spring.rest.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        return allEmployees;
    }

    @GetMapping(path = "/employees/{id}")
    public Employee showEmployee(@PathVariable("id") int id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null)
            throw new NoSuchEmployeeException("There is no employee with ID = " + id + " in DataBase!");
        return employee;
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(NoSuchEmployeeException e) {
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(Exception e) {
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
