package crm.java;

import android.content.Context;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
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

/**
 * Created by narek.yegoryan on 04/08/2017.
 */

public class CrmSession implements ICrmSession {
    private static String token = null;
    private static String baseUrl = "http://crmbetd.azurewebsites.net/api/";
    private AndroidHttpClient client = null;
    private String userAgent = "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13";
    private HttpResponse httpResponse = null;
    private HttpEntity httpResponseText = null;
    private StatusLine statusLine = null;
    private static CrmSession instance;
    private String data;

    public CrmSession() {
        instance = this;
    }
    // Login section

    @Override
    public String Login(SuccessModel model, String usname, String password, Context context) {
        HttpPost request = new HttpPost(baseUrl + "token");
        this.client = AndroidHttpClient.newInstance(userAgent, context);
        try {
            StringEntity credentials = new StringEntity("grant_type=password&username=" + usname + "&password=" + password);
            credentials.setContentType(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
            request.setEntity(credentials);
            httpResponse = client.execute(request);
            httpResponseText = httpResponse.getEntity();
            String data = EntityUtils.toString(httpResponseText, "UTF-8").toString();
            statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 400) {
                model.setSuccess(false);
                return "Invalid credentials";
            } else if (token != null) {
                model.setSuccess(true);
                return "You are logged in the system.";
            } else {
                model.setSuccess(true);
                JSONObject json = new JSONObject(data);
                token = json.getString("access_token");
                return "Logged in as: " + usname;
            }
        } catch (Exception e) {
            model.setSuccess(false);
            return "Something went wrong";
        }
    }

    @Override
    public void logOut() {
        token = null;
    }

    @Override
    public String getToken() {
        // place for some logic, such as administrative tools;
        return this.token;
    }

    // TODO: fetch with one array method, and one simple object
    @Override
    public Object fetch(String uri, String method, JSONObject body, Context context) {
        Object result = null;
        client = AndroidHttpClient.newInstance(userAgent, context);
        // if (method == "GET") {
        HttpGet request = new HttpGet(baseUrl + uri);
        prepareRequest(request);

        try {
            httpResponse = null;
            httpResponse = client.execute(request); // change
            httpResponseText = httpResponse.getEntity();
            statusLine = httpResponse.getStatusLine();
            data = EntityUtils.toString(httpResponseText, "UTF-8").toString();
            if (statusLine.getStatusCode() == 401) {
                return null;
            }
            result = new JSONObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            try {
                result = new JSONArray(data);
            } catch (JSONException e1) {
                result = null;
            }
        }
        return result;
    }

    private boolean prepareRequest(HttpRequest request) {
        request.setHeader("Content-type", "application/json");
        if (token != null) {
            request.addHeader("authorization", "bearer " + getToken());
            return true;
        }
        return false;
    }

    public static CrmSession getInstance() {
        return instance != null ? instance : new CrmSession();
    }
}
