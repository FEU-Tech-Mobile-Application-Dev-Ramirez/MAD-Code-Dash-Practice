package com.example.mad_code_dash_practice

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mad_code_dash_practice.ui.theme.MADCodeDashPracticeTheme

class MainActivity : ComponentActivity() {
    data class Items(
        var name: String = "",
        var price: Double = 0.0,
        var quantity: Int = 0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home)

        val bakeryStorageList = mutableListOf(
            Items("Bread",2.0, 10),
            Items("Cake", 5.0, 5),
            Items("Cookie", 1.0, 20)
        )

        // Find the TextViews for each item and set the data
        setItemData(bakeryStorageList)

        val item1Selector = findViewById<EditText>(R.id.item1Selector)
        val item2Selector = findViewById<EditText>(R.id.item2Selector)
        val item3Selector = findViewById<EditText>(R.id.item3Selector)

        // Find the Button and set the onClickListener where it will calculate the total price
        val computeBtn = findViewById<Button>(R.id.calculateBtn)

        computeBtn.setOnClickListener {
            val result = findViewById<TextView>(R.id.result)

            val item1Quantity = item1Selector.text.toString().toInt()
            val item2Quantity = item2Selector.text.toString().toInt()
            val item3Quantity = item3Selector.text.toString().toInt()

            if (!checkStock(bakeryStorageList[0], item1Quantity)) {
                result.text = "Not enough stock for ${bakeryStorageList[0].name}"
            } else if (!checkStock(bakeryStorageList[1], item2Quantity)) {
                result.text = "Not enough stock for ${bakeryStorageList[1].name}"
                return@setOnClickListener
            } else if (!checkStock(bakeryStorageList[2], item3Quantity)) {
                result.text = "Not enough stock for ${bakeryStorageList[2].name}"
                return@setOnClickListener
            } else {
                val total = (bakeryStorageList[0].price * item1Quantity) + (bakeryStorageList[1].price * item2Quantity) + (bakeryStorageList[2].price * item3Quantity)
                result.text = "Total: $total"

                bakeryStorageList[0].quantity -= item1Quantity

                //setItemData(bakeryStorageList)
            }
        }


    }

    fun checkStock(item: Items, quantity: Int): Boolean {
        return item.quantity >= quantity
    }

    fun setItemData(bakeryStorageList: MutableList<Items>) {
        val item1 = findViewById<TextView>(R.id.item1)
        val item2 = findViewById<TextView>(R.id.item2)
        val item3 = findViewById<TextView>(R.id.item3)

        item1.text = "${bakeryStorageList[0].name} - ${bakeryStorageList[0].price}: ${bakeryStorageList[0].quantity}"
        item2.text = "${bakeryStorageList[1].name} - ${bakeryStorageList[1].price}: ${bakeryStorageList[1].quantity}"
        item3.text = "${bakeryStorageList[2].name} -: ${bakeryStorageList[2].price}: ${bakeryStorageList[2].quantity}"
    }
}