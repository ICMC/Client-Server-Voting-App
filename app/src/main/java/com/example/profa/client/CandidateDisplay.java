package com.example.profa.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.json.*;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogRecord;

/**
 * Created by pawpepe on 15/06/2017.
 */


public class CandidateDisplay extends AppCompatActivity{
    String votesInfo;
    String candidateName= "";
    String serverJason =null;
    String nullJson = "[{" +
            "\"name\":\"\",\n" +
            "\"num_votes\":0}\n" +
            ",{\n" +
            "\"name\":\"\",\n" +
            " \"num_votes\":0},\n" +
            " {\n" +
            "  \"name\":\"\",\n" +
            "  \"num_votes\":0},\n" +
            "  {\n" +
            "  \"name\":\"\",\n" +
            "  \"num_votes\":0},\n" +
            "  {\n" +
            "  \"name\":\"\",\n" +
            "  \"num_votes\":0}\n" +
            "  ]";
    JSONArray serverResponse = new JSONArray();

    public CandidateDisplay() throws JSONException {

    }
    public void addVote(String name, JSONArray jArr) throws JSONException {
        JSONObject obj;
        int i;

        for( i=0; i < jArr.length(); i++){
            obj = jArr.getJSONObject(i);
            if(obj.getString("name").compareTo(name) == 0) {
                obj.put("num_votes",obj.getInt("num_votes")+1);
                jArr.put(i,obj);
                break;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String voterId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.voterID);
        textView.setText(voterId);


        final Button vote= (Button) findViewById(R.id.vote);
        final Button close = (Button) findViewById(R.id.close);

        final String address = "cosmos.lasdpc.icmc.usp.br";
        final int port = 40011;
        final String response = null;
        final Context context = getApplicationContext();
        final String voteInfo = "";
        final String[] opcode = {"999","888"};
        final RadioButton candidat = (RadioButton) findViewById(R.id.candidate1);
        final RadioButton candidat2 = (RadioButton) findViewById(R.id.candidate2);
        final RadioButton candidat3 = (RadioButton) findViewById(R.id.candidate3);
        final RadioButton candidat4 = (RadioButton) findViewById(R.id.candidate4);
        final RadioButton candidat5 = (RadioButton) findViewById(R.id.candidate5);
        final TextView electionTitle = (TextView) findViewById(R.id.electionTitle);



        final Cliente myCliente = new Cliente(address,port,voterId, response, context, voteInfo, opcode[0], candidat, candidat2, candidat3, candidat4, candidat5, electionTitle, serverResponse);
        myCliente.execute();
        while(serverResponse==null){
            serverResponse = myCliente.serverResponse;
        }



        //create Json array to return the values of the votes;

        vote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = Toast.LENGTH_LONG;
                if(candidateName.compareTo("")==0){
                    //print that no candidate was selected
                }else {
                        try {
                            addVote(candidateName, serverResponse);
                            Toast toast = Toast.makeText(context,"Voto salvo para "+candidateName, duration);
                            toast.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
                    radioGroup.clearCheck();
                    Toast toast = Toast.makeText(context, "Proximo Voto", duration);
                    toast.show();

                }
            }
        });

        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myCliente.serverResponse = serverResponse;
                myCliente.opcode = "888";
                //start new intent thas shows that the voting is over
            }
        });

    }



    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton candidate1 = (RadioButton) findViewById(R.id.candidate1);
        RadioButton candidate2 = (RadioButton) findViewById(R.id.candidate2);
        RadioButton candidate3 = (RadioButton) findViewById(R.id.candidate3);
        RadioButton candidate4 = (RadioButton) findViewById(R.id.candidate4);
        RadioButton candidate5 = (RadioButton) findViewById(R.id.candidate5);




        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.candidate1:
                if (checked)
                    candidateName = candidate1.getText().toString();
                    break;
            case R.id.candidate2:
                if (checked)
                    candidateName = candidate2.getText().toString();

                    candidate1.setChecked(false);
                    break;
            case R.id.candidate3:
                if (checked)
                    candidateName = candidate3.getText().toString();

                    candidate1.setChecked(false);
                    break;
            case R.id.candidate4:
                if (checked)
                    candidateName = candidate4.getText().toString();

                    candidate1.setChecked(false);
                    break;
            case R.id.candidate5:
                if (checked)
                    candidateName = candidate5.getText().toString();

                    candidate1.setChecked(false);
                    break;

        }


    }



}
