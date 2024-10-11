package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("recruiter")
@Table(name = "recruiters")
public class Recruiter extends User {
}
