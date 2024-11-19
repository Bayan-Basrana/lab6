package com.example.employeemanagementsystem.Controller;

import com.example.employeemanagementsystem.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees= new ArrayList<>();
    @GetMapping("/get")
    public ResponseEntity getEmployee (){
        return ResponseEntity.status(200).body(employees);
    }

@PostMapping("/add")
    public ResponseEntity addEmployee (@RequestBody @Valid Employee employee , Errors errors){
     if (errors.hasErrors()) {
         String message = errors.getFieldError().getDefaultMessage();
         return ResponseEntity.status(400).body(message);
     }
     employees.add(employee);
     return ResponseEntity.status(200).body("Employee added Successfully");
    }

@PutMapping("/update/{id}")
    public ResponseEntity updateEmployee (@PathVariable String id , @RequestBody @Valid Employee employee,Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for(Employee e :employees){
            if (e.getId().equalsIgnoreCase(id)){
                e.setName(employee.getName());
                e.setEmail(employee.getEmail());
                e.setPhone(employee.getPhone());
                e.setAge(employee.getAge());
                e.setPosition(employee.getPosition());
                e.setOnLeave(employee.isOnLeave());
                e.setHireDate(employee.getHireDate());
                e.setAnnualLeave(employee.getAnnualLeave());
            }
        }return ResponseEntity.status(200).body("Employee updated Successfully");
    }

@DeleteMapping("/delete/{id}")
public ResponseEntity deleteEmployee (@PathVariable String id){
        employees.removeIf(employee -> employee.getId().equalsIgnoreCase(id));
return ResponseEntity.status(200).body("Employee deleted Successfully");
}

@GetMapping("/searchByPosition/{position}")
public ResponseEntity searchByPosition (@PathVariable String position ){
        ArrayList<Employee> filterEmployee=new ArrayList<>();
    for (Employee e:employees){
        if (e.getPosition().equalsIgnoreCase(position)){
            filterEmployee.add(e);
        }
        }return ResponseEntity.status(200).body(filterEmployee);
}

@GetMapping("/EmployeesByAgeRange/{minAge}/{maxAge}")
public ResponseEntity EmployeesByAgeRange (@PathVariable int minAge, @PathVariable int maxAge){

       ArrayList<Employee> employeeByAgeRange =new ArrayList<>();
        for(Employee e:employees) {
           if (e.getAge()>= minAge && e.getAge()<=maxAge)
               employeeByAgeRange.add(e);
       }
           return ResponseEntity.status(200).body(employeeByAgeRange);
}

@PutMapping("/applyForLeave/{id}")
public ResponseEntity applyForLeave(@PathVariable String id) {
    for (Employee emp : employees) {
        if (emp.getId().equals(id)) {
            if (emp.isOnLeave()) {
                return ResponseEntity.status(400).body("Employee is already on leave.");
            }
            if (emp.getAnnualLeave() <= 0) {
                return ResponseEntity.status(400).body("No annual leave remaining.");
            }
            emp.setOnLeave(true);
            emp.setAnnualLeave(emp.getAnnualLeave() - 1);
            return ResponseEntity.status(200).body("Leave applied successfully!");
        }
    }
    return ResponseEntity.status(404).body("Employee not found.");
}


@GetMapping("/noAnnual")
    public ResponseEntity getEmployeesWithNoAnnualLeave() {
    ArrayList<Employee> noLeaveEmployees = new ArrayList<>();
    for (Employee emp : employees) {
        if (emp.getAnnualLeave() == 0) {
            noLeaveEmployees.add(emp);
        }
    }
        return ResponseEntity.status(200).body(noLeaveEmployees);
    }

@PutMapping("/promote/{id}/{requesterID}")
public ResponseEntity PromoteEmployee (@PathVariable String id,@PathVariable String requesterID ){
Employee requester = null;
        for (Employee e :employees){
    if (e.getId().equalsIgnoreCase(requesterID) &&  e.getPosition().equalsIgnoreCase("supervisor")){
requester=e;
    }

        if(requester==null)
    return ResponseEntity.status(400).body("Requester must be a supervisor.");
}
for(Employee e: employees){
    if(e.getId().equalsIgnoreCase(id)){
        if (e.getAge()<30) {
            return ResponseEntity.status(400).body("the employee's age is at least 30 years");
        }
        if (e.isOnLeave()==true){
            return ResponseEntity.status(400).body("the employee is currently on leave");
        }

    } e.setPosition("supervisor");
}    return ResponseEntity.status(200).body("promote Employee");

}

}
