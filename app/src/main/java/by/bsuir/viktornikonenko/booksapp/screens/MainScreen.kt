package by.bsuir.viktornikonenko.booksapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.bsuir.viktornikonenko.booksapp.navigation.Screen
import by.bsuir.viktornikonenko.booksapp.R
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesNavigationActions
import by.bsuir.viktornikonenko.booksapp.viewmodels.HomeViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.bsuir.viktornikonenko.booksapp.BookNote
import java.util.UUID

@Composable
fun MainScreen(navController: NavHostController, paddingValues: PaddingValues){
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navActions: MemoriesNavigationActions = remember(navController) {
        MemoriesNavigationActions(navController)
    }
    val robotoFamily = FontFamily(
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_medium, FontWeight.Medium)
    )
    HomeScreenContent(
        items = uiState.books,
        onEdit = { memoryId ->
            navActions.navigateToAddEditMemory(R.string.edit_screen, memoryId)
        },
        onRemove = {
            viewModel.deleteBook(it)
        },
        navController = navController,
        paddingValues = paddingValues
    )
}

@Composable
private fun HomeScreenContent(
    items: List<BookNote>,
    onRemove: (UUID) -> Unit,
    onEdit: (UUID) -> Unit,
    navController: NavController,
    paddingValues: PaddingValues
) {
    if(items.isEmpty()){
        Text(
            text = stringResource(id = R.string.empty_list),
            modifier = Modifier
                .padding(vertical = 1.dp, horizontal = 5.dp),
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
    }
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()

    ) {
        items(items = items) { item ->
            NoteItem(
                note = item,
                onRemove = {
                    onRemove(it)
                },
                onEdit = {
                    onEdit(it)
                }
            )
        }
    }

}

@Composable
private fun NoteItem(
    note: BookNote,
    onRemove: (UUID) -> Unit,
    onEdit: (UUID) -> Unit,
    modifier: Modifier = Modifier,
) {
    val robotoFamily = FontFamily(
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_medium, FontWeight.Medium)
    )
    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row{
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = "Round Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                )

                Column {
                    Text(
                        text = stringResource(id = R.string.label_book) + note.title,
                        modifier = Modifier
                            .padding(vertical = 1.dp, horizontal = 5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = stringResource(id = R.string.author) + note.author,
                        modifier = Modifier
                            .padding(vertical = 3.dp, horizontal = 5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Light,
                    )
                    Text(
                        text = stringResource(id = R.string.lastPage) + note.lastPage.toString(),
                        modifier = Modifier
                            .padding(vertical = 3.dp, horizontal = 5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Light,
                    )
                    if(note.read)
                        Text(
                            text = stringResource(id = R.string.status) + stringResource(id = R.string.status_read),
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 5.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Light,
                        )
                    else
                        Text(
                            text = stringResource(id = R.string.status) + stringResource(id = R.string.status_not_read),
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 5.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Light,
                        )
                }
            }

        }
        Row (modifier = Modifier
            .padding(start = 55.dp)) {
            IconButton(
                onClick =
                {
                    onEdit(note.id)
                }
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(vertical = 6.dp, horizontal = 3.dp)
                )
            }

            IconButton(
                onClick =
                {
                    onRemove(note.id)
                }
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(vertical = 6.dp, horizontal = 3.dp)
                )
            }


        }
    }

}