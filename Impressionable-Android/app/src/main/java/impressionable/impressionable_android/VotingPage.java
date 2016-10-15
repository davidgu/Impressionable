package impressionable.impressionable_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.*;
import javax.json.*;
//import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

public class VotingPage extends AppCompatActivity {

    int currentIndex = 0;
    int maxIndex = 0;

    TextView txtName;
    TextView txtMajor;
    TextView txtMinor;
    TextView txtYear;
    TextView txtGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtName = (TextView)findViewById(R.id.txtviewName);
        txtMajor = (TextView)findViewById(R.id.txtviewMajor);
        txtMinor = (TextView)findViewById(R.id.txtviewMinor);
        txtYear = (TextView)findViewById(R.id.txtviewYear);
        txtGPA = (TextView)findViewById(R.id.txtviewGPA);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voting_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFromJson(View v){   //Loads user data from json
        displayInfo(currentIndex);

    }

    public void buttonPrevious(View v){
        if(currentIndex>0){
            currentIndex--;
        }
        displayInfo(currentIndex);
    }

    public void buttonNext(View v){
        if(currentIndex<maxIndex-1){
            currentIndex++;
        }
        displayInfo(currentIndex);
    }

    void displayInfo(int currentIndex){
        String realName = new String();
        String major = new String();
        String minor = new String();
        String year = new String();
        String gpa = new String();

        try {
            InputStream inputStream = getAssets().open("sessionusers.json");
            String text2 = convertStreamToString(inputStream);
            JSONArray jsonArray = new JSONArray(text2);
            realName = jsonArray.getJSONObject(currentIndex).getString("realName");
            major = jsonArray.getJSONObject(currentIndex).getString("major");
            minor = jsonArray.getJSONObject(currentIndex).getString("minor");
            year = jsonArray.getJSONObject(currentIndex).getString("year");
            gpa = jsonArray.getJSONObject(currentIndex).getString("gpa");
            maxIndex=jsonArray.length();
        }
        catch(Exception e){
        }

        txtName.setText(realName);
        txtMajor.setText(major);
        txtMinor.setText(minor);
        txtYear.setText(year);
        txtGPA.setText(gpa);
    }

    void buttonYes(View v){

    }

    void buttonNo(View v){

    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    int[] votes = new int[maxIndex];

    public JsonObject createResults(int[] votes){
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder json = factory.createObjectBuilder();
        JsonArrayBuilder users = factory.createArrayBuilder();
            for(int i = 0; i<maxIndex; i++){
                users.add(votes[i]);
            }
        json.add("users", users);
        return json.build();
    }


}
