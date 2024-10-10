package ma.yc.entity;

import jakarta.persistence.*;
import ma.yc.entity.valueObject.SocialSecurityNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "employees")
public class Employee extends Person {

    private String departement;
    private String jobTitle;
    private LocalDateTime birthDate;
    @Embedded
    private SocialSecurityNumber securityNumber;

    private LocalDateTime hireDate;
    private Integer soldVacation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "famillyAllowance_id")
    private FamillyAllowance famillyAllowance;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacation> vacations = new ArrayList<>();


    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HistoryUpdate> historyUpdates;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JobOffer> jobOffers = new ArrayList<>();


    public String departement () {
        return departement;
    }

    public Employee setDepartement ( String departement ) {
        this.departement = departement;
        return this;
    }

    public String jobTitle () {
        return jobTitle;
    }

    public Employee setJobTitle ( String jobTitle ) {
        this.jobTitle = jobTitle;
        return this;
    }

    public LocalDateTime birthDate () {
        return birthDate;
    }

    public Employee setBirthDate ( LocalDateTime birthDate ) {
        this.birthDate = birthDate;
        return this;
    }

    public SocialSecurityNumber securityNumber () {
        return securityNumber;
    }

    public Employee setSecurityNumber ( SocialSecurityNumber securityNumber ) {
        this.securityNumber = securityNumber;
        return this;
    }

    public LocalDateTime hireDate () {
        return hireDate;
    }

    public Employee setHireDate ( LocalDateTime hireDate ) {
        this.hireDate = hireDate;
        return this;
    }

    public Integer soldVacation () {
        return soldVacation;
    }

    public Employee setSoldVacation ( Integer soldVacation ) {
        this.soldVacation = soldVacation;
        return this;
    }

    public FamillyAllowance famillyAllowance () {
        return famillyAllowance;
    }

    public Employee setFamillyAllowance ( FamillyAllowance famillyAllowance ) {
        this.famillyAllowance = famillyAllowance;
        return this;
    }

    public List<Vacation> vacations () {
        return vacations;
    }

    public Employee setVacations ( List<Vacation> vacations ) {
        this.vacations = vacations;
        return this;
    }

    public List<HistoryUpdate> historyUpdates () {
        return historyUpdates;
    }

    public Employee setHistoryUpdates ( List<HistoryUpdate> historyUpdates ) {
        this.historyUpdates = historyUpdates;
        return this;
    }

    public List<JobOffer> jobOffers () {
        return jobOffers;
    }

    public Employee setJobOffers ( List<JobOffer> jobOffers ) {
        this.jobOffers = jobOffers;
        return this;
    }

    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("departement='").append(departement).append('\'');
        sb.append(", jobTitle='").append(jobTitle).append('\'');
        sb.append(", name='").append(name()).append('\'');
        sb.append(", email='").append(email()).append('\'');
        sb.append(", address='").append(address()).append('\'');
        sb.append(", role='").append(role()).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", securityNumber=").append(securityNumber);
        sb.append(", hireDate=").append(hireDate);
        sb.append(", soldVacation=").append(soldVacation);
        sb.append('}');
        return sb.toString();
    }
}

