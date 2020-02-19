package com.example.flaviusborojanc196;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> currentNotes;
//    public static int courseId;
    public NoteRepository(Application application){
        WGUDatabase database = WGUDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        currentNotes = noteDao.getCurrentNotes(AssessmentRepository.courseId);

    }
    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);

    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void newCourseNoteInsert(){
        new newCourseNoteInsertTask(noteDao).execute();
    };

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    public LiveData<List<Note>> getCurrentNotes() {
        return currentNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes){
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class newCourseNoteInsertTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        private newCourseNoteInsertTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            noteDao.newCourseNoteInsert();
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes){
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes){
            noteDao.update(notes[0]);
            return null;
        }
    }

}
