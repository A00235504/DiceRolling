package com.aakash.dicerolling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String[] sides = {"4", "6", "8", "10", "12", "20"};
    Spinner spin;
    Button rollOneDiceButton, rollTwoDiceButton;
    String diceResult;
    int maxValue = 4;
    TextView diceOneSelectorTextView,diceTwoSelectorTextView, diceResult1Textview, diceResult2Textview;
    SwitchCompat diceSelectorSwitch;
    LinearLayout secondDiceLayoutView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getID();
        rollOneDiceButton.setOnClickListener(this);
        rollTwoDiceButton.setOnClickListener(this);

        secondDiceLayoutView.setVisibility(View.GONE);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sides);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

diceSelectorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            diceTwoSelectorTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_200));
            diceOneSelectorTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            secondDiceLayoutView.setVisibility(View.VISIBLE);

        } else {
            diceTwoSelectorTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            diceOneSelectorTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_200));
            secondDiceLayoutView.setVisibility(View.GONE);

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
        rollTwoDiceButton = findViewById(R.id.rollTwoDiceButton);

        diceResult1Textview = findViewById(R.id.diceResult1Textview);
        diceResult2Textview = findViewById(R.id.diceResult2Textview);

        diceOneSelectorTextView = findViewById(R.id.diceOneSelectorTextView);
        diceTwoSelectorTextView = findViewById(R.id.diceTwoSelectorTextView);

        diceSelectorSwitch = findViewById(R.id.selectDiceTypeSwitch);
        secondDiceLayoutView = findViewById(R.id.secondDiceLayoutView);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.rollOneDiceButton:
                 diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                    diceResult1Textview.setText(diceResult);
                break;

            case R.id.rollTwoDiceButton:
                diceResult = String.valueOf(new Dice(maxValue).rollmethod());
                diceResult1Textview.setText(diceResult);


                diceResult = String.valueOf(new Dice(maxValue).rollmethod());

                diceResult2Textview.setText(diceResult);

                break;
            default:
                Toast.makeText(this, "View not implemented", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}