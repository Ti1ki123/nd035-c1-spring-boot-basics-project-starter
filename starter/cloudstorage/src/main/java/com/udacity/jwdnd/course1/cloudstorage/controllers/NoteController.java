package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.Constant.cloudStorageConst;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.validateGroup.SignUpGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @ModelAttribute("note")
    Note initModel() {
        return new Note();
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("note") Note note, BindingResult bindingResult, HttpServletRequest request, Model model) {

//
//        if (bindingResult.hasErrors()) {
//            return "";
//        }
        note.setUserid(((User)request.getSession().getAttribute("informationUser")).getUserid());
        if (noteService.addNote(note) < 1) {
            return forwardErrorString(request,model);
        }
        return forwardSuccessString(request,model);
    }

    @PostMapping("/delete")
    public String deleteNote(@ModelAttribute("note") Note note, BindingResult bindingResult, HttpServletRequest request, Model model) {

        if (noteService.deleteNote(note) < 1) {
            return forwardErrorString(request,model);
        }
        return forwardSuccessString(request,model);
    }

    @PostMapping("/change")
    public String updateNote(@ModelAttribute("note") Note note, BindingResult bindingResult, HttpServletRequest request, Model model) {

        note.setUserid(((User)request.getSession().getAttribute("informationUser")).getUserid());
        if (noteService.updateNote(note) < 1) {
            return forwardErrorString(request,model);
        }

        return forwardSuccessString(request,model);
    }


    private String forwardSuccessString(HttpServletRequest request,Model model) {
        model.addAttribute(cloudStorageConst.TAB_PAGE , "note");
        model.addAttribute("success", true);
        return "result";
    }

    private String forwardErrorString(HttpServletRequest request,Model model) {
        model.addAttribute(cloudStorageConst.TAB_PAGE , "note");
        model.addAttribute("errorNotSaved", true);
        return "result";
    }
}
