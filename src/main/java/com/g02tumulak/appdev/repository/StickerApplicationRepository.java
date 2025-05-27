package com.g02tumulak.appdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g02tumulak.appdev.entity.StickerApplicationEntity;

public interface StickerApplicationRepository extends JpaRepository<StickerApplicationEntity, Integer> {

}
