package crm.java;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.narek_pc.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by narek.yegoryan on 04/08/2017.
 */

public class Utilities extends AppCompatActivity {

    public void hideKeyboard(final View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void showMessage(final View view, final String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public void initialize( JSONArray object) {
        String fullname = "Full Name";
        String email = "Email";
        String company = "Company Name";
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
}
