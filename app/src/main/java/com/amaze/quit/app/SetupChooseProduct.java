package com.amaze.quit.app;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SetupChooseProduct extends Fragment {

    public final static String TAG = "SetupChooseProduct";

    EditText etSearch;
    public String searchQuery;
    ArrayAdapter<String> adapter;

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


    protected View.OnClickListener searchProduct = new View.OnClickListener(){
        public void onClick(View v){


            ListView lv = (ListView) getActivity().findViewById(R.id.lvResult);

            if((lv).getChildCount() > 0)
                lv.setAdapter(null);

            searchQuery = etSearch.getText().toString();

            String url = "https://api.bol.com/catalog/v4/search/?apikey=EDEFFA4EB07D4BB6AEB71C011711381E&format=json&limit=10&q=" + searchQuery;


            String result = GET(url);

            try {
                JSONObject jsonn = new JSONObject(result);

                String total = jsonn.toString(1);
                //Log.d(TAG, "result: " + total);


                JSONArray products = jsonn.getJSONArray("products");
                int rows = products.length();


                ArrayList<String> titles = new ArrayList<String>();
                ArrayList<Double> prices = new ArrayList<Double>();
                ArrayList<String> imagesURL = new ArrayList<String>();


                JSONObject product = new JSONObject();
                for (int i = 0; i < rows; i++) {
                    product = (JSONObject) jsonn.getJSONArray("products").get(i);

                    String title = product.getString("title");

                    JSONObject offerData = (JSONObject) product.get("offerData");
                    JSONObject offers = (JSONObject) offerData.getJSONArray("offers").get(0);
                    String priceString = offers.getString("price");

                    Double price = Double.parseDouble(priceString);

                    JSONObject imagess = (JSONObject) product.getJSONArray("images").get(1);
                    String image = imagess.getString("url");

                    titles.add(title);
                    prices.add(price);
                    imagesURL.add(image);

                    //titles.add(title);

                    //TextView tvResults = new TextView(getActivity());
                    //tvResults.setText(title);
                    //tvResults.setId(i);

                    //lv.addView(tvResults);
                }

                //adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_bol, titles, new int[] {R.id.tvProductTitle, R.id.tvProductPrice});

                String[] title = titles.toArray(new String[titles.size()]);
                Double[] price = prices.toArray(new Double[prices.size()]);
                String[] imageURL = imagesURL.toArray(new String[imagesURL.size()]);


                CustomList adapter = new
                        CustomList(getActivity(), title, price, imageURL);
                ListView list=(ListView) getActivity().findViewById(R.id.lvResult);
                list.setAdapter(adapter);




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