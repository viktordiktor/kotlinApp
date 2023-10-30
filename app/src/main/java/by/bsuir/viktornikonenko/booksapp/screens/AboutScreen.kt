package by.bsuir.viktornikonenko.booksapp.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.viktornikonenko.booksapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(){
    val aboutMe = listOf(
        R.string.fact1, R.string.fact2, R.string.fact3,
        R.string.factApp1, R.string.factApp2
    )

    val context = LocalContext.current
    val urlInst = stringResource(id = R.string.urlInst)
    val urlGit = stringResource(id = R.string.urlGit)
    val instIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(urlInst)) }
    val gitIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(urlGit)) }
    val robotoFamily = FontFamily(
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_medium, FontWeight.Medium)
    )

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.mipmap.img),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )

            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(top = 10.dp),
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
            )

        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { context.startActivity(instIntent) }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(50.dp)
                        .padding(start = 30.dp)
                )

                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.inst1),
                        fontSize = 26.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = stringResource(id = R.string.inst2),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Light,
                        color = Color(R.color.Indigo)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .clickable { context.startActivity(gitIntent) }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.github),
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(50.dp)
                        .padding(start = 30.dp)
                )

                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.git1),
                        fontSize = 26.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = stringResource(id = R.string.git2),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Light,
                        color = Color(R.color.Indigo)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(50.dp)
                        .padding(start = 30.dp)
                )

                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.email1),
                        fontSize = 26.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = stringResource(id = R.string.email2),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Light,
                        color = Color(R.color.Indigo)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = stringResource(id = R.string.factsTitle),
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 14.dp)
            )
            for(factId in aboutMe){
                Text(
                    text = stringResource(id = factId),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 11.dp)
                )
            }
        }
    }
}