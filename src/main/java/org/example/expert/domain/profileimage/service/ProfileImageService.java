package org.example.expert.domain.profileimage.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.profileimage.dto.response.ProfileImageResponse;
import org.example.expert.domain.profileimage.entity.ProfileImage;
import org.example.expert.domain.profileimage.repository.ProfileImageRepository;
import org.example.expert.domain.s3.service.S3Service;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public ProfileImageResponse getProfileImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        ProfileImage profileImage = user.getProfileImage();

        if(profileImage == null) {
            throw new InvalidRequestException("Profile image not found");
        }

        return ProfileImageResponse.from(profileImage);
    }

    @Transactional
    public void uploadOrUpdateProfileImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        ProfileImage profileImage = user.getProfileImage();

        // 이미지 존재하면 삭제
        if (profileImage != null) {
            s3Service.deleteFile(profileImage.getFileName());
            profileImageRepository.delete(profileImage);
        }

        String fileUrl = s3Service.uploadFile("profile-images", file);
        String fileName = "profile-images/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        ProfileImage newImage = new ProfileImage(fileUrl, fileName);
        profileImageRepository.save(newImage);

        user.updateProfileImage(newImage);
        userRepository.save(user);
    }

    @Transactional
    public void deleteProfileImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        ProfileImage profileImage = user.getProfileImage();

        if (profileImage != null) {
            s3Service.deleteFile(profileImage.getFileName());
            profileImageRepository.delete(profileImage);
            user.updateProfileImage(null);
            userRepository.save(user);
        }
    }
}
