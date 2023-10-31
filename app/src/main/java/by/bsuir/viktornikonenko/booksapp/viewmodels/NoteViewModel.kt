package by.bsuir.viktornikonenko.booksapp.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.viktornikonenko.booksapp.BookNote
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesDestinationsArgs.MEMORY_ID_ARG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

interface BooksDataSource {

    fun getBooks(): Flow<List<BookNote>>
    fun getBook(id: UUID?): Flow<BookNote?>

    suspend fun upsert(book: BookNote)
    suspend fun delete(id: UUID)
}

object InMemoryBookNotesDatasource : BooksDataSource {

    private val DefaultBooks = listOf(
        BookNote("Best of the best", true, 22, "Viktor Nikonenko"),
        BookNote("Papanya", false, 10, "Papich Papanya")
        )

    private val bookNotes = DefaultBooks.associateBy { it.id }.toMutableMap() // (1)

    private val _booksFlow = MutableSharedFlow<Map<UUID, BookNote>>(1) // (2)


    override fun getBooks(): Flow<List<BookNote>> {

        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                _booksFlow.emit(bookNotes)
                delay(5000L)
            }
        }
        return _booksFlow.asSharedFlow().map { it.values.toList() }
    }
    override fun getBook(id: UUID?): Flow<BookNote?> {

        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                _booksFlow.emit(bookNotes)
                delay(5000L)
            }
        }

        return _booksFlow.asSharedFlow().map { it[id] }
    }

    override suspend fun upsert(book: BookNote) {
        bookNotes[book.id] = book
    }

    override suspend fun delete(id: UUID) {
        bookNotes.remove(id)
        delay(5000L)
    }
}

interface BookRepository {
    fun getBooks(): Flow<List<BookNote>>
    fun getBook(id: UUID?): Flow<BookNote?>

    suspend fun upsert(book: BookNote)
    suspend fun delete(id: UUID)
}

object BookRepositoryImpl : BookRepository {

    private val dataSource: BooksDataSource = InMemoryBookNotesDatasource

    override fun getBooks(): Flow<List<BookNote>> {
        return dataSource.getBooks()
    }


    override fun getBook(id: UUID?): Flow<BookNote?> {
        return dataSource.getBook(id)
    }

    override suspend fun upsert(book: BookNote) {
        dataSource.upsert(book)
    }

    override suspend fun delete(id: UUID) {
        dataSource.delete(id)
    }

}


data class BooksListUiState(
    val books: List<BookNote> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)


data class BookNoteUIState(
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val author: String = "",
    val lastPage: Int = 0,
    val read: Boolean = false,

    val isLoading: Boolean = false,
    val isBookNoteSaved: Boolean = false,
    val isBookNoteSaving: Boolean = false,
    val bookNoteSavingError: String? = null
) {
    val bookNotesDate = Date()
}

class EditViewModel(

    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val repository: BookRepository = BookRepositoryImpl

    private var bookNoteId: String? = savedStateHandle[MEMORY_ID_ARG]

    private val _uiState = MutableStateFlow(BookNoteUIState())
    val uiState: StateFlow<BookNoteUIState> = _uiState.asStateFlow()

    init {
        if (bookNoteId != null) {
            loadBook(UUID.fromString(bookNoteId))
        }
    }

    private fun loadBook(bookNoteId: UUID?) {

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.getBook(bookNoteId).first()
            if (result == null) {
                _uiState.update { it.copy(isLoading = false) }
            } else {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        title = result.title,
                        author = result.author,
                        read = result.read,
                        lastPage = result.lastPage
                    )
                }
            }
        }


    }


    fun saveBook() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBookNoteSaving = true) }
                System.out.println(_uiState.value.title)
                if (bookNoteId != null) {
                    repository.upsert(
                        BookNote(
                            id = UUID.fromString(bookNoteId),
                            title = _uiState.value.title,
                            author = _uiState.value.author,
                            read = _uiState.value.read,
                            lastPage = _uiState.value.lastPage
                        )
                    )
                } else {
                    repository.upsert(
                        BookNote(
                            title = _uiState.value.title,
                            author = _uiState.value.author,
                            read = _uiState.value.read,
                            lastPage = _uiState.value.lastPage
                        )
                    )
                }
                _uiState.update{it.copy(isBookNoteSaved = true)}

            } catch (e: Exception){
                _uiState.update { it.copy(bookNoteSavingError = "unable to save or edit book") }
            } finally {
                _uiState.update { it.copy(isBookNoteSaving = false) }
            }

        }


    }

    fun deleteBook() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBookNoteSaving = true) }

                if(bookNoteId!=null) {
                    repository.delete(UUID.fromString(bookNoteId))
                }
                _uiState.update { it.copy(isBookNoteSaved = true) }
            }
            catch (e: Exception) {
                System.out.println(e)
                _uiState.update { it.copy(bookNoteSavingError = "Error!!!") }
            }
            finally {
                _uiState.update { it.copy(isBookNoteSaving  = false) }
            }

        }
    }

    fun setBookTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }
    fun setBookAuthor(author: String) {
        _uiState.update { it.copy(author = author) }
    }
    fun setBookStatus(read: Boolean) {
        _uiState.update { it.copy(read = read) }
    }
    fun setBookLastPage(page: Int) {
        _uiState.update { it.copy(lastPage = page) }
    }

}


class HomeViewModel : ViewModel() {

    private val repository: BookRepository = BookRepositoryImpl
    private val books = repository.getBooks()


    private val bookLoadingItems = MutableStateFlow(0)
    val uiState = combine(books, bookLoadingItems) { books, loadingItems ->
        BooksListUiState(
            books = books.toList(),
            isLoading = loadingItems > 0,
            isError = false

        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BooksListUiState(isLoading = true)
    )



    private suspend fun withLoading(block: suspend () -> Unit) {
        try {
            addLoadingElement()
            block()
        }
        finally {
            removeLoadingElement()
        }
    }

    private fun addLoadingElement() = bookLoadingItems.getAndUpdate { num -> num + 1 }
    private fun removeLoadingElement() = bookLoadingItems.getAndUpdate { num -> num - 1 }
    fun deleteBook(bookId: UUID){
        viewModelScope.launch {
            withLoading { repository.delete(bookId) }
        }
    }

}