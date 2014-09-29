package com.example.android.angelhacks.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

import org.apache.http.HttpResponse;
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


public class signup extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GooglePlayServicesClient.ConnectionCallbacks {
public String url = "http://ec2-54-84-27-191.compute-1.amazonaws.com/register.php";
public List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
public String response;
private PlusClient mPlusClient;
private ConnectionResult mConnectionResult;
private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mPlusClient = new PlusClient.Builder(this, this, this)
                .setActions("http://schemas.google.com/AddActivity", "http://schemas.google.com/ListenActivity")
                .build();
        findViewById(R.id.sign_in_button_google).setOnClickListener(this);

    }
    @Override
    public void onClick(View view){
        if (view.getId() == R.id.sign_in_button_google && !mPlusClient.isConnected() && mConnectionResult != null){
            try {
                mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
            }
            catch (IntentSender.SendIntentException e){
                mConnectionResult =null;
                mPlusClient.connect();
            }
        }
    }
    protected void onActivityResult(int requestCode, int responseCode, Intent intent){
        if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK){
            mConnectionResult = null;
            mPlusClient.connect();
        }
    }
    public void onStart(){
        super.onStart();
        mPlusClient.connect();
    }
    public void onStop(){
        super.onStop();
        mPlusClient.disconnect();
    }
    public void register(View view){

        EditText usernamesignup = (EditText)findViewById(R.id.signupusername);
        EditText passwordsignup = (EditText)findViewById(R.id.signuppassword);
        EditText emailsignup = (EditText)findViewById(R.id.email);
        EditText firstnamesignup = (EditText)findViewById(R.id.firstname);
        EditText lastnamesignup = (EditText)findViewById(R.id.lastname);
        EditText emergency1signup = (EditText)findViewById(R.id.emergencyone);
        EditText emergency2signup = (EditText)findViewById(R.id.emergencytwo);
        EditText emergency3signup = (EditText)findViewById(R.id.emergencythree);
        EditText sexsignup = (EditText)findViewById(R.id.sex);
        String username = usernamesignup.getText().toString();
        String password = passwordsignup.getText().toString();
        String email = emailsignup.getText().toString();
        String firstname = firstnamesignup.getText().toString();
        String lastname = lastnamesignup.getText().toString();
        String sex = sexsignup.getText().toString();
        String emergency1 = emergency1signup.getText().toString();
        String emergency2 = emergency2signup.getText().toString();
        String emergency3 = emergency3signup.getText().toString();
        myTasks register = new myTasks();
        register.execute(firstname, lastname, email, username, password, sex, emergency1,emergency2, emergency3 );


    }
    public void validate(){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
        return true;
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
    public void onConnected(Bundle bundle) {
        Person user = mPlusClient.getCurrentPerson();
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mConnectionResult = connectionResult;
    }

    private class myTasks extends AsyncTask<String, Void, String>  {

        //String response = null;
        TextView error =(TextView)findViewById(R.id.errormessage);
        @Override
        protected String doInBackground(String... params) {


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try {

                nameValuePairs.add(new BasicNameValuePair("firstname", params[0]));
                nameValuePairs.add(new BasicNameValuePair("lastname", params[1]));
                nameValuePairs.add(new BasicNameValuePair("email", params[2]));
                nameValuePairs.add(new BasicNameValuePair("username", params[3]));
                nameValuePairs.add(new BasicNameValuePair("password", params[4]));
                nameValuePairs.add(new BasicNameValuePair("sex", params[5]));
                nameValuePairs.add(new BasicNameValuePair("emergencyone", params[6]));
                nameValuePairs.add(new BasicNameValuePair("emergencytwo", params[7]));
                nameValuePairs.add(new BasicNameValuePair("emergencythree", params[8]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                System.out.print(nameValuePairs);

                response = httpclient.execute(httppost, new BasicResponseHandler());
                System.out.println(response);

                System.out.println("check");
                nameValuePairs.clear();
                return response;



            } catch (ClientProtocolException e) {

                System.out.println("check1");
                return "error";

            } catch (IOException e) {

                System.out.println(e.getMessage());
                return "error";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            result = result.trim();
            if (result.equals("Success"))
            {validate();}
            else if (result.equals("username")){

                error.setText("Someone already has this username");
            }
            else if (result.equals("email"))
            {
                error.setText("This email has already been used to register");
            }

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
