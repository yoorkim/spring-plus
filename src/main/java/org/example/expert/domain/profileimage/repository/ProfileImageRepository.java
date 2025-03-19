package org.example.expert.domain.profileimage.repository;

import org.example.expert.domain.profileimage.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

}
