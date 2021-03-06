package com.example.narek_pc.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import crm.java.CrmSession;
import crm.java.ICrmSession;
import crm.java.Utilities;

public class contactsActivity extends AppCompatActivity {

    private static String fullname = "Full Name";
    private static String email = "Email";
    private static String company = "Company Name";
    private static String position = "Position";

    private ICrmSession session;
    private Utilities util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Utilities();
        session = CrmSession.getInstance();
        setContentView(R.layout.activity_contacts);
        setTitle("Contacts");
        GetContacts();
    }

    public void GetContacts() {
        final Context context = this;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = session.fetch("contacts", "GET", null, context);
                if (o == null) return;
                final JSONArray array = (JSONArray) o;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        init(array);
                    }
                });
            }
        });
        thread.start();
    }

    public void init(JSONArray object) {
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
        TextView tv4 = new TextView(this);
        tv4.setText("Position");
        tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv4.setTextColor(Color.RED);
        tbrow0.addView(tv4);
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
                TextView t5v = new TextView(this);
                t5v.setText(data.getString(position));
                t5v.setTextColor(Color.WHITE);
                t5v.setGravity(Gravity.CENTER);
                tbrow.addView(t5v);
                stk.addView(tbrow);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
