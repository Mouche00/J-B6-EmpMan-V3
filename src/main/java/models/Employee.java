package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@DiscriminatorValue("employee")
@Table(name = "employees")
public class Employee extends User {

    public Employee(String name, String email, String phone, String post, Department department) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.post = post;
        this.department = department;
    }

    public Employee() {
    }
}