package com.amaze.quit.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    ArrayList<String> ids;
    ArrayList<String> titels;
    ArrayList<Double> prices;
    ArrayList<String> imagesURL;
    ArrayList<String> description;
    protected String result;
    View searching;

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
        searching = v.findViewById(R.id.searching);
        //sets the onclicklistener for the complete button
        complete.setOnClickListener(searchProduct);
        //The completeSetup button. This button should be moved to the final setup fragment which is this one at the moment.
        Button completeSetup = (Button) v.findViewById(R.id.bSetupComplete);

        //if the activity is the chooseProductHost then don't show the completeSetup button since the fragment isn't hosted in the setup.
        if (getActivity().getClass() == ChooseProductHost.class) {
            completeSetup.setVisibility(View.GONE);
        } else {
            completeSetup.setVisibility(View.VISIBLE);
            //sets the onclicklistener for the completeSetup button
            completeSetup.setOnClickListener(attachButton);
        }
        return v;
    }

    protected View.OnClickListener searchProduct = new View.OnClickListener() {
        public void onClick(View v) {
            searching.setVisibility(View.VISIBLE);
            View noResults = getActivity().findViewById(1337);
            if (noResults != null)
                noResults.setVisibility(View.GONE);

            InputMethodManager inputManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            searchQuery = etSearch.getText().toString();

            if (searchQuery.equals(null) || searchQuery.trim().length() <= 0) {
                return;
            } else {
                searchQuery = java.net.URLEncoder.encode(searchQuery);
                String url = "https://api.bol.com/catalog/v4/search/?apikey=EDEFFA4EB07D4BB6AEB71C011711381E&format=json&limit=10&q=" + searchQuery;
                new DownloadTask().execute(url);
            }
        }
    };

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            InputStream inputStream = null;
            String result = "";
            try {
                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                HttpResponse httpResponse = httpclient.execute(new HttpGet(urls[0]));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return result;
        }

        protected void onPostExecute(String result) {
            fillList(result);
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
            return result;
        }
    }

    protected void fillList(String result) {
        this.result = result;
        ListView lv = (ListView) getActivity().findViewById(R.id.lvResult);

        if ((lv).getChildCount() > 0)
            lv.setAdapter(null);

        try {
            // Make result to arrays
            JSONObject jsonn = new JSONObject(result);

            String total = jsonn.toString(1);

            JSONArray products = jsonn.getJSONArray("products");
            int rows = products.length();

            ids = new ArrayList<String>();
            titels = new ArrayList<String>();
            prices = new ArrayList<Double>();
            imagesURL = new ArrayList<String>();
            description = new ArrayList<String>();

            JSONObject product = new JSONObject();
            searching.setVisibility(View.GONE);
            for (int i = 0; i < rows; i++) {
                product = (JSONObject) jsonn.getJSONArray("products").get(i);

                // Get info of json
                String titel = product.getString("title");
                String id = product.getString("id");
                String desc = product.getString("longDescription");
                JSONObject offerData = (JSONObject) product.get("offerData");
                JSONObject offers = (JSONObject) offerData.getJSONArray("offers").get(0);
                String priceString = offers.getString("price");

                Double price = Double.parseDouble(priceString);

                JSONObject imagess = (JSONObject) product.getJSONArray("images").get(4);
                String image = imagess.getString("url");

                // add to array
                ids.add(id);
                titels.add(titel);
                prices.add(price);
                imagesURL.add(image);
                description.add(desc);
            }

            String[] id = ids.toArray(new String[ids.size()]);
            String[] title = titels.toArray(new String[titels.size()]);
            Double[] price = prices.toArray(new Double[prices.size()]);
            String[] imageURL = imagesURL.toArray(new String[imagesURL.size()]);

            CustomList adapter = new
                    CustomList(getActivity(), id, title, price, imageURL);
            ListView list = (ListView) getActivity().findViewById(R.id.lvResult);
            list.setAdapter(adapter);

        } catch (JSONException e) {
            //makes the progressbar disappear
            searching.setVisibility(View.GONE);
            //shows the no results string if there is no result for the searchterm.
            View linearLayout = getActivity().findViewById(R.id.llChooseProduct);
            TextView noResults = new TextView(getActivity());
            noResults.setText(R.string.product_no_results);
            noResults.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            noResults.setId(1337);
            noResults.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout) linearLayout).addView(noResults, 2);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // extra om mee te geven naar intent
                Bundle extras = new Bundle();
                // When clicked, show a toast with the TextView text
                Intent productIntent = new Intent(view.getContext(), ProductDetail.class);

                TextView tvTitle = (TextView) view.findViewById(R.id.tvProductTitle);
                String idP = tvTitle.getTag().toString();
                String image = imagesURL.get(position);
                String title = titels.get(position);
                Double price = prices.get(position);
                String desc = description.get(position);
                extras.putString("id", idP);
                extras.putString("image", image);
                extras.putString("titel", title);
                extras.putDouble("prijs", price);
                extras.putString("description", desc);
                productIntent.putExtras(extras);
                startActivityForResult(productIntent, 0);
            }
        });
        return;
    }

    //The onClickListener for the complete button
    private View.OnClickListener attachButton = new View.OnClickListener() {
        public void onClick(View v) {
            SetupBrandAmount setupBrandAmount = new SetupBrandAmount();
            Intent myIntent = new Intent(getActivity(), Home.class);
            DatabaseHandler db = new DatabaseHandler(getActivity());
            EditText etDayAmount = setupBrandAmount.getEtDayAmount();
            int perPak = setupBrandAmount.getPerPak();
            float prijs = setupBrandAmount.getPrijs();

            try {
                db.getProduct(1);
            } catch (Exception e) {
                forgotDialog("Kies alstublief een product om voor te sparen.");
                return;
            }
            if (etDayAmount.getText().toString().matches("") || String.valueOf(perPak).matches("") || String.valueOf(prijs).matches("")) {
                forgotDialog("Controleer alstublieft of u alles heeft ingevuld op het vorige scherm.");
            } else if ((Integer.parseInt(etDayAmount.getText().toString()) == 0 || perPak == 0 || prijs == 0)) {
                forgotDialog("Controleer alstublieft of u nergen 0 heeft ingevoerd op het vorige scherm, dit is namelijk niet toegestaan.");
            } else {

                Integer dayAmount = Integer.parseInt(etDayAmount.getText().toString());
                if (setupBrandAmount.sigaret == true) {
                    Sigaretten sigaret = setupBrandAmount.getSigarettenPosition();
                    try {
                        sigaret.setAantal(perPak);
                        sigaret.setPrijs(prijs);
                        db.updateSigaretten(sigaret);
                        db.addUser(new User(1, sigaret.getsID(), dayAmount, 1, SetupQuitDate.quitYear, SetupQuitDate.quitMonth, SetupQuitDate.quitDay, SetupQuitDate.quitHour, SetupQuitDate.quitMinute, 0, 1));
                    } catch (Exception e) {
                        db.addUser(new User(1, sigaret.getsID(), dayAmount, 1, SetupQuitDate.quitYear, SetupQuitDate.quitMonth, SetupQuitDate.quitDay, SetupQuitDate.quitHour, SetupQuitDate.quitMinute, 0, 1));
                        //e.printStackTrace();
                    }
                } else {
                    Shag shag = setupBrandAmount.getShagPos();

                    shag.setAantal(perPak);
                    shag.setPrijs(prijs);
                    db.updateShag(shag);

                    try {

                        db.addUser(new User(1, shag.getsID(), dayAmount, 1, SetupQuitDate.quitYear, SetupQuitDate.quitMonth, SetupQuitDate.quitDay, SetupQuitDate.quitHour, SetupQuitDate.quitMinute, 0, 0));
                    } catch (Exception e) {
                        db.updateUser(new User(1, shag.getsID(), dayAmount, 1, SetupQuitDate.quitYear, SetupQuitDate.quitMonth, SetupQuitDate.quitDay, SetupQuitDate.quitHour, SetupQuitDate.quitMinute, db.getUser(1).getSpentAmount(), db.getUser(1).getShagorsig()));
                        e.printStackTrace();
                    }
                }
                db.close();
                //this makes sure the activity resumes rather than creating a new one.
                myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(myIntent);
                getActivity().finish();
            }

        }
    };

    private void forgotDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("foutje");
        alertDialogBuilder
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Okee", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}