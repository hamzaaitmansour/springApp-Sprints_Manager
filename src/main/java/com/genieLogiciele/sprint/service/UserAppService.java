package com.genieLogiciele.sprint.service;


import com.genieLogiciele.sprint.entities.UserApp;
import com.genieLogiciele.sprint.entities.UserPrincipal;
import com.genieLogiciele.sprint.exception.EntityAlreadyExist;
import com.genieLogiciele.sprint.repo.UserAppRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserAppService implements UserDetailsService {
    @Autowired
    private UserAppRepo userRepo;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(); // Injecté correctement
    @Autowired
    private UserAppRepo userAppRepo;

    public UserAppService() {}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(" Utilisateur non trouvé avec l'email : " + email);
        }
        return new UserPrincipal(user);
    }
    public String register(UserApp user) {
        UserApp us=userRepo.findByEmail(user.getEmail());
        if (us != null) {
            throw new EntityAlreadyExist("ce user already exist");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "Success";
    }

    public String verify(String email, String password) {
        UserDetails userDetails;
        try {
            userDetails = loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            return "Email";
        }
        if (encoder.matches(password, userDetails.getPassword())) {
            return jwtService.generateToken(userDetails);
        }
        return "Fail";
    }


}

