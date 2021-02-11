/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity get the details entered by the user on a form and prompts them to select an image



 **/


package com.example.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {
    //Declares all layoutattributes
    TextView fullName;
    TextView email;
    TextView age;
    TextView size;
    ImageView mImageView;
    Button mChooseBtn;
    BottomNavigationView bottomNavigationView;

    //declares image pick code and permission code
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        Intent intent = getIntent();//gets the intent from previous activities

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.AccountInfo);
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





        //All the values transfered are gotten
        String fname = intent.getStringExtra("fullname");
        String pword = intent.getStringExtra("password");
        String emails = intent.getStringExtra("email");
        String ages = intent.getStringExtra("Age");
        String sizes = intent.getStringExtra("size");
        //img = findViewById(R.id.imageView);
        //img.setImageResource(intent.getIntExtra("image",0));
        fullName = findViewById(R.id.txtName);
        email = findViewById(R.id.emailTxt);
        age = findViewById(R.id.txtAge);
        size = findViewById(R.id.sizeTxt);

        fullName.setText(" Name: "+fname);
        email.setText(" Email Address: "+emails);
        age.setText(" Age: "+ages);
        size.setText(" Shirt size: "+sizes);

        Button nextPageBtn = (Button) findViewById(R.id.nextpage);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, cartController.class);
                startActivity(intent);
            }
        });
        //Image_View and selection ,ethod are created
        mImageView = findViewById(R.id.image_view);
        mChooseBtn = findViewById(R.id.choose_image_btn);



        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On first app run asks for permission to select image
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);

                    }
                    else{
                        pickImageFromGallery();

                    }
                }
                else{
                    pickImageFromGallery();
                }
            }
        });
    }

    //This method select an image from user
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("image",0);

        startActivityForResult(intent, IMAGE_PICK_CODE);



    }

    //Sets the image if the permission is granted
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            mImageView.setImageURI(data.getData());


        }
    }

    //BDisplays a toast to tell the user permission is denied if
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
