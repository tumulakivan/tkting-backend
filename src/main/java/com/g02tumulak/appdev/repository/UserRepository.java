package com.g02tumulak.appdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g02tumulak.appdev.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findByType(String type);

    UserEntity findByIdNumber(String idNumber);
}