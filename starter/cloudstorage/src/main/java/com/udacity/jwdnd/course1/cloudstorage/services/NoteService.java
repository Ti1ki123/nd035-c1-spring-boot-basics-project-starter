package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    public List<Note> getNotesByUserid(int id) {
        return noteMapper.getNotesByUserid(id);
    }

    public int addNote(Note note) {
        return noteMapper.insert(note);
    }

    public int deleteNote(Note note){
         return noteMapper.delete(note.getNoteid());
    }

    public int updateNote(Note note){
        return noteMapper.update(note);
    }
}
