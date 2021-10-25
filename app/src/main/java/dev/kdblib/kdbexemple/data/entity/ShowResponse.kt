package dev.kdblib.kdbexemple.data.entity

class ShowResponse{
    constructor(name: String, rating: Double) {
        this.name = name
        this.rating = rating
    }

    var name:String = ""
    var rating: Double = 0.0
}