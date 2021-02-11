package com.example.project1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class GreenFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private EditText cardNum;
    private EditText expiryDate;
    private EditText cvv;
    private CheckBox showCardNum;
    private ImageButton imageButton;
    String cardnumStr,cardtypeStr, expdateStr, cvvStr;

    private OnGreenFragmentListener mCallback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getContext(),"Please we do not save bank details for security reasons",Toast.LENGTH_SHORT).show();
        View v = inflater.inflate(R.layout.fragment_green, container, false);
        cardNum = (EditText) v.findViewById(R.id.cardNumTxt);

        expiryDate = v.findViewById(R.id.exprDate);
        imageButton = v.findViewById(R.id.imageButton);
        cvv =  v.findViewById(R.id.cvvTxt);
        Spinner spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.cardtype,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        showCardNum = v.findViewById(R.id.checkBox);
        showCardNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   cardNum.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    cardNum.setInputType(InputType.TYPE_CLASS_PHONE| InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Scanner not supported by the tab",Toast.LENGTH_SHORT).show();

            }
        });
        Button button = v.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardNum.getText().toString().matches("^[0-9]*$") && cardNum.getText().toString().length() == 16
                        && expiryDate.getText().toString().matches("\\d{2}/\\d{2}")
                        &&cvv.getText().toString().matches("^[0-9]*$")&&cvv.getText().toString().length() == 3){
                    cardnumStr = cardNum.getText().toString();
                    expdateStr = expiryDate.getText().toString();
                    cvvStr = cvv.getText().toString();
                    mCallback.messageFromGreenFragment(cardnumStr,expdateStr,cvvStr,cardtypeStr);
                }
                else{
                    cardNum.setError("Must be 16 digits");
                    expiryDate.setError("Invalid date!! format must be MM/YY");
                    cvv.setError("Must be 3 digits");
                }














            }
        });
        return v;
    }












    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
        cardtypeStr = text;
    }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public interface  OnGreenFragmentListener {
        void messageFromGreenFragment(String name, String age, String size, String quantity);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGreenFragmentListener) {
            mCallback = (OnGreenFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGreenFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
