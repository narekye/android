package com.example.narek_pc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private static String url = "http://crmbetd.azurewebsites.net/api/token";
    Context mcontext;
    public static String responseString;
    HttpResponse response = null;
    StatusLine statusLine = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Login(View view) throws InterruptedException {
        mcontext = this;
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                AndroidHttpClient client = null;
                client = AndroidHttpClient.newInstance(userAgent(), mcontext);
                HttpPost request = new HttpPost(url);
                try {
                    StringEntity entity = new StringEntity("grant_type=password&username=" + username.getText() + "&password=" + password.getText());
                    entity.setContentType(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
                    request.setEntity(entity);
                    response = client.execute(request);
                    statusLine = response.getStatusLine();
                    HttpEntity en = response.getEntity();
                    String resp = EntityUtils.toString(en, "UTF-8");
                    responseString = resp.toString();
                    if (statusLine.getStatusCode() == 400) {
                        showMessage("The username/password is incorrect.");
                    } else {
                        showMessage("Please wait");
                        JSONObject json = new JSONObject(responseString.toString());
                        responseString = json.getString("access_token");
                        Thread.sleep(3000);
                        next();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,
                        message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private String userAgent() {
        return "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13";
    }

    private void next() {
        Intent intent = new Intent(getApplicationContext(), contactsActivity.class);
        startActivity(intent);
    }

    public void eraseonclick(View view) {
        EditText filed = (EditText) findViewById(view.getId());
        filed.setText("");
    }
}