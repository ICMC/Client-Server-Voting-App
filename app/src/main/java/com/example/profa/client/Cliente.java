package com.example.profa.client; /**
 * Created by pawpepe on 12/06/2017.
 */

import java.io.IOException;


import android.content.Context;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
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
    String voteInfo;
    int opcode;



    Cliente(String addr, int port, String id, String textResponse, Context context, String voteInfo, int opcode) {
        dstAddress = addr;
        dstPort = port;
        candidateId =id;
        this.context = context;
        this.textResponse = textResponse;
        this.voteInfo = voteInfo;
        this.opcode = opcode;

    }




    protected String doInBackground(Object... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            OutputStream outpuStream = socket.getOutputStream();

            // Send to server the voteInfo string , and the server will return is the vote was valid
            // Else it will throw a message telling for example that the candidate already voted

            //prints message from the server
            inputStream.read(buffer);

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

            Toast toast = Toast.makeText(context, textResponse, duration);
            toast.show();



        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        textResponse= response;
        super.onPostExecute(textResponse);
    }

}