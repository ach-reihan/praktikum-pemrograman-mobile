package com.dicerollercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF121212)
            ) {
                DiceWithButtonAndImage()
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result1 by remember { mutableStateOf(0) }
    var result2 by remember { mutableStateOf(0) }

    val imageResource1 = if (result1 == 0) R.drawable.dice_0 else getDiceDrawable(result1)
    val imageResource2 = if (result2 == 0) R.drawable.dice_0 else getDiceDrawable(result2)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imageResource1),
                contentDescription = "Dadu 1: $result1",
                modifier = Modifier
                    .size(120.dp)
                    .padding(end = 16.dp)
            )
            Image(
                painter = painterResource(imageResource2),
                contentDescription = "Dadu 2: $result2",
                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                result1 = (1..6).random()
                result2 = (1..6).random()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB388FF))
        ) {
            Text(text = "Roll", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (result1 != 0 && result2 != 0) {
            val pesan = if (result1 == result2) {
                "Selamat, anda dapat dadu double!"
            } else {
                "Anda belum beruntung!"
            }

            Text(
                text = pesan,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .background(Color.White)
                    .padding(12.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun getDiceDrawable(result: Int): Int {
    return when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}