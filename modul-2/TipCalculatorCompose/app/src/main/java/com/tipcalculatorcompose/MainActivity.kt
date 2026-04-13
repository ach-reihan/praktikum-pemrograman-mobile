package com.tipcalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                TipCalculatorScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen() {
    val tipOptions = stringArrayResource(R.array.tip_options)

    var amountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(tipOptions[0]) }
    var roundUp by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val percent = tipPercent.replace("%", "").toDoubleOrNull() ?: 0.0
    var tip = (percent / 100) * amount
    if (roundUp) tip = ceil(tip)

    val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
    val tipAmountColor = colorResource(id = R.color.material_dynamic_primary0)
    val inputBackground = colorResource(id = R.color.input_background)
    val switchRoundUpThumb = colorResource(id = R.color.switch_round_up_thumb)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp, 0.dp, 32.dp, 0.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text(stringResource(R.string.bill_amount)) },
            leadingIcon = { Icon(painterResource(id = R.drawable.ic_money), contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackground,
                unfocusedContainerColor = inputBackground,
                disabledContainerColor = inputBackground
            )
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = tipPercent,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.tip_percentage)) },
                leadingIcon = { Icon(painterResource(id = R.drawable.ic_percent), contentDescription = null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = inputBackground,
                    unfocusedContainerColor = inputBackground,
                    disabledContainerColor = inputBackground
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            tipPercent = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.round_up_tip), fontSize = 16.sp)
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = inputBackground,
                    checkedTrackColor = switchRoundUpThumb,
                    uncheckedThumbColor = switchRoundUpThumb,
                    uncheckedTrackColor = inputBackground
                )
            )
        }

        Text(
            text = stringResource(R.string.tip_amount, formattedTip),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = tipAmountColor
        )
    }
}