package com.g02tumulak.appdev.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class ParkingStickerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stickerId;

    private String carMake;
    private String carModel;
    private String carColor;
    private Date issueDate;
    private Date expiryDate;
    private int status;

    @ManyToOne
    @JoinColumn(name = "user_id_number")
    @JsonIgnoreProperties({ "stickerApplications", "parkingStickers" })
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "application_id")
    @JsonIgnoreProperties("parkingSticker")
    private StickerApplicationEntity stickerApplication;

    @OneToOne(mappedBy = "occupant")
    @JsonIgnoreProperties("occupant")
    private ParkingSlotEntity parkingSlot;

    public ParkingStickerEntity() {
        super();
    }

    public ParkingStickerEntity(Date issueDate, Date expiryDate,
            UserEntity user, String carMake, String carModel, String carColor) {
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.user = user;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carColor = carColor;
        this.stickerApplication = null;
        this.status = 1; // 0 - expired, 1 - valid
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getStatus() {
        // Automatically update status based on expiry date
        if (com.g02tumulak.appdev.util.DateUtil.isExpired(expiryDate)) {
            this.status = 0; // expired
        }
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

    public StickerApplicationEntity getStickerApplication() {
        return stickerApplication;
    }

    public void setStickerApplication(StickerApplicationEntity stickerApplication) {
        this.stickerApplication = stickerApplication;
    }

    public int getStickerId() {
        return stickerId;
    }

    public void setStickerId(int stickerId) {
        this.stickerId = stickerId;
    }

    public ParkingSlotEntity getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlotEntity parkingSlot) {
        this.parkingSlot = parkingSlot;
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
