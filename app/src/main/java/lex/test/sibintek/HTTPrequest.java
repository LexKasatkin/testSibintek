package lex.test.sibintek;

/**
 * Created by lex on 2/4/17.
 */

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


/**
 * Created by lex on 28.06.16.
 */
public class HTTPrequest {
    String url;
    Context context;

    public HTTPrequest(String urlthis, Context context){
        this.url=urlthis;
        this.context=context;
    }
    public String getJSONString(){
        String json_string = new String();
        // Create local HTTP context - to store cookies
        // Bind custom cookie store to the local context
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGetJson = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
            httpGetJson.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpGetJson.setHeader("Accept-Language", "ru-RU,en;q=0.5");
            httpGetJson.setHeader("X-Requested-With", "XMLHttpRequest");
            httpResponse = httpclient.execute(httpGetJson);
            HttpEntity httpEntity = httpResponse.getEntity();
            if(httpResponse.getStatusLine().getStatusCode() != 200) {
                Toast toast = Toast.makeText( context, String.valueOf(httpResponse.getStatusLine().getStatusCode())
                        , Toast.LENGTH_SHORT);
                toast.show();
                throw new Exception("Error " + String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            }
            json_string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return json_string;
    }
}
