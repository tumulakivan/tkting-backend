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

import com.g02tumulak.appdev.entity.StickerApplicationEntity;
import com.g02tumulak.appdev.entity.UserEntity;
import com.g02tumulak.appdev.entity.ParkingStickerEntity;
import com.g02tumulak.appdev.exception.NameErrorResponse;
import com.g02tumulak.appdev.exception.NameNotFoundException;
import com.g02tumulak.appdev.repository.StickerApplicationRepository;
import com.g02tumulak.appdev.repository.UserRepository;
import com.g02tumulak.appdev.repository.ParkingStickerRepository;

@Service
public class StickerApplicationService {
    @Autowired
    private StickerApplicationRepository stickerApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingStickerRepository parkingStickerRepository;

    public StickerApplicationService() {
        super();
    }

    public StickerApplicationService(StickerApplicationRepository stickerApplicationRepository) {
        this.stickerApplicationRepository = stickerApplicationRepository;
    }

    public StickerApplicationEntity postStickerApplication(StickerApplicationEntity stickerApplication) {
        stickerApplication.setSubmissionDate(new Date());
        stickerApplication.setStatus(2); // 0 = declined, 1 = approved, 2 = pending
        return stickerApplicationRepository.save(stickerApplication);
    }

    public List<StickerApplicationEntity> getAllStickerApplications() {
        return stickerApplicationRepository.findAll();
    }

    public StickerApplicationEntity getStickerApplicationById(int id) {
        return stickerApplicationRepository.findById(id).orElse(null);
    }

    public StickerApplicationEntity putStickerApplication(int id,
            StickerApplicationEntity newStickerApplicationDetails) {
        StickerApplicationEntity stickerApplication = new StickerApplicationEntity();

        stickerApplication = stickerApplicationRepository.findById(id).orElse(null);

        stickerApplication.setCarMake(newStickerApplicationDetails.getCarMake());
        stickerApplication.setCarModel(newStickerApplicationDetails.getCarModel());
        stickerApplication.setCarColor(newStickerApplicationDetails.getCarColor());
        stickerApplication.setStatus(newStickerApplicationDetails.getStatus());

        return stickerApplicationRepository.save(stickerApplication);
    }

    public StickerApplicationEntity postStickerApplicationByUserId(String idNumber,
            StickerApplicationEntity stickerApplication) {
        UserEntity user = userRepository.findByIdNumber(idNumber);
        if (user == null) {
            throw new RuntimeException("User not found with ID Number: " + idNumber);
        }

        stickerApplication.setUser(user);
        stickerApplication.setSubmissionDate(new Date());
        stickerApplication.setStatus(2); // 0 = declined, 1 = approved, 2 = pending

        return stickerApplicationRepository.save(stickerApplication);
    }

    public StickerApplicationEntity approveStickerApplication(int applicationId) {
        StickerApplicationEntity application = stickerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        System.out.println("Approving application ID: " + applicationId);
        application.setStatus(1); // 1 = approved
        stickerApplicationRepository.save(application);

        // Create a new parking sticker
        ParkingStickerEntity parkingSticker = new ParkingStickerEntity(
                com.g02tumulak.appdev.util.DateUtil.getCurrentDate(),
                com.g02tumulak.appdev.util.DateUtil.getExpiryDate(),
                application.getUser(),
                application.getCarMake(),
                application.getCarModel(),
                application.getCarColor());

        System.out.println(
                "Created sticker object with car: " + parkingSticker.getCarMake() + " " + parkingSticker.getCarModel());
        parkingSticker.setStickerApplication(application);
        ParkingStickerEntity savedSticker = parkingStickerRepository.save(parkingSticker);
        System.out.println("Saved sticker with ID: " + savedSticker.getStickerId());

        application.setParkingSticker(parkingSticker);
        return stickerApplicationRepository.save(application);
    }

    public StickerApplicationEntity declineStickerApplication(int applicationId) {
        StickerApplicationEntity application = stickerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(0); // 0 = declined
        return stickerApplicationRepository.save(application);
    }

    public void deleteStickerApplication(int id) {
        stickerApplicationRepository.deleteById(id);
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
