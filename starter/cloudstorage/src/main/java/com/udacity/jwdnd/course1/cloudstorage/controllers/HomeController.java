package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.FileLoad;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.Constant.cloudStorageConst;

@Controller
public class HomeController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private FileService fileService;

    @RequestMapping(path = {"/home"})
    public String showHomePage(HttpServletRequest request, HttpServletResponse response, Model model) {

        User user = (User) request.getSession().getAttribute("informationUser");
        List<Credential> credentialList = credentialService.getCredentialsByUserId(user.getUserid());
        List<Note> noteList = noteService.getNotesByUserid(user.getUserid());
        List<FileLoad> fileList = fileService.getFilesByUserId(user.getUserid());
        model.addAttribute("notes",noteList);
        model.addAttribute("credentials",credentialList);
        model.addAttribute("files",fileList);
        String tabPage = "";
        if (request.getParameter(cloudStorageConst.TAB_PAGE) != null) {
            tabPage = (String) request.getParameter(cloudStorageConst.TAB_PAGE);
        }
        model.addAttribute(cloudStorageConst.TAB_PAGE,tabPage);
        return "home";
    }

}
