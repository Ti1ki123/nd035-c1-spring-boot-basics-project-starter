package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.Constant.cloudStorageConst;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private EncryptionService encryptionService;

    @ModelAttribute("credential")
    public Credential initCre() {
        return new Credential();
    }


    @PostMapping("/add")
    public String addCredential(@ModelAttribute("credential") Credential credential, BindingResult bindingResult, HttpServletRequest request, Model model) {

        // Check for validation errors (uncomment if needed)
        // if (bindingResult.hasErrors()) {
        //     return "";
        // }

        // Set the user ID for the credential from the session
        credential.setUserid(((User) request.getSession().getAttribute("informationUser")).getUserid());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credential.getPasswordReal(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        // Add the credential using the service
        credentialService.addCredential(credential);



        // Forward the request
        return forwardSuccessString(request,model);
    }

    @PostMapping("/delete")
    public String deleteCredential(@ModelAttribute("credential") Credential credential, BindingResult bindingResult, HttpServletRequest request, Model model) {

        // Delete the credential using the service
        credentialService.deleteCredential(credential.getCredentialid());

        // Forward the request
        return forwardSuccessString(request,model);
    }

    @PostMapping("/change")
    public String updateCredential(@ModelAttribute("credential") Credential credential, BindingResult bindingResult, HttpServletRequest request, Model model) {

        credential.setUserid(((User) request.getSession().getAttribute("informationUser")).getUserid());
        // Update the credential using the service
        Credential tempCredential = credentialService.getCredentialById(credential.getCredentialid());
        String encryptedPassword = encryptionService.encryptValue(credential.getPasswordReal(), tempCredential.getKey());

        credential.setKey(tempCredential.getKey());
        credential.setPassword(encryptedPassword);
        credentialService.updateCredential(credential);

        // Forward the request
        return forwardSuccessString(request,model);
    }

    private String forwardSuccessString(HttpServletRequest request,Model model) {
        model.addAttribute(cloudStorageConst.TAB_PAGE , "credential");
        model.addAttribute("success", true);
        return "result";
    }

    private String forwardErrorString(HttpServletRequest request,Model model) {
        model.addAttribute(cloudStorageConst.TAB_PAGE , "credential");
        model.addAttribute("errorNotSaved", true);
        return "result";
    }

}
