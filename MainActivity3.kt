package com.example.vp_week3_no1.soal3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vp_week3_no1.ui.theme.VP_Week3_No1Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VP_Week3_No1Theme(){
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ColorMatching()
                }
            }
        }
    }
}

enum class ColorName{
    RED,
    BLUE,
    GREEN,
    YELLOW,
    PURPLE,
    ORANGE
}

enum class GameMode{
    COLOR,
    TEXT
}

fun getInkColor(colorName: ColorName): Color {
    if (colorName == ColorName.RED) {
        return Color.Red
    } else if (colorName == ColorName.BLUE) {
        return Color.Blue
    } else if (colorName == ColorName.GREEN) {
        return Color.Green
    } else if (colorName == ColorName.YELLOW) {
        return Color(0xFFFFC107)
    } else if (colorName == ColorName.PURPLE) {
        return Color(0xFF9C27B0)
    } else {
        return Color(0xFFFF9800)
    }
}

@Composable
fun ColorMatching() {
    var isStarted by remember {
        mutableStateOf(false)
    }
    var countdown by remember {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()
    var gameRunning by remember {
        mutableStateOf(false)
    }
    var word by remember {
        mutableStateOf(ColorName.RED)
    }
    var ink by remember {
        mutableStateOf(ColorName.BLUE)
    }
    var mode by remember {
        mutableStateOf(GameMode.COLOR)
    }
    var buttonLeft by remember {
        mutableStateOf(ColorName.BLUE)
    }
    var buttonRight by remember {
        mutableStateOf(ColorName.RED)
    }
    var isInkOnLeft by remember {
        mutableStateOf(true)
    }
    var score by remember {
        mutableStateOf(0)
    }
    var mistakes by remember {
        mutableStateOf(0)
    }
    var bestScore by remember {
        mutableStateOf(0)
    }
    var gameOver by remember {
        mutableStateOf(false)
    }
    var questionId by remember {
        mutableStateOf(0)
    }
    var timeLeft by remember {
        mutableStateOf(0L)
    }

    fun nextQuestion(){
        word = ColorName.values().random()
        ink = ColorName.values().random()

        while (word==ink){
            ink = ColorName.values().random()
        }

        mode = if((0..1).random()==0){
            GameMode.COLOR
        } else {
            GameMode.TEXT
        }
        isInkOnLeft = (0..1).random() == 0
        if (isInkOnLeft) {
            buttonLeft = ink
            buttonRight = word
        } else {
            buttonLeft = word
            buttonRight = ink
        }
        questionId = questionId + 1
    }

    LaunchedEffect(gameRunning, questionId, gameOver) {
        if (gameRunning && !gameOver) {
            timeLeft = 5000L

            while (timeLeft > 0L) {
                delay(100L)
                timeLeft = (timeLeft - 100L).coerceAtLeast(0L)
            }

            mistakes = mistakes + 1

            if (mistakes >= 3) {
                gameOver = true
                gameRunning = false
            } else {
                nextQuestion()
            }
        }
    }

    fun checkAnswer(answer: ColorName) {
        if (!gameRunning || gameOver) {
            return
        }
        val isCorrect =
            (mode == GameMode.COLOR && answer == ink) ||
                    (mode == GameMode.TEXT && answer == word)

        if (isCorrect){
            score = score+1
            if(score > bestScore){
                bestScore = score
            }
        }else{
            mistakes = mistakes+1
        }

        if(mistakes>=3){
            gameOver = true
            gameRunning = false
        }else{
            nextQuestion()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (!isStarted) {
                Text(
                    text = "Welcome\nto\nCOLOR WORD MATCHING",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        isStarted = true

                        scope.launch {
                            countdown = 3
                            delay(700)
                            countdown = 2
                            delay(700)
                            countdown = 1
                            delay(700)
                            countdown = -1
                            delay(700)
                            nextQuestion()
                            gameRunning = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB8CBD4),
                        contentColor = Color.DarkGray
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 6.dp
                    )
                ) {
                    Text(
                        text = "Start Game",
                        fontSize = 14.sp
                    )
                }
            }else if (gameOver){
                Text(
                    text = "Game Over!",
                    fontSize = 35.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Your Score : \n$score",
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Best Score : \n$bestScore",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        score = 0
                        mistakes = 0
                        gameOver = false
                        isStarted = true

                        scope.launch {
                            countdown = 3
                            delay(700)
                            countdown = 2
                            delay(700)
                            countdown = 1
                            delay(700)
                            countdown = -1
                            delay(700)

                            nextQuestion()
                            gameRunning = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB8CBD4),
                        contentColor = Color.DarkGray
                    )
                ){
                    Text("Restart Game")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        isStarted = false
                        gameRunning = false
                        gameOver = false
                        countdown = 0
                        score = 0
                        mistakes = 0
                        timeLeft = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB8CBD4),
                        contentColor = Color.DarkGray
                    )
                ) {
                    Text("Exit")
                }
            }else if (!gameRunning){
                Text(
                    text = when(countdown){
                        3 -> "3"
                        2 -> "2"
                        1 -> "1"
                        -1 -> "Start!"
                        else -> ""
                    },
                    fontSize = 32.sp,
                    color = Color.DarkGray
                )
            }else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mode: ${mode.name}",
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✅ $score",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "❌ $mistakes/3",
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(70.dp))
                Text(
                    text = "${(timeLeft + 999L)/1000L}s",
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = word.name,
                    fontSize = 56.sp,
                    color = getInkColor(ink)
                )
                Spacer(modifier = Modifier.height(130.dp))
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        onClick = {
                            checkAnswer(buttonLeft)
                        },
                        modifier = Modifier
                            .width(104.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB8CBD4),
                            contentColor = Color.DarkGray
                        )
                    ) {
                        Text(buttonLeft.name, fontSize = 14.sp)
                    }
                    Button(
                        onClick = {
                            checkAnswer(buttonRight)
                        },
                        modifier = Modifier
                            .width(104.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB8CBD4),
                            contentColor = Color.DarkGray
                        )
                    ) {
                        Text(buttonRight.name, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)

@Composable
fun No3Preview(){
    ColorMatching()
}