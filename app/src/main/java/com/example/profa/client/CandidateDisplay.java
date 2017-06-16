package com.example.profa.client;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 * Created by pawpepe on 15/06/2017.
 */

public class CandidateDisplay extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String candidateId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.candidateID);
        textView.setText(candidateId);

        String address = "cosmos.lasdpc.icmc.usp.br";
        final int port = 40011;
        String response = "";
        Context context = getApplicationContext();

        Cliente myCliente = new Cliente(address,port,candidateId, response, context);
        myCliente.execute();




    }
}
