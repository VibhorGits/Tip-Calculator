package com.example.tippy

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var BaseAmount: EditText
    private lateinit var SeekBar: SeekBar
    private lateinit var PercentLabel: TextView
    private lateinit var TipValue: TextView
    private lateinit var TotalAmount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseAmount = findViewById(R.id.BaseAmount)
        SeekBar = findViewById(R.id.SeekBar)
        PercentLabel = findViewById(R.id.PercentLabel)
        TipValue = findViewById(R.id.TipValue)
        TotalAmount = findViewById(R.id.TotalAmount)

        SeekBar.progress = INITIAL_TIP_PERCENT
        PercentLabel.text = "$INITIAL_TIP_PERCENT%"

        SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG,"onProgressChanged $p1")
                PercentLabel.text = "$p1%"
                computeTipandTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        BaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"afterTextChanged $p0")
                computeTipandTotal()
            }

        })
    }

    private fun computeTipandTotal() {
        if (BaseAmount.text.isEmpty()){
            TipValue.text = ""
            TotalAmount.text = ""
            return
        }
        // 1.Get the value of the base and tip percent
        val baseamount = BaseAmount.text.toString().toDouble()
        val tippercent = SeekBar.progress
        // 2.Compute the tip and total
        val tipamount = baseamount * tippercent / 100
        val totalamount = baseamount + tipamount
        // 3.Update the UI
        TipValue.text = "%.2f".format(tipamount)
        TotalAmount.text = "%.2f".format(totalamount)
    }
}