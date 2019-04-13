package com.crud.bikeshare.bikeshare.Controller;


import com.crud.bikeshare.bikeshare.ArrayToString;
import com.crud.bikeshare.bikeshare.Model.Bike;
import com.crud.bikeshare.bikeshare.Model.BikeStation;
import com.crud.bikeshare.bikeshare.Model.User;
import com.crud.bikeshare.bikeshare.Repository.BikeRepository;
import com.crud.bikeshare.bikeshare.Repository.BikeStationRepository;
import com.crud.bikeshare.bikeshare.Repository.UserRepository;
import com.crud.bikeshare.bikeshare.Validators.UserValidaor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BikeStationRepository bikeStations;
    @Autowired
    BikeRepository bikeRepository;

    User activeUser;

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sign-up";
        }
        String val = UserValidaor.validate(user);
        if(!val.equals("success")){
            model.addAttribute("fail", val);
            return "sign-up";
        }
        userRepository.save(user);
        activeUser = user;
        return "index";
    }

    @PostMapping("/get-bike")
    public String getBike(String X, String Y, Model model){
        Integer x = Integer.valueOf(X);
        Integer y = Integer.valueOf(Y);
        BikeStation bikeStation1 = new BikeStation();
        for(BikeStation bs : bikeStations.findAll()){
            if(x.equals(bs.getX()) && y.equals(bs.getY())){
                bikeStation1 = bs;
            }
        }
        if(bikeStation1.getId() == null){
            return "fail";
        }
        ArrayList<Long> bikeList = ArrayToString.toArray(bikeStation1.getBikes());
        if(!(activeUser.getBikeId() == null)){
            model.addAttribute("fail", "You cannot have more then one bike at a time");
            model.addAttribute("bikeStations", bikeStations.findAll());
            return "/stations";
        }
        if(bikeList.size() > 0) {
            Long bikeId = bikeList.get(bikeList.size() - 1);
            Bike bike = bikeRepository.findById(bikeId).get();
            bikeStation1.removeBike(activeUser, bike);
            bikeRepository.save(bike);
            bikeStations.save(bikeStation1);
            userRepository.save(activeUser);
            model.addAttribute("user", activeUser);
            model.addAttribute("bikeStations", bikeStations.findAll());
            return "index";
        }
        else {
            model.addAttribute("fail", "No Available Bikes in Selected Station");
            model.addAttribute("bikeStations", bikeStations.findAll());
            return "/stations";
        }
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("user", activeUser);
        return "index";
    }

    @GetMapping("/stations")
    public String features(BikeStation bikestation, Model model){
        model.addAttribute("user", activeUser);
        model.addAttribute("bikeStations", bikeStations.findAll());
        return "stations";
    }

    @GetMapping("/pricing")
    public String pricing(Model model){
        model.addAttribute("user", activeUser);
        return "pricing";
    }

    @GetMapping("/sign-in")
    public String sign_in(User user, Model model){
        model.addAttribute("user", user);
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String sign_up(User user, Model model){
        model.addAttribute("user", user);
        return "sign-up";
    }

    @PostMapping("/add-bike")
    public String addBike(String X, String Y, Model model){
        Integer x = Integer.valueOf(X);
        Integer y = Integer.valueOf(Y);
        BikeStation bikeStation = new BikeStation();
        for(BikeStation bs : bikeStations.findAll()){
            if(x.equals(bs.getX()) && y.equals(bs.getY())){
                bikeStation = bs;
            }
        }
        if(activeUser.getBikeId() == null){
            model.addAttribute("fail", "You don't have a bike to return");
            model.addAttribute("bikeStations", bikeStations.findAll());
            return "/stations";
        }
        Bike bike = bikeRepository.findById(activeUser.getBikeId()).get();
        if(bikeStation.getFreeSpace() > 0){
            bikeStation.addBike(activeUser, bike);
            bikeRepository.save(bike);
            bikeStations.save(bikeStation);
            userRepository.save(activeUser);
            model.addAttribute("user", activeUser);
            model.addAttribute("bikeStations", bikeStations.findAll());
            return "index";
        }
        else {
            return "fail";
        }
    }

    @GetMapping("/sign-out")
    public String sign_out(){
        activeUser = null;
        return "index";
    }

    @PostMapping("/login")
    public String login(String email, String password, Model model, User plm){
        for(User user : userRepository.findAll()){
            if(user.getEmail().equals(email)){
                if(user.getPassword().equals(password)){
                    activeUser = user;
                    model.addAttribute("user", activeUser);
                    return "index";
                }
            }
        }

      model.addAttribute("fail", "fail");
      return "sign-in";
    }

    @GetMapping("/add-bike-Admin")
    public String addBike(Bike bike, @Valid BikeStation bikeStation){
        BikeStation bikeRepo;
        if(bikeStations.findById(bikeStation.getId()).isPresent()){
            bikeRepo = bikeStations.findById(bikeStation.getId()).get();
        }
        else {
            return "fail";
        }
        if(bikeRepo.getFreeSpace() > 0){
            bikeRepo.addBike(bike);
            return "success";
        }
        else {
            return "fail";
        }
    }

    @GetMapping("/edit")
    public String showUpdateForm(User user, Model model) {
        model.addAttribute("user", activeUser);
        return "/edit";
    }

    @PostMapping("/update")
    public String updateUser(@Valid User user, BindingResult result, Model model) {
        String val = UserValidaor.validate(user);
        if(!val.equals("success")){
            model.addAttribute("fail", val);
            return "edit";
        }
        userRepository.delete(activeUser);
        userRepository.save(user);
        activeUser = user;
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
}
