package com.g02tumulak.appdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g02tumulak.appdev.entity.ParkingAreaEntity;

@Repository
public interface ParkingAreaRepository extends JpaRepository<ParkingAreaEntity, Integer> {

}
