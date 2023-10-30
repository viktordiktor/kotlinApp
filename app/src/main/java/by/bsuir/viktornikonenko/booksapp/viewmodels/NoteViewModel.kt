package by.bsuir.viktornikonenko.booksapp.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.util.UUID

data class BookNote(
    var title: String,
    var read: Boolean,
    var lastPage: Int,
    var author: String,
    val id: UUID = UUID.randomUUID()
)

class NoteViewModel(): ViewModel() {
    val items: SnapshotStateList<BookNote> = DefaultNotes.toMutableStateList()

    fun onClickRemoveNote(note: BookNote) = items.remove(note)

    fun onClickAddNote(title: String, read: Boolean, lastPage: Int, author: String){
        items.add(BookNote(title, read, lastPage, author))
    }

    fun onClickEditNote(title: String, read: Boolean, lastPage: Int, author: String, index : Int){
        val note = items[index]
        items[index].title = title
        items[index].read = read
        items[index].lastPage = lastPage
        items[index].author = author
    }

    fun getItem(id: Int): BookNote {
        return items[id]
    }

    private companion object {
        private val DefaultNotes = listOf(
            BookNote("Best of the best", true, 22, "Viktor Nikonenko"),
            BookNote("Papanya", false, 10, "Papich Papanya")
        )
    }
}