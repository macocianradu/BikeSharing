package com.crud.bikeshare.bikeshare.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bike {

    @Id
    @GeneratedValue
    private Long id;
    private String status;

    public Bike(){
        status = "not in use";
    }

    public Long getId() {
        return id;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
