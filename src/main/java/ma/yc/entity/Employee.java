package ma.yc.entity;

import jakarta.persistence.*;
import ma.yc.entity.valueObject.SocialSecurityNumber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "employees")
public class Employee extends Person {

    private String phone;
    private String departement;
    private String jobTitle;
    private LocalDate birthDate;
    @Embedded
    private SocialSecurityNumber securityNumber;

    private LocalDate hireDate;
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

    public String getPhone () {
        return phone;
    }

    public Employee setPhone ( String phone ) {
        this.phone = phone;
        return this;
    }

    public String getDepartement () {
        return departement;
    }

    public Employee setDepartement ( String departement ) {
        this.departement = departement;
        return this;
    }

    public String getJobTitle () {
        return jobTitle;
    }

    public Employee setJobTitle ( String jobTitle ) {
        this.jobTitle = jobTitle;
        return this;
    }

    public LocalDate getBirthDate () {
        return birthDate;
    }

    public Employee setBirthDate ( LocalDate birthDate ) {
        this.birthDate = birthDate;
        return this;
    }

    public SocialSecurityNumber getSecurityNumber () {
        return securityNumber;
    }

    public Employee setSecurityNumber ( SocialSecurityNumber securityNumber ) {
        this.securityNumber = securityNumber;
        return this;
    }

    public LocalDate getHireDate () {
        return hireDate;
    }

    public Employee setHireDate ( LocalDate hireDate ) {
        this.hireDate = hireDate;
        return this;
    }

    public Integer getSoldVacation () {
        return soldVacation;
    }

    public Employee setSoldVacation ( Integer soldVacation ) {
        this.soldVacation = soldVacation;
        return this;
    }

    public FamillyAllowance getFamillyAllowance () {
        return famillyAllowance;
    }

    public Employee setFamillyAllowance ( FamillyAllowance famillyAllowance ) {
        this.famillyAllowance = famillyAllowance;
        return this;
    }

    public List<Vacation> getVacations () {
        return vacations;
    }

    public Employee setVacations ( List<Vacation> vacations ) {
        this.vacations = vacations;
        return this;
    }

    public List<HistoryUpdate> getHistoryUpdates () {
        return historyUpdates;
    }

    public Employee setHistoryUpdates ( List<HistoryUpdate> historyUpdates ) {
        this.historyUpdates = historyUpdates;
        return this;
    }

    public List<JobOffer> getJobOffers () {
        return jobOffers;
    }

    public Employee setJobOffers ( List<JobOffer> jobOffers ) {
        this.jobOffers = jobOffers;
        return this;
    }
}

