package com.g02tumulak.appdev.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.g02tumulak.appdev.entity.ParkingStickerEntity;
import com.g02tumulak.appdev.exception.NameErrorResponse;
import com.g02tumulak.appdev.exception.NameNotFoundException;
import com.g02tumulak.appdev.repository.ParkingStickerRepository;
import com.g02tumulak.appdev.util.DateUtil;

@Service
public class ParkingStickerService {
    @Autowired
    private ParkingStickerRepository parkingStickerRepository;

    public ParkingStickerService() {
        super();
    }

    public ParkingStickerService(ParkingStickerRepository parkingStickerRepository) {
        this.parkingStickerRepository = parkingStickerRepository;
    }

    public List<ParkingStickerEntity> getAllStickers() {
        List<ParkingStickerEntity> stickers = parkingStickerRepository.findAll();
        System.out.println("Found " + stickers.size() + " stickers in database");
        for (ParkingStickerEntity sticker : stickers) {
            System.out.println("Sticker ID: " + sticker.getStickerId() +
                    ", Car: " + sticker.getCarMake() + " " + sticker.getCarModel() +
                    ", User: " + (sticker.getUser() != null ? sticker.getUser().getIdNumber() : "null"));
        }
        return stickers;
    }

    public Optional<ParkingStickerEntity> getStickerById(int id) {
        return parkingStickerRepository.findById(id);
    }

    public ParkingStickerEntity createSticker(ParkingStickerEntity sticker) {
        // Set issue date to current date
        sticker.setIssueDate(DateUtil.getCurrentDate());

        // Set expiry date to 1 year from now
        sticker.setExpiryDate(DateUtil.getExpiryDate());

        ParkingStickerEntity saved = parkingStickerRepository.save(sticker);
        System.out.println("Created new sticker with ID: " + saved.getStickerId());
        return saved;
    }

    public ParkingStickerEntity updateSticker(int id, ParkingStickerEntity stickerDetails) {
        Optional<ParkingStickerEntity> sticker = parkingStickerRepository.findById(id);
        if (sticker.isPresent()) {
            ParkingStickerEntity existingSticker = sticker.get();
            existingSticker.setStatus(stickerDetails.getStatus());
            // Don't update issue date as it should remain unchanged
            if (stickerDetails.getExpiryDate() != null) {
                // Validate that new expiry date is not in the past
                if (DateUtil.isExpired(stickerDetails.getExpiryDate())) {
                    throw new RuntimeException("Cannot set expiry date to a past date");
                }
                existingSticker.setExpiryDate(stickerDetails.getExpiryDate());
            }
            return parkingStickerRepository.save(existingSticker);
        }
        return null;
    }

    public void deleteSticker(int id) {
        parkingStickerRepository.deleteById(id);
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
