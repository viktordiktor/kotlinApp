package by.bsuir.viktornikonenko.booksapp

import java.util.UUID

data class BookNote(
    var title: String,
    var read: Boolean,
    var lastPage: Int,
    var author: String,
    val id: UUID = UUID.randomUUID()
)