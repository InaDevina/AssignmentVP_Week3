package com.example.vp_week3_no1.soal4

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vp_week3_no1.soal3.ColorMatching
import com.example.vp_week3_no1.ui.theme.VP_Week3_No1Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VP_Week3_No1Theme(){
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFFAFAFA)
                ){ innerPadding ->
                    RPSgame(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

val BEST_OF = 5
val TARGET_SCORE = (BEST_OF/2) +1
val REVEAL_DELAY = 700L

enum class RPS(val emoji: String, val lable: String){
    ROCK("✊", "Rock"),
    PAPER("✋", "Paper"),
    SCISSOR("✌️", "Scissor");
}

enum class GameState{
    INITIAL,
    PICK,
    REVEAL,
    FINISHED
}

enum class Result (val text: String){
    WIN("You Win!"),
    LOSE("You Lose!"),
    DRAW("Draw")
}

@Composable
fun RPSgame(modifier: Modifier = Modifier){
    var state by rememberSaveable() {
        mutableStateOf(GameState.INITIAL)
    }
    var yourScore by rememberSaveable {
        mutableStateOf(0)
    }
    var cpuScore by rememberSaveable {
        mutableStateOf(0)
    }
    var bestScore by rememberSaveable {
        mutableStateOf(0)
    }
    var playerPick by rememberSaveable {
        mutableStateOf(RPS.ROCK)
    }
    var cpuPick by rememberSaveable {
        mutableStateOf(RPS.ROCK)
    }
    var result by rememberSaveable {
        mutableStateOf(Result.DRAW)
    }
    var buttonOrder by remember {
        mutableStateOf(RPS.values().toList().shuffled())
    }

    LaunchedEffect(state) {
        if (state == GameState.REVEAL){
            delay(REVEAL_DELAY)

            val matchOver = yourScore >= TARGET_SCORE ||
                    cpuScore >= TARGET_SCORE
            if (matchOver) {
                if (yourScore > cpuScore) {
                    bestScore = maxOf(bestScore, yourScore - cpuScore)
                }
                state = GameState.FINISHED
            } else {
                playerPick = RPS.ROCK
                cpuPick = RPS.ROCK
                result = Result.DRAW
                buttonOrder = RPS.values().toList().shuffled()
                state = GameState.PICK
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(200.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text =  "🧑 ${yourScore} - ${cpuScore} 🤖",
                fontSize = 16.sp,
                color = Color(0xFF555555),
            )
            Text(
                text = "Best of $BEST_OF",
                fontSize = 18.sp,
                color = Color(0xFF666666)
            )
        }
        Spacer(modifier = Modifier.weight(2f))

        if (state == GameState.INITIAL) {
            Text(
                text = "Rock • Paper • Scissors",
                fontSize = 25.sp,
                color = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.height(70.dp))
            Button(
                onClick = {
                    yourScore = 0
                    cpuScore = 0
                    buttonOrder = RPS.values().toList().shuffled()
                    state = GameState.PICK
                },
                modifier = Modifier
                    .width(184.dp)
                    .height(46.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBBCBD4),
                    contentColor = Color(0xFF37474F)
                )
            ) {
                Text(
                    text = "Start",
                    fontSize = 18.sp
                )
            }
        } else if (state == GameState.PICK) {
            Text(
                text = "Pick your move!",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "❔ VS ❔",
                fontSize = 32.sp,
                color = Color(0xFF555555)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                buttonOrder.forEach { rps ->
                    Button(
                        onClick = {
                            playerPick = rps
                            cpuPick = RPS.values().random()

                            if (playerPick == cpuPick) {
                                result = Result.DRAW
                            } else if (
                                (playerPick == RPS.ROCK && cpuPick == RPS.SCISSOR) ||
                                (playerPick == RPS.PAPER && cpuPick == RPS.ROCK) ||
                                (playerPick == RPS.SCISSOR && cpuPick == RPS.PAPER)
                            ) {
                                result = Result.WIN
                                yourScore++
                            } else {
                                result = Result.LOSE
                                cpuScore++
                            }
                            state = GameState.REVEAL
                        },
                        modifier = Modifier
                            .width(104.dp)
                            .height(46.dp)
                            .semantics {
                                contentDescription = "Choose ${rps.lable}"
                            },
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBBCBD4),
                            contentColor = Color(0xFF37474F)
                        )
                    ){
                        Text(
                            text = "${rps.emoji} ${rps.lable}",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        } else if (state == GameState.REVEAL) {
            Text(
                text = "${playerPick.emoji} VS ${cpuPick.emoji}",
                fontSize = 40.sp,
                color = Color(0xFF555555)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = result.text,
                modifier = Modifier.semantics {
                    contentDescription = "Round result: ${result.text}"
                },
                fontSize = 18.sp,
                color = Color(0xFF666666)
            )
        } else if (state == GameState.FINISHED) {
            Text(
                text =
                    if (yourScore > cpuScore) {
                        "You Win the Match!"
                    } else {
                        "You Lose the Match"
                    },
                fontSize = 22.sp,
                color = Color(0xFF555555)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Best Score: $bestScore",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    onClick = {
                        yourScore = 0
                        cpuScore = 0
                        playerPick = RPS.ROCK
                        cpuPick = RPS.ROCK
                        result = Result.DRAW
                        buttonOrder = RPS.values().toList().shuffled()
                        state = GameState.PICK
                    },
                    modifier = Modifier
                        .width(104.dp)
                        .height(46.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBBCBD4),
                        contentColor = Color(0xFF37474F)
                    )
                ){
                    Text(text = "Restart", fontSize = 14.sp)
                }
                Button(
                    onClick = {
                        yourScore = 0
                        cpuScore = 0
                        state = GameState.INITIAL
                    },
                    modifier = Modifier
                        .width(104.dp)
                        .height(46.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBBCBD4),
                        contentColor = Color(0xFF37474F)
                    )
                ){
                    Text(text = "Exit", fontSize = 14.sp)
                }
            }
        }
        Spacer(
            modifier = Modifier.weight(5f)
        )
    }
}

@Preview (showBackground = true, showSystemUi = true)

@Composable
fun No4Preview(){
    RPSgame()
}