package com.example.todoapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ListItemBinding
import kotlin.time.measureTime

class TodoAdapter(private val viewModel: TodoViewModel) :
    ListAdapter<Todo, TodoAdapter.MyViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.todoText.text = viewModel.todos.value!![holder.adapterPosition].task

        // menghapus
        holder.delBtn.setOnClickListener{
            viewModel.removeTodo(holder.adapterPosition)
//            notifyItemRemoved(position)
//            notifyItemChanged(position, viewModel.todos.value!!.size)
        }

        // mengedit
        holder.editBtn.setOnClickListener {
            val context = holder.itemView.context

            val inflater = LayoutInflater.from(context)
            val view     = inflater.inflate(R.layout.edit_item, null)

            // mengambil data sebelumnya
            val prevText = getItem(holder.adapterPosition).task
            val editText = view.findViewById<TextView>(R.id.editText)
            editText.text = prevText

            //alert
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Edit item")
                .setView(view)
                .setPositiveButton("update", DialogInterface.OnClickListener{
                    dialog, id ->

                    //edit
                    val editedText = editText.text.toString()
                    viewModel.updateTodo( holder.adapterPosition, editedText)
                    holder.todoText.text = editedText
//                    notifyDataSetChanged()
                })
                .setNegativeButton("cancel", DialogInterface.OnClickListener{
                    dialog, id->
                })

                alertDialog.create().show()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() = viewModel.todos.value!!.size



    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        val todoText = binding.todoItem
        val delBtn   = binding.btnDelete
        val editBtn  = binding.btnEdit
    }

}


class TodoDiffCallback: DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.equals(newItem)
    }
}