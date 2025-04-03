package com.example.todowithkotlin

class Todo{
    var id : Int = 0
    var todo : String
    var flag : Int
    constructor(id: Int, todo: String, flag: Int){
        this.id = id
        this.todo = todo
        this.flag = flag
    }

    constructor(id: Int, flag: Int, todo: String) {
        this.id = id
        this.flag = flag
        this.todo = todo
    }

    constructor(todo: String, flag: Int){
        this.todo = todo
        this.flag = flag
    }
}