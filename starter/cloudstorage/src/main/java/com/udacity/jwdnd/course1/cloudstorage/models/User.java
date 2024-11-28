package com.udacity.jwdnd.course1.cloudstorage.models;


import com.udacity.jwdnd.course1.cloudstorage.validateGroup.LoginGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class User {
    private Integer userid;

    @NotBlank(message = "Name must be required", groups = LoginGroup.class)
    @Size(max = 20, message = "Name must not exceed 50 characters", groups = LoginGroup.class)
    private String username;

    private String salt;

    @NotBlank(message = "Password must be required", groups = LoginGroup.class)
    private String password;

    private String firstname;

    private String lastname;


    public User() {
    }

    public User(Integer userid, String username, String salt, String password, String firstname, String lastname) {
        this.userid = userid;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
