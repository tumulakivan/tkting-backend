package com.g02tumulak.appdev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g02tumulak.appdev.entity.ParkingStickerEntity;
import com.g02tumulak.appdev.service.ParkingStickerService;

@RestController
@RequestMapping(path = "api/parkingSticker")
public class ParkingStickerController {
    @Autowired
    ParkingStickerService parkingStickerService;

    @GetMapping("/")
    public List<ParkingStickerEntity> getParkingStickers() {
        return parkingStickerService.getAllStickers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingStickerEntity> getParkingStickerById(@PathVariable int id) {
        Optional<ParkingStickerEntity> sticker = parkingStickerService.getStickerById(id);
        return sticker.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ParkingStickerEntity createParkingSticker(@RequestBody ParkingStickerEntity parkingSticker) {
        return parkingStickerService.createSticker(parkingSticker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingStickerEntity> updateParkingSticker(
            @PathVariable int id,
            @RequestBody ParkingStickerEntity parkingSticker) {
        ParkingStickerEntity updatedSticker = parkingStickerService.updateSticker(id, parkingSticker);
        return updatedSticker != null ? ResponseEntity.ok(updatedSticker) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSticker(@PathVariable int id) {
        parkingStickerService.deleteSticker(id);
        return ResponseEntity.ok().build();
    }
}
