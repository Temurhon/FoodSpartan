package com.example.ktemu.foodspartan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
//import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView mTextViewer;
    private RequestQueue mQueue;




    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_storyboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //reference to my button "parse".
        mTextViewer = findViewById(R.id.text_view);
        Button  buttonParse = findViewById(R.id.button_parse);


        //created an instance of queue.
        //only need it for "this" activity.
        mQueue = Volley.newRequestQueue(this);


        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });


    }


    //source : https://codinginflow.com/code-examples/android/volley-json-parsing


    private void jsonParse() {

        String url = "https://api.myjson.com/bins/kp9wz";
        //I've created a variable called request that has 4 arguments.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Created an array that will store JSON information.
                            JSONArray jsonArray = response.getJSONArray("employees");


                            //The for loop will be displaying on the amount of JSON information.
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //Created an object called employee so i can access Information from getString.
                                JSONObject employee = jsonArray.getJSONObject(i);

                                //This will get the information relating to its object, e.g  age: 30.
                                String firstName = employee.getString("firstname");
                                int age = employee.getInt("age");
                                String mail = employee.getString("mail");

                                //this is kinda like toString, it will print out the information in a for loop.
                                mTextViewer.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        //this will execute the request that i have created at the top.
        mQueue.add(request);
    }

}
