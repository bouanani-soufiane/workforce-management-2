package ma.yc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import ma.yc.enums.VacationStatus;

import java.time.LocalDate;

@Entity
@Table(name = "vacations")
public class Vacation {
    @Id
    @GeneratedValue
    private Long id;


    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    @AssertTrue(message = "End date must be after start date")
    private LocalDate endDate;

    @NotBlank(message = "Reason is required")
    private String reason;

    @Enumerated(EnumType.STRING)
    private VacationStatus vacationStatus;

    @Size(max = 255, message = "Certificate must be at most 255 characters")
    private String certificate;

    @ManyToOne()
    @JoinColumn(name = "employee_id")
    @NotNull(message = "Employee is required")
    private Employee employee;

    public Long getId () {
        return id;
    }

    public Vacation setId ( Long id ) {
        this.id = id;
        return this;
    }

    public LocalDate getStartDate () {
        return startDate;
    }

    public Vacation setStartDate ( LocalDate startDate ) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate () {
        return endDate;
    }

    public Vacation setEndDate ( LocalDate endDate ) {
        this.endDate = endDate;
        return this;
    }

    public String getReason () {
        return reason;
    }

    public Vacation setReason ( String reason ) {
        this.reason = reason;
        return this;
    }

    public VacationStatus getVacationStatus () {
        return vacationStatus;
    }

    public Vacation setVacationStatus ( VacationStatus vacationStatus ) {
        this.vacationStatus = vacationStatus;
        return this;
    }

    public String getCertificate () {
        return certificate;
    }

    public Vacation setCertificate ( String certificate ) {
        this.certificate = certificate;
        return this;
    }

    public Employee getEmployee () {
        return employee;
    }

    public Vacation setEmployee ( Employee employee ) {
        this.employee = employee;
        return this;
    }
}
