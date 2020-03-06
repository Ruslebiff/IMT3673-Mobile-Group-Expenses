package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


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
            null -> setupUI()
            else -> { // Populate the expenses again and resume correct fragment
                val savedList = savedInstanceState.getParcelable<Expenses>("expenses")
                fragName = savedInstanceState.getString("fragment").toString()

                // Restore the list into expenses
                savedList!!.allExpenses().forEach{
                    expenses.add(SingleExpense(it.person,it.amount,it.description))
                }

                supportFragmentManager.beginTransaction().add(R.id.main_view, fragment).commit()
            }
        }
    }

    // Whenever user rotates, save all expenses and the fragment user is on
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("expenses", expenses)
        outState.putString("fragment", fragName)
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
