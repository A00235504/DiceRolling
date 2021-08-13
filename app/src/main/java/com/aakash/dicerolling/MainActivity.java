package com.aakash.dicerolling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    //initialization of various widgets here
    Spinner spin;
    Button rollOneDiceButton;
    String diceResult;
    int maxValue = 4;
    TextView diceResult1Textview, diceResult2Textview, historyDiceTextView, restoreAllSidesTextView, clearListTextView;
    EditText customDiceSidesEditText;
    LinearLayout secondDiceLayoutView, customSidesLayout;
    RadioGroup radioGroup;
    RadioButton oneDiceRadioButton, twoDiceRadioButton, customDiceRadioButton;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calling the get ids and getdata if any available
        getID();
        getData();

        //setting on click listners
        rollOneDiceButton.setOnClickListener(this);
        clearListTextView.setOnClickListener(this);
        restoreAllSidesTextView.setOnClickListener(this);
        spin.setOnItemSelectedListener(this);

        //setting visibility of seconf dice view to gone
        secondDiceLayoutView.setVisibility(View.GONE);
        customSidesLayout.setVisibility(View.GONE);

        //defining arrayadapter for spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //we can set the adapter here
        spin.setAdapter(arrayAdapter);

        //check for any text in custom edit text field and set the text in textedit and spinner option selection
        if(customDiceSidesEditText.getText() != null && arrayList.contains(customDiceSidesEditText.getText().toString()) == true){
            int gettext = arrayList.indexOf(customDiceSidesEditText.getText().toString());
            spin.setSelection(gettext);
        }

        //radio group onclick check listner for checking the radio button selected to do a task
        //based on selections views will change
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.oneDiceRadioButton:
                        secondDiceLayoutView.setVisibility(View.GONE);
                        customSidesLayout.setVisibility(View.GONE);
                        break;
                    case R.id.twoDiceRadioButton:
                        secondDiceLayoutView.setVisibility(View.VISIBLE);
                        customSidesLayout.setVisibility(View.GONE);
                        break;
                    case R.id.customDiceRadioButton:
                        secondDiceLayoutView.setVisibility(View.GONE);
                        customSidesLayout.setVisibility(View.VISIBLE);
                        break;


                }
            }
        });

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Object item = arg0.getItemAtPosition(position);
        if(item.toString().contentEquals("10 sides")){
            maxValue = new Dice(10).numberOfSides;
        }
        else if(item.toString().contentEquals("10 sides 0-9")){
            maxValue = new Dice(10).numberOfSides -1;
        }
        else {
            maxValue = new Dice(Integer.parseInt(item.toString())).numberOfSides;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //here all the Ids are defined and called at the oncreate
    public void getID() {
        spin = findViewById(R.id.selectDiceSpinner);
        rollOneDiceButton = findViewById(R.id.rollOneDiceButton);
        diceResult1Textview = findViewById(R.id.diceResult1Textview);
        diceResult2Textview = findViewById(R.id.diceResult2Textview);
        secondDiceLayoutView = findViewById(R.id.secondDiceLayoutView);
        radioGroup = findViewById(R.id.selectSidesRadioGroup);
        oneDiceRadioButton = findViewById(R.id.oneDiceRadioButton);
        twoDiceRadioButton = findViewById(R.id.twoDiceRadioButton);
        customDiceRadioButton = findViewById(R.id.customDiceRadioButton);
        customSidesLayout = findViewById(R.id.customSidesLayout);
        customDiceSidesEditText = findViewById(R.id.customSidesEditText);
        historyDiceTextView = findViewById(R.id.historyDiceTextView);
        restoreAllSidesTextView = findViewById(R.id.restoreAllSidesTextView);
        clearListTextView = findViewById(R.id.clearListTextView);
    }

    //the onclick listner click is defined here by the button id we can check what button is clicked
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rollOneDiceButton:
                if (oneDiceRadioButton.isChecked()) {
                    if(spin.getSelectedItem() == "Custom 10 sides"){
                        diceResult = String.valueOf(new Dice(maxValue).rollmethod() * 10);
                    }
                    else{
                        diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                    }
                    diceResult1Textview.setText(diceResult);
                    arrayListHistory.add(diceResult);
                    setArrayListdata();

                } else if (twoDiceRadioButton.isChecked()) {
                    diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                    diceResult1Textview.setText(diceResult);

                    arrayListHistory.add(diceResult);

                    diceResult = String.valueOf(new Dice(maxValue).rollmethod());

                    diceResult2Textview.setText(diceResult);

                    arrayListHistory.add(diceResult);

                    setArrayListdata();

                } else if (customDiceRadioButton.isChecked()) {

                    //here try catch is used as user input may cause some error and crash
                    try{

                        int numberofsides = Integer.parseInt(String.valueOf(customDiceSidesEditText.getText()));

                        if (numberofsides < 100) {
                        try {
                            diceResult = String.valueOf(new Dice(numberofsides).rollmethod());
                            diceResult1Textview.setText(diceResult);
                            if(arrayList.contains(String.valueOf(numberofsides))){
                                arrayListHistory.add(diceResult);
                                setArrayListdata();
                            }
                            else{
                            arrayList.add(String.valueOf(numberofsides));
                            int sides = arrayList.indexOf(String.valueOf(numberofsides));
                            spin.setSelection(sides);
                                arrayListHistory.add(diceResult);
                                setArrayListdata();

                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter values below 100!", Toast.LENGTH_SHORT).show();
                    }
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.restoreAllSidesTextView :
                Toast.makeText(getApplicationContext(), "Restoring..", Toast.LENGTH_SHORT).show();
                arrayList.clear();
                arrayList.add("4");
                arrayList.add("6");
                arrayList.add("8");
                arrayList.add("10 sides");
                arrayList.add("10 sides 0-9");
                arrayList.add("12");
                arrayList.add("20");
                saveData();
                spin.setSelection(0);
                break;

            case R.id.clearListTextView:
                Toast.makeText(getApplicationContext(), "Restoring..", Toast.LENGTH_SHORT).show();
                arrayListHistory.clear();
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.remove("historyList1");
                myEdit.apply();
                setArrayListdata();
                diceResult = "No History";
                break;

                default:
                Toast.makeText(this, "View not implemented", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }

    //defining a function for saving data in shared pref
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("value", customDiceSidesEditText.getText().toString());


        if (diceResult != null && !diceResult.contentEquals("No History")) {
            arrayListHistory.add(diceResult);
            Set<String> setHistory = new HashSet<String>();
            setHistory.addAll(arrayListHistory);
            myEdit.putStringSet("historyList1", setHistory).apply();

        } else {
            myEdit.putString("historyList1", null);
        }
        myEdit.putStringSet("dicelist", null);
        Set<String> set = new HashSet<String>();
        Collections.sort(arrayList);
        set.addAll(arrayList);
        myEdit.putStringSet("dicelist", set);
        myEdit.apply();

    }

    //all the data saved in shared pref are get through this function
    public void getData() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("value", "0");

        customDiceSidesEditText.setText(s1);

        Set<String> set = sh.getStringSet("dicelist", null);
        if(set != null){
            arrayList.clear();
            arrayList.addAll(set);
            Collections.sort(arrayList);
        }
        else{
            arrayList.clear();
            arrayList.add("4");
            arrayList.add("6");
            arrayList.add("8");
            arrayList.add("10 sides");
            arrayList.add("10 sides 0-9");
            arrayList.add("12");
            arrayList.add("20");

        }

        Set<String> setii = sh.getStringSet("historyList1", null);
        if(setii != null) {
            List<String> sample=new ArrayList<String>(setii);
            arrayListHistory.clear();
        for(int k=0;k<sample.size();k++){
            arrayListHistory.add(sample.get(k));
            Log.d("retrivesharedPreferences", "" + setii);
    }
            setArrayListdata();
        }
    }

    //here we set the arraylist data from the data we get from the historyof arraylist by the sharedpref
    public void setArrayListdata(){
        if (!arrayListHistory.isEmpty()) {
            historyDiceTextView.setText("");
            for(int i=0; i<arrayListHistory.size(); i++){
                historyDiceTextView.append(arrayListHistory.get(i) + "\n");
            }
        } else {
            historyDiceTextView.setText("No History!");
        }
    }

}