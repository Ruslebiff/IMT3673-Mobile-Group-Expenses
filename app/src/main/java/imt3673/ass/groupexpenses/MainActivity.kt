package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // The storage for all expenses
    val expenses: Expenses = Expenses()
    var settlement = listOf<Transaction>()


    private val fragMain = FragMainActivity()
    private val fragSettlement = FragSettlement()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
    }

    // implements the settlement calculation and keeps it in this.settlement
    fun updateSettlement() {
        this.settlement = calculateSettlement(this.expenses)
    }

    // TODO implement setupUI method
    private fun setupUI() {
        supportFragmentManager.beginTransaction().add(R.id.main_view, fragMain, "main").commit()
    }

    fun showSettlement() {
        supportFragmentManager.beginTransaction().addToBackStack("settlement").replace(R.id.main_view, fragSettlement, "settlement").commit()
    }



}
