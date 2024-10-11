package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role")
@Table(name = "users")
public abstract class User extends BaseUser {

    protected String password;

    @Column(unique = true)
    protected String SSN;

    protected LocalDate hiringDate;
    protected double salary;
    protected int children;
    protected int leaveBalance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Set<Leave> leaves;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(int leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public Set<Leave> getLeaves() {
        return leaves;
    }

    public void setLeaves(Set<Leave> leaves) {
        this.leaves = leaves;
    }

    public User(String name, String phone, String address, String email, LocalDate DOB, String password, String SSN, LocalDate hiringDate, double salary, int children, int leaveBalance) {
        super(name, phone, address, email, DOB);
        this.password = password;
        this.SSN = SSN;
        this.hiringDate = hiringDate;
        this.salary = salary;
        this.children = children;
        this.leaveBalance = leaveBalance;
    }

    public User() {
    }
}
