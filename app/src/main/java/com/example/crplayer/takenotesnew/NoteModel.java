package com.example.crplayer.takenotesnew;

/**
 * Created by CR Sohan on 6/6/2018.
 */

public class NoteModel {

    public String noteTitle, noteTime;

    public NoteModel() {
    }

    public NoteModel(String noteTitle, String noteTime) {
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }
}

