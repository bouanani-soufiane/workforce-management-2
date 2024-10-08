package ma.yc.entity;

import jakarta.persistence.*;

@Entity()
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;

    public Employee () {
    }

    public Integer id () {
        return id;
    }

    public Employee setId ( Integer id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Employee setName ( String name ) {
        this.name = name;
        return this;
    }

    public String email () {
        return email;
    }

    public Employee setEmail ( String email ) {
        this.email = email;
        return this;
    }

    public String phone () {
        return phone;
    }

    public Employee setPhone ( String phone ) {
        this.phone = phone;
        return this;
    }
}
