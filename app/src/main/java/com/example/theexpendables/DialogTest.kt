package com.example.theexpendables

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment


class DialogTest : DialogFragment() {
    var mNum = 0

    // Create a new instance of MyDialogFragment, providing "num" as an argument.
    fun newInstance(num: Int): DialogTest {
        val f = DialogTest()

        // Supply num input as an argument.
        val args = Bundle()
        args.putInt("num", num)
        f.setArguments(args)
        return f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNum = getArguments()?.getInt("num") ?:

        // Pick a style based on the num.
        var style: Int = DialogFragment.STYLE_NORMAL
        var theme = 0
        when ((mNum - 1) % 6) {
            1 -> style = DialogFragment.STYLE_NO_TITLE
            2 -> style = DialogFragment.STYLE_NO_FRAME
            3 -> style = DialogFragment.STYLE_NO_INPUT
            4 -> style = DialogFragment.STYLE_NORMAL
            5 -> style = DialogFragment.STYLE_NORMAL
            6 -> style = DialogFragment.STYLE_NO_TITLE
            7 -> style = DialogFragment.STYLE_NO_FRAME
            8 -> style = DialogFragment.STYLE_NORMAL
        }
        setStyle(style, theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_expense, container, false)
    }

    fun onViewCreated(view: View?, @Nullable savedInstanceState: Bundle?) {
        if (view != null) {
            super.onViewCreated(view, savedInstanceState)
        }

        // set DialogFragment title
        getDialog()?.setTitle("Dialog #$mNum")
    }

    companion object {
        // Create a new instance of MyDialogFragment, providing "num" as an argument.
        fun newInstance(num: Int): DialogTest {
            val f = DialogTest()

            // Supply num input as an argument.
            val args = Bundle()
            args.putInt("num", num)
            f.setArguments(args)
            return f
        }
    }
}

