
package com.example.vp_week3_no1.soal6

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTravel() {
    var spot by remember {
        mutableStateOf("Fjellheisen Cable Car")
    }
    var experience by remember {
        mutableStateOf(
            "Pemandangan langit malam hijau aurora spektakuler dari puncak gunung..."
        )
    }
    var notes by remember {
        mutableStateOf("")
    }
    var satisfaction by remember {
        mutableStateOf("Luar Biasa ★")
    }
    var status by remember {
        mutableStateOf("Draft Tersimpan")
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 40.dp,
                    bottom = 100.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF28458F),
                                Color(0xFF397FB8)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "LOG PERJALANAN",
                    color = Color(0xFFB8CFF2),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tromsø, Norway ❄",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Ekspedisi Aurora Borealis",
                    color = Color(0xFFD5E5FA),
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            TravelTextField(
                title = "SPOT WISATA FAVORIT",
                value = spot,
                onValueChange = {
                    spot = it
                    status = "Draft Tersimpan"
                },
                placeholder = "Masukkan spot wisata",
                singleLine = true,
                minLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))
            TravelTextField(
                title = "APA YANG PALING KAMU NIKMATI?",
                value = experience,
                onValueChange = {
                    experience = it
                    status = "Draft Tersimpan"
                },
                placeholder = "Ceritakan pengalamanmu...",
                singleLine = false,
                minLines = 3
            )
            Spacer(modifier = Modifier.height(10.dp))
            TravelTextField(
                title = "CATATAN TAMBAHAN / PERLENGKAPAN",
                value = notes,
                onValueChange = {
                    notes = it
                    status = "Draft Tersimpan"
                },
                placeholder = "(Ketik perlengkapan ekstra di sini...)",
                singleLine = false,
                minLines = 1
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "TINGKAT KEPUASAN:",
                color = Color(0xFF9CA9BC),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "Biasa",
                    "Seru",
                    "Luar Biasa ★"
                ).forEach { option ->
                    val selected = satisfaction == option

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selected) {
                                    Color(0xFF5CB8F2)
                                } else {
                                    Color(0xFF111827)
                                },
                                shape = RoundedCornerShape(50.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (selected) {
                                    Color(0xFF5CB8F2)
                                } else {
                                    Color(0xFF3B4659)
                                },
                                shape = RoundedCornerShape(50.dp)
                            )
                            .clickable {
                                satisfaction = option
                                status = "Draft Tersimpan"
                            }
                            .padding(
                                horizontal = 13.dp,
                                vertical = 8.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            color = if (selected) {
                                Color(0xFF111827)
                            } else {
                                Color(0xFF9CA9BC)
                            },
                            fontSize = 13.sp,
                            fontWeight = if (selected) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 20.dp,
                    bottom = 32.dp
                )
        ) {
            Text(
                text = "Status: ",
                color = Color(0xFF9CA9BC),
                fontSize = 18.sp
            )
            Text(
                text = status,
                color = Color(0xFF5CB8F2),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        FloatingActionButton(
            onClick = {
                status = "Jurnal Tersimpan"

                Toast.makeText(
                    context,
                    "Jurnal $spot berhasil disimpan!",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 20.dp,
                    bottom = 20.dp
                )
                .size(58.dp),
            containerColor = Color(0xFF5CB8F2),
            contentColor = Color(0xFF111827),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "+",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TravelTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean,
    minLines: Int
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = if (minLines == 3) {
                    120.dp
                } else {
                    75.dp
                }
            )
            .background(
                Color(0xFF202B3D),
                RoundedCornerShape(11.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF374459),
                shape = RoundedCornerShape(11.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 12.dp
            )
    ) {
        Text(
            text = title,
            color = Color(0xFF9CA9BC),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            minLines = minLines,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 17.sp,
                lineHeight = 23.sp
            ),
            cursorBrush = SolidColor(
                Color(0xFF5CB8F2)
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color(0xFF6B778B),
                            fontSize = 17.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun No5Preview() {
    MyTravel()
}
