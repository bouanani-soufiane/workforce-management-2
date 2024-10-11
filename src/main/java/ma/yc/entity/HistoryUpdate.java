package ma.yc.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historyUpdates")
public class HistoryUpdate {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime updatedAt;
    private String updatedColumn;
    private String oldValue;
    private String newValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;


    public Long getId () {
        return id;
    }

    public HistoryUpdate setId ( Long id ) {
        this.id = id;
        return this;
    }

    public LocalDateTime getUpdatedAt () {
        return updatedAt;
    }

    public HistoryUpdate setUpdatedAt ( LocalDateTime updatedAt ) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getUpdatedColumn () {
        return updatedColumn;
    }

    public HistoryUpdate setUpdatedColumn ( String updatedColumn ) {
        this.updatedColumn = updatedColumn;
        return this;
    }

    public String getOldValue () {
        return oldValue;
    }

    public HistoryUpdate setOldValue ( String oldValue ) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue () {
        return newValue;
    }

    public HistoryUpdate setNewValue ( String newValue ) {
        this.newValue = newValue;
        return this;
    }

    public Employee getEmployee () {
        return employee;
    }

    public HistoryUpdate setEmployee ( Employee employee ) {
        this.employee = employee;
        return this;
    }
}
