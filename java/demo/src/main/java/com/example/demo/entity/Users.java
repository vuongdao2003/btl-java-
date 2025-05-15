package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name="Users")
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="fullname")
    private String fullname;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name = "createdDate")
    private LocalDateTime createDate;

    @Column(name="lasttimelogin")
    private LocalDateTime lastTimeLogin;

    public LocalDateTime getLastTimeLogin() {
        return lastTimeLogin;
    }

    public void setLastTimeLogin(LocalDateTime lastTimeLogin) {
        this.lastTimeLogin = lastTimeLogin;
    }

    @ManyToOne
    @JoinColumn(name ="roleID")
    private Roles roles;


    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
