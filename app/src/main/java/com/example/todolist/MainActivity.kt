package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapter()
        setGesture()
        fetchData()
    }

    override fun onStart() {
        super.onStart()
    }

    fun fetchData(){
        TodoViewModel().loadTasks(this)
    }

    fun setAdapter(){
        val todosLayoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,false)
        val todosAdapter = TodoAdapter(this)
//        todosLayoutManiger.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
//            override fun getSpanSize(position: Int): Int {
//                return if(missionAdapter.getItemViewType(position) == missionAdapter.VIEW_TYPE_HEADER) 2 else 1
//            }
//        }
        todoListView.adapter = todosAdapter
        todoListView.layoutManager = todosLayoutManager
    }


    fun setGesture(){
        addButton.setOnClickListener{
            var detail = todoDetailTextView.text.toString()

            if(detail.isEmpty()){
                return@setOnClickListener
            }
            else {
                var todo = TodoManager.shared.createTodo(detail, false)
                TodoViewModel().addToto(this, todo)
                todoDetailTextView.setText("")
                todoListView.adapter?.notifyDataSetChanged()
                todayButton.isActivated = false
            }
        }
    }
}