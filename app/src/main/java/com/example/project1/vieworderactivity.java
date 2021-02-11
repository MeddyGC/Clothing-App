/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity lets the user views their orders with no duplicate in a recycler listview



 **/


package com.example.project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class vieworderactivity extends AppCompatActivity {
    public static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    ListView orderListView;
    BottomNavigationView bottomNavigationView;
    TextView estimatedTxt;
    Button checkOut;
    ArrayList<Integer> images ;
    ArrayList<String> productNames;
    ArrayList<String> listData;
    int itemId;
    double estTotal = 0.00;
    /**= {"Black_Hoodie $30","Grey_Hoodie $30","Green_Hoodie $30",
            "Brown_Sweatshirt $25","Black_Sweatshirt $25","White_Sweatshirt $25",
            "White_Sleeves $20","Brown_Sleeves $20","Black_Sleeves $20",
            "White_Tee $15","Black_Tee $15","Gold_Tee $15"};
     **/



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.myCart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.shop:
                        intent = new Intent(getApplicationContext(),cartController.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.AccountInfo:
                        intent = new Intent(getApplicationContext(),Main2Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myCart:
                        intent = new Intent(getApplicationContext(),Pop.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.contactSupport:
                        intent = new Intent(getApplicationContext(),ChatActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }

        });







        Bundle bundle = getIntent().getExtras();
        estimatedTxt = findViewById(R.id.estTotaltxt);


        images = bundle.getIntegerArrayList("userImages");
        productNames = bundle.getStringArrayList("itemTotal");
        final String estTotal = bundle.getString("Estimated total");
        estimatedTxt.setText("Estimated Total: "+estTotal);
        orderListView = (ListView) findViewById(R.id.productListview);
        final CustomAdapter customAdapter = new CustomAdapter();
        orderListView.setAdapter(customAdapter);
        registerForContextMenu(orderListView);


        orderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemId = position;
                new AlertDialog.Builder(vieworderactivity.this).setIcon(android.R.drawable.ic_delete).
                        setTitle("Are you sure?")
                .setMessage("Do you want to delete this item")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),listData.get(itemId),Toast.LENGTH_SHORT).show();
                        double item = calcPrice(productNames.get(itemId));
                        double tot = Double.valueOf(estTotal) - item;

                        productNames.remove(itemId);
                        images.remove(itemId);
                        Integer deletedRow = mDatabaseHelper.deleteData(String.valueOf(itemId));
                        if(deletedRow>0){
                            Toast.makeText(getApplicationContext(),"Delete successful " + itemId,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Delete not successful " + itemId,Toast.LENGTH_SHORT).show();
                        }
                        customAdapter.notifyDataSetChanged();

                        estimatedTxt.setText("Estimated Total: "+String.valueOf(tot));

                    }
                })
                .setNegativeButton("no",null).show();


                return true;
            }
        });
        checkOut = findViewById(R.id.checkOutBtn);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productNames.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Your cart is empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(vieworderactivity.this, messageActivity.class);
                    String balanceStr = estimatedTxt.getText().toString();
                    intent.putExtra(balanceStr, "balance");
                    startActivity(intent);
                }
            }
        });
    }
    public double calcPrice(String i){
        double Total;
            if(i.contains("Hoodie")){
                 Total = 30;
            }
            else  if(i.contains("Sweatshirt")){
                Total = 25;
            }
            else  if(i.contains("Sleeves")){
                Total = 20;
            }
            else{
                Total = 20;
            }

        return Total;
    }



    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in Listview.");
        //get the data and append to list
        Cursor data = mDatabaseHelper.getData();
        listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));

        }
        //Toast.makeText(getApplicationContext(), (CharSequence) listData,Toast.LENGTH_SHORT).show();

    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.listview_item, null);
            ImageView nimageView = view.findViewById(R.id.imageView);
            TextView mTextView = view.findViewById(R.id.details);
            nimageView.setImageResource(images.get(position));
            mTextView.setText(productNames.get(position));
            return view;
        }
    }
}
