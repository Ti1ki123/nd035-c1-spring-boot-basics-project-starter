package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CloudStorageErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        // default error
        model.addAttribute("error","SYSTEM ERROR ; PLEASE CONTACT TO ADMIN");
        model.addAttribute("anyError",true);
        return "result";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

