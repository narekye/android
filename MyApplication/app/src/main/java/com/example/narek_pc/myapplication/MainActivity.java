package com.example.narek_pc.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private static String url = "http://www.mic.am";
    Context mcontext;
    String responseString;
    HttpResponse response = null;

    private String userAgent() {
        String userAgent = null;
        if (userAgent != null) {
        }
        if (userAgent == null) {
            userAgent = "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13";
        }
        return userAgent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        try {
            AndroidHttpClient client;// = new AndroidHttpClient();
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            DataOutputStream printout;
            DataInputStream input;
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            // conn.setConnectTimeout(10000);
            // conn.setReadTimeout(10000);
            // conn.setRequestProperty("USER-AGENT","Mozilla/5.0");
            //setContentView(R.id.button);
            //conn.connect();
            //TextView views = (TextView)findViewById(R.id.textView);
            // views.setText("mtav axper,");
            // HttpResponse resp = new HttpResponse();
            printout = new DataOutputStream(conn.getOutputStream());
            byte[] data = "data".getBytes("UTF-8");
            printout.write(data);
            printout.flush();
            printout.close();

            int responseCode = conn.getResponseCode();
            // self.setText(responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    //  response += line;
                }
            } else {
                // response = "";
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void Login(View view) throws InterruptedException {
        mcontext = this;

        // Button but = (Button)findViewById(R.id.button);
        // String response = "";
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //  Utils.log(TAG, "start download....");
                AndroidHttpClient client = null;
                client = AndroidHttpClient.newInstance(userAgent(), mcontext);
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost request = new HttpPost("http://crmbetd.azurewebsites.net/api/token");
                try {
                    response = client.execute(request);


                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity en = response.getEntity();
                    String resp = EntityUtils.toString(en, "UTF-8");
                    responseString = resp.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        TextView text = (TextView) findViewById(R.id.textView);
        // TimeUnit.SECONDS.sleep(1);
        if(responseString != null) {

            text.setText(responseString.toString());
            // text = (EditText)findViewById(R.id.password);
        }
        else
        {
            text.setText("chka hly");
        }
        //text.setText();
    }
}