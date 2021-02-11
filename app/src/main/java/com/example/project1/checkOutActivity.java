/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity get the user selection on the recyclerview



 **/


package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class checkOutActivity extends AppCompatActivity implements View.OnClickListener{
    TextView priceTxt;
    TextView totalTxt;
//    Button payBtn;
    Button vieworders;
    ArrayList<Integer> userImages;
    ArrayList<String> userProdNames;
    String estTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        priceTxt = findViewById(R.id.totalPrice);
        totalTxt = findViewById(R.id.totalitems);
        //payBtn = findViewById(R.id.payBtn);
        vieworders = findViewById(R.id.viewOrders);

        //A bundle is created to get values or arrays passed from parent activity
        Bundle bundle = getIntent().getExtras();
        userImages = bundle.getIntegerArrayList("imagesList");
        userProdNames = bundle.getStringArrayList("imageDetails");
//        payBtn.setOnClickListener(this);
        vieworders.setOnClickListener(this);

        //int p;
        //p = bundle.getString("sumPrice",0);
        //String tot = intent.getStringExtra("itemTotal");
        priceTxt.setText("Subtotal: " + bundle.getString("sumPrice").toString());
        estTotal = bundle.getString("sumPrice");
        totalTxt.setText("Number of items: " + bundle.getString("itemTotal").toString());
        DisplayMetrics dm = new DisplayMetrics();                   //Displays layout
        getWindowManager().getDefaultDisplay().getMetrics(dm);      //gets the actual sceen size of he phone screen

        int width = dm.widthPixels;             //sets the width pixels of the layout
        int height = dm.heightPixels;           //sets the height pixels of the layout
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
    }


        @Override
        public void onClick(View view){
            switch(view.getId()) {
//                case R.id.payBtn:
//                    Toast toast = Toast.makeText(getApplicationContext(), "Your Purchase is being processed", Toast.LENGTH_SHORT);
//                    toast.show();
//                    break;


                case R.id.viewOrders:
                    //Passes an ArrayList of images and product detail in a bundle
                    Intent intent = new Intent(checkOutActivity.this, vieworderactivity.class);
                    Bundle bundle = new Bundle();
                    Toast toast = Toast.makeText(getApplicationContext(), "Your view is being processed", Toast.LENGTH_SHORT);
                    toast.show();
                    // These put the arrays in the bundle
                    bundle.putIntegerArrayList("userImages", userImages);
                    bundle.putStringArrayList("itemTotal", userProdNames);
                    bundle.putString("Estimated total",estTotal);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;


            }
        }





}
