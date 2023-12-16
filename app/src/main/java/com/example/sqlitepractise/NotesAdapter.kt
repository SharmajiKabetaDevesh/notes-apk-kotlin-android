
package com.example.sqlitepractise


import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, private val context: MainActivity) :
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

        private lateinit var db:NoteDatabaseHelper
        private lateinit var dialog:AlertDialog.Builder
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)!!
        val contentTextView = itemView.findViewById<TextView>(R.id.contentTextView)!!
        val updateButton= itemView.findViewById<ImageView>(R.id.updateButton)!!
        val deleteButton=itemView.findViewById<ImageView>(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(
                holder.itemView.context,
                UpdateActivity::class.java
            ).apply { putExtra("note_id", note.id) }
            holder.itemView.context.startActivity(intent)
db= NoteDatabaseHelper(holder.itemView.context)
            holder.deleteButton.setOnClickListener {
                dialog= AlertDialog.Builder(holder.itemView.context)
                dialog.setMessage("There is nothing important")
                dialog.setTitle("Are You Sure ??")

                dialog.setPositiveButton("Yes"){dialog,i ->
                    db.deleteNote(note.id)
                    refreshData(db.getAllNotes())
                    Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
                }
                dialog.setNegativeButton("No"){dialog,i->
                    dialog.dismiss()
                }
                dialog.create()
                dialog.show()



            }
        }


    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    }
