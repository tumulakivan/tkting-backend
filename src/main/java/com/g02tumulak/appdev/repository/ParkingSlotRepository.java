package com.g02tumulak.appdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g02tumulak.appdev.entity.ParkingAreaEntity;
import com.g02tumulak.appdev.entity.ParkingSlotEntity;
import com.g02tumulak.appdev.entity.UserEntity;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlotEntity, Integer> {
    int countByParkingArea(ParkingAreaEntity parkingArea);

    int countByParkingAreaAndStatus(ParkingAreaEntity area, boolean status);

    ParkingSlotEntity findByUser(UserEntity user);
}
