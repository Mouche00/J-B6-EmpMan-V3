package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Entity
@DiscriminatorValue("recruiter")
@Table(name = "recruiters")
public class Recruiter extends User {

    @OneToMany(mappedBy = "recruiter")
    private Set<JobOffer> jobOffers;

    public Recruiter(String name, String phone, String address, String email, String password, LocalDate DOB, String SSN, LocalDate hiringDate, double salary, int children, int leaveBalance) {
        super(name, phone, address, email, password, DOB, SSN, "recruiter", hiringDate, salary, children, leaveBalance);
    }

    public Recruiter() {
    }
}
