package com.example.chatapp.Controller;

import com.example.chatapp.Entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.chatapp.Repository.userRepo;
import com.example.chatapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/api")
public class RegisterUser {
    @Autowired
    userRepo usrRepo;

    @Autowired
    UserService service;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder pew;

    @PostMapping("/register-user")
    public String addUser(@ModelAttribute("user") User usr, Model model) throws Exception{
        //User usr = objectMapper.readValue(body, User.class);
        model.addAttribute("user", new User());
        service.createUser(usr);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User usr, Model model, HttpSession session){
        String email = usr.getEmail();
        String uname = "";
        String password = usr.getPassword();
        model.addAttribute("user", new User());
        if(!usrRepo.findByEmail(email).isEmpty()){
            User tempUser = usrRepo.findByEmail(email).get();
            String passwordFromDb = tempUser.getPassword();
            uname = tempUser.getUserName();
            if(!pew.matches(password, passwordFromDb))
                return "registerUser";
        }else {
            return "registerUser";
        }
        session.setAttribute("username", uname);
        session.setAttribute("email", email);

        model.addAttribute("username", uname);
        return "chat";
    }

    @GetMapping("/")
    public String start(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/registerUser")
    public String registerUser(Model model){
        model.addAttribute("user", new User());
        return "registerUser";
    }
}
