package com.example.profa.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.profa.client.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final EditText idNumber;
        Button buttonConnect;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        idNumber = (EditText) findViewById(R.id.idNumber);
        buttonConnect =(Button) findViewById(R.id.connectButton);

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idNumber.getText().toString()!=null){
                    Intent intent = new Intent(v.getContext(), CandidateDisplay.class);
                    String message = idNumber.getText().toString();
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Type your ID";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });




    }
}