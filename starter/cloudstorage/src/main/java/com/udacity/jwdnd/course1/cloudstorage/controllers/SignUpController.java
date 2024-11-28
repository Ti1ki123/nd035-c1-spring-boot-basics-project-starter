package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.validateGroup.SignUpGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/signup"})
public class SignUpController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showSignUpPage() {
        return "signup";
    }

    @ModelAttribute("signUpUser")
    public User initUser() {
        return new User();
    }

    @PostMapping
    public String executedSignUp(@ModelAttribute("signUpUser") @Validated(SignUpGroup.class) User user, BindingResult resultError, Model model) {

        if (resultError.hasErrors()) {
            return "signup";
        }
        int result = userService.createUser(user);
        if (result < 0) {
            model.addAttribute("messages","User is existed");
            model.addAttribute("error",true);
            return "signup";
        }
        model.addAttribute("success",true);
        return "signup";
    }
}
