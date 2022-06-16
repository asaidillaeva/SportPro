package com.kotlin.sportpro.ui.profile.judge

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.kotlin.sportpro.R
import kotlinx.android.synthetic.main.fragment_judging.*


class JudgingFragment : Fragment() {
    private var one = 0;
    private var two = 0;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_judging, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners() {

        plusThree.setOnClickListener { v ->
            one +=3
            pointsTv.text = one.toString()
        }
        plusOnePoint.setOnClickListener { v ->
            ++one;
            pointsTv.text = one.toString()
        }
        minusOnePoint.setOnClickListener { v ->
            --one;
            pointsTv.text = one.toString()
        }

        plusThreeTwo.setOnClickListener { v ->
            two+=3;
            pointsTvTwo.text = two.toString()
        }

        minusPointTwo.setOnClickListener { v ->
            --two;
            pointsTvTwo.text = two.toString()
        }
        plusPointTwo.setOnClickListener { v ->
            ++two;
            pointsTvTwo.text = two.toString()
        }

        btnSave.setOnClickListener { v->
            makeAlert("Результаты отправлены на обработку.")
            val action = JudgingFragmentDirections.actionJudgingFragmentToJudgeFragment()
            findNavController().navigate(action)
        }
        btnSendAgain.setOnClickListener { v ->
            makeNotSimple("Вы уверены?")
        }

    }
    private fun makeAlert(s: String) {
        val view1: View = requireActivity().layoutInflater.inflate(R.layout.simple_alert, null)

        val alert: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view1).create()
        }
        alert?.show()

        val ok: TextView? = view1.findViewById(R.id.onPositive)
        ok?.setOnClickListener {
            alert?.dismiss()
        }
    }
    private fun makeNotSimple(s: String) {
        val view1: View = requireActivity().layoutInflater.inflate(R.layout.not_simple_alert, null)

        val alert: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view1).create()
        }
        alert?.show()

        val ok: TextView? = view1.findViewById(R.id.onPositive)
        ok?.setOnClickListener {
            reset()
            alert?.dismiss()
        }
        val no: TextView? = view1.findViewById(R.id.onNegative)
        no?.setOnClickListener {
            alert?.dismiss()
        }
    }

    private fun reset() {
        one = 0
        two = 0
        pointsTv.text = "0"
        pointsTvTwo.text = "0"
    }
}