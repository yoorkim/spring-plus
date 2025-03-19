package org.example.expert.domain.profileimage.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.profileimage.dto.response.ProfileImageResponse;
import org.example.expert.domain.profileimage.service.ProfileImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile-images")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileImageResponse> getProfileImage(@PathVariable Long userId) {
        return ResponseEntity.ok(profileImageService.getProfileImage(userId));
    }

    @PutMapping
    public ResponseEntity<String> uploadOrUpdateProfileImage(@AuthenticationPrincipal AuthUser authUser, @RequestParam("file") MultipartFile file) {
        profileImageService.uploadOrUpdateProfileImage(authUser.getId(), file);
        return ResponseEntity.ok("Profile image uploaded/updated successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProfileImage(@AuthenticationPrincipal AuthUser authUser) {
        profileImageService.deleteProfileImage(authUser.getId());
        return ResponseEntity.ok("Profile image deleted successfully");
    }
}
