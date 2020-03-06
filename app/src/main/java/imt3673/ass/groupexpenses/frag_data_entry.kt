package imt3673.ass.groupexpenses

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_data_entry.*

class FragDataEntry : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_data_entry, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mA: MainActivity = activity as MainActivity
        mA.fragName = "data_entry"
        setUpTextListeners()

        // Cancel
        btn_cancel.setOnClickListener(){
            edit_person.text!!.clear()
            edit_amount.text!!.clear()
            edit_description.text!!.clear()
            mA.supportFragmentManager.findFragmentByTag("main")
            mA.supportFragmentManager.popBackStack()
        }

        // Add expense
        btn_add_expense.setOnClickListener(){
            val mA : MainActivity = activity as MainActivity

            // TODO: save edit_person, edit_amount, edit_description
            // add data fields to
            mA.expenses.add(SingleExpense(edit_person.text.toString(), convertStringToAmount(edit_amount.text.toString()).getOrDefault(0), edit_description.text.toString()))

            edit_person.text!!.clear()
            edit_amount.text!!.clear()
            edit_description.text!!.clear()
            mA.supportFragmentManager.findFragmentByTag("main")
            mA.supportFragmentManager.popBackStack()
        }

    }

    // Text field listeners
    private fun setUpTextListeners(){
        edit_person.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkText()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        edit_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkText()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        edit_description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkText()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }

    // Checks if text fields are filled, enable btn_add_expense
    private fun checkText(){

        val nameOk: Boolean = edit_person.text.toString().matches(Regex(pattern = "[A-Za-z \\-]+"))
        var amountOk: Boolean = edit_amount.text.toString().matches(Regex(pattern = "[/^(\\d+(?:[\\.\\,]\\d{2})?)\$/]+"))
        var commas = 0
        edit_amount.text.toString().forEach {
            if (it.equals(".") || it.equals(",")){
                commas++
            }
        }

        when {      // disable button if multiple commas or dots are written in amount field
            commas > 1 -> {
                amountOk = false
            }
        }

        if (nameOk && amountOk && fieldsAreFilled()){
            btn_add_expense.isClickable = true
            btn_add_expense.isEnabled = true

            // TODO: change color of button when it is enabled/disabled

        }
    }

    // Returns true if all fields are not empty
    private fun fieldsAreFilled() : Boolean {
        return (edit_person.text!!.toString() != ""
                && edit_amount.text!!.toString() != ""
                && edit_description.text!!.toString() != "")
    }



}

