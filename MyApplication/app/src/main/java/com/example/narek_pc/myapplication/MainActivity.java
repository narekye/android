package com.example.narek_pc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import crm.java.CrmSession;
import crm.java.ICrmSession;
import crm.java.SuccessModel;
import crm.java.Utilities;

public class MainActivity extends AppCompatActivity {
    Context mcontext;

    private ICrmSession session;
    private Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        utilities = new Utilities();
        session = CrmSession.getInstance();
//        (R.id.link_signup) TextView _signupLink;
//        _signupLink.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
//            }
//        });
        super.onCreate(savedInstanceState);
        if (!isConnected()) {
            Toast.makeText(MainActivity.this,
                                    "Please connecct to internet",
                                   Toast.LENGTH_LONG).show();
            return;
        }
        setContentView(R.layout.activity_main);
    }

    public void Login(final View view) {
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button button = (Button) findViewById(R.id.button);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SuccessModel model = new SuccessModel();
                String result = session.Login(model, username.getText().toString(), password.getText().toString(), mcontext);
                utilities.showMessage(view, result);
                setButton(button, false);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (model.isSuccess()) {
                    naviPage();
                } else {
                    setButton(button, true);
                }
            }
        }).start();
    }

    public static void setButton(Button bt, boolean flag) {
        bt.setClickable(flag);
    }

    public void eraseonclick(View view) {
        EditText filed = (EditText) findViewById(view.getId());
        filed.setText("");
    }

    public void naviPage() {
        Intent intent = new Intent(getApplicationContext(), naviActivity.class);
        startActivity(intent);
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}