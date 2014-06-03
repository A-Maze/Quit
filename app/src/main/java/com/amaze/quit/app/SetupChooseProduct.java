package com.amaze.quit.app;

import android.content.Context;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SetupChooseProduct extends Fragment {

    public final static String TAG = "SetupChooseProduct";

    EditText etSearch;
    public String searchQuery;


    public static final SetupChooseProduct newInstance() {
        SetupChooseProduct f = new SetupChooseProduct();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_choose_product, container, false);
        //The complete button. This button should be moved to the final setup fragment which is this one at the moment.
        Button complete = (Button) v.findViewById(R.id.bSearchProduct);

        etSearch = (EditText) v.findViewById(R.id.etProduct);

        //sets the onclicklistener for the complete button
        complete.setOnClickListener(searchProduct);
        return v;
    }


    private View.OnClickListener searchProduct = new View.OnClickListener(){
        public void onClick(View v){


            LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.llResult);

            if((ll).getChildCount() > 0)
                (ll).removeAllViews();

            searchQuery = etSearch.getText().toString();

            String url = "https://api.bol.com/catalog/v4/search/?apikey=EDEFFA4EB07D4BB6AEB71C011711381E&format=json&limit=10&q=" + searchQuery;


            String result = GET(url);

            try {
                JSONObject jsonn = new JSONObject(result);

                String total = jsonn.toString(1);
                //Log.d(TAG, "result: " + total);

                JSONObject sum = new JSONObject();

                JSONArray products = jsonn.getJSONArray("products");
                int rows = products.length();




                for (int i = 0; i < rows; i++) {
                    sum = (JSONObject) jsonn.getJSONArray("products").get(i);
                    String end = sum.getString("title");

                    TextView tvResults = new TextView(getActivity());
                    tvResults.setText(end);
                    tvResults.setId(i);

                    ll.addView(tvResults);
                }

                Log.d(TAG, " " + rows);


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    };

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            Log.d(TAG, "cleint");


            // make GET request to the given URL

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));


            Log.d(TAG, "res[pon");

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            Log.d(TAG, "repsoncontent");

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
            Log.d(TAG, "convert");

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    /*
    public static InputStream sendHttpGet(String url) {

        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            content = response.getEntity().getContent();
        } catch (Exception e) {
            Log.d("[GET REQUEST]", "Network exception", e);
        }
        return content;




        HttpClient http = new DefaultHttpClient();
        StringBuilder buffer = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            output = EntityUtils.toString(httpEntity);
        }
        catch(Exception e) {
            Log.d(TAG, "gay");
            return null;
        } finally {
            TextView result = (TextView) getActivity().findViewById(R.id.tvBolResultAmount);
            result.setText(httpEntity);
        }

        Log.d(TAG, "okay. " + buffer + "/n url output: " );
        return buffer;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
        */

}