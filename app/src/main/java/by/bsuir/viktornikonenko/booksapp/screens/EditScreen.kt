package by.bsuir.viktornikonenko.booksapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bsuir.viktornikonenko.booksapp.R
import by.bsuir.viktornikonenko.booksapp.navigation.Screen
import by.bsuir.viktornikonenko.booksapp.viewmodels.EditViewModel

@SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavController, onMemoryUpdate: () -> Unit){
    val viewModel: EditViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val robotoFamily = FontFamily(
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_medium, FontWeight.Medium)
    )
    MaterialTheme {
        Scaffold {
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
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Black,
                        ),

                        value = uiState.title,
                        onValueChange = { newText ->
                            viewModel.setBookTitle(newText)
                        },
                        placeholder = { Text(text= stringResource(id = R.string.title)) },
                        label = {
                            Text(
                                text = stringResource(id = R.string.title),
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Light,
                            )
                        },
                        maxLines = 10
                    )

                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            //containerColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Black,
                        ),

                        value = uiState.author,
                        onValueChange = { newText ->
                            viewModel.setBookAuthor(newText)
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.author),
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Light,
                            )
                        },
                        placeholder = {Text(text = stringResource(id = R.string.author))},
                        maxLines = 10
                    )

                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            //containerColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Black,
                        ),

                        value = uiState.lastPage.toString(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = { newText ->
                            if(newText == "") viewModel.setBookLastPage(0);
                            else viewModel.setBookLastPage(Integer.parseInt(newText))
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.lastPage),
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Light,
                            )
                        },
                        placeholder = {Text(text = stringResource(id = R.string.lastPage))},
                        maxLines = 10
                    )


                    val options = listOf(stringResource(id = R.string.status_read), stringResource(id = R.string.status_not_read))
                    var expanded by remember { mutableStateOf(false) }

                    var selectedOptionText by remember { mutableStateOf(options[if(uiState.read) 0 else 1]) }
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
                                            viewModel.setBookStatus(read = true)
                                        };
                                        else if (selectedOptionText.equals(options[1])) {
                                            viewModel.setBookStatus(read = false)
                                        };
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )

                            }
                        }
                    }
                    Row {
                        Button(
                            modifier = Modifier.padding(vertical = 10.dp),
                            onClick = {
                                viewModel.saveBook()
                                navController.navigate(Screen.MainScreen.route)
                            },

                            ) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                            Text(
                                text = "save"
                            )
                        }


                        Button(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 3 .dp),
                            onClick = {

                                viewModel.deleteBook()
                                navController.navigate(Screen.MainScreen.route)

                            },

                            ) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                            Text(
                                text = "delete"
                            )
                        }
                    }
                }
        }
        }
    }
}

