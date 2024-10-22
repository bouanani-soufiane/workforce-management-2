package ma.yc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private boolean role ;

    public Long getId () {
        return id;
    }

    public Person setId ( Long id ) {
        this.id = id;
        return this;
    }

    public String getName () {
        return name;
    }

    public Person setName ( String name ) {
        this.name = name;
        return this;
    }

    public String getEmail () {
        return email;
    }

    public Person setEmail ( String email ) {
        this.email = email;
        return this;
    }

    public String getPassword () {
        return password;
    }

    public Person setPassword ( String password ) {
        this.password = password;
        return this;
    }

    public String getAddress () {
        return address;
    }

    public Person setAddress ( String address ) {
        this.address = address;
        return this;
    }

    public boolean isRole () {
        return role;
    }

    public Person setRole ( boolean role ) {
        this.role = role;
        return this;
    }
}
