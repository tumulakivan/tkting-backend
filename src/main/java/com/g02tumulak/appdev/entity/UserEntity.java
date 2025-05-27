package com.g02tumulak.appdev.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private String idNumber;

    private String firstName;
    private String lastName;
    private String password;
    private String type; // "student" or "staff"

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<StickerApplicationEntity> stickerApplications;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<ParkingStickerEntity> parkingStickers;

    @OneToOne(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private ParkingSlotEntity parkingSlot;

    public UserEntity() {
        super();
    }

    public UserEntity(String idNumber, String firstName, String lastName, String password, String type) {
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.type = type;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<StickerApplicationEntity> getStickerApplications() {
        return stickerApplications;
    }

    public void setStickerApplications(List<StickerApplicationEntity> stickerApplications) {
        this.stickerApplications = stickerApplications;
    }

    public List<ParkingStickerEntity> getParkingStickers() {
        return parkingStickers;
    }

    public void setParkingStickers(List<ParkingStickerEntity> parkingStickers) {
        this.parkingStickers = parkingStickers;
    }

    public ParkingSlotEntity getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlotEntity parkingSlot) {
        this.parkingSlot = parkingSlot;
    }
}