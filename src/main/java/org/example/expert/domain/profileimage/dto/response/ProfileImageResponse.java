package org.example.expert.domain.profileimage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.expert.domain.profileimage.entity.ProfileImage;

@Getter
@AllArgsConstructor
public class ProfileImageResponse {

    private Long id;
    private String imageUrl;
    private String fileName;

    public static ProfileImageResponse from(ProfileImage profileImage) {
        return new ProfileImageResponse(
                profileImage.getId(),
                profileImage.getImageUrl(),
                profileImage.getFileName()
        );
    }
}
