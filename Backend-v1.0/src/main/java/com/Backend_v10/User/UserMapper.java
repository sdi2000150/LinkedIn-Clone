package com.Backend_v10.User;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserID(user.getUserID());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setLastname(user.getLastname());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setBirthdate(user.getBirthdate());
        dto.setRole(user.getRole());
        dto.setProfilePhotoUrl(user.getProfilePhotoUrl());
        dto.setCoverPhotoUrl(user.getCoverPhotoUrl());
        dto.setCvFile(user.getCvFile());
        dto.setAbout(user.getAbout());
        dto.setExperience(user.getExperience());
        dto.setExperienceDescription(user.getExperienceDescription());
        dto.setEducation(user.getEducation());
        dto.setEducationDescription(user.getEducationDescription());
        dto.setSkills(user.getSkills());
        dto.setMyArticles(user.getMyArticles());
        dto.setMyComments(user.getMyComments());
        dto.setMyJobs(user.getMyJobs());
        dto.setMyJobApplications(user.getMyJobApplications());
        dto.setMyContacts(user.getMyContacts());
        dto.setLikedArticles(user.getLikedArticles());
        return dto;
    }
}