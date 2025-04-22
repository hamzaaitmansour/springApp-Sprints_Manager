package com.genieLogiciele.sprint.controller;


import com.genieLogiciele.sprint.entities.UserApp;
import com.genieLogiciele.sprint.repo.UserAppRepo;
import com.genieLogiciele.sprint.service.UserAppService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserAppService userAppService;
    @Autowired
    private UserAppRepo userAppRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserApp user) {
        String token = userAppService.verify(user.getEmail(), user.getPassword());
        if ("Fail".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Password incorrect"));
        }

        if ("Email".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email incorrect"));
        }
        return ResponseEntity.ok(Map.of("token", token)); // 🔥 Renvoie un objet JSON { token: "..." }
    }


    @PostMapping("/auth/register")
    public String register(@Valid @RequestBody UserApp user)
    {
        return userAppService.register(user);
    }

    @GetMapping("/auth/hey")
    public String heyyy()
    { return "hey from auth il ya un erreur ";}

}