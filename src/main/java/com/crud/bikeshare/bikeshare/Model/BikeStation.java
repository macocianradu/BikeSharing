package com.crud.bikeshare.bikeshare.Model;

import com.crud.bikeshare.bikeshare.ArrayToString;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class BikeStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bikes;
    private Integer X;
    private Integer Y;
    private Integer capacity;

    public BikeStation(int x, int y, int capacity){
        this.bikes = "";
        this.X = x;
        this.Y = y;
        this.capacity = capacity;
//        this.freeSpace = this.capacity;
    }

    public BikeStation(){}

    public BikeStation(int x, int y){
        this.bikes = "";
        this.X = x;
        this.Y = y;
        this.capacity = 10;
//        this.freeSpace = 10;
    }

    public void addBike(Bike bike){
        this.bikes += bike.getId();
        bike.setStatus("on station");
//        this.freeSpace --;
    }

    public void addBike(User user, Bike bike){
        bike.setStatus("on station");
        this.bikes += bike.getId();
        user.setBikeId(null);
//        this.freeSpace --;
    }

    public void removeBike(User user, Bike bike){
        ArrayList<Long> aux = ArrayToString.toArray(bikes);

        user.setBikeId(bike.getId());
        aux.remove(bike.getId());
        bike.setStatus("in use");

        this.bikes = ArrayToString.toString(aux);
//        this.freeSpace ++;
    }

    public String getLocation(){
        StringBuilder string = new StringBuilder();
        string.append(this.X);
        string.append(" - ");
        string.append(this.Y);
        return string.toString();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public int getFreeSpace(){
        ArrayList<Long> bikes = ArrayToString.toArray(this.bikes);
        return this.capacity - bikes.size();
    }

    public String getBikes() {
        return bikes;
    }

    public Long getId(){
        return this.id;
    }

    public Integer getX() {
        return X;
    }

    public Integer getY() {
        return Y;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBikes(String bikes) {
        this.bikes = bikes;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
