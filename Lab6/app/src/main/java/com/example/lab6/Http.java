package com.example.lab6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Http extends AppCompatActivity {
    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpPost httpPost = new HttpPost("http://www.cbr.ru/scripts/XML_daily.asp");
        HttpClient client = HttpClientBuilder.create().build();
        StringBuilder result = new StringBuilder();
        Document document = null;
        try {
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "windows-1251"));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                result = new StringBuilder(result.substring(45));
                document = loadXMLFromString(result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NodeList nameList = Objects.requireNonNull(document).getElementsByTagName("Name");
        NodeList valueList = Objects.requireNonNull(document).getElementsByTagName("Value");
        ArrayList<String> Names = new ArrayList<>();
        ArrayList<String> Values = new ArrayList<>();
        for (int i = 0; i < nameList.getLength(); i++) {
            Names.add(nameList.item(i).getTextContent());
            Values.add(valueList.item(i).getTextContent());
        }

//        ArrayList<Double> dValues = new ArrayList<>();
//        for (int i = 0; i < Values.size(); i++) {
//            dValues.add(Double.parseDouble(Values.get(i).replace(',', '.')));
//        }
//        double dmax = Collections.max(dValues);
//        double dmin = Collections.min(dValues);
//        Collections.sort(dValues);
//        double dmedian  = dValues.get(dValues.size()/2);
//        int max = Values.indexOf(String.valueOf(dmax).replace('.', ',')+"00");
//        int min = Values.indexOf(String.valueOf(dmin).replace('.', ','));
//        int median = Values.indexOf(String.valueOf(dmedian).replace('.', ','));
//        System.out.println(min);
//
//        ArrayList<String> extraNames = new ArrayList<>();
//        ArrayList<String> extraValues = new ArrayList<>();
//        extraNames.add(Names.get(max));
//        extraValues.add(Values.get(max));
//        extraNames.add(Names.get(median));
//        extraValues.add(Values.get(median));
//        extraNames.add(Names.get(min));
//        extraValues.add(Values.get(min));

        ListView listView = findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter(this, R.layout.list_items_http, Names, Values);
        listView.setAdapter(adapter);

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(Http.this, NoteList.class);
            startActivity(intent);
        });
        Button bPrevious = findViewById(R.id.PreviousPage);
        bPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(Http.this, ElementsList.class);
            startActivity(intent);
        });
    }

    private static class ListAdapter extends ArrayAdapter<String> {

        private final int resourceLayout;
        private final Context mContext;
        private final ArrayList<String> Names;
        private final ArrayList<String> Values;


        public ListAdapter(Context context, int resource, ArrayList<String> _Names, ArrayList<String> _Values) {
            super(context, resource, _Names);
            this.resourceLayout = resource;
            this.mContext = context;
            this.Names = _Names;
            this.Values = _Values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(mContext);
                v = vi.inflate(resourceLayout, null);
            }
            TextView Name = v.findViewById(R.id.Name);
            TextView Value = v.findViewById(R.id.Value);

            Name.setText(String.valueOf(Names.get(position)));
            Value.setText(String.valueOf(Values.get(position)));

            return v;
        }
    }

}
