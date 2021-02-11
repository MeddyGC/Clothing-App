package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;




public class paymentActivity extends AppCompatActivity {
    TextView paymentConfirmTxt;
    String progressStr = "";
    Button backButton;
    Button viewinvBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        paymentConfirmTxt = findViewById(R.id.paymentcofirmTxt);
        backButton = findViewById(R.id.backBtn);
        viewinvBtn = findViewById(R.id.viewInvBtn);
        final Intent intent = getIntent();
        String amount = intent.getStringExtra("balance");
        //paymentConfirmTxt.setText("Thank you ! Your Payment of $"+amount+" has been received.");

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final TextView progressTxt = findViewById(R.id.progressText);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                progressTxt.setText(""+ progress+"%");
                progressStr = ""+progress+"%";
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //String s = seekBar.toString();
                //Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(paymentActivity.this,cartController.class);
                startActivity(newIntent);
            }
        });
        viewinvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(paymentActivity.this,inventoryActivity.class);
                startActivity(newIntent);
            }
        });

    }
}
