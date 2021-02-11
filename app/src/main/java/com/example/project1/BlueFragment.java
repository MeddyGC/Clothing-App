package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class BlueFragment extends Fragment {
    private ListView accountView;
    ArrayList<String> accountArray = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blue, container, false);
        accountView = v.findViewById(R.id.accountListView);

        return v;
    }

    public void msgReceived(String cardnumStr,String expdateStr,String cvvStr,String cardtypeStr) {
        String details = ("************")+cardnumStr.substring(12)+"  "+cardtypeStr;


        if (accountArray.contains(details)){
            Toast.makeText(getContext(),"Already exists",Toast.LENGTH_SHORT).show();;
        }
        else{
            accountArray.add(details);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,accountArray);
            accountView.setAdapter(adapter);
            accountView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(),paymentActivity.class);
                    startActivity(intent);
                }
            });
            registerForContextMenu(accountView);
        }

    }

        @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
        getActivity().getMenuInflater().inflate(R.menu.example_menu,menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId()==R.id.option_1){
            Toast.makeText(getContext(),"Long click works ",Toast.LENGTH_SHORT).show();

            return true;

        }
        else{
            return false;
        }
    }
}
