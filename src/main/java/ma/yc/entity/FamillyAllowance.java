package ma.yc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "famillyAllowances")
public class FamillyAllowance {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY , mappedBy = "famillyAllowance")
    private Employee employee;

    public Long id () {
        return id;
    }

    public FamillyAllowance setId ( Long id ) {
        this.id = id;
        return this;
    }

    public Employee employee () {
        return employee;
    }

    public FamillyAllowance setEmployee ( Employee employee ) {
        this.employee = employee;
        return this;
    }
}
