package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("salary")
@Table(name = "salary_history")
public class SalaryHistory extends ModHistory{
    public SalaryHistory(String modifications, LocalDate date, User user) {
        super(modifications, date, user);
    }

    public SalaryHistory() {
    }
}
