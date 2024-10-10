package ma.yc.entity;

import jakarta.persistence.*;
import ma.yc.enums.ApplicationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidatures")
public class Candidature {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String skills;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    private LocalDateTime submissionDate;

    @ManyToOne()
    @JoinColumn(name = "jobOffer_id")
    private JobOffer jobOffer;

    public Long id () {
        return id;
    }

    public Candidature setId ( Long id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Candidature setName ( String name ) {
        this.name = name;
        return this;
    }

    public String email () {
        return email;
    }

    public Candidature setEmail ( String email ) {
        this.email = email;
        return this;
    }

    public String skills () {
        return skills;
    }

    public Candidature setSkills ( String skills ) {
        this.skills = skills;
        return this;
    }

    public ApplicationStatus applicationStatus () {
        return applicationStatus;
    }

    public Candidature setApplicationStatus ( ApplicationStatus applicationStatus ) {
        this.applicationStatus = applicationStatus;
        return this;
    }

    public LocalDateTime submissionDate () {
        return submissionDate;
    }

    public Candidature setSubmissionDate ( LocalDateTime submissionDate ) {
        this.submissionDate = submissionDate;
        return this;
    }

    public JobOffer jobOffer () {
        return jobOffer;
    }

    public Candidature setJobOffer ( JobOffer jobOffer ) {
        this.jobOffer = jobOffer;
        return this;
    }
}
