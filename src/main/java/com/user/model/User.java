package com.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotNull
    @Column(name = "user_name", columnDefinition = "VARCHAR(15)")
    private String name;
    @NotNull
    @Column(name = "user_surname", columnDefinition = "VARCHAR(30)")
    private String surname;
    @NotNull
    @Column(name = "user_email")
    private String email;
    @NotNull
    @Column(name = "user_date")
    private Date date;
    @Column(name = "user_address")
    private String address;
    @Column(name = "user_phone_number")
    private String phoneNumber;
    public User(){}

    public User(String name, String surname, String email, Date date,
                String address, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.date = date;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
