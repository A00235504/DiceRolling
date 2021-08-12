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


    Spinner spin;
    Button rollOneDiceButton;
    String diceResult, diceResult2;
    int maxValue = 4;
    TextView diceResult1Textview, diceResult2Textview, historyDiceTextView, historyTitleDice2RollTextView, historyDice2TextView, restoreAllSidesTextView, clearListTextView;
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

        //SharedPreferences sharedPreferences = SharedPreferences


        getID();
        getData();
        rollOneDiceButton.setOnClickListener(this);
        clearListTextView.setOnClickListener(this);

        secondDiceLayoutView.setVisibility(View.GONE);
        customSidesLayout.setVisibility(View.GONE);
        spin.setOnItemSelectedListener(this);
        restoreAllSidesTextView.setOnClickListener(this);

        historyTitleDice2RollTextView.setVisibility(View.GONE);
        historyDice2TextView.setVisibility(View.GONE);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Creating the ArrayAdapter instance having the country list
        //ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sides);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(arrayAdapter);

        if(customDiceSidesEditText.getText() != null && arrayList.contains(customDiceSidesEditText.getText().toString()) == true){
            int kk = arrayList.indexOf(customDiceSidesEditText.getText().toString());
            spin.setSelection(kk);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.oneDiceRadioButton:

                        secondDiceLayoutView.setVisibility(View.GONE);
                        customSidesLayout.setVisibility(View.GONE);
                        historyTitleDice2RollTextView.setVisibility(View.GONE);
                        historyDice2TextView.setVisibility(View.GONE);

                        break;
                    case R.id.twoDiceRadioButton:
                        secondDiceLayoutView.setVisibility(View.VISIBLE);
                        customSidesLayout.setVisibility(View.GONE);

                        historyTitleDice2RollTextView.setVisibility(View.VISIBLE);
                        historyDice2TextView.setVisibility(View.VISIBLE);

                        break;
                    case R.id.customDiceRadioButton:
                        secondDiceLayoutView.setVisibility(View.GONE);
                        customSidesLayout.setVisibility(View.VISIBLE);

                        historyTitleDice2RollTextView.setVisibility(View.GONE);
                        historyDice2TextView.setVisibility(View.GONE);

                        break;


                }
            }
        });

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Object item = arg0.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();
        if(item.toString().contentEquals("10 sides")){
            Toast.makeText(getApplicationContext(), "Side 10 Custom", Toast.LENGTH_LONG).show();
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

        historyTitleDice2RollTextView = findViewById(R.id.historyTitleDice2RollTextView);
        historyDice2TextView = findViewById(R.id.historyDice2TextView);

        restoreAllSidesTextView = findViewById(R.id.restoreAllSidesTextView);

        clearListTextView = findViewById(R.id.clearListTextView);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rollOneDiceButton:
                //saveData();

                if (oneDiceRadioButton.isChecked()) {
                    if(spin.getSelectedItem() == "Custom 10 sides"){
                        diceResult = String.valueOf(new Dice(maxValue).rollmethod() * 10);
                    }
                    else{
                        diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                    }
                    diceResult1Textview.setText(diceResult);

                    arrayListHistory.add(diceResult);
//                    saveData();
//                    getData();
                    getArrayListdata();
                } else if (twoDiceRadioButton.isChecked()) {
                    diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                    diceResult1Textview.setText(diceResult);

                    arrayListHistory.add(diceResult);

                    diceResult2 = diceResult;

                    diceResult = String.valueOf(new Dice(maxValue).rollmethod());

                    diceResult2Textview.setText(diceResult);

                    arrayListHistory.add(diceResult);

                    getArrayListdata();

                } else if (customDiceRadioButton.isChecked()) {
                    try{
                    int numberofsides = Integer.parseInt(String.valueOf(customDiceSidesEditText.getText()));
                    if (numberofsides < 100) {
                        try {
                            diceResult = String.valueOf(new Dice(numberofsides).rollmethod());
                            diceResult1Textview.setText(diceResult);
                            if(arrayList.contains(String.valueOf(numberofsides))){

                            }
                            else{
                            arrayList.add(String.valueOf(numberofsides));
//                            saveData();
//                            getData();
                            int kk = arrayList.indexOf(String.valueOf(numberofsides));
                            spin.setSelection(kk);

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
                arrayListHistory.clear();
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.remove("historyList1");
                myEdit.apply();
                //saveData();
                //getData();
                getArrayListdata();
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

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("value", customDiceSidesEditText.getText().toString());


        if (diceResult != null) {
            arrayListHistory.add(diceResult);
            Set<String> setHistory = new HashSet<String>();
            setHistory.addAll(arrayListHistory);
            myEdit.putStringSet("historyList1", setHistory).apply();

        } else {
            myEdit.putString("history", "0");
        }
        myEdit.putStringSet("dicelist", null);
        Set<String> set = new HashSet<String>();
        Collections.sort(arrayList);
        set.addAll(arrayList);
        myEdit.putStringSet("dicelist", set);
        myEdit.apply();

    }

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
        List<String> sample=new ArrayList<String>(setii);
        
//        Set<String> setii = sh.getStringSet("historyList1", null);
        if(setii != null) {
            arrayListHistory.clear();
        for(int k=0;k<sample.size();k++){
            arrayListHistory.add(sample.get(k));
            Log.d("retrivesharedPreferences", "" + setii);
}
        }


        getArrayListdata();
    }

    public void getArrayListdata(){
        if (arrayListHistory != null) {

            historyDiceTextView.setText("");
            for(int i=0; i<arrayListHistory.size(); i++){
                historyDiceTextView.append(arrayListHistory.get(i) + "\n");

                Log.i("arraylist", arrayListHistory.get(i));
            }
            Log.i("arraylist", "new log");
            //historyDice2TextView.setText(getHistory2);
        } else {
//            getHistory = "0";
        }
    }

}