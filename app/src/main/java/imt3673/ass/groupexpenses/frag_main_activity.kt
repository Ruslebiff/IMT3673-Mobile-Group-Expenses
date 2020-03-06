package imt3673.ass.groupexpenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_activity_main.*
import kotlinx.android.synthetic.main.frag_data_entry.*

class FragMainActivity : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_activity_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mA: MainActivity = activity as MainActivity
        mA.fragName = "main"

        btn_add_data.setOnClickListener{
            val mA : MainActivity = activity as MainActivity
            mA.showAddData()
        }

        btn_settlement.setOnClickListener{
            val mA : MainActivity = activity as MainActivity
            mA.showSettlement()
        }

        showRows()

        // update total and average
        var total: Long = 0L
        var persons = 0
        var personList = mutableListOf<String>()
        mA.expenses.allExpenses().forEach{
            total+=it.amount
            if (!personList.contains(it.person)){
                personList.add(it.person)
                persons++
            }
        }
        if (persons.equals(0)){
            persons = 1
        }
        val divider = total / persons

        txt_expenses_total.text = convertAmountToString(total)
        txt_expenses_avr.text = convertAmountToString(divider)
    }

    private fun showRows(){
        val mA : MainActivity = activity as MainActivity
        mA.expenses.allExpenses().forEach{
            val row = TableRow(context)
            row.layoutParams = TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)

            val name = TextView(context)
            name.text = it.person

            val amount = TextView(context)
            amount.text = convertAmountToString(it.amount)

            val description = TextView(context)
            description.text = it.description

            row.addView(name)
            row.addView(amount)
            row.addView(description)

            tbl_expenses.addView(row)
        }

        if (mA.expenses.allExpenses().size > 1){
            btn_settlement.isClickable = true
            btn_settlement.isEnabled = true
        }
    }
}