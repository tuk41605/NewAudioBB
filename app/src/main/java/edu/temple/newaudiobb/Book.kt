package edu.temple.newaudiobb

import java.io.Serializable

data class Book(var id: Int, var title:String, var author:String, var coverURL: String) : Serializable