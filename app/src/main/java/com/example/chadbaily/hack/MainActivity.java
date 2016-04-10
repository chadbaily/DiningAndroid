package com.example.chadbaily.hack;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText respText;
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
        respText = (EditText) findViewById(R.id.edtResp);
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
                buffer.append(divHomeContent.text());

                myPattern = Pattern.compile(".*? Menu for (?:Sun|Mon|Tues|Wednes|Thurs|Fri|Satur)day, (\\w+) (\\w+) Hours: (.*)");
                myMatch = myPattern.matcher(test);
                Log.d(TAG, myMatch.group());
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

