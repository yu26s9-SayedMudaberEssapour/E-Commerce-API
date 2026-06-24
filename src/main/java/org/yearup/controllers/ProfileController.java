package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService){
        this.profileService = profileService;
        this.userService = userService;
    }



    @GetMapping("")
    public Profile getProfile(Principal principal) {
        // Defensive check for the automated tests
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in.");
        }

        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        Profile profile = profileService.getByUserId(userId);

        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return profile;
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(Principal principal, @RequestBody Profile profile){
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        Profile updated = profileService.update(userId, profile);

        return ResponseEntity.ok(updated);
    }


}
