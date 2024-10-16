package models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("admin")
@Table(name = "admins")
public class Admin extends User {

    public Admin(String name, String phone, String address, String email, String password, LocalDate DOB, String SSN, LocalDate hiringDate, double salary, int children, int leaveBalance) {
        super(name, phone, address, email, password, DOB, SSN, "admin", hiringDate, salary, children, leaveBalance);
    }

    public Admin() {
    }
}
