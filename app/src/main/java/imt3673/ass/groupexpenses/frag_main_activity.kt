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
import kotlinx.android.synthetic.main.remove_me.*
import kotlinx.android.synthetic.main.remove_me.btn_add_expense

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

        btn_add_data.setOnClickListener{
            val mA : MainActivity = activity as MainActivity
            mA.showAddData()
        }

        btn_settlement.setOnClickListener{
            val mA : MainActivity = activity as MainActivity
            mA.showSettlement()
        }

        showRows()
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

            table_expenses.addView(row)
        }

        if (mA.expenses.allExpenses().size > 1){
            btn_settlement.isClickable = true
            btn_settlement.isEnabled = true
        }
    }
}