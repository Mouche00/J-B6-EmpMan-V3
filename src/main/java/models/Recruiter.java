package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@DiscriminatorValue("recruiter")
@Table(name = "recruiters")
public class Recruiter extends User {

    @OneToMany(mappedBy = "recruiter")
    private Set<JobOffer> jobOffers;
}
