package com.crud.bikeshare.bikeshare.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank
    private String password;
    private Long bikeId;
    private String repeatPassword;

    public User(String name, String password, String email, String resetPassword){
        this.name = name;
        this.bikeId = null;
        this.email = email;
        this.password = password;
        this.repeatPassword = resetPassword;
    }

    public User(){}

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBikeId(){
        return this.bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String resetPassword) {
        this.repeatPassword = resetPassword;
    }
}
