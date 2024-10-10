package ma.yc.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private boolean role ;

    public Long id () {
        return id;
    }

    public Person setId ( Long id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Person setName ( String name ) {
        this.name = name;
        return this;
    }

    public String email () {
        return email;
    }

    public Person setEmail ( String email ) {
        this.email = email;
        return this;
    }

    public String password () {
        return password;
    }

    public Person setPassword ( String password ) {
        this.password = password;
        return this;
    }

    public String address () {
        return address;
    }

    public Person setAddress ( String address ) {
        this.address = address;
        return this;
    }

    public boolean role () {
        return role;
    }

    public Person setRole ( boolean role ) {
        this.role = role;
        return this;
    }
}
