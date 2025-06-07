package com.example.chatapp.Service;

import com.example.chatapp.Entity.User;
import com.example.chatapp.Repository.userRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private userRepo uRepo;

    @Autowired
    private PasswordEncoder pew;

    public void createUser(User userDTO){
        String password = userDTO.getPassword();
        userDTO.setPassword(pew.encode(password));
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        log.info(userDTO.toString());
        uRepo.save(userDTO);
    }
}
