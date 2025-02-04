package ma.yc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import ma.yc.enums.ApplicationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidatures")
public class Candidature {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Skills cannot be blank")
    @Size(max = 255, message = "Skills must not exceed 255 characters")
    private String skills;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Application status must be specified")
    private ApplicationStatus applicationStatus;

    @NotNull(message = "Submission date cannot be null")
    private LocalDateTime submissionDate;

    @NotBlank(message = "Resume cannot be blank")
    private String resume;
    @ManyToOne()
    @JoinColumn(name = "jobOffer_id")
    private JobOffer jobOffer;

    public Long getId () {
        return id;
    }

    public Candidature setId ( Long id ) {
        this.id = id;
        return this;
    }

    public String getName () {
        return name;
    }

    public Candidature setName ( String name ) {
        this.name = name;
        return this;
    }

    public String getEmail () {
        return email;
    }

    public Candidature setEmail ( String email ) {
        this.email = email;
        return this;
    }

    public String getSkills () {
        return skills;
    }

    public Candidature setSkills ( String skills ) {
        this.skills = skills;
        return this;
    }

    public ApplicationStatus getApplicationStatus () {
        return applicationStatus;
    }

    public Candidature setApplicationStatus ( ApplicationStatus applicationStatus ) {
        this.applicationStatus = applicationStatus;
        return this;
    }

    public LocalDateTime getSubmissionDate () {
        return submissionDate;
    }

    public Candidature setSubmissionDate ( LocalDateTime submissionDate ) {
        this.submissionDate = submissionDate;
        return this;
    }

    public JobOffer getJobOffer () {
        return jobOffer;
    }

    public Candidature setJobOffer ( JobOffer jobOffer ) {
        this.jobOffer = jobOffer;
        return this;
    }

    public String getResume () {
        return resume;
    }

    public Candidature setResume ( String resume ) {
        this.resume = resume;
        return this;
    }
}
