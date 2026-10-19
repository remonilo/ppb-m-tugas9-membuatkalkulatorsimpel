package com.remonilo.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.remonilo.simplecalculator.ui.theme.SimpleCalculatorTheme

private val HeaderBlue = Color(0xFF2F6FE0)
private val PurplePrimary = Color(0xFF6C4CE0)
private val GreenPrimary = Color(0xFF3BB77E)
private val FieldBackground = Color(0xFFEFF2F9)
private val ScreenBackground = Color(0xFFF6F8FC)

private val Operators = listOf("+", "-", "×", "÷")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalculatorTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator() {
    var num1 by rememberSaveable { mutableStateOf("10") }
    var num2 by rememberSaveable { mutableStateOf("8") }
    var operator by rememberSaveable { mutableStateOf("+") }
    var result by rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = ScreenBackground) {
        Column(modifier = Modifier.fillMaxSize()) {
            CalcHeader()
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .navigationBarsPadding()
                        .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                CalcInput(
                    label = "Masukkan angka pertama",
                    value = num1,
                    onValueChange = { num1 = it },
                )
                CalcInput(
                    label = "Masukkan angka kedua",
                    value = num2,
                    onValueChange = { num2 = it },
                )

                Text(
                    text = "Pilih operator",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B7280),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Operators.forEach { op ->
                        OperatorButton(
                            symbol = op,
                            selected = op == operator,
                            onClick = { operator = op },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                Button(
                    onClick = {
                        val a = num1.toIntOrNull() ?: 0
                        val b = num2.toIntOrNull() ?: 0
                        result =
                            when (operator) {
                                "+" -> (a + b).toString()
                                "-" -> (a - b).toString()
                                "×" -> (a * b).toString()
                                "÷" -> if (b != 0) (a.toDouble() / b.toDouble()).toString() else "Tidak bisa dibagi dengan nol"
                                else -> "Operator tidak valid"
                            }
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                ) {
                    Text("Hitung", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                CalcInput(
                    label = "Hasil",
                    value = result,
                    onValueChange = {},
                )
            }
        }
    }
}

@Composable
private fun OperatorButton(
    symbol: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    if (selected) {
        Button(
            onClick = onClick,
            modifier = modifier.height(52.dp),
            shape = shape,
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
        ) {
            Text(symbol, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(52.dp),
            shape = shape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = PurplePrimary),
        ) {
            Text(symbol, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun CalcHeader() {
    Surface(color = HeaderBlue, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Simple Calculator",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun CalcInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF6B7280),
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = FieldBackground,
                    unfocusedContainerColor = FieldBackground,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    SimpleCalculatorTheme {
        Calculator()
    }
}
