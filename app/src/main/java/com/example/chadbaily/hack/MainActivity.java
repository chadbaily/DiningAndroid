package com.example.chadbaily.hack;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private TextView respText;
    private static final String TAG = "MAIN";
    private String html = "http://www.dineoncampus.com/stetson/";
    private Pattern myPattern;
    private Matcher myMatch;
    //chad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        final EditText edtUrl = (EditText) findViewById(R.id.edtURL);
        Button btnGo = (Button) findViewById(R.id.btnGo);
        respText = (TextView) findViewById(R.id.edtResp);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String siteUrl = html.toString();
                (new ParseURL()).execute(new String[]{siteUrl});
            }
        });
    }

    private class ParseURL extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer = new StringBuffer();
            try {
                Log.d("JSwa", "Connecting to [" + strings[0] + "]");
                org.jsoup.nodes.Document doc = Jsoup.connect(strings[0]).get();
                Log.d("JSwa", "Connected to [" + strings[0] + "]");
// Get document (HTML page) title
//                String title = doc.title();
//                Log.d("JSwA", "Title [" + title + "]");
//                buffer.append("Title: " + title + "\n");

// Get hour info
                Elements divHomeContent = doc.body().select("div#container").select("div#home-menus-content");
                Log.d(TAG, "The Hours");
                Log.d(TAG, divHomeContent.text());
                String test = divHomeContent.text().toString();
                /*
                Formatting the code that is pulled to make it a little more legible. Maybe it can be made into a table?
                 */
                myPattern = Pattern.compile("(?:Sun|Mon|Tues|Wednes|Thurs|Fri|Satur)day- (\\d+)(\\w+)-(\\d+)(\\w+)");
                myMatch = myPattern.matcher(test);
                for (int i = 0; i < 7; i++) {
                    if (myMatch.find()) {
                        Log.d(TAG, myMatch.group(0));
                        if (i ==6) {
                            buffer.append(myMatch.group(0));
                        } else
                            buffer.append(myMatch.group(0)+ "\n");
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            respText.setText(s);
        }

    }
}

