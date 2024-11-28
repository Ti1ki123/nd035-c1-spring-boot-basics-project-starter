package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.Constant.cloudStorageConst;
import com.udacity.jwdnd.course1.cloudstorage.models.FileLoad;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload-dir}")  // Inject upload directory from application.properties
    private String uploadDir;

    @Autowired
    private FileService fileService;

    @ModelAttribute("file")
    public FileLoad initModel() {
        return new FileLoad();
    }

    @PostMapping("/add")
    public String addFile(@ModelAttribute("file") FileLoad file, BindingResult bindingResult, HttpServletRequest request, Model model) throws IOException {

        // Handle file upload, assuming file is being passed in the request
        if (bindingResult.hasErrors()) {
            return forwardErrorString(request,model);
        }

        file.setUserid(((User)request.getSession().getAttribute("informationUser")).getUserid());
        String fileName = file.getFileupload().getOriginalFilename();
        String contentType = file.getFileupload().getContentType();
        String fileSize = String.valueOf(file.getFileupload().getSize());
        try {
            file.setFilename(fileName);
            file.setContenttype(contentType);
            file.setFilesize(fileSize);;
            file.setFiledata(file.getFileupload().getBytes());
            //checkexistfile
            if (fileService.getFileByFileName(file) != null) {
                return forwardErrorAnyString(request,model,"File is EXISTED");
            }

            fileService.addFile(file);

            Path targetLocation = new File(uploadDir, fileName).toPath();

            // Copy the file to the target directory
            Files.copy(file.getFileupload().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
        model.addAttribute("message", "Error uploading file: " + e.getMessage());
        return forwardErrorString(request,model);
        }
        return forwardSuccessString(request,model);
    }

    @PostMapping("/delete")
    public String deleteFile(@ModelAttribute("file") FileLoad file, BindingResult bindingResult, HttpServletRequest request, Model model) {

        FileLoad fileDB = fileService.getFileById(file.getFileId());
        File fileDeleteTemp = new File(uploadDir +"/" + fileDB.getFilename());
        if (fileDeleteTemp.exists()) {
            fileDeleteTemp.delete();
        }

        fileService.deleteFile(file.getFileId());

        return forwardSuccessString(request,model);
    }

    @PostMapping("/download")
    public String downloadFile(@ModelAttribute("file") FileLoad file, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        // Create a File object pointing to the file on the file system
        FileLoad fileDB = fileService.getFileById(file.getFileId());
        File fileDownload = new File(uploadDir +"/" + fileDB.getFilename());

        if (!fileDownload.exists()) {
            return forwardErrorAnyString(request,model,"Cannot Download as Not exist file");
        }

        // Set the appropriate headers for file download
        String contentType = "application/octet-stream"; // Default MIME type (can be dynamic)
        response.setContentType(contentType);
        response.setContentLength((int) fileDownload.length());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileDownload.getName());

        // Stream the file content to the response output stream
        try (FileInputStream fileInputStream = new FileInputStream(fileDownload);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }

        return null;
    }

    private String forwardErrorAnyString(HttpServletRequest request,Model model, String message) {
        model.addAttribute(cloudStorageConst.TAB_PAGE , "file");
        model.addAttribute("anyError",true);
        model.addAttribute("error",message);
        return "result";
    }

    private String forwardSuccessString(HttpServletRequest request,Model model) {
        model.addAttribute(cloudStorageConst.TAB_PAGE , "file");
        model.addAttribute("success", true);
        return "result";
    }

    private String forwardErrorString(HttpServletRequest request,Model model) {
        model.addAttribute(cloudStorageConst.TAB_PAGE , "file");
        model.addAttribute("errorNotSaved", true);
        return "result";
    }

}
