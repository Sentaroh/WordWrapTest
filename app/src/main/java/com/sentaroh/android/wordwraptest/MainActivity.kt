package com.sentaroh.android.wordwraptest

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.slf4j.LoggerFactory


class MainActivity : AppCompatActivity() {
    companion object {
        private val log = LoggerFactory.getLogger(NonWordwrapTextView::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        log.setLogOption(true, true, true, true, true)
        log.info("onCreate entered")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val normal0 = findViewById<View>(R.id.normal_view0) as TextView
        val normal1 = findViewById<View>(R.id.normal_view1) as TextView
        val normal2 = findViewById<View>(R.id.normal_view2) as TextView
        val nonwordwrap0 = findViewById<View>(R.id.non_wordwrap_view0) as NonWordwrapTextView
        val nonwordwrap1 = findViewById<View>(R.id.non_wordwrap_view1) as NonWordwrapTextView
        val nonwordwrap2 = findViewById<View>(R.id.non_wordwrap_view2) as NonWordwrapTextView

        val sb = SpannableStringBuilder(getString(R.string.test_string0))
        //        sb.setSpan(new TabStopSpan.Standard(100), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(BackgroundColorSpan(Color.YELLOW), 0, sb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(ForegroundColorSpan(Color.RED), 20, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        normal0.text = sb
        normal1.text = getString(R.string.test_string1)
        normal2.text = getString(R.string.test_string2)

        nonwordwrap0.setText(sb, TextView.BufferType.SPANNABLE)
        nonwordwrap1.setText(getString(R.string.test_string1), TextView.BufferType.NORMAL)
        nonwordwrap2.setText(getString(R.string.test_string2), TextView.BufferType.NORMAL)

    }

}
