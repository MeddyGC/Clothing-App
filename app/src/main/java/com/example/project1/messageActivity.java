package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class messageActivity extends AppCompatActivity    implements GreenFragment.OnGreenFragmentListener{
    BottomNavigationView bottomNavigationView;
    BlueFragment mBlueFragment;
    GreenFragment mGreenFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.contactSupport);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(),cartController.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.AccountInfo:
                        startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myCart:
                        startActivity(new Intent(getApplicationContext(),checkOutActivity.class));
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.contactSupport:

                        return true;

                }
                return false;
            }

        });
        // add gragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        mBlueFragment = (BlueFragment)
                fragmentManager.findFragmentById(R.id.blue_fragment_container);
        if (mBlueFragment == null) {
            mBlueFragment = new BlueFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.blue_fragment_container, mBlueFragment).commit();
        }
        mGreenFragment = (GreenFragment)
                fragmentManager.findFragmentById(R.id.green_fragment_container);
        if (mGreenFragment == null) {
            mGreenFragment = new GreenFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.green_fragment_container, mGreenFragment).commit();
        }
    }
    @Override
    public void messageFromGreenFragment(String name, String age, String size, String quantity) {
        mBlueFragment.msgReceived(name,age,size,quantity);
    }

}
