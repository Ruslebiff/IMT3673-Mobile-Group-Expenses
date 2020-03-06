package imt3673.ass.groupexpenses

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.frag_activity_main.*

class MainActivity : AppCompatActivity() {

    val expenses: Expenses = Expenses()     // The storage for all expenses
    var fragName = ""                       // Name of fragment


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

    // Whenever user rotates, save all expenses and the fragment user is on
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("expenses", expenses)
        outState.putString("fragment", fragName)
    }

    // Populate the expenses again and resume correct fragment
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedList = savedInstanceState.getParcelable<Expenses>("expenses")
        fragName = savedInstanceState.getString("fragment").toString()

        // Restore the list into expenses
        savedList!!.allExpenses().forEach{
            expenses.add(SingleExpense(it.person,it.amount,it.description))
        }
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
