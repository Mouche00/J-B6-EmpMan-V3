package models;

import models.enums.ApplicationStatus;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "applications",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"applicant", "jobOffer"})})
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String CV;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.RECEIVED;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private BaseUser applicant;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public BaseUser getApplicant() {
        return applicant;
    }

    public void setApplicant(BaseUser applicant) {
        this.applicant = applicant;
    }

    public Application(String CV) {
        this.CV = CV;
    }

    public Application() {
    }
}
