package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/signup"})
public class SignUpController {

    @Autowired
    private UserService userService;

    public String excutedSignUpPage() {
        return "signup";
    }
}
