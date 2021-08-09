package com.aakash.dicerolling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String[] sides = {"4", "6", "8", "10", "12", "20"};
    Spinner spin;
    Button rollOneDiceButton;
    String diceResult;
    int maxValue = 4;
    TextView diceResult1Textview, diceResult2Textview;
    EditText customDiceSidesEditText;
    LinearLayout secondDiceLayoutView, customSidesLayout;
    RadioGroup radioGroup;
    RadioButton oneDiceRadioButton, twoDiceRadioButton, customDiceRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreferences sharedPreferences = SharedPreferences

        getID();
        rollOneDiceButton.setOnClickListener(this);

        secondDiceLayoutView.setVisibility(View.GONE);
        customSidesLayout.setVisibility(View.GONE);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sides);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
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


        switch (position) {
            case 0: {
                Toast.makeText(getApplicationContext(), "Side 4", Toast.LENGTH_LONG).show();
                maxValue = new Dice(4).numberOfSides;
                break;
            }
            case 1: {
                Toast.makeText(getApplicationContext(), "Side 6", Toast.LENGTH_LONG).show();
                maxValue = new Dice(6).numberOfSides;
                break;
            }
            case 2: {
                Toast.makeText(getApplicationContext(), "Side 8", Toast.LENGTH_LONG).show();
                maxValue = new Dice(8).numberOfSides;
                break;
            }
            case 3: {
                Toast.makeText(getApplicationContext(), "Side 10", Toast.LENGTH_LONG).show();
                maxValue = new Dice(10).numberOfSides;
                break;
            }
            case 4: {
                Toast.makeText(getApplicationContext(), "Side 12", Toast.LENGTH_LONG).show();
                maxValue = new Dice(12).numberOfSides;
                break;
            }
            case 5: {
                Toast.makeText(getApplicationContext(), "Side 20", Toast.LENGTH_LONG).show();
                maxValue = new Dice(20).numberOfSides;
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), "default", Toast.LENGTH_LONG).show();
                break;
            }

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
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.rollOneDiceButton:
                saveData();
                if(oneDiceRadioButton.isChecked()){
                    diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                    diceResult1Textview.setText(diceResult);

                }
                else if(twoDiceRadioButton.isChecked()){
                diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                diceResult1Textview.setText(diceResult);


                diceResult = String.valueOf(new Dice(maxValue).rollmethod());

                diceResult2Textview.setText(diceResult);

                }
                else if(customDiceRadioButton.isChecked()){
                    int numberofsides = Integer.parseInt(String.valueOf(customDiceSidesEditText.getText()));
                    if(numberofsides < 100){
                        try{
                            diceResult = String.valueOf(new Dice(numberofsides).rollmethod());
                            diceResult1Textview.setText(diceResult);
                        }
                        catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter values below 100!", Toast.LENGTH_SHORT).show();
                    }
                }
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

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("value", "");

        customDiceSidesEditText.setText(s1);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("value", customDiceSidesEditText.getText().toString());
        myEdit.apply();
    }

}