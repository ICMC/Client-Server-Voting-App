package com.example.profa.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class CandidateDisplay extends Activity{
    String votesInfo;
    String candidateName= "";
    String serverJason =null;

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

        final String address = "cosmos.lasdpc.icmc.usp.br";
        final int port = 40011;
        final String response = null;
        final Context context = getApplicationContext();
        String voteInfo = "";
        int opcode = 0;
        RadioButton candidat = (RadioButton) findViewById(R.id.candidate1);
        RadioButton candidat2 = (RadioButton) findViewById(R.id.candidate2);
        RadioButton candidat3 = (RadioButton) findViewById(R.id.candidate3);
        RadioButton candidat4 = (RadioButton) findViewById(R.id.candidate4);
        RadioButton candidat5 = (RadioButton) findViewById(R.id.candidate5);

        Cliente myCliente = new Cliente(address,port,voterId, response, context, voteInfo, opcode, candidat, candidat2, candidat3, candidat4, candidat5);
        myCliente.execute();






    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton candidate1 = (RadioButton) findViewById(R.id.candidate1);
        RadioButton candidate2 = (RadioButton) findViewById(R.id.candidate2);
        RadioButton candidate3 = (RadioButton) findViewById(R.id.candidate3);
        RadioButton candidate4 = (RadioButton) findViewById(R.id.candidate4);
        RadioButton candidate5 = (RadioButton) findViewById(R.id.candidate5);
        RadioButton branco = (RadioButton) findViewById(R.id.branco);
        RadioButton nulo = (RadioButton) findViewById(R.id.nulo);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.candidate1:
                if (checked)
                    candidateName = candidate1.getText().toString();
                    break;
            case R.id.candidate2:
                if (checked)
                    candidateName = candidate2.getText().toString();
                    break;
            case R.id.candidate3:
                if (checked)
                    candidateName = candidate3.getText().toString();
                    break;
            case R.id.candidate4:
                if (checked)
                    candidateName = candidate4.getText().toString();
                    break;
            case R.id.candidate5:
                if (checked)
                    candidateName = candidate5.getText().toString();
                    break;
            case R.id.branco:
                if (checked)
                    candidateName = branco.getText().toString();
                    break;
            case R.id.nulo:
                if (checked)
                    candidateName = nulo.getText().toString();
                    break;
        }
    }



}
