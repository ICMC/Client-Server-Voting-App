package com.example.profa.client;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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
        //
        RadioButton candidate1 = (RadioButton) findViewById(R.id.candidate1);
        RadioButton candidate2 = (RadioButton) findViewById(R.id.candidate2);
        RadioButton candidate3 = (RadioButton) findViewById(R.id.candidate3);
        RadioButton candidate4 = (RadioButton) findViewById(R.id.candidate4);
        RadioButton candidate5 = (RadioButton) findViewById(R.id.candidate5);

        Button vote= (Button) findViewById(R.id.vote);



        String address = "cosmos.lasdpc.icmc.usp.br";
        final int port = 40011;
        String response = "";
        String voted = "";
        Context context = getApplicationContext();

        Cliente myCliente = new Cliente(address,port,candidateId, response, context, candidate1,candidate2,candidate3,candidate4,candidate5, voted);
        myCliente.execute();

        try {
            String content = new Scanner(new File("candidate.txt")).useDelimiter("\\Z").next();
            JSONObject candidates = new JSONObject(content);
            JSONArray name = candidates.getJSONArray("candidates");

            candidate1.setText(name.getInt(0));
            candidate2.setText(name.getInt(1));
            candidate3.setText(name.getInt(2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
