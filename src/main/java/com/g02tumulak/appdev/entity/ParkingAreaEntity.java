package com.g02tumulak.appdev.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ParkingAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int areaId;

    private String areaName;
    private int totalSlots;
    private int availableSlots;

    @OneToMany(mappedBy = "parkingArea", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ParkingSlotEntity> parkingSlots;

    public ParkingAreaEntity() {
        super();
    }

    public ParkingAreaEntity(String areaName, int totalSlots, int availableSlots) {
        this.areaName = areaName;
        this.totalSlots = totalSlots;
        this.availableSlots = availableSlots;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaID(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public List<ParkingSlotEntity> getParkingSlots() {
        return parkingSlots;
    }

    public void setParkingSlots(List<ParkingSlotEntity> parkingSlots) {
        this.parkingSlots = parkingSlots;
    }

}
