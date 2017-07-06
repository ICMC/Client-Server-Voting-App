package com.example.profa.client; /**
 * Created by pawpepe on 12/06/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;


import android.content.Context;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Cliente extends AsyncTask<Object, String, String> {

    String dstAddress;
    int dstPort;
    String response = "";
    String textResponse;
    String candidateId;
    String finalVotes;
    String candidate = "";
    Context context;
    String voteInfo;
    volatile String opcode;
    RadioButton candidat;
    RadioButton candidat2;
    RadioButton candidat3;
    RadioButton candidat4;
    RadioButton candidat5;
    TextView electionTitle;
    JSONArray serverResponse;



    Cliente(String addr, int port, String id, String textResponse, Context context, String voteInfo, String opcode, RadioButton candidat, RadioButton candidat2, RadioButton candidat3, RadioButton candidat4, RadioButton candidat5, TextView electionTitle, JSONArray serverResponse) {
        dstAddress = addr;
        dstPort = port;
        candidateId =id;
        this.context = context;
        this.textResponse = textResponse;
        this.voteInfo = voteInfo;
        this.opcode = opcode;
        this.candidat = candidat;
        this.candidat2 = candidat2;
        this.candidat3 = candidat3;
        this.candidat4 = candidat4;
        this.candidat5 = candidat5;
        this.electionTitle =electionTitle;
        this.serverResponse = serverResponse;


    }




    protected String doInBackground(Object... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];


            // TODO send opcode 999 to server, then it will return the json file empty with the candidates names

            // TODO if sent 888 it means it will close the conection , and send right away the votes



            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


           for(;;){
               if(opcode.compareTo("999") == 0 ){
                   serverWriter.println(opcode);
                   response = serverReader.readLine();
                   System.out.println(response);
                   opcode = "1234";
                   publishProgress(response);

               }

               if(opcode.compareTo("888") == 0){
                   //TODO  send json
                   finalVotes = serverResponse.toString();
                   finalVotes = finalVotes.replace("\n","");
                   serverWriter.println(finalVotes);
                   publishProgress(response);
                   opcode = "800";
                   // TODO close conection
                  // socket.close();
               }
           }


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
            opcode = "222";
            publishProgress(response);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
            opcode = "333";
            publishProgress(response);
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
        Toast toast;
       // Toast toast = Toast.makeText(context,"INFO DO SERVIDOR"+textResponse, duration);
        //toast.show();
        if(opcode.compareTo("800")==0){
             toast = Toast.makeText(context,"AHHHHHHHHHHHH  "+finalVotes, duration);
            //toast.show();
        }
        if(opcode.compareTo("1234")==0){


            // toast = Toast.makeText(context,"INFO DO SERVIDOR"+textResponse, duration);
            //toast.show();

            try {
                JSONArray json = new JSONArray(textResponse);

                JSONObject obj = json.getJSONObject(0);

                String election = obj.getString("election");
                electionTitle.setText(election);

                String name =  obj.getString("name");
                candidat.setText(name);


                obj = json.getJSONObject(1);
                name = obj.getString("name");
                candidat2.setText(name);

                obj = json.getJSONObject(2);
                name = obj.getString("name");
                candidat3.setText(name);

                obj = json.getJSONObject(3);
                name = obj.getString("name");
                candidat4.setText(name);

                obj = json.getJSONObject(4);
                name = obj.getString("name");
                candidat5.setText(name);

                serverResponse = json;




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(opcode.compareTo("111")==0){
                // TODO update user that the votes were sent to the server
        }else if(opcode.compareTo("222")== 0){
             toast = Toast.makeText(context,"ERRO Sem conexao", duration);
            toast.show();
        }else if(opcode.compareTo("333") == 0){
            toast = Toast.makeText(context,"ERRO Endereco Invalido", duration);
            toast.show();
        }

        if(opcode.compareTo("444")==0){
            toast = Toast.makeText(context,"OPCODE is 8888!!!!!!", duration);
            toast.show();
        }

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        textResponse= result;
        super.onPostExecute(textResponse);
    }

}