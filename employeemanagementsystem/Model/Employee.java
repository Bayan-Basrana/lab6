package com.example.employeemanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "your ID is empty")
    @Size(min=2,message = "the ID must be more than 2")
    private String id;
    @NotEmpty(message = "your name is empty")
    @Size(min=4,message = "the name must be more than 4")
@Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only characters.")
    private String name;
    @Email(message = "Enter a valid Email")
    private String email;
    @Pattern(regexp="^05\\d{8}$", message = "Phone number must start with '05' and be exactly 10 digits.")
    private String phone;
    @NotNull(message = "your age is empty")
    @Min(value = 25,message = "The age must be greater than or equal to 25")
    private int age;
    @NotEmpty(message = "the position is empty")
    @Pattern(regexp = "supervisor|coordinator" , message = "the position Must be either supervisor or coordinator only")
    private String position;

    private boolean onLeave=false;
    @JsonFormat(pattern = "yyyy-MM-dd")
@NotNull(message = "hire Date is empty")
    @PastOrPresent(message = " hireDate can not be in the future")
    private LocalDate hireDate;
    @NotNull(message = "annualLeave is empty")
    @Positive(message = "annualLeave must be positive number")
    private int annualLeave;
}
