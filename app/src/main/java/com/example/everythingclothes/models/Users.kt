package com.example.everythingclothes.models

 class Users {
    var name:String=""
    var email:String=""
    var Password:String=""
    var id:String=""

    constructor(name: String, email: String, Password: String, id: String) {
        this.name = name
        this.email = email
        this.Password = Password
        this.id = id
    }
    constructor()
}