package com.g02tumulak.appdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g02tumulak.appdev.entity.StickerApplicationEntity;
import com.g02tumulak.appdev.service.StickerApplicationService;

@RestController
@RequestMapping(path = "api/stickerApplication")
public class StickerApplicationController {
    @Autowired
    StickerApplicationService stickerApplicationService;

    @GetMapping("/")
    public List<StickerApplicationEntity> getStickerApplications() {
        return stickerApplicationService.getAllStickerApplications();
    }

    @PostMapping("/postStickerApplication")
    public StickerApplicationEntity postStickerApplication(@RequestBody StickerApplicationEntity stickerApplication) {
        return stickerApplicationService.postStickerApplication(stickerApplication);
    }

    @PostMapping("/postStickerApplication/{userId}")
    public StickerApplicationEntity postStickerApplication(
            @PathVariable String userId,
            @RequestBody StickerApplicationEntity stickerApplication) {
        return stickerApplicationService.postStickerApplicationByUserId(userId, stickerApplication);
    }

    @PutMapping("/putStickerApplication")
    public StickerApplicationEntity putStickerApplication(@RequestParam int id,
            @RequestBody StickerApplicationEntity stickerApplication) {
        return stickerApplicationService.putStickerApplication(id, stickerApplication);
    }

    @DeleteMapping("/deleteStickerApplication/{id}")
    public ResponseEntity<Void> deleteStickerApplication(@PathVariable int id) {
        stickerApplicationService.deleteStickerApplication(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/declineStickerApplication/{id}")
    public StickerApplicationEntity declineStickerApplication(@PathVariable int id) {
        return stickerApplicationService.declineStickerApplication(id);
    }

    @PutMapping("/approveStickerApplication/{id}")
    public StickerApplicationEntity approveStickerApplication(@PathVariable int id) {
        return stickerApplicationService.approveStickerApplication(id);
    }
}
