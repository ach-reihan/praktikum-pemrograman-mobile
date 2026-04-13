package com.tipcalculatorxml

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var editCostOfService: TextInputEditText
    private lateinit var autoCompleteTipOptions: AutoCompleteTextView
    private lateinit var switchRoundUp: SwitchMaterial
    private lateinit var textTipResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCostOfService = findViewById(R.id.editCostOfService)
        autoCompleteTipOptions = findViewById(R.id.autoCompleteTipOptions)
        switchRoundUp = findViewById(R.id.switchRoundUp)
        textTipResult = findViewById(R.id.textTipResult)

        setupDropdown()
        displayTip(0.0)

        editCostOfService.addTextChangedListener { calculateTip() }
        autoCompleteTipOptions.setOnItemClickListener { _, _, _, _ -> calculateTip() }
        switchRoundUp.setOnCheckedChangeListener { _, _ -> calculateTip() }
    }

    private fun setupDropdown() {
        val items = resources.getStringArray(R.array.tip_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        autoCompleteTipOptions.setAdapter(adapter)

        autoCompleteTipOptions.setText(items[0], false)
    }

    private fun calculateTip() {
        val stringInTextField = editCostOfService.text.toString()

        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            displayTip(0.0)
            return
        }

        val selectedPercentText = autoCompleteTipOptions.text.toString()
        val percent = selectedPercentText.replace("%", "").toDoubleOrNull() ?: 0.0

        var tip = (percent / 100) * cost

        if (switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        textTipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}