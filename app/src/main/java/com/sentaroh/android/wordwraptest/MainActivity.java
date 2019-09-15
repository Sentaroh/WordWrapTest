package com.sentaroh.android.wordwraptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView normal0=(TextView)findViewById(R.id.normal_view0);
        TextView normal1=(TextView)findViewById(R.id.normal_view1);
//        TextView normal2=(TextView)findViewById(R.id.normal_view2);
        NonWordwrapTextView nonwordwrap0=(NonWordwrapTextView)findViewById(R.id.non_wordwrap_view0);
        NonWordwrapTextView nonwordwrap1=(NonWordwrapTextView)findViewById(R.id.non_wordwrap_view1);
//        NonWordwrapTextView nonwordwrap2=(NonWordwrapTextView)findViewById(R.id.non_wordwrap_view2);

        normal0.setText(getString(R.string.test_string0));
        normal1.setText(getString(R.string.test_string1));
//        normal2.setText(getString(R.string.test_string2));
        nonwordwrap0.setText(getString(R.string.test_string0));
        nonwordwrap1.setText(getString(R.string.test_string1));
//        nonwordwrap2.setText(getString(R.string.test_string2));

    }
}
