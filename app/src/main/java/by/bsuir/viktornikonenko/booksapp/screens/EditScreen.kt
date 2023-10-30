package by.bsuir.viktornikonenko.booksapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.bsuir.viktornikonenko.booksapp.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import by.bsuir.viktornikonenko.booksapp.R
import by.bsuir.viktornikonenko.booksapp.navigation.Screen

@SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(id: String?, navController: NavController, viewModel: NoteViewModel, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState){
    val note = id?.let { viewModel.getItem(it.toInt()) }
    val robotoFamily = FontFamily(
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_medium, FontWeight.Medium)
    )
    MaterialTheme {
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var showBottomSheet by remember { mutableStateOf(true) }
        Scaffold {
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                        navController.navigate(Screen.MainScreen.route)
                    },
                    sheetState = sheetState
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                        {
                            Text(
                                text = stringResource(id = R.string.edit),
                                color = Color.DarkGray,
                                fontSize = 28.sp,
                                modifier = Modifier.padding(vertical = 10.dp),
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium,
                            )
                            var title by remember {
                                mutableStateOf(note!!.title)
                            }
                            OutlinedTextField(
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    //containerColor = Color.White,
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.Black,
                                ),

                                value = title,
                                onValueChange = { newText ->
                                    title = newText
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.title),
                                        fontFamily = robotoFamily,
                                        fontWeight = FontWeight.Light,
                                    )
                                },
                                maxLines = 10
                            )

                            var author by remember {
                                mutableStateOf(note!!.author)
                            }
                            OutlinedTextField(
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    //containerColor = Color.White,
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.Black,
                                ),

                                value = author,
                                onValueChange = { newText ->
                                    author = newText
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.author),
                                        fontFamily = robotoFamily,
                                        fontWeight = FontWeight.Light,
                                    )
                                },
                                maxLines = 10
                            )

                            var lastPage by remember {
                                mutableIntStateOf(note!!.lastPage)
                            }
                            OutlinedTextField(
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    //containerColor = Color.White,
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.Black,
                                ),

                                value = lastPage.toString(),
                                onValueChange = { newText ->
                                    lastPage = newText.toInt()
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.lastPage),
                                        fontFamily = robotoFamily,
                                        fontWeight = FontWeight.Light,
                                    )
                                },
                                maxLines = 10
                            )


                            var read by remember {
                                mutableStateOf(note!!.read)
                            }

                            val options = listOf(stringResource(id = R.string.status_read), stringResource(id = R.string.status_not_read))
                            var expanded by remember { mutableStateOf(false) }

                            var selectedOptionText by remember { mutableStateOf(options[if(read) 0 else 1]) }
                            // We want to react on tap/press on TextField to show menu
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded },
                                modifier = Modifier.padding(top = 10.dp),
                            ) {
                                TextField(
                                    // The `menuAnchor` modifier must be passed to the text field for correctness.
                                    modifier = Modifier.menuAnchor(),
                                    readOnly = true,
                                    value = selectedOptionText,
                                    onValueChange = {},
                                    label = {Text(text = stringResource(id = R.string.status),
                                        fontFamily = robotoFamily,
                                        fontWeight = FontWeight.Light,)},
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                    colors = ExposedDropdownMenuDefaults.textFieldColors(),

                                    )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                ) {
                                    options.forEach { selectionOption ->
                                        DropdownMenuItem(
                                            text = { Text(text = selectionOption,
                                                fontFamily = robotoFamily,
                                                fontWeight = FontWeight.Light,) },
                                            onClick = {
                                                selectedOptionText = selectionOption
                                                expanded = false
                                                if (selectedOptionText.equals(options[0])) {
                                                    read = true
                                                };
                                                else if (selectedOptionText.equals(options[1])) {
                                                    read = false
                                                };
                                            },
                                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                        )

                                    }
                                }
                            }
                            remember {
                                mutableStateListOf<String>()
                            }

                            Button(onClick = {
                                if (id != null) {
                                    viewModel.onClickEditNote(title, read, lastPage, author, index = id.toInt())
                                }
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Note changed",
                                        actionLabel = "Ok",
                                    )
                                }
                                navController.navigate(Screen.MainScreen.route)

                            }, enabled = author != "" && title != "",
                                modifier = Modifier.padding(vertical = 16.dp),
                                colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.
                                colorScheme.background, contentColor = MaterialTheme.colorScheme.primary)){
                                Text(
                                    text = stringResource(id = R.string.edit), fontSize = 20.sp,
                                    color = Color.DarkGray,
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }

                    }
                }
            }
        }

    }
}

