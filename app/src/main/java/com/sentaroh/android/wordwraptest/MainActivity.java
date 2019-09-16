package com.sentaroh.android.wordwraptest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {
    final private static Logger log= LoggerFactory.getLogger(NonWordwrapTextView.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log.setLogOption(true,true,true,true,true);

        TextView normal0=(TextView)findViewById(R.id.normal_view0);
        TextView normal1=(TextView)findViewById(R.id.normal_view1);
        NonWordwrapTextView nonwordwrap0=(NonWordwrapTextView)findViewById(R.id.non_wordwrap_view0);
        NonWordwrapTextView nonwordwrap1=(NonWordwrapTextView)findViewById(R.id.non_wordwrap_view1);

        SpannableStringBuilder sb=new SpannableStringBuilder(getString(R.string.test_string0));
        sb.setSpan(new BackgroundColorSpan(Color.YELLOW), 1,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new ForegroundColorSpan(Color.RED), 20,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        normal0.setText(sb);
        normal1.setText(getString(R.string.test_string1));

        nonwordwrap0.setText(sb);
        nonwordwrap1.setText(getString(R.string.test_string1));


    }
}
