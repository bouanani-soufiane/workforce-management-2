package ma.yc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jobOffers")
public class JobOffer {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Required skills are required")
    private String requiredSkills;

    @NotNull(message = "Publish date is required")
    private LocalDateTime datePublish;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDateTime dateEnd;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL)
    private List<Candidature> candidatures;

    public Long getId () {
        return id;
    }

    public JobOffer setId ( Long id ) {
        this.id = id;
        return this;
    }

    public String getTitle () {
        return title;
    }

    public JobOffer setTitle ( String title ) {
        this.title = title;
        return this;
    }

    public String getDescription () {
        return description;
    }

    public JobOffer setDescription ( String description ) {
        this.description = description;
        return this;
    }

    public String getRequiredSkills () {
        return requiredSkills;
    }

    public JobOffer setRequiredSkills ( String requiredSkills ) {
        this.requiredSkills = requiredSkills;
        return this;
    }

    public LocalDateTime getDatePublish () {
        return datePublish;
    }

    public JobOffer setDatePublish ( LocalDateTime datePublish ) {
        this.datePublish = datePublish;
        return this;
    }

    public LocalDateTime getDateEnd () {
        return dateEnd;
    }

    public JobOffer setDateEnd ( LocalDateTime dateEnd ) {
        this.dateEnd = dateEnd;
        return this;
    }

    public boolean getIsActive () {
        return isActive;
    }

    public JobOffer setActive ( boolean active ) {
        isActive = active;
        return this;
    }

    public Employee getEmployee () {
        return employee;
    }

    public JobOffer setEmployee ( Employee employee ) {
        this.employee = employee;
        return this;
    }

    public List<Candidature> getCandidatures () {
        return candidatures;
    }

    public JobOffer setCandidatures ( List<Candidature> candidatures ) {
        this.candidatures = candidatures;
        return this;
    }
}
