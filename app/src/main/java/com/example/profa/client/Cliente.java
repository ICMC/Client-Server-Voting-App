package com.example.profa.client; /**
 * Created by pawpepe on 12/06/2017.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.*;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente extends AsyncTask<Object, String, String> {

    String dstAddress;
    int dstPort;
    String response = "";
    String textResponse;
    String candidateId;
    String candidate = "";
    Context context;
    RadioButton candidate1, candidate2, candidate3,  candidate4, candidate5;
    String vote;


    Cliente(String addr, int port, String id, String textResponse, Context context, RadioButton candidate1, RadioButton candidate2, RadioButton candidate3, RadioButton candidate4, RadioButton candidate5, String vote) {
        dstAddress = addr;
        dstPort = port;
        candidateId =id;
        this.context = context;
        this.textResponse = textResponse;
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
        this.candidate3 = candidate3;
        this.candidate4 = candidate4;
        this.candidate5 = candidate5;
        this.vote = vote;
    }


    protected String doInBackground(Object... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[6022386];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();



            //if String is empty get file from server
//            if(vote.compareTo("") == 0){
//                File candidates = new File("candidate.txt");
//                FileInputStream inFile = new FileInputStream(candidates);
//
//                int count;
//                while ((count = inFile.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, count);
//                }
//
//                outputStream.close();
//                inFile.close();
//
//            }


            //prints message from the server
            inputStream.read(buffer);
            response = new String(buffer);

            publishProgress(response);

            socket.close();
			/*
             * notice: inputStream.read() will block if no data return
			 */
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                inputStream.read(buffer);
                response = new String(buffer);
                publishProgress(response);

            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    @Override
    protected void onProgressUpdate(String... values) {
           textResponse = values[0];

            CharSequence text = "connected";
            int duration = Toast.LENGTH_SHORT;
            vote =  textResponse;
            Toast toast = Toast.makeText(context, textResponse, duration);
            toast.show();

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        textResponse= response;
        super.onPostExecute(result);
    }

}