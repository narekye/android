package com.example.narek_pc.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import crm.java.CrmSession;
import crm.java.Utilities;

public class contactsActivity extends AppCompatActivity {
    private static String baseUrl = "http://crmbetd.azurewebsites.net/api/";
    private static String fullname = "Full Name";
    private static String email = "Email";
    private static String company = "Company Name";
    private static String position = "Position";
    Context mcontext;
    public static String responseString;
    HttpResponse response = null;
    StatusLine statusLine = null;
    JSONArray json = new JSONArray();
    CrmSession session;
    Utilities util = new Utilities();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = CrmSession.getInstance();
        setContentView(R.layout.activity_contacts);
        GetContacts();
    }

    private String userAgent() {
        return "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13";
    }

    public void GetContacts() {
        // final JSONArray array = session.fetch("contacts", "GET", null, this);
        // mcontext = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONArray array = session.fetch("contacts", "GET", null, null);
                if (true) {
                    init(array);
                }
            }
        }).start();
    }

    public void init(final JSONArray object) {
        if (object == null) {
            return;
        }
        String separator = " | ";
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("No");
        tv0.setTextColor(Color.GREEN);
        tv0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv1.setText("Name");
        tv1.setTextColor(Color.RED);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv2.setText("Company");
        tv2.setTextColor(Color.RED);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("Email");
        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv3.setTextColor(Color.RED);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        try {
            for (int i = 0; i < object.length(); i++) {
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(i + separator);
                t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                JSONObject data = object.getJSONObject(i);

                t2v.setText(data.getString(fullname));

                t2v.setTextColor(Color.WHITE);
                t2v.setGravity(Gravity.CENTER);
                tbrow.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(data.getString(company));
                t3v.setTextColor(Color.WHITE);
                t3v.setGravity(Gravity.CENTER);
                tbrow.addView(t3v);

                TextView t4v = new TextView(this);
                t4v.setText(data.getString(email));

                t4v.setTextColor(Color.WHITE);
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);
                stk.addView(tbrow);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showMessage(final View view, final String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                                    /*.setAction("Action", null)*/.show();
        // runOnUiThread(new Runnable() {
        //    @Override
        //    public void run() {
        //        Toast.makeText(MainActivity.this,
        //                message,
        //                Toast.LENGTH_LONG).show();
        //    }
        //});
    }
}
