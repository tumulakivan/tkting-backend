package com.g02tumulak.appdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class ParkingSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotId;

    private boolean status; // true = taken, false = free

    @ManyToOne
    @JoinColumn(name = "parkingArea")
    @JsonBackReference
    private ParkingAreaEntity parkingArea;

    @OneToOne
    @JoinColumn(name = "sticker_id")
    private ParkingStickerEntity occupant;

    @OneToOne
    @JoinColumn(name = "user_id_number")
    @JsonIgnoreProperties("parkingSlot")
    private UserEntity user;

    public ParkingSlotEntity() {
        super();
    }

    public ParkingSlotEntity(ParkingAreaEntity parkingArea) {
        this.status = false;
        this.parkingArea = parkingArea;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ParkingAreaEntity getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingAreaEntity parkingArea) {
        this.parkingArea = parkingArea;
    }

    public ParkingStickerEntity getOccupant() {
        return occupant;
    }

    public void setOccupant(ParkingStickerEntity occupant) {
        this.occupant = occupant;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
