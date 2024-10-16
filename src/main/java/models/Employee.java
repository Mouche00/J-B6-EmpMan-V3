package models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("employee")
@Table(name = "employees")
public class Employee extends User {

    protected String post;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee(String name, String phone, String address, String email, String password, LocalDate DOB, String SSN, LocalDate hiringDate, double salary, int children, int leaveBalance, String post, Department department) {
        super(name, phone, address, email, password, DOB, SSN, "employee", hiringDate, salary, children, leaveBalance);
        this.post = post;
        this.department = department;
    }

    public Employee() {
    }
}