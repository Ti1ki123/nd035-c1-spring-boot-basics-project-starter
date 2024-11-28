package com.udacity.jwdnd.course1.cloudstorage.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    @RequestMapping(path = {"/"})
    public String executeCommonController() {
        return "redirect:/home";
    }


}
