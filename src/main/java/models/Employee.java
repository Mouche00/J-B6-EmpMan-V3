package models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("employee")
@Table(name = "employees")
public class Employee extends User {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}