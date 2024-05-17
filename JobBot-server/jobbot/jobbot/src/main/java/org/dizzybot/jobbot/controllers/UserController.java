package org.dizzybot.jobbot.controllers;

import org.dizzybot.jobbot.entities.User;
import org.dizzybot.jobbot.reponses.GeneralResponse;
import org.dizzybot.jobbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // TODO: write unit test.
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Map<Object, Object> body) {
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        String email = (String) body.get("email");

        User user = new User(username, password, email);

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(new GeneralResponse("error", "Email already registered"), HttpStatus.BAD_REQUEST);
        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("id", user.getId().toString());
        responseMap.put("email", user.getEmail());
        responseMap.put("username", user.getUsername());
        responseMap.put("created_at", LocalDateTime.now().toString());
        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }

}
