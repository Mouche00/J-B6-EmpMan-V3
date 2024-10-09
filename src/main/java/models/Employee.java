package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@DiscriminatorValue("employee")
@Table(name = "employees")
public class Employee extends User {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    protected Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

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