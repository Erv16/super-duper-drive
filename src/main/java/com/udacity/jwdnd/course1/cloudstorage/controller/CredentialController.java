package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService,
                                EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credential")
    public String createOrUpdateCredential(Authentication authentication,
                                           @ModelAttribute("credential")Credential credential,
                                           Model model) {

        User currentUser = userService.getUser(authentication.getName());
        Integer userId = currentUser.getUserId();
        String encodedKey = getEncodedKey();
        String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encodedPassword);

        try {
            if(credential.getCredentialId() == null) {

                credential.setUserId(userId);
                credentialService.createCredential(credential);
                model.addAttribute("successMessage", "Credential created successfully");
            }
            else {
                credentialService.updateCredential(credential);
                model.addAttribute("successMessage", "Credential updated successfully");
            }
        }
        catch (Exception e) {
            model.addAttribute("failureMessage", "Something went wrong, your changes were not saved. Please try again!");
        }

        return "result";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model) {

        try {
            Credential credentialToBeDeleted = credentialService.getCredentialByCredentialId(credentialId);
            if (credentialToBeDeleted == null) {
                model.addAttribute("errorMessage", "Credential does not exist");
                return "result";
            }
            credentialService.deleteCredential(credentialId);
            model.addAttribute("successMessage", "Credential deleted successfully");
        }
        catch (Exception e) {
            model.addAttribute("failureMessage", "Something went wrong, your changes were not saved. Please try again!");
        }
        return "result";
    }

    private String getEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
