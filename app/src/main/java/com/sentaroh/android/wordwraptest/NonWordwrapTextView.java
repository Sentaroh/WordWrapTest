package com.sentaroh.android.wordwraptest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonWordwrapTextView extends TextView {
    private static Logger log= LoggerFactory.getLogger(NonWordwrapTextView.class);
    private CharSequence mOrgText = "";
    private BufferType mOrgBufferType = BufferType.NORMAL;
    private int mSplitTextLineCount=0;
    private SpannableStringBuilder mSpannableSplitText=null;
    private boolean mWordWrapMode =false;

    private boolean mDebugEnabled=true;

    public NonWordwrapTextView(Context context) {
        super(context);
    }

    public NonWordwrapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonWordwrapTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDebugEnable(boolean enabled) {mDebugEnabled=enabled;}

    public void setWordWrapEnabled(boolean word_wrap_enabled) {
        mWordWrapMode =word_wrap_enabled;
    }

    public boolean isWordWrapEnabled() {
        return mWordWrapMode;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mDebugEnabled) log.info("onLayout changed="+changed+", left="+left+", top="+top+", right="+right+", bottom="+bottom);
//        if (!isWordWrapEnabled()) {
//            if (changed) {
//                setText(mSpannableSplitText, mOrgBufferType);
//                requestLayout();
//            }
//            if (mDebugEnabled) log.info("onLayout setText issued");
//        }
    }

    @Override
    final protected void onMeasure(int w, int h) {
        TextPaint paint = this.getPaint();
        if (mDebugEnabled) log.info("onMeasure entered, w="+MeasureSpec.getSize(w)+", h="+MeasureSpec.getSize(h));
        if (!isWordWrapEnabled() && w>0) {
            mSpannableSplitText=buildSplitText(MeasureSpec.getSize(w), MeasureSpec.getSize(h));
////            float sep_line1=(int)toPixel(getResources(), 3);
//            float sep_line1=0f;//toPixel(getResources(), 3);
//            int sep_line2=0;//(int)toPixel(getResources(), 3);
//            TextPaint.FontMetrics fm=paint.getFontMetrics();
//            Rect bounds = new Rect();
////            paint.getTextBounds(mSpannableSplitText.toString(), 0, mSpannableSplitText.toString().length(), bounds);
//            paint.getTextBounds(mOrgText.toString(), 0, mOrgText.toString().length(), bounds);
//            log.info("bounds bottom="+bounds.bottom+", left="+bounds.left+", right="+bounds.right+", top="+bounds.top+", height="+bounds.height()+", width="+bounds.width());
//            int textBoundsHeight = bounds.bottom + bounds.height();
//
//            StaticLayout layout = new StaticLayout(mOrgText.toString(), paint, w , Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
//            log.info("layout bottomPading="+getCompoundPaddingBottom()+", topPadding="+getCompoundPaddingTop());
////            float ts_height=Math.abs(fm.top)+Math.abs(fm.bottom);
////            float ts_height=Math.abs(fm.ascent)+Math.abs(fm.descent);
////            float ts_height=Math.abs(fm.ascent)+Math.abs(fm.descent)+Math.abs(fm.leading);
//            float ts_height=textBoundsHeight+(Math.abs(layout.getBottomPadding()));
//            int new_h=((int)Math.ceil((ts_height+sep_line1))*mSplitTextLineCount)+sep_line2;
//            if (mDebugEnabled) {
//
//                log.info("onMeasure lineHeight="+ts_height+
//                        ", ascent="+fm.ascent+", bottom="+fm.bottom+", decent="+fm.descent+", leading="+fm.leading+", top="+fm.top);
//                log.info("onMeasure "+
//                        "ascent="+paint.ascent()+", decent="+paint.descent()+", fontSpacing="+paint.getFontSpacing()+
//                        ", baseLineShift="+paint.baselineShift+", letterSpacing="+paint.getLetterSpacing()+
//                        ", textBounds="+textBoundsHeight);
//                log.info("onMeasure textSize="+getTextSize()+", paint text size="+paint.getTextSize()+", no of lines="+mSplitTextLineCount+
//                        ", LineSpacing="+getLineSpacingExtra()+", LineSpcingMult="+getLineSpacingMultiplier());
//                log.info("onMeasure w="+MeasureSpec.getSize(w)+", h="+MeasureSpec.getSize(h)+
//                        ", new w="+MeasureSpec.getSize(w)+", new h="+MeasureSpec.getSize(new_h));
//            }
////            setMeasuredDimension(w, new_h);
            super.onMeasure(w, h);
            super.setText(mSpannableSplitText, mOrgBufferType);
        } else {
            super.onMeasure(w, h);
        }
    }

    @Override
    final protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mDebugEnabled) log.info("onSizeChanged w="+w+", h="+h+", oldw="+oldw+", oldh="+oldh);
    };

    @Override
    public void setText(CharSequence text, BufferType type) {
        mOrgText = text;
        mOrgBufferType = type;
        super.setText(text, type);
        if (mDebugEnabled) log.info("setNowrapText length="+text.length()+", type="+type.toString()+", text="+text);
        if (mDebugEnabled) log.info("setText length="+text.length()+", type="+type.toString()+", text="+text);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDebugEnabled) {
            log.info("onDraw height="+canvas.getHeight());
        }
    }

    @Override
    public CharSequence getText() {
        return mOrgText;
    }

    @Override
    public int length() {
        return mOrgText.length();
    }

    public SpannableStringBuilder buildSplitText(int w, int h) {
        TextPaint paint = getPaint();
//        log.info("typeface="+paint.getTypeface().toString()+", fontSpacing="+paint.getFontSpacing());
        int wpl =getCompoundPaddingLeft();
        int wpr =getCompoundPaddingRight();
        int width = w - wpl - wpr;
        if (mDebugEnabled) log.info("buildSplitText width="+width+", w="+w+", wpl="+wpl+", wpr="+wpr+", h="+h+", length="+mOrgText.length()+", Text="+mOrgText.toString());

        SpannableStringBuilder output = null;
        int add_cr_cnt=0;
        if (width<=0) {
            output=new SpannableStringBuilder(mOrgText);
            mSplitTextLineCount=mOrgText.toString().split("\n").length;
        } else {
            output=new SpannableStringBuilder(mOrgText);
            int start=0;
            if (mDebugEnabled)  log.info("input length="+output.length()+", Text="+output.toString());
            while(start<output.length()) {
                if (mDebugEnabled)  log.info("start="+start);
                String in_text=output.subSequence(start, output.length()).toString();
                int cr_pos=in_text.indexOf("\n");
                if (cr_pos>0) {
                    in_text = output.subSequence(start, start + cr_pos).toString();
                    int nc = paint.breakText(in_text, true, width, null);
                    if (output.charAt(start + nc) != '\n') {
                        output.insert(start + nc, "\n");
//                        log.info("cr inserted1, pos="+(start + nc));
                    }
                    start = start + nc + 1;
                } else if (cr_pos==0) {
                    start = start + 1;
                } else {
                    int nc=paint.breakText(in_text, true, width, null);
                    float nc2=Layout.getDesiredWidth(in_text, 0, in_text.length(), paint);
                    log.info("start="+start+", nc="+nc+", nc2="+nc2);
//                    log.info("in_text length="+in_text.length()+", text="+in_text);
                    if (nc<=(output.length()-start-1)) {
                        output.insert(start+nc, "\n");
//                        log.info("cr inserted2, pos="+(start + nc)+", output length="+output.length());
                        start=start+nc+1;
                    } else {
                        start=start+nc+1;
                    }
                }
            }
            mSplitTextLineCount=output.toString().split("\n").length;
        }

        if (mDebugEnabled)  {
            log.info("buildSplitText Number of Lines="+mSplitTextLineCount);
            log.info("buildSplitText input  char="+mOrgText.toString());
            log.info("buildSplitText input  hex ="+ StringUtil.getDumpFormatHexString(mOrgText.toString().getBytes(), 0, mOrgText.toString().getBytes().length));
            log.info("buildSplitText output char="+output.toString());
            log.info("buildSplitText output hex ="+ StringUtil.getDumpFormatHexString(output.toString().getBytes(), 0, output.toString().getBytes().length));
        }
        return output;
    }

    final static private float toPixel(Resources res, int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
        return px;
    };


}