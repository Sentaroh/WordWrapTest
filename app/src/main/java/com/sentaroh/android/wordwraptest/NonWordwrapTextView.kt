package com.sentaroh.android.wordwraptest

import android.content.Context
import android.content.res.Resources
import android.text.Layout
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import org.slf4j.LoggerFactory
import android.content.res.TypedArray
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class NonWordwrapTextView : TextView {
    companion object {
        private val log = LoggerFactory.getLogger(NonWordwrapTextView::class.java)

        private fun toPixel(res: Resources, dp: Int): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), res.displayMetrics)
        }
    }
    private var mOrgText: CharSequence = ""
    private var mOrgBufferType: TextView.BufferType = TextView.BufferType.NORMAL
    private var mSplitTextLineCount = 0
    private var mSpannableSplitText: SpannableStringBuilder? = null
    private var isWordWrapEnabled = false

    private var mViewId=-1L;

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (!isWordWrapEnabled) {
            super.setText(mSpannableSplitText, mOrgBufferType)
        }
        log.debug("id=$mViewId, onLayout changed=$changed, left=$left, top=$top, right=$right, bottom=$bottom")
//        log.debug("layout h="+layoutParams.height);
    }

    override fun onMeasure(w: Int, h: Int) {
        val paint = paint
        super.onMeasure(w, h)
        if (!isWordWrapEnabled) {
            mSpannableSplitText = buildSplitText(View.MeasureSpec.getSize(w), View.MeasureSpec.getSize(h))
            val sep_line1 = toPixel(resources, 3).toInt().toFloat()
            val fm = paint.fontMetrics
            val ts_height = Math.abs(fm.top) + Math.abs(fm.bottom)
            val new_h = Math.ceil(((ts_height + sep_line1) * mSplitTextLineCount.toFloat()).toDouble()).toInt()
            setMeasuredDimension(View.MeasureSpec.getSize(w), new_h)
            log.debug("id=$mViewId, onMeasure w=$w, h=$h, new w="+View.MeasureSpec.getSize(w)+", h=$new_h")
//            log.debug("id="+mViewId+", onMeasure w="+w+", h=0x%8h".format(h)+", new w="+View.MeasureSpec.getSize(w)+", h="+new_h)
        } else {
            log.debug("id="+mViewId+", onMeasure w="+w+", h="+h)
        }
//        log.debug("layout h="+layoutParams.height);
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        log.debug("id="+mViewId+", onSizeChanged w=$w, h=$h, old_w=$oldw, old_h=$oldh")
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        mOrgText = text
        mOrgBufferType = type
        super.setText(text, type)
        log.debug("id="+mViewId+", setText enterd, text="+text)
    }

    override fun getText(): CharSequence {
        return mOrgText
    }

    override fun length(): Int {
        return mOrgText.length
    }

    fun buildSplitText(w: Int, h: Int): SpannableStringBuilder {
        val debug = false
        val paint = paint
        val wpl = compoundPaddingLeft
        val wpr = compoundPaddingRight
        val width = w - wpl - wpr
        if (debug) log.info("id=$mViewId, buildSplitText width=$width, w=$w, wpl=$wpl, wpr=$wpr, h=$h, length=$mOrgText.length")

        var output: SpannableStringBuilder
        val add_cr_cnt = 0
        if (width <= 0) {
            output = SpannableStringBuilder(mOrgText)
            mSplitTextLineCount = mOrgText.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size
        } else {
            output = SpannableStringBuilder(mOrgText)
            var start = 0
            if (debug) log.info("id="+mViewId+", input=$output")
            while (start < output.length) {
                if (debug) log.info("id="+mViewId+", start=$start")
                var in_text = output.subSequence(start, output.length).toString()
                val cr_pos = in_text.indexOf("\n")
                if (cr_pos > 0) {
                    in_text = output.subSequence(start, start + cr_pos).toString()
                    val nc = paint.breakText(in_text, true, width.toFloat(), null)
                    if (output[start + nc] != '\n') output.insert(start + nc, "\n")
                    start = start + nc + 1
                } else if (cr_pos == 0) {
                    start = start + 1
                } else {
                    val nc = paint.breakText(in_text, true, width.toFloat(), null)
                    output.insert(start + nc, "\n")
                    start = start + nc + 1
                }
            }
            mSplitTextLineCount = output.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size
        }

        if (debug) {
            log.info("id="+mViewId+", buildSplitText Number of Lines=$mSplitTextLineCount, added_cr/lf_count=$add_cr_cnt")
        }
        return output
    }
}