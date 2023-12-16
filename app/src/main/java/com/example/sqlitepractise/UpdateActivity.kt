package com.example.sqlitepractise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlitepractise.databinding.ActivityUpdateBinding


class UpdateActivity : AppCompatActivity() {
    lateinit var binding:ActivityUpdateBinding
    lateinit var db:NoteDatabaseHelper
    private var noteId:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = NoteDatabaseHelper(this)

        noteId=intent.getIntExtra("note_id",-1)
        if(noteId==-1){
            finish()
            return
        }

        val note=db.getNoteById(noteId)
        binding.updatetitleEditText.setText(note.title)
        binding.updatecontentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle=binding.updatetitleEditText.text.toString()
            val newContent=binding.updatecontentEditText.text.toString()
            val updatednote=Note(noteId,newTitle,newContent)
            db.updateNote(updatednote)
            finish()
            Toast.makeText(applicationContext, "Changes Done", Toast.LENGTH_SHORT).show()

        }





    }
}