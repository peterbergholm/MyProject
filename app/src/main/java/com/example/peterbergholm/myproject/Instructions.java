package com.example.peterbergholm.myproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        //Get the caller from the call parameters
        String caller=getIntent().getStringExtra("caller");

        WebView helpWebView;
        helpWebView = (WebView) findViewById(R.id.WebView01);
        if(caller!=null)
            helpWebView.loadUrl(getString(R.string.detailinstructionsFile));
        else
            helpWebView.loadUrl(getString(R.string.instructionsFile));

        // Create button
        Button btClose;
        // Get reference to the button
        btClose= (Button) findViewById(R.id.button3);
        // Attach the listener
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
