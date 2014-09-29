package com.example.android.angelhacks.app;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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




public class login extends ActionBarActivity {
    private EditText user, pass;
    private Button mSubmit, mRegister;
    public String url = "http://ec2-54-84-27-191.compute-1.amazonaws.com/login.php";
    public List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void login(View view) {
        EditText username = (EditText) findViewById(R.id.loginusername);
        EditText password = (EditText) findViewById(R.id.loginpassword);
        String pass = password.getText().toString();
        String user = username.getText().toString();
        AttemptLogin attempt = new AttemptLogin();
        attempt.execute(user, pass);
    }

    public void validate() {
        //
        // System.out.print("validated");
        Intent intent = new Intent(getApplicationContext(), home.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    class AttemptLogin extends AsyncTask<String, Void, String> {
        TextView error = (TextView) findViewById(R.id.errorlogin);
        String result;

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

                nameValuePairs.add(new BasicNameValuePair("username", params[0]));
                nameValuePairs.add(new BasicNameValuePair("password", params[1]));

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
            System.out.print(result);

            /*result = result.trim();
            if (result.equals("Success")) {
                validate();
                System.out.print(result);
            } else if (result.equals("Failure")) {
                System.out.print(result);
                error.setText("Don't match");
            } else {
                System.out.print("no response");
            }*/
        }
    }
}

