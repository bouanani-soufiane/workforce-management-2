package ma.yc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import ma.yc.entity.valueObject.SocialSecurityNumber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "employees")
public class Employee extends Person {


    @Pattern(regexp = "^(\\+212\\s?|0)(6|7)[0-9]{8}|((0(2|5)[0-9]{7}))$",
            message = "Phone must be a valid Moroccan format (e.g., 0601234567 or +212 6 0123 4567)")
    private String phone;

    @NotBlank(message = "Department cannot be blank")
    private String departement;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Embedded
    private SocialSecurityNumber securityNumber;

    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;

    @Positive(message = "Vacation days must be a positive number")
    @Max(value = 30, message = "Vacation days cannot exceed 30")
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

    public double calcFamillyAllowance () {
        if (famillyAllowance == null) {
            return 0.0;
        }
        int childrenCount = famillyAllowance.getChildrenCount();
        double salary = famillyAllowance.getSalary();
        double totalAllowance = 0.0;

        if (salary < 6000) {
            for (int i = 1; i <= childrenCount; i++) {
                if (i <= 3) {
                    totalAllowance += 300;
                } else if (i <= 6) {
                    totalAllowance += 150;
                }
            }
        } else if (salary > 8000) {
            for (int i = 1; i <= childrenCount; i++) {
                if (i <= 3) {
                    totalAllowance += 200;
                } else if (i <= 6) {
                    totalAllowance += 110;
                }
            }
        }
        return totalAllowance;
    }


}


