package com.example.chadbaily.hack;

import android.os.AsyncTask;
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

public class MainActivity extends AppCompatActivity {

    private EditText respText;
    private static final String TAG = "MAIN";
    private String html = "http://www.dineoncampus.com/stetson/";

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

// Get meta info
                Elements divHomeContent = doc.body().select("div#container").select("div#home-menus-content");
                // .select("div#home-content").select("div#whatsopen_container");
                Log.d(TAG, "printing table");
                Log.d(TAG, divHomeContent.text());
                buffer.append(divHomeContent.text());
                //                for (Element metaElem : divTable.select("table#whatsopen")) {
//                    Log.d("Main", metaElem);
//                }
//
//                Elements topicList = doc.select("h2.topic");
//                buffer.append("Topic listrn");
//                for (Element topic : topicList) {
//                    String data = topic.text();
//
//                    buffer.append("Data [" + data + "] rn");
//                }

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

