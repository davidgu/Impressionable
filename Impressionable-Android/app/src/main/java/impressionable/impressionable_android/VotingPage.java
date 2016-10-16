package impressionable.impressionable_android;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.*;
import javax.json.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import com.dropbox.core.*;
import com.dropbox.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import android.os.StrictMode;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.*;

public class VotingPage extends AppCompatActivity {

    private static final String ACCESS_TOKEN = "<9h0Mq3DJmhAAAAAAAAAALEjEBaNSzR-eqz3XDyosvaMq3ssLd3zBT00e5Y1qm2wp>";

    //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    //StrictMode.setThreadPolicy


    int currentIndex = 0;
    int maxIndex = 0;
    //int[] votes;
    ArrayList<Integer> votes = new ArrayList();

    TextView txtName;
    TextView txtMajor;
    TextView txtMinor;
    TextView txtYear;
    TextView txtGPA;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtName = (TextView) findViewById(R.id.txtviewName);
        txtMajor = (TextView) findViewById(R.id.txtviewMajor);
        txtMinor = (TextView) findViewById(R.id.txtviewMinor);
        txtYear = (TextView) findViewById(R.id.txtviewYear);
        txtGPA = (TextView) findViewById(R.id.txtviewGPA);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    public void loadFromJson(View v) {   //Loads user data from json
        displayInfo(currentIndex);
        //votes = new int[maxIndex];
        //for(int i = 0; i<maxIndex; i++){
        //    votes[i]=0;
        //}
    }

    public void buttonPrevious(View v) {
        if (currentIndex > 0) {
            currentIndex--;
        }
        displayInfo(currentIndex);
    }

    public void buttonNext(View v) {
        if (currentIndex < maxIndex - 1) {
            currentIndex++;
        }
        displayInfo(currentIndex);
    }

    void displayInfo(int currentIndex) {
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
            maxIndex = jsonArray.length();
        } catch (Exception e) {
        }

        txtName.setText(realName);
        txtMajor.setText(major);
        txtMinor.setText(minor);
        txtYear.setText(year);
        txtGPA.setText(gpa);
    }

    public void buttonYes(View v) {
        votes.add(currentIndex, 1);
        currentIndex++;
        displayInfo(currentIndex);
    }

    public void buttonNo(View v) {
        votes.add(currentIndex, -1);
        currentIndex++;
        displayInfo(currentIndex);
    }

    static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public JsonObject createResults(ArrayList<Integer> votes) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder json = factory.createObjectBuilder();
        JsonArrayBuilder users = factory.createArrayBuilder();
        for (int i = 0; i < maxIndex; i++) {
            users.add(votes.get(i));
        }
        json.add("users", users);
        return json.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void buttonSubmit(View v) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
                    DbxRequestConfig config = new DbxRequestConfig("gillzrus1997@gmail.com/Impressionable", "en_US");
                    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

                    // Get current account info
                    try {
                        FullAccount account = client.users().getCurrentAccount();
                        System.out.println(account.getName().getDisplayName());
                        try (InputStream in = new FileInputStream("test.txt")) {
                            FileMetadata metadata = client.files().uploadBuilder("/test.txt")
                                    .uploadAndFinish(in);
                        } catch (Exception e) {

                        }

                    }
                    catch(Exception e){

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


/*
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        // Get current account info
        try {
            FullAccount account = client.users().getCurrentAccount();
            System.out.println(account.getName().getDisplayName());
            try (InputStream in = new FileInputStream("test.txt")) {
                FileMetadata metadata = client.files().uploadBuilder("/test.txt")
                        .uploadAndFinish(in);
            } catch (Exception e) {

            }

*/



        /*
        FTPClient ftpClient = new FTPClient();
        boolean k=false;
        try {
            ftpClient.connect("ftp.impressionable-online.com", 21);
            k = ftpClient.login("impressionableonline", "IImpress1#1");
        }
        catch(NetworkOnMainThreadException e){
            Log.d("test", e.getMessage());
        }
        catch (IOException ex) {
            Log.d("test", ex.getMessage());
        }
        if(k){
            txtGPA.setText("FTP passed");
        }
        try {
            JsonObject jsonObject = createResults(votes);
            String str = jsonObject.toString();
            InputStream inputStream = new ByteArrayInputStream(str.getBytes());
            ftpClient.storeFile("results.json", inputStream);
        }
        catch(Exception e){

        }
        */
/*
        } catch (DbxApiException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
        */
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("VotingPage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }



}
