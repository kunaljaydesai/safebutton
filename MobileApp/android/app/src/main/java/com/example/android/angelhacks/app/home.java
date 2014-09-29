package com.example.android.angelhacks.app;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class home extends ActionBarActivity implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    public String url = "http://ec2-54-84-27-191.compute-1.amazonaws.com/safebuttontwilio.php";
    public List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void findOthers(View view){
        txtLat = (TextView) findViewById(R.id.test);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /*Criteria locationCriteria = new Criteria();
        locationCriteria.setAccuracy(Criteria.ACCURACY_HIGH);
        String myProvider = locationManager.getBestProvider(locationCriteria, true);*/
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0,this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.test);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        sendText emergency = new sendText();
        emergency.execute("true", Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    class sendText extends AsyncTask<String, Void, String> {
        //TextView error = (TextView) findViewById(R.id.errorlogin);
        //String result;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            if (params[0].equals(""))
                return null;

            String result = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try {
                nameValuePairs.add(new BasicNameValuePair("alarm", params[0]));
                nameValuePairs.add(new BasicNameValuePair("latitude", params[1]));
                nameValuePairs.add(new BasicNameValuePair("longitude", params[2]));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //System.out.print(nameValuePairs);

                result = httpclient.execute(httppost, new BasicResponseHandler());
                //System.out.print(result);

                //System.out.println("check");
                nameValuePairs.clear();

                return result;

            } catch (ClientProtocolException e) {

                //System.out.println("check1");
                return null;

            } catch (IOException e) {

                //System.out.println(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(String result) {
            /*System.out.print(result);
            result = result.trim();
           txtLat.setText(result);*/
        }
    }
}



