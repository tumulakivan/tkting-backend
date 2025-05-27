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

import com.g02tumulak.appdev.dto.ParkingSlotDTO;
import com.g02tumulak.appdev.entity.ParkingSlotEntity;
import com.g02tumulak.appdev.service.ParkingSlotService;

@RestController
@RequestMapping(path = "api/parkingSlot")
public class ParkingSlotController {
    @Autowired
    ParkingSlotService parkingSlotService;

    @GetMapping("/")
    public List<ParkingSlotEntity> getParkingSlots() {
        return parkingSlotService.getAllParkingSlots();
    }

    @PostMapping("/postParkingSlot")
    public ParkingSlotEntity postParkingSlot(@RequestBody ParkingSlotDTO parkingSlot) {
        return parkingSlotService.postParkingSlot(parkingSlot);
    }

    @PutMapping("/putParkingSlot/{id}")
    public ParkingSlotEntity putParkingSlot(@PathVariable int id) {
        return parkingSlotService.updateSlotStatus(id);
    }

    @PutMapping("/updateUser/{slotId}")
    public ParkingSlotEntity updateUser(
            @PathVariable int slotId,
            @RequestParam(required = false) String userIdNumber) {
        return parkingSlotService.updateUser(slotId, userIdNumber);
    }

    @DeleteMapping("/deleteSlot/{id}")
    public String deleteParkingSlot(@PathVariable int id) {
        return parkingSlotService.deleteParkingSlot(id);
    }
}
