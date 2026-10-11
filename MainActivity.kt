package com.example.vp_week3_no1.soal1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vp_week3_no1.R
import com.example.vp_week3_no1.ui.theme.VP_Week3_No1Theme
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VP_Week3_No1Theme(){
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ReactionTest()
                }
            }
        }
    }
}

enum class state {
    START,
    WAIT,
    GO,
    RESULT,
    FINAL,
    FAILED
}
enum class response(val title: String) {
    FAST("DANG YOU ARE SO FAST BRO!"),
    GOOD("YOUR REFLEX IS GOOD"),
    NORMAL("MEH LIKE OTHER PERSON"),
    SLOW("YOU LIKE A SNAIL BRO")
}

fun cat(avgTime: Long): response {
    if (avgTime < 180) {
        return response.FAST
    } else if (avgTime < 280) {
        return response.GOOD
    } else if (avgTime < 450) {
        return response.NORMAL
    } else {
        return response.SLOW
    }
}

@Composable
fun TrialResultCard(trialTimes: List<Long?>){
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Trial Results",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Row(
                modifier = Modifier
                    .width(130.dp)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("1", fontSize = 10.sp)
                    Text(
                        text = if (trialTimes[0] != null)
                            "${trialTimes[0]}ms"
                        else "-",
                        fontSize = 9.sp
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("2", fontSize = 10.sp)
                    Text(
                        text = if (trialTimes[1] != null)
                            "${trialTimes[1]}ms"
                        else "-",
                        fontSize = 9.sp
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("3", fontSize = 10.sp)
                    Text(
                        text = if (trialTimes[2] != null)
                            "${trialTimes[2]}ms"
                        else "-",
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FinalResultsCard(
    trialTimes: List<Long?>,
    average: Long
) {
    Card(
        modifier = Modifier
            .width(210.dp)
            .height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Trial Results",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )

            Row(
                modifier = Modifier
                    .width(180.dp)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("1", color = Color(0xFF4CAF50))
                Text("2", color = Color(0xFF4CAF50))
                Text("3", color = Color(0xFF4CAF50))
            }

            Row(
                modifier = Modifier
                    .width(180.dp)
                    .padding(top = 4.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = trialTimes[0]?.let { "${it}ms" } ?: "-",
                    fontSize = 10.sp
                )

                Text(
                    text = trialTimes[1]?.let { "${it}ms" } ?: "-",
                    fontSize = 10.sp
                )

                Text(
                    text = trialTimes[2]?.let { "${it}ms" } ?: "-",
                    fontSize = 10.sp
                )
            }

            Text(
                text = "Average Score",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )

            Text(
                text = "${average}ms",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6D00)
            )
        }
    }
}

@Composable
fun ReactionTest() {
    var currentState = remember {
        mutableStateOf(state.START)
    }
    var trialNumber = remember {
        mutableStateOf(1)
    }
    var startTime = remember {
        mutableStateOf(0L)
    }
    var trialTimes = remember {
        mutableStateOf(listOf<Long?>(null, null, null))
    }
    var showHistory = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(currentState.value) {
        if (currentState.value == state.WAIT) {
            val randomDelay = Random.nextLong(500, 4501)
            delay(randomDelay)
            startTime.value = System.currentTimeMillis()
            currentState.value = state.GO
        }
    }

    if (currentState.value == state.START) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF6BC5D4)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Reaction",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.gambar_petir),
                contentDescription = "Petir",
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "Test",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                text = "Click to Start",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.clickable {
                    currentState.value = state.WAIT
                }
            )
            if (showHistory.value){
                TrialResultCard(trialTimes.value)
            }
        }
    } else if (currentState.value == state.WAIT) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .clickable {
                    currentState.value = state.FAILED
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Get Ready",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.warn_sign),
                contentDescription = "Warning",
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "Wait for green light",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                text = "DON'T CLICK YET",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    } else if (currentState.value == state.GO) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF4CAF50))
                .clickable {
                    val reactionTime = System.currentTimeMillis() - startTime.value
                    trialTimes.value = trialTimes.value.toMutableList().apply {
                        set(trialNumber.value - 1, reactionTime)
                    }
                    currentState.value = state.RESULT
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "GO!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.women_run),
                contentDescription = "running",
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "CLICK NOW!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            Text(
                text = "TAP AS FAST AS YOU CAN!",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    } else if (currentState.value == state.RESULT) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF4CAF50)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Trial ${trialNumber.value} Complete!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.success),
                contentDescription = "success",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(bottom = 25.dp)
            )
            Text(
                text = "Time: ${trialTimes.value[trialNumber.value - 1]}ms",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            if (trialNumber.value < 3) {
                Text(
                    text = "Continue to Trial ${trialNumber.value + 1}",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .clickable {
                            trialNumber.value = trialNumber.value + 1
                            currentState.value = state.WAIT
                        }
                )
            } else {
                Text(
                    text = "See Final Result",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .clickable {
                            currentState.value = state.FINAL
                        }
                )
            }
            TrialResultCard(trialTimes.value)
        }
    } else if (currentState.value == state.FAILED) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Trial ${trialNumber.value} Failed!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Text(
                text = "FAIL!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Image(
                painter = painterResource(R.drawable.jempolbawah),
                contentDescription = "fail",
                modifier = Modifier
                    .size(250.dp)
                    .padding(top = 25.dp, bottom = 20.dp)
            )
            Text(
                text = "You Clicked too early, TRY TO \n READ THE RULE BRO",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "TRY AGAIN",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .clickable {
                        showHistory.value = true
                        currentState.value = state.START
                    }
            )
            TrialResultCard(trialTimes.value)
        }
    } else if (currentState.value == state.FINAL) {
        val validTimes = trialTimes.value.filterNotNull()

        val average = if (validTimes.isNotEmpty()) {
            validTimes.average().toLong()
        } else {
            0L
        }

        val result = cat(average)

        val resultColor = if (result == response.FAST) {
            Color(0xFF00D878)
        } else if (result == response.GOOD) {
            Color(0xFF2196F3)
        } else if (result == response.NORMAL) {
            Color(0xFFFF9800)
        } else {
            Color(0xFFFF5722)
        }

        val resultImage = if (result == response.FAST) {
            R.drawable.reflex_fast
        } else if (result == response.GOOD) {
            R.drawable.reflex_good
        } else if (result == response.NORMAL) {
            R.drawable.reflex_normal
        } else {
            R.drawable.reflex_slow
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(resultColor)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = result.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Image(
                painter = painterResource(id = resultImage),
                contentDescription = result.title,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "Average: ${average}ms",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                text = "Click to Start New Test",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .clickable {
                        trialNumber.value = 1
                        trialTimes.value = listOf(null, null, null)
                        showHistory.value = false
                        currentState.value = state.START
                    }
            )
            FinalResultsCard(
                trialTimes = trialTimes.value,
                average = average
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)

@Composable
fun No1Preview(){
    ReactionTest()
}


