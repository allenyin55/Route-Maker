package com.example.allen.routemaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CollectingActivity extends AppCompatActivity {

    private int numberOfAddresses;

    EditText numberED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting);

        numberED = (EditText) findViewById(R.id.numberEditText);
    }
    public void MapsScreen(View view ){
        numberOfAddresses = Integer.valueOf(String.valueOf(numberED.getText()));

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("theNumber",numberOfAddresses);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }


}
