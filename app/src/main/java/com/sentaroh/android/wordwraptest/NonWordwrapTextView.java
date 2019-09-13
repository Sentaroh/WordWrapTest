package com.sentaroh.android.wordwraptest;

import android.content.Context;
import android.content.res.Resources;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.logging.Logger;

public class NonWordwrapTextView extends TextView {
    private CharSequence mOrgText = "";
    private BufferType mOrgBufferType = BufferType.NORMAL;
    private int mSplitTextLineCount=0;
    private SpannableStringBuilder mSpannableSplitText=null;
    private boolean mWordWrapMode =false;

    public NonWordwrapTextView(Context context) {
        super(context);
    }

    public NonWordwrapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonWordwrapTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setWordWrapEnabled(boolean word_wrap_enabled) {
        mWordWrapMode =word_wrap_enabled;
    }

    public boolean isWordWrapEnabled() {
        return mWordWrapMode;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!isWordWrapEnabled()) {
            super.setText(mSpannableSplitText, mOrgBufferType);
        }
    }

    @Override
    final protected void onMeasure(int w, int h) {
        TextPaint paint = getPaint();
        super.onMeasure(w, h);
        if (!isWordWrapEnabled()) {
            mSpannableSplitText=buildSplitText(MeasureSpec.getSize(w), MeasureSpec.getSize(h));
            float sep_line1=(int)toPixel(getResources(), 3);
            float sep_line2=(int)toPixel(getResources(), 2);
            int new_h=(int)(paint.getTextSize()+sep_line1)*mSplitTextLineCount+(int)sep_line2;
            setMeasuredDimension( MeasureSpec.getSize(w), new_h);
        }
    }

    @Override
    final protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    };

    @Override
    public void setText(CharSequence text, BufferType type) {
        mOrgText = text;
        mOrgBufferType = type;
        super.setText(text, type);
    }

    @Override
    public CharSequence getText() {
        return mOrgText;
    }

    @Override
    public int length() {
        return mOrgText.length();
    }

    synchronized public SpannableStringBuilder buildSplitText(int w, int h) {
        TextPaint paint = getPaint();
        int wpl =getCompoundPaddingLeft();
        int wpr =getCompoundPaddingRight();
        int width = w - wpl - wpr;

        SpannableStringBuilder output = null;
        if (width<=0) {
            output=new SpannableStringBuilder(mOrgText);
            mSplitTextLineCount=mOrgText.toString().split("\n").length;
        } else {
            output=new SpannableStringBuilder(mOrgText);
            String input_string=output.toString();
            int start=0;
            int end=mOrgText.length();
            for (int index = start; index < output.length(); index++) {
                float rts= Layout.getDesiredWidth(output, start, index+1, paint);
                if (rts > width) {
                    output.insert(index,"\n");
                    start = index+1;
                    index++;
                } else if (output.charAt(index) == '\n') {
                    start = index;
                }
            }
            mSplitTextLineCount=output.toString().split("\n").length;
        }
        return output;
    }

    final static private float toPixel(Resources res, int sp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
        return px;
    };


}