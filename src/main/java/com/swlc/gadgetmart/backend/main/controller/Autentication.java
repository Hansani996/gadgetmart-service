package com.swlc.gadgetmart.backend.main.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.swlc.gadgetmart.backend.main.dto.UserDto;
import com.swlc.gadgetmart.backend.main.dto.response.Response;
import com.swlc.gadgetmart.backend.main.repo.UserRepo;
import com.swlc.gadgetmart.backend.main.util.JwtManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/authentication")
public class Autentication {

    private final UserRepo userRepo;

    public Autentication(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticateUser(@RequestBody String requestBody) {
        JsonObject jsonObject = new JsonParser().parse(requestBody).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String password = DigestUtils.md5Hex(jsonObject.get("password").getAsString());
        try {
            UserDto user = userRepo.authenticateUser(username);
            if (user != null) {
                if (user.getPassword().equals(password)){
                    user.setState(true);
                    user.setMessage("User Name and Password Correct!");
                    String jws = new JwtManager().signJWS(username, password, user.getUserType());
                    user.setToken(jws);
                } else {
                    user.setState(false);
                    user.setMessage("Password Invalid!");
                }
                user.setPassword(null);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
               Response standardResponse = new Response();
                standardResponse.setMessage("User not found");
                return new ResponseEntity<>(standardResponse, HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Response standardResponse = new Response();
            standardResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(standardResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserDto userResponse) {
        try {
            boolean added = userRepo.createUser(userResponse);
            return new ResponseEntity<>(added, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
