package com.swlc.gadgetmart.backend.main.controller;

import com.swlc.gadgetmart.backend.main.dto.UserDto;
import com.swlc.gadgetmart.backend.main.repo.UserRepo;
import com.swlc.gadgetmart.backend.main.util.TokenValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UsersController {

    private final UserRepo userRepo;

    public UsersController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PatchMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestHeader("Authorization") String auth,@RequestBody UserDto userDto) {
        boolean isValid = new TokenValidator(userRepo).validatePublicToken(auth);
        if (!isValid) {
            return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
        }
        try {
            boolean updated = userRepo.updateUser(userDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(@RequestHeader("Authorization") String auth) {
        boolean isValid = new TokenValidator(userRepo).validateAdminToken(auth);
        if (!isValid) {
            return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
        }
        try {
            List<UserDto> allUsers = userRepo.getAllUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
