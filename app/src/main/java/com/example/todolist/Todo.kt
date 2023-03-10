package com.example.todolist

import android.content.Context
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.RawValue
import java.util.*
import kotlin.collections.ArrayList


@Parcelize
data class Todo(
    val id: Int,
    var isDone: Boolean,
    var detail: String,
    var isToday: Boolean
) : Parcelable
{
    fun update(idDone: Boolean, detail: String, isToday: Boolean ){
        this.isDone = idDone
        this.detail = detail
        this.isToday = isToday
    }

    fun toHashMap(): HashMap<String, @RawValue Any?> {
        return hashMapOf(
            "id" to id,
            "idDone" to if(isDone!=null) isDone else false,
            "detail" to if(detail!=null) detail else "",
            "isToday" to if(isToday!=null) isToday else false,

            )
    }

}

class TodoManager {
    companion object{
        val shared = TodoManager()
        var lastId: Int = 0
    }

    var todos:ArrayList<Todo> = ArrayList<Todo>()

    fun createTodo(detail: String, isToday: Boolean): Todo  {

        var nextId = TodoManager.lastId + 1
        TodoManager.lastId = nextId

        return Todo(nextId, false, detail, isToday)
    }

    fun addTodo(context: Context, todo: Todo){
        todos.add(todo)
        saveTodo(context)

    }
    fun deleteTodo(context: Context, todo: Todo){
        todos = todos.filter { it.id != todo.id } as ArrayList<Todo>

        saveTodo(context)
    }

    fun updateTodo(context: Context, todo: Todo){
        val index = todos.indexOf(todo)
        todos[index].update(todo.isDone, todo.detail, todo.isToday)
        saveTodo(context)
    }

    fun saveTodo(context: Context) {
        Storage.store(context, todos, "todos.json")

    }

    fun retrieveTodo(context: Context) {
        todos = Storage.retrive<ArrayList<Todo>>(context, "todos.json", object : TypeToken<List<Todo>>() {}.type) ?: ArrayList<Todo>()
        val lastId = if(todos.last().id != null) todos.last().id else  0
        TodoManager.lastId = lastId
    }
}


class TodoViewModel {

    private val manager = TodoManager.shared

    fun todos(): ArrayList<Todo> {
        return manager.todos
    }

    fun todayTodos():  ArrayList<Todo> {
        return manager.todos.filter { it.isToday == true } as ArrayList<Todo>
    }

    fun upcomingTodos():  ArrayList<Todo> {
        return manager.todos.filter { it.isToday == false } as ArrayList<Todo>
    }


    fun addToto(context: Context, todo: Todo){
        manager.addTodo(context, todo)
    }

    fun deleteTodo(context: Context, todo: Todo){
        manager.deleteTodo(context, todo)
    }

    fun updateTodo(context: Context, todo: Todo){
        manager.updateTodo(context, todo)
    }



}
