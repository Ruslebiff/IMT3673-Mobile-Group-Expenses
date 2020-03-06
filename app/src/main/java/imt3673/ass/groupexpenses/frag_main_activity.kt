package imt3673.ass.groupexpenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_activity_main.*

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

        // TODO: settlement button should be disabled if no data is saved
        btn_settlement.setOnClickListener{
            val mA : MainActivity = activity as MainActivity
            mA.showSettlement()
        }


    }
}