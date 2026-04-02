package com.dicerollerxml

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.rollButton)
        val diceImage1: ImageView = findViewById(R.id.diceImage1)
        val diceImage2: ImageView = findViewById(R.id.diceImage2)
        val resultText: TextView = findViewById(R.id.resultText)

        rollButton.setOnClickListener {
            rollDice(diceImage1, diceImage2, resultText)
        }
    }

    private fun rollDice(dice1: ImageView, dice2: ImageView, resultText: TextView) {
        val randomInt1 = Random.nextInt(1, 7)
        val randomInt2 = Random.nextInt(1, 7)

        val drawableResource1 = when (randomInt1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        val drawableResource2 = when (randomInt2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        dice1.setImageResource(drawableResource1)
        dice2.setImageResource(drawableResource2)

        resultText.visibility = View.VISIBLE
        if (randomInt1 == randomInt2) {
            resultText.text = "Selamat, anda dapat dadu double!"
        } else {
            resultText.text = "Anda belum beruntung!"
        }
    }
}