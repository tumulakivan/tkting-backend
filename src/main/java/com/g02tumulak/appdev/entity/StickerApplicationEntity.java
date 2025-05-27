package com.g02tumulak.appdev.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class StickerApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    private String carMake;
    private String carModel;
    private String carColor;
    private Date submissionDate;
    private int status;

    @ManyToOne
    @JoinColumn(name = "user_id_number")
    @JsonIgnoreProperties({ "parkingStickers", "stickerApplications" })
    private UserEntity user;

    @OneToOne(mappedBy = "stickerApplication", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("stickerApplication")
    private ParkingStickerEntity parkingSticker;

    public StickerApplicationEntity() {
        super();
        this.status = 2;
    }

    public StickerApplicationEntity(Date submissionDate, UserEntity user, String carMake,
            String carModel, String carColor) {
        this.submissionDate = submissionDate;
        this.user = user;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carColor = carColor;
        this.parkingSticker = null;
        this.status = 2; // 0 = declined, 1 = approved, 2 = pending
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ParkingStickerEntity getParkingSticker() {
        return parkingSticker;
    }

    public void setParkingSticker(ParkingStickerEntity parkingSticker) {
        this.parkingSticker = parkingSticker;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
}
