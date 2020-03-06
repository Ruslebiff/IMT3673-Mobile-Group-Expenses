package imt3673.ass.groupexpenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
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
        if (arguments != null) {
            val settlements = requireArguments().getParcelableArray("settlement")?.filterIsInstance<Transaction>()
            settlements!!.forEach{
                val row = TableRow(context)
                row.layoutParams = TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)

                val payer = TextView(context)
                val payee = TextView(context)
                val amount = TextView(context)

                payer.text = it.payer
                payee.text = it.payee
                amount.text = convertAmountToString(it.amount)

                row.addView(payer)
                row.addView(payee)
                row.addView(amount)
                table_settlements.addView(row)
            }
        }
    }
}