package com.example.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class inventoryActivity extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    ListView invListView;
    Button backBtn;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> listData;
    int[] images = {R.drawable.black_hoodie, R.drawable.grey_hoodie, R.drawable.green_hoodie, R.drawable.brown_sweatshirt, R.drawable.black_sleeves, R.drawable.white_sweatshirt,
            R.drawable.white_sleeves, R.drawable.brown_sweatshirt, R.drawable.black_sleeves, R.drawable.white_tee, R.drawable.black_tee, R.drawable.gold_tee};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        backBtn = findViewById(R.id.backButton);
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();
        invListView = findViewById(R.id.inventoryListView);
        final CustomAdapter customAdapter = new CustomAdapter();
        invListView.setAdapter(customAdapter);

        invListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            String item = "";
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //item  = invListView.getItemAtPosition(position).toString();
                new AlertDialog.Builder(inventoryActivity.this).setIcon(android.R.drawable.ic_delete).
                        setTitle("Are you sure?")
                .setMessage("Do you want to delete this item").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listData.remove(position);
                        Integer deletedRow = mDatabaseHelper.deleteData(String.valueOf(position));
                        if(deletedRow>0){
                            Toast.makeText(getApplicationContext(),"Delete successful " + item,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Delete not successful " + item,Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("no",null).show();
                customAdapter.notifyDataSetChanged();

                return true;
            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(inventoryActivity.this,cartController.class);
                startActivity(newIntent);
            }
        });
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
            return images.length;
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
            nimageView.setImageResource(images[position]);
            mTextView.setText(listData.get(position));
            return view;
        }
    }
}
