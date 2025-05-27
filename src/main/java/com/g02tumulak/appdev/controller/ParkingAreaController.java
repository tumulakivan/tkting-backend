package com.g02tumulak.appdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g02tumulak.appdev.entity.ParkingAreaEntity;
import com.g02tumulak.appdev.service.ParkingAreaService;

@RestController
@RequestMapping(path = "api/parkingArea")
public class ParkingAreaController {
    @Autowired
    ParkingAreaService parkingAreaService;

    @GetMapping("/")
    public List<ParkingAreaEntity> getAreaNames() {
        return parkingAreaService.getAllParkingAreas();
    }

    @PostMapping("/postParkingArea")
    public ParkingAreaEntity postParkingArea(@RequestBody ParkingAreaEntity parkingArea) {
        return parkingAreaService.postParkingArea(parkingArea);
    }

    @PutMapping("/putParkingArea")
    public ParkingAreaEntity putParkingArea(@RequestParam int id, @RequestBody ParkingAreaEntity newParkingArea) {
        return parkingAreaService.putParkingAreaEntity(id, newParkingArea);
    }

    @DeleteMapping("/deleteParkingArea/{id}")
    public String deleteParkingArea(@PathVariable int id) {
        return parkingAreaService.deleteParkingArea(id);
    }
}
