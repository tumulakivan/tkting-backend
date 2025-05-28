package com.g02tumulak.appdev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.g02tumulak.appdev.dto.ParkingSlotDTO;
import com.g02tumulak.appdev.entity.ParkingAreaEntity;
import com.g02tumulak.appdev.entity.ParkingSlotEntity;
import com.g02tumulak.appdev.entity.UserEntity;
import com.g02tumulak.appdev.exception.NameErrorResponse;
import com.g02tumulak.appdev.exception.NameNotFoundException;
import com.g02tumulak.appdev.repository.ParkingAreaRepository;
import com.g02tumulak.appdev.repository.ParkingSlotRepository;
import com.g02tumulak.appdev.repository.UserRepository;
import com.g02tumulak.appdev.repository.ParkingStickerRepository;

@Service
public class ParkingSlotService {
    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    ParkingAreaRepository parkingAreaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParkingStickerRepository parkingStickerRepository;

    public ParkingSlotService() {
        super();
    }

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public ParkingSlotEntity postParkingSlot(ParkingSlotDTO parkingSlotDTO) {
        ParkingAreaEntity parkingArea = parkingAreaRepository.findById(parkingSlotDTO.getParkingAreaId())
                .orElseThrow(() -> new RuntimeException("Parking Area not found"));

        int currentSlotCount = parkingSlotRepository.countByParkingArea(parkingArea);

        // Prevent adding more slots than allowed
        if (currentSlotCount >= parkingArea.getTotalSlots()) {
            throw new RuntimeException("All slots are already filled for this parking area");
        }

        ParkingSlotEntity slot = new ParkingSlotEntity();
        slot.setParkingArea(parkingArea);
        return parkingSlotRepository.save(slot);
    }

    public List<ParkingSlotEntity> getAllParkingSlots() {
        return parkingSlotRepository.findAll();
    }

    public ParkingSlotEntity updateSlotStatus(int id) {
        ParkingSlotEntity slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new NameNotFoundException("Parking slot " + id + " does not exist."));

        ParkingAreaEntity area = slot.getParkingArea();
        slot.setStatus(!slot.getStatus());
        parkingSlotRepository.save(slot);
        updateAvailableSlots(area);
        return slot;
    }

    public void updateAvailableSlots(ParkingAreaEntity parkingArea) {
        int availableCount = parkingSlotRepository.countByParkingAreaAndStatus(parkingArea, false);
        parkingArea.setAvailableSlots(availableCount);
        parkingAreaRepository.save(parkingArea);
    }

    public String deleteParkingSlot(int id) {
        String message = "";

        if (parkingSlotRepository.findById(id) != null) {
            parkingSlotRepository.deleteById(id);
            message = "Parking Slot " + id + " is successfully deleted.";
        } else {
            message = "Parking Slot " + id + " does not exist.";
        }

        return message;
    }

    public ParkingSlotEntity updateUser(int slotId, String userIdNumber) {
        ParkingSlotEntity slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new NameNotFoundException("Parking slot " + slotId + " does not exist."));

        if (userIdNumber == null) {
            // Remove user assignment
            slot.setUser(null);
            slot.setStatus(false);
        } else {
            UserEntity user = userRepository.findByIdNumber(userIdNumber);
            if (user == null) {
                throw new NameNotFoundException("User with ID " + userIdNumber + " does not exist.");
            }

            // Check if user already has a parking slot
            ParkingSlotEntity existingSlot = parkingSlotRepository.findByUser(user);
            if (existingSlot != null && existingSlot.getSlotId() != slotId) {
                throw new RuntimeException("User already has an assigned parking slot.");
            }

            slot.setUser(user);
            slot.setStatus(true);
        }

        ParkingAreaEntity area = slot.getParkingArea();
        ParkingSlotEntity updatedSlot = parkingSlotRepository.save(slot);
        updateAvailableSlots(area);
        return updatedSlot;
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
