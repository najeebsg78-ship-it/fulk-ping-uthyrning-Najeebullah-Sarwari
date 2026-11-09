package se.fulkoping.uthyrning.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    @Email
    private String mail;

    public Person(Long id, String name, String mail, Set<Sak> saker) {
        this.name = name;
        this.mail = mail;
    }

    @ManyToMany
    @JoinTable (
            name = "person_sak",
            joinColumns = @JoinColumn (name = "person_id"),
            inverseJoinColumns = @JoinColumn (name = "sak_id")
    )


    private Set<Sak> saker = new HashSet<>();

    public Person(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public Person() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Sak> getSaker() {
        return saker;
    }

    public void setSaker(Set<Sak> saker) {
        this.saker = saker;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", saker=" + saker +
                '}';
    }
}
