package models;

import models.enums.JobOfferStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "job_offers")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String title;
    private String description;
    private LocalDate publishDate;
    private LocalDate deadline;

    @Lob
    private String criteria;

    @Enumerated(EnumType.STRING)
    private JobOfferStatus status = JobOfferStatus.OPEN;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public JobOfferStatus getStatus() {
        return status;
    }

    public void setStatus(JobOfferStatus status) {
        this.status = status;
    }

    public JobOffer(String title, String description, LocalDate publishDate, LocalDate deadline, String criteria) {
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.deadline = deadline;
        this.criteria = criteria;
    }

    public JobOffer() {
    }
}
