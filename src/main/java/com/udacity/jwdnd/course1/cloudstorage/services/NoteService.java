package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper notesMapper;

    public NoteService(NoteMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public int createNote(Note note) {
        return notesMapper.insert(note);
    }

    public List<Note> getNotesByUserId(Integer userId) {
        return notesMapper.getNotesByUserId(userId);
    }

    public int updateNote(Note note) {
        return notesMapper.update(note);
    }

    public void deleteNote(Integer noteId) {
        notesMapper.delete(noteId);
    }

    public Note noteExists(Integer noteId) {
        return notesMapper.getNoteByNoteId(noteId);
    }
}
