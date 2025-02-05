package com.example.mad_code_dash_practice

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    data class Items(
        var name: String = "",
        var price: Double = 0.0,
        var quantity: Int = 0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val bakeryStorageList = mutableListOf(
            Items("Bread", 2.0, 10),
            Items("Cake", 5.0, 5),
            Items("Cookie", 1.0, 20)
        )

        // Initialize item data in UI
        setItemData(bakeryStorageList)

        val item1Selector = findViewById<EditText>(R.id.item1Selector)
        val item2Selector = findViewById<EditText>(R.id.item2Selector)
        val item3Selector = findViewById<EditText>(R.id.item3Selector)
        val computeBtn = findViewById<Button>(R.id.calculateBtn)
        val result = findViewById<TextView>(R.id.result)

        computeBtn.setOnClickListener {
            try {
                // Get quantity inputs safely
                val item1Quantity = item1Selector.text.toString().toIntOrNull() ?: 0
                val item2Quantity = item2Selector.text.toString().toIntOrNull() ?: 0
                val item3Quantity = item3Selector.text.toString().toIntOrNull() ?: 0

                // Check stock availability
                if (!checkStock(bakeryStorageList[0], item1Quantity)) {
                    result.text = "Not enough stock for ${bakeryStorageList[0].name}"
                    return@setOnClickListener
                }
                if (!checkStock(bakeryStorageList[1], item2Quantity)) {
                    result.text = "Not enough stock for ${bakeryStorageList[1].name}"
                    return@setOnClickListener
                }
                if (!checkStock(bakeryStorageList[2], item3Quantity)) {
                    result.text = "Not enough stock for ${bakeryStorageList[2].name}"
                    return@setOnClickListener
                }

                // Calculate total cost
                val total = (bakeryStorageList[0].price * item1Quantity) +
                            (bakeryStorageList[1].price * item2Quantity) +
                            (bakeryStorageList[2].price * item3Quantity)

                result.text = "Total: $total"

                // Update stock
                bakeryStorageList[0].quantity -= item1Quantity
                bakeryStorageList[1].quantity -= item2Quantity
                bakeryStorageList[2].quantity -= item3Quantity

                // Update UI to reflect new stock values
                setItemData(bakeryStorageList)

            } catch (e: Exception) {
                result.text = "Error: ${e.message}"
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

        item1.text = "${bakeryStorageList[0].name} - ₱${bakeryStorageList[0].price}: Stock ${bakeryStorageList[0].quantity}"
        item2.text = "${bakeryStorageList[1].name} - ₱${bakeryStorageList[1].price}: Stock ${bakeryStorageList[1].quantity}"
        item3.text = "${bakeryStorageList[2].name} - ₱${bakeryStorageList[2].price}: Stock ${bakeryStorageList[2].quantity}"
    }
}