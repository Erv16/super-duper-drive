package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class NoteController {

    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String addOrUpdateNote(Authentication authentication,
                                  @ModelAttribute("note") Note note,
                                  Model model) {

        User currentUser = userService.getUser(authentication.getName());
        Integer userId = currentUser.getUserId();

        try {
            if (note.getNoteId() == null) {
                note.setUserId(userId);
                noteService.createNote(note);
                model.addAttribute("successMessage", "Note created successfully");
            } else {
                noteService.updateNote(note);
                model.addAttribute("successMessage","Note updated successfully");
            }
        }
        catch(Exception e) {
            model.addAttribute("failureMessage", "Something went wrong, your changes were not saved. Please try again!");
        }
        return "result";

    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model) {
        try {
            Note noteToBeDeleted = noteService.noteExists(noteId);
            if(noteToBeDeleted == null) {
                model.addAttribute("errorMessage","Note does not exist");
                return "redirect:/result";
            }
            noteService.deleteNote(noteId);
            model.addAttribute("successMessage","Note deleted successfully");
        }
        catch(Exception e) {
            model.addAttribute("failureMessage", "Something went wrong, your changes were not saved. Please try again!");
        }
        return "redirect:/result";
    }
}
