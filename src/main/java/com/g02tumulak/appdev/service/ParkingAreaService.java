package com.g02tumulak.appdev.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.g02tumulak.appdev.entity.ParkingAreaEntity;
import com.g02tumulak.appdev.exception.NameErrorResponse;
import com.g02tumulak.appdev.exception.NameNotFoundException;
import com.g02tumulak.appdev.repository.ParkingAreaRepository;

@Service
public class ParkingAreaService {
    @Autowired
    ParkingAreaRepository parkingAreaRepository;

    public ParkingAreaService() {
        super();
    }

    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository) {
        this.parkingAreaRepository = parkingAreaRepository;
    }

    public ParkingAreaEntity postParkingArea(ParkingAreaEntity parkingArea) {
        return parkingAreaRepository.save(parkingArea);
    }

    public List<ParkingAreaEntity> getAllParkingAreas() {
        return parkingAreaRepository.findAll();
    }

    public ParkingAreaEntity putParkingAreaEntity(int id, ParkingAreaEntity newParkingArea) {
        ParkingAreaEntity parkingArea = new ParkingAreaEntity();

        try {
            parkingArea = parkingAreaRepository.findById(id).get();
            parkingArea.setAvailableSlots(newParkingArea.getAvailableSlots());
        } catch (NoSuchElementException ex) {
            throw new NameNotFoundException("Parking Area " + id + " does not exist.");
        }

        return parkingAreaRepository.save(parkingArea);
    }

    public String deleteParkingArea(int id) {
        String message = "";

        if (parkingAreaRepository.findById(id) != null) {
            parkingAreaRepository.deleteById(id);
            message = "Parking Area " + id + " is successfully deleted";
        } else {
            message = "Parking Area " + id + " does not exist.";
        }

        return message;
    }

    @ExceptionHandler
    public ResponseEntity<NameErrorResponse> handleException(NameNotFoundException ex) {
        NameErrorResponse error = new NameErrorResponse();

        error.setMessage(ex.getMessage());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NameErrorResponse> handleException(Exception ex) {
        NameErrorResponse error = new NameErrorResponse();

        error.setMessage(ex.getMessage());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
