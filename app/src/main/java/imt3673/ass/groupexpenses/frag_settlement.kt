package imt3673.ass.groupexpenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_activity_main.*
import kotlinx.android.synthetic.main.frag_data_entry.*
import kotlinx.android.synthetic.main.frag_settlement.*

class FragSettlement : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_settlement, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mA: MainActivity = activity as MainActivity
        mA.fragName = "settlement"

        showSettlement()


    }

    private fun showSettlement(){
        val mA : MainActivity = activity as MainActivity
        val settlementList = calculateSettlement(mA.expenses)

        settlementList.forEach{
            val row = TableRow(context)
            row.layoutParams = TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)

            val payer = TextView(context)
            payer.text = it.payer

            val payee = TextView(context)
            payee.text = it.payee

            val amount = TextView(context)
            amount.text = convertAmountToString(it.amount)

            row.addView(payer)
            row.addView(payee)
            row.addView(amount)

            table_settlements.addView(row)
        }
    }
}