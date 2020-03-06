package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // The storage for all expenses
    val expenses: Expenses = Expenses()
    var settlement = listOf<Transaction>()
    var fragName = ""


    private val fragMain = FragMainActivity()
    private val fragSettlement = FragSettlement()
    private val fragAddData = FragDataEntry()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragment = when(fragName){
            "main"  -> fragMain
            "data_entry"  -> fragAddData
            "settlement"  -> fragSettlement
            else          -> fragMain
        }

        when (savedInstanceState) {
            null -> supportFragmentManager.beginTransaction().add(R.id.main_view, fragment).commit()
            else -> setupUI()
        }
    }

    // implements the settlement calculation and keeps it in this.settlement
    fun updateSettlement() {
        this.settlement = calculateSettlement(this.expenses)
    }



    private fun setupUI() {
        supportFragmentManager.beginTransaction().add(R.id.main_view, fragMain, "main").commit()
    }

    fun showSettlement() {
        supportFragmentManager.beginTransaction().addToBackStack("settlement").replace(R.id.main_view, fragSettlement, "settlement").commit()
    }

    fun showAddData() {
        supportFragmentManager.beginTransaction().addToBackStack("data_entry").replace(R.id.main_view, fragAddData, "data_entry").commit()
    }

}
