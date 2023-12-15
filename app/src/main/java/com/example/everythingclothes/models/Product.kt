package com.example.everythingclothes.models

class Product {
    var name:String=""
    var price:String=""
    var imageUrl:String=""
    var id:String=""
    var quantity:String=""

    constructor(name: String, price: String, imageUrl: String, id: String, quantity: String) {
        this.name = name
        this.price = price
        this.imageUrl = imageUrl
        this.id = id
        this.quantity = quantity
    }
    constructor()
}