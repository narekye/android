package crm.java;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by narek.yegoryan on 07/08/2017.
 */

public interface ICrmSession {
    String Login(SuccessModel model, String usname, String password, Context context);

    String getToken();

    Object fetch(String uri, String method, JSONObject body, Context context);
}
