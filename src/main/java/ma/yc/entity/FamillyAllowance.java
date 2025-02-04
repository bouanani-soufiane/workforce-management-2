package ma.yc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "famillyAllowances")
public class FamillyAllowance {
    @Id
    @GeneratedValue
    private Long id;

    @Min(value = 0, message = "Children count must be non-negative")
    private int childrenCount;

    @Positive(message = "Salary must be a positive value")
    private double salary;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "famillyAllowance")
    private Employee employee;

    public Long getId () {
        return id;
    }

    public FamillyAllowance setId ( Long id ) {
        this.id = id;
        return this;
    }

    public int getChildrenCount () {
        return childrenCount;
    }

    public FamillyAllowance setChildrenCount ( int childrenCount ) {
        this.childrenCount = childrenCount;
        return this;
    }

    public double getSalary () {
        return salary;
    }

    public FamillyAllowance setSalary ( double salary ) {
        this.salary = salary;
        return this;
    }

    public Employee getEmployee () {
        return employee;
    }

    public FamillyAllowance setEmployee ( Employee employee ) {
        this.employee = employee;
        return this;
    }
}
