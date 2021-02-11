/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity controls the cart activities



 **/

package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class cartController extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);;
    BottomNavigationView bottomNavigationView;
    Button checkoutBtn;
    private RecyclerView recyclerView;
    //This is an int array of images
    int[] prodImages = {R.drawable.black_hoodie,R.drawable.grey_hoodie,R.drawable.green_hoodie,R.drawable.brown_sweatshirt,R.drawable.black_sleeves,R.drawable.white_sweatshirt,
           R.drawable.white_sleeves,R.drawable.brown_sweatshirt,R.drawable.black_sleeves,R.drawable.white_tee,R.drawable.black_tee,R.drawable.gold_tee};
    //This is the corresponding names IN TERMS OF ID OR POSITION
    String[] Products = {"Black_Hoodie $30","Grey_Hoodie $30","Green_Hoodie $30",
            "Brown_Sweatshirt $25","Black_Sweatshirt $25","White_Sweatshirt $25",
            "White_Sleeves $20","Brown_Sleeves $20","Black_Sleeves $20",
            "White_Tee $15","Black_Tee $15","Gold_Tee $15"};
    int[] price = {30,30,30,25,25,25,20,20,20,15,15,15};

    //Arraylist of string and int will contains users order at the end of it
    ArrayList<String> allProduct = new ArrayList<>();
    ArrayList<Integer> prodImages2 = new ArrayList<>();
    float total = 0.00f;
    String s="";

    Intent intent;

    private  RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.shop);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.shop:
                        return true;

                    case R.id.AccountInfo:
                        intent = new Intent(getApplicationContext(),Main2Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myCart:
                        intent = new Intent(cartController.this,Pop.class);
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




        checkoutBtn = findViewById(R.id.checkOutBtn);
        Toast toast = Toast.makeText(getApplicationContext(), "Long press add to cart button to add item to cart and tap to view more details", Toast.LENGTH_LONG);
        toast.show();
        //The recycler view is gotten throughs its ID and its adapter is set
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setHasFixedSize((true));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(prodImages);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //This implements tap to view more details about an item
                intent = new Intent(cartController.this,Pop.class);
                intent.putExtra("name",Products[position]);
                intent.putExtra("image",prodImages[position]);

                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {   //This implement the add to cart method
                s = Products[position];          //This indicates the position of the item clciekd therefore user's order
                if(!allProduct.contains(s)) {    //This lines ensures no duplicate of items
                    if(s.equals(Products[position])) {
                        prodImages2.add(prodImages[position]);    //add image of user order to the array
                        allProduct.add(s);                        //add details of users order to the array
                        AddData(s);
                        total += price[position];
                        //Toast toast= Toast. makeText(getApplicationContext(),"Added to cart the first",Toast. LENGTH_SHORT);
                       // toast.show();
                    }
                }

            }
        }));

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Implemments the checkout button
                intent = new Intent(cartController.this,checkOutActivity.class);
                //Cretaes a bundle that transfers the the arrays and other element to transfer to the next activity
                Bundle bundle = new Bundle();
                bundle.putString("sumPrice", String.valueOf(total));
                bundle.putString("itemTotal", String.valueOf(allProduct.size()));
                bundle.putIntegerArrayList("imagesList",prodImages2);
                bundle.putStringArrayList("imageDetails",allProduct);

                intent.putExtras(bundle); //this is the most important line easily forgotten its puts the bundle extras into the intent
                //intent.putExtra("itemTotal",allProduct.size());
                Toast toast= Toast. makeText(getApplicationContext(),String.valueOf(allProduct.size()),Toast. LENGTH_SHORT);
                toast.show();
                startActivity(intent);


            }
        });











    }

    public void AddData(String newEntry) {

        boolean insertData = mDatabaseHelper.addData(newEntry);
        if(insertData){
            Toast toast= Toast. makeText(getApplicationContext(),"Data Successfully inserted",Toast. LENGTH_SHORT);
            toast.show();
        }
        else {
            Toast toast= Toast. makeText(getApplicationContext(),"Something went wrong",Toast. LENGTH_SHORT);
            toast.show();
        }
    }
}
