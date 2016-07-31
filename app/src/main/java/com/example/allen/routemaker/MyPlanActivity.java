package com.example.allen.routemaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPlanActivity extends AppCompatActivity {

    ArrayList<String> savedAddresses = new ArrayList<String>();
    private String addresses;
    private int number;

    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);

        myText = (TextView) findViewById(R.id.addresses);
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            number = 0;
        } else {
            number = extras.getInt("number");
        }


        savedAddresses = (ArrayList<String>) getIntent().getSerializableExtra("addressList");

        if (savedAddresses.size() == number) {
            for (int i = 0; i < number; i++) {
                myText.append("\n");
                myText.append(savedAddresses.get(i));
                myText.append("\n");
            }
        } else {
            for (int i = 0; i < savedAddresses.size(); i++) {
                myText.append("\n");
                myText.append(savedAddresses.get(i));
                myText.append("\n");
            }
        }
    }


}
