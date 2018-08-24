package rahul.com.movies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ListView mList;
    private TextView txtMsg;
    private Button btnTry;
    private ProgressBar progressBar;
    private List<Movie> movieList;
    private MovieAdapter adapter;
    String next;
    String url = "https://swapi.co/api/people";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = findViewById(R.id.main_list);
        txtMsg=findViewById(R.id.txt_msg);
        btnTry=findViewById(R.id.btn_try);
       progressBar=findViewById(R.id.progress_bar);
        fetchData();
       btnTry.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fetchData();
               mList.setVisibility(View.GONE);
               txtMsg.setVisibility(View.VISIBLE);
               btnTry.setVisibility(View.VISIBLE);
           }
       });

       mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Intent intent=new Intent(MainActivity.this,DetailActivity.class);
               intent.putExtra("NAME",movieList.get(position).getName());
               intent.putExtra("HEIGHT",movieList.get(position).getHeight() +" m");
               intent.putExtra("MASS",movieList.get(position).getMass() + " Kg");
               intent.putExtra("CREATED",movieList.get(position).getCreated());
               startActivity(intent);
           }
       });

    }

    private void fetchData(){
        // Check the validation for Network Connection
        if (NetworkConnectionChecker.isNetworkAvailable(MainActivity.this)) {
            getData();
            movieList = new ArrayList<>();
            adapter = new MovieAdapter(MainActivity.this,movieList);
            mList.setAdapter(adapter);
        }else{

            txtMsg.setText("Network Connection Not Available...");
            mList.setVisibility(View.GONE);
            txtMsg.setVisibility(View.VISIBLE);
            btnTry.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to fetch the data
     */
    private void getData() {


        progressBar.setVisibility(View.VISIBLE);
        mList.setVisibility(View.GONE);
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            JSONArray movieArray = obj.getJSONArray("results");
                            next=obj.getString("next");
                            //now looping through all the elements of the json array 
                            for (int i = 0; i < movieArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject movieObject = movieArray.getJSONObject(i);

                                //creating a movie object and giving them the values from json object
                                Movie movie = new Movie();
                                movie.setName(movieObject.getString("name"));
                                movie.setHeight(movieObject.getString("height"));
                                movie.setMass(movieObject.getString("mass"));
                                movie.setCreated(movieObject.getString("created"));
                                movieList.add(movie);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(next.equalsIgnoreCase("null")){
                            progressBar.setVisibility(View.GONE);
                        // Check the validation for empty list
                        if(movieList.size()>0){
                            mList.setVisibility(View.VISIBLE);
                            txtMsg.setVisibility(View.GONE);
                            btnTry.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }else{
                            txtMsg.setText("No Data Available...");
                            mList.setVisibility(View.GONE);
                            btnTry.setVisibility(View.VISIBLE);
                            txtMsg.setVisibility(View.VISIBLE);
                        }
                        }else{
                            url=next;
                            getData();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
