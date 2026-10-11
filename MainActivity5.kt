package com.example.vp_week3_no1.soal5

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val hargaNormal = 25000
private val hargaLarge = 6000
private val pajak = 0.10

@Composable
fun CoffeeOrder(){
    var quantity by remember { mutableStateOf(1) }
    var isLarge by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var hargaSatuan = hargaNormal
    if (isLarge) {
        hargaSatuan = hargaNormal + hargaLarge
    }
    val subtotal = hargaSatuan*quantity
    val nilaiPajak = (subtotal*pajak).toInt()
    val total = subtotal + nilaiPajak

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFCF6F1))
                .padding(20.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Kopi Kenangan Senja",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A1F10)
                )
                Text (
                    text = "☕",
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFAF6)),
                border = BorderStroke(2.dp, Color(0xFFFAD9BF))
            ){
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color(0xFF7A3416), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "☕", fontSize = 64.sp)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Caramel Latte",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A1F10)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "Espresso shot, steamed milk & caramel syrup",
                        fontSize = 15.sp,
                        color = Color(0xFF4A1F10)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = formatRupiah(hargaSatuan) + " / cup",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE0662A)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "PILIHAN UKURAN:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A1F10)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { isLarge = false },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLarge) Color(0xFFFFEBD9) else Color(0xFFE0662A),
                        contentColor = if (isLarge) Color(0xFFB07A57) else Color.White
                    )
                ) {
                    Text("Regular (+0)")
                }
                Button(
                    onClick = { isLarge = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLarge) Color(0xFFE0662A) else Color(0xFFFFEBD9),
                        contentColor = if (isLarge) Color.White else Color(0xFFB07A57)
                    )
                ) {
                    Text("Large (+6k)")
                }
            }
            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFAF6)),
                border = BorderStroke(2.dp, Color(0xFFFAD9BF))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Jumlah Pesanan:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A1F10)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                if (quantity > 1) {
                                    quantity = quantity - 1
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFFE0662A)
                            )
                        ) {
                            Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = quantity.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A1F10),
                            modifier = Modifier.padding(horizontal = 14.dp)
                        )
                        Button(
                            onClick = { quantity = quantity + 1 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFEBD9),
                                contentColor = Color(0xFFE0662A)
                            )
                        ) {
                            Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF6EE), RoundedCornerShape(14.dp))
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFF2B98F),
                            cornerRadius = CornerRadius(14.dp.toPx()),
                            style = Stroke(
                                width = 1.5.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f))
                            )
                        )
                    }
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subtotal:", fontSize = 14.sp, color = Color(0xFF4A1F10))
                    Text(text = formatRupiah(subtotal), fontSize = 14.sp, color = Color(0xFF4A1F10))
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Pajak Resto (10%):", fontSize = 14.sp, color = Color(0xFF4A1F10))
                    Text(text = formatRupiah(nilaiPajak), fontSize = 14.sp, color = Color(0xFF4A1F10))
                }
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFF2B98F))
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Tagihan:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF4A1F10)
                    )
                    Text(
                        text = formatRupiah(total),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFFE0662A)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "$quantity Caramel Latte berhasil ditambahkan ke keranjang!"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0662A))
            ) {
                Text(
                    text = "TAMBAH KE KERANJANG",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        )
    }
}

fun formatRupiah(angka: Int): String {
    val teks = angka.toString()
    var hasil = ""
    var hitung = 0

    for (i in teks.length - 1 downTo 0) {
        hasil = teks[i] + hasil
        hitung++
        if (hitung % 3 == 0 && i != 0) {
            hasil = "." + hasil
        }
    }
    return "Rp " + hasil
}

@Preview (showBackground = true, showSystemUi = true)

@Composable
fun No5Preview (){
    CoffeeOrder()
}