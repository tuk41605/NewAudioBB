package edu.temple.newaudiobb

import java.io.Serializable

// It wasn't necessary to alter this file very much from the professor's code
object BookList : Serializable {

    private val BOOK_LIST : MutableList<Book> by lazy {
        ArrayList()
    }

    fun add(book: Book) {
        BOOK_LIST.add(book)
    }

    fun remove(book: Book){
        BOOK_LIST.remove(book)
    }

    fun clear(){
        BOOK_LIST.clear()
    }

    operator fun get(index: Int) = BOOK_LIST[index]

    fun size() = BOOK_LIST.size

}