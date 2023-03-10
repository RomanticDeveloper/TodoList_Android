package com.example.todolist

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item.view.*


class TodoItemViewHolder(val todoItemView: View) : RecyclerView.ViewHolder(todoItemView){}

class TodoAdapter (var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_HEADER = 0
    val VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        return TodoItemViewHolder(inflator.inflate(R.layout.todo_item, parent, false))
//        return when(viewType){
//            VIEW_TYPE_HEADER -> todoItemViewHolder(inflator.inflate(R.layout.todo_item, parent, false))
//            else -> todoItemViewHolder(inflator.inflate(R.layout.todo_item, parent, false))
//        }
    }

    override fun getItemCount(): Int {
        return TodoViewModel().todos().count()  // Header추가
    }

    override fun getItemViewType(position: Int): Int {
//        return when(position){
//            0-> VIEW_TYPE_HEADER
//            else -> VIEW_TYPE_ITEM
//        }


        return VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        check(TodoViewModel().todos().isEmpty() == false) {
            Log.d("TAG", "missionList is empty")
            return
        }
        var todo = TodoViewModel().todos().get(position)

        var todoItemViewHolder = holder as TodoItemViewHolder
        todoItemViewHolder.todoItemView.todoDetail.setText(todo.detail)

        if(todo.isDone){
            todoItemViewHolder.todoItemView.stroke.visibility = View.VISIBLE

        }
        else {
            todoItemViewHolder.todoItemView.stroke.visibility = View.INVISIBLE
        }

        todoItemViewHolder.todoItemView.doneButton.setOnClickListener{
            it.doneButton.isChecked = true
            todo.isDone = false
            TodoViewModel().updateTodo(context, todo)
            this.notifyDataSetChanged()

        }


    }
}