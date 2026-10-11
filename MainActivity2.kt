package com.example.vp_week3_no1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vp_week3_no1.ui.theme.VP_Week3_No1Theme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VP_Week3_No1Theme(){
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    CatGame()
                }
            }
        }
    }
}

@Composable
fun CatGame(){
    var coins by remember {mutableStateOf(0.0)}
    var tapValue by remember {mutableStateOf(1.0)}
    var upgradeCost by remember {mutableStateOf(10)}
    var isCatPressed by remember {mutableStateOf(false)}
    val canUpgrade = coins >= upgradeCost
    val coinsNeeded = (upgradeCost - coins).coerceAtLeast(0.0)
    val nextTapValue = tapValue*1.5

    val coinsText = if (coins % 1.0 == 0.0) {
        coins.toInt().toString()
    }else{
        coins.toString()
    }

    val neededText = if (coinsNeeded % 1.0 == 0.0) {
        coinsNeeded.toInt().toString()
    }else{
        coinsNeeded.toString()
    }

    val tapValueText = if (tapValue % 1.0 == 0.0) {
        tapValue.toInt().toString()
    } else {
        tapValue.toString()
    }

    val nextTapValueText = if (nextTapValue % 1.0 == 0.0) {
        nextTapValue.toInt().toString()
    } else {
        nextTapValue.toString()
    }

    fun tapCat(){
        coins += tapValue
    }

    fun buyUpgrade(){
        if (coins >= upgradeCost){
            coins -= upgradeCost
            tapValue *= 1.5
            upgradeCost *= 2
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                modifier = Modifier
                    .offset(y = 44.dp)
                    .width(190.dp)
                    .height(150.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.45f)
                )
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Your Coins",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = coinsText,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00E676)
                    )
                    Text(
                        text = "${tapValueText} coins per tap",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Tap the Cat!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(
                    if (isCatPressed){
                        R.drawable.buka
                    } else {
                        R.drawable.tutup
                    }
                ),
                contentDescription = "Tap the cat",
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isCatPressed = true
                                tapCat()
                                tryAwaitRelease()
                                isCatPressed = false
                            }
                        )
                    },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "MEOWW",
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(0.8f))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .offset(y = (-40).dp)
                    .height(185.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.94f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 20.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Give Me Your Coin",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Next upgrade: +$nextTapValueText coins per tap",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Button(
                        onClick = { buyUpgrade() },
                        enabled = canUpgrade,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            disabledContainerColor = Color(0xFFBDBDBD),
                            disabledContentColor = Color.White,
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (canUpgrade) {
                                "Upgrade for $upgradeCost coins"
                            } else {
                                "Find $neededText more coins"
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)

@Composable
fun No2Preview(){
    CatGame()
}