package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileToUpload,
                             Model model) {

        try {
            if(fileToUpload.isEmpty()) {
                model.addAttribute("errorMessage", "You have to select a file to upload");
                return "result";
            }

            if(!fileToUpload.isEmpty() && fileToUpload.getSize() > 1000000) {
                model.addAttribute("errorMessage", "File size too large for upload");
                return "result";
            }

            if(fileService.getFileByFileName(fileToUpload.getOriginalFilename()) != null) {
                model.addAttribute("errorMessage", "File already exists");
                return "result";
            }

            User currentUser = userService.getUser(authentication.getName());
            Integer userId = currentUser.getUserId();
            fileService.uploadFile(fileToUpload, userId);
            model.addAttribute("successMessage", "File has been uploaded successfully");
        }
        catch(Exception e) {
            model.addAttribute("failureMessage", "Something went wrong, your changes were not saved. Please try again!");
        }
        return "result";

    }

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") Integer fileId) {
        File fileToBeDownloaded = fileService.getFileByFileId(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileToBeDownloaded.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename\"" + fileToBeDownloaded.getFileName() + "\"")
                .body(new ByteArrayResource(fileToBeDownloaded.getFileData()));
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {

        try{
            fileService.deleteFile(fileId);
            model.addAttribute("successMessage", "File deleted successfully");
        }
        catch(Exception e){
            model.addAttribute("failureMessage", "Something went wrong, your changes were not saved. Please try again!");
        }
        return "result";
    }
}
