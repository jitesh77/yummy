package in.engineer4u.yummy;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import in.engineer4u.jsonparser.Tryfile;


public class DetailView extends ActionBarActivity {

    Toolbar toolbar;
    Button call, web, more;
    TextView name1, address, rating1, location;
    String name,
            icon,
            vicinity,
            lat,
            lng,
            formatted_address,
            formatted_phone,
            website,
            rating,
            international_phone_number,
            url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        toolbar = (Toolbar) findViewById(R.id.yummy_app_bar);
        setSupportActionBar(toolbar);

        name1= (TextView) findViewById(R.id.restaurantName);
        address= (TextView) findViewById(R.id.detail_res_address);
        rating1= (TextView) findViewById(R.id.location);
        location=(TextView) findViewById(R.id.rating);
        call= (Button) findViewById(R.id.call);
        web= (Button) findViewById(R.id.website);
        more= (Button) findViewById(R.id.more);





        String reference = getIntent().getStringExtra("reference");


        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        sb.append("reference=" + reference);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyD-XNnFQ6iQeWJqd5OiothaHgU7609E2-I");


        PlacesTask placesTask = new PlacesTask();

        placesTask.execute(sb.toString());


    }


    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);


            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Excepti downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;


        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, HashMap<String, String>> {

        JSONObject jObject;


        @Override
        protected HashMap<String, String> doInBackground(String... jsonData) {

            HashMap<String, String> hPlaceDetails = null;
            Tryfile placeDetailsJsonParser = new Tryfile();

            try {
                jObject = new JSONObject(jsonData[0]);


                hPlaceDetails = placeDetailsJsonParser.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return hPlaceDetails;
        }


        @Override
        protected void onPostExecute(HashMap<String, String> hPlaceDetails) {


             name = hPlaceDetails.get("name");
            icon = hPlaceDetails.get("icon");
            vicinity = hPlaceDetails.get("vicinity");
            lat = hPlaceDetails.get("lat");
            lng = hPlaceDetails.get("lng");
            formatted_address = hPlaceDetails.get("formatted_address");
            formatted_phone = hPlaceDetails.get("formatted_phone");
            website = hPlaceDetails.get("website");
            rating = hPlaceDetails.get("rating");
            international_phone_number = hPlaceDetails.get("international_phone_number");
             url = hPlaceDetails.get("url");

            name1.setText(name);
            address.setText(vicinity);
            rating1.setText(lat+","+lng);
            location.setText(rating);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + formatted_phone));
                    startActivity(intent);
                }
            });

            web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                    startActivity(browserIntent);
                }
            });

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });


        }
    }
}
