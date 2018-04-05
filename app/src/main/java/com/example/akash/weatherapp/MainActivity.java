package com.example.akash.weatherapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.TestLooperManager;
import android.provider.MediaStore;
import android.renderscript.Double2;
import android.renderscript.ScriptGroup;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.XMLFormatter;

public class MainActivity extends AppCompatActivity  {
    public TextView temp;
    public TextView three;
    public TextView six;
    public TextView nine;
    public TextView twelve;
    public TextView minmax;
    public TextView maincondition;
    public TextView datetext;
    public TextView q;
    public TextView t;
    public EditText zipcode;
    public Button Search;
    public ConstraintLayout background;
    public int ftemp;
    public String date;
    public int [] min = new int [5];
    public int [] max = new int [5];
    public String [] time = new String[5];
    public String [] condition = new String[5];
    public String [] actualCondition = new String[5];
    public int [] conditionids = new int [5];
    public boolean [] day = new boolean[5];
    public ImageView [] ics = new ImageView[5];
    public ArrayList<String> quotes = new ArrayList<String>();
    String zip ="08810";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = (TextView)findViewById(R.id.current);
        three = (TextView)findViewById(R.id.threehour);
        six = (TextView)findViewById(R.id.sixhour);
        nine = (TextView)findViewById(R.id.ninehour);
        twelve = (TextView)findViewById(R.id.twelvehour);
        minmax = (TextView)findViewById(R.id.minmax);
        datetext = (TextView)findViewById(R.id.date);
        maincondition = (TextView)findViewById(R.id.condition);
        q = (TextView)findViewById(R.id.quote);
        t = (TextView)findViewById(R.id.time);
        ics[0] = (ImageView)findViewById(R.id.main);
        ics[1] = (ImageView)findViewById(R.id.imageView);
        ics[2] = (ImageView)findViewById(R.id.imageView2);
        ics[3] = (ImageView)findViewById(R.id.imageView3);
        ics[4] = (ImageView)findViewById(R.id.imageView4);
        background = (ConstraintLayout)findViewById(R.id.back);
        quotes.add("All clouds have limits. They learn what they are and learn to get bigger and worse");//cloudy
        quotes.add("Why does snow fall? So that robin can play snowball fights with batman");//snow
        quotes.add("I won't rain on you but I won't keep you dry");//rain
        quotes.add("I wanted to ruin Gotham. I failed and kept it sunny");//sunny
        quotes.add("They scream, and they cry! Just like Thunder!");//thunderstorm
        quotes.add("The weather is quite unique");//extraneous

        zipcode = (EditText)findViewById(R.id.zipcode);
        Search = (Button)findViewById(R.id.Search);

        temperatureAccess input = new temperatureAccess();
        input.execute(zip);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zip = zipcode.getText().toString();
                temperatureAccess input = new temperatureAccess();
                input.execute(zip);
                String now = Integer.toString(ftemp)+"º";
                temp.setText(now);
                minmax.setText(max[0]+"º/"+min[0]+"º");
                maincondition.setText(actualCondition[0]);
                t.setText("("+time[0]+")");
                three.setText(time[1]+"\n"+actualCondition[1]+"\n"+min[1]+"º / "+max[1]+"º  ");
                six.setText(time[2]+"\n"+ actualCondition[2] + "\n"+min[2]+"º / "+max[2]+"º  ");
                nine.setText(time[3]+"\n" + actualCondition[3] + "\n"+min[3]+"º / "+max[3]+"º  ");
                twelve.setText(time[4]+"\n" + actualCondition[4] + "\n"+min[4]+"º / "+max[4]+"º  ");
                Images();
                setQuotes();
            }
        });

    }

    public void Images(){
        for(int x = 0; x<5; x++){
            switch(conditionids[x]){
                case 200: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 201: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 202: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 210: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 211: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 212: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 221: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 230: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 231: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 232: ics[x].setImageResource(R.drawable.weezle_cloud_thunder_rain);
                    break;
                case 300: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 301: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 302: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 310: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 311: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 312: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 313: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 314: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 321: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 500: if(day[x])
                    ics[x].setImageResource(R.drawable.weezle_rain);
                else
                    ics[x].setImageResource(R.drawable.weezle_night_rain);
                    break;
                case 501: ics[x].setImageResource(R.drawable.weezle_rain);
                    break;
                case 502: ics[x].setImageResource(R.drawable.weezle_rain);
                    break;
                case 503: ics[x].setImageResource(R.drawable.weezle_rain);
                    break;
                case 504: ics[x].setImageResource(R.drawable.weezle_rain);
                    break;
                case 511: ics[x].setImageResource(R.drawable.weezle_medium_ice);
                    break;
                case 520: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 521: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 522: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 531: ics[x].setImageResource(R.drawable.weezle_medium_rain);
                    break;
                case 600: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 601: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 602: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 611: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 612: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 615: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 616: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 620: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 621: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 622: ics[x].setImageResource(R.drawable.weezle_snow);
                    break;
                case 701: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 711: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 721: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 731: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 741: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 751: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 761: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 762: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 771: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 781: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 800: if(day[x])
                            ics[x].setImageResource(R.drawable.weezle_sun);
                          else
                            ics[x].setImageResource(R.drawable.weezle_fullmoon);
                    break;
                case 801: if(day[x])
                            ics[x].setImageResource(R.drawable.weezle_cloud_sun);
                        else
                            ics[x].setImageResource(R.drawable.weezle_moon_cloud);
                    break;
                case 802: ics[x].setImageResource(R.drawable.weezle_max_cloud);
                    break;
                case 803: ics[x].setImageResource(R.drawable.weezle_overcast);
                    break;
                case 804: ics[x].setImageResource(R.drawable.weezle_overcast);
                    break;
                case 900: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 901: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 902: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 903: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 904: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 905: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 906: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 951: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 952: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 953: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 954: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 955: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 956: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 957: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 958: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 959: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 960: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 961: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;
                case 962: ics[x].setImageResource(R.drawable.weezle_fog);
                    break;




            }
        }

    }

    public void setQuotes(){
        int c = conditionids[0];
        if(c>199 && c<250){//thunder
            q.setText(quotes.get(4));
        }
        else if(c>299 && c<550){//rain
            q.setText(quotes.get(2));
        }
        else if(c>599 && c<650){//snow
            q.setText(quotes.get(1));
        }
        else if (c==800){//sunny
            q.setText(quotes.get(3));
        }
        else if(c>700 && c<850){//clouds
            q.setText(quotes.get(4));
        }
        else {//random
            q.setText(quotes.get(5));
        }

    }

    public class temperatureAccess extends AsyncTask<String , Void , Void>{
        public String st;
        @Override
        protected Void doInBackground(String... strings) {
            int temperature1 = 0;
            try {
                String code = strings[0];
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip="+code+"&appid=bc3a5fe021c3c556d184b37ad0f18aa2");

                URLConnection urlConnection = url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                BufferedReader br = new BufferedReader(reader);
                st = br.readLine();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                JSONObject jsonObject = new JSONObject(st);
                JSONArray forecast = jsonObject.getJSONArray("list");
                JSONObject current = forecast.getJSONObject(0);
                JSONObject currentTemp = new JSONObject(current.getString("main"));
                Double temperature = Double.parseDouble(currentTemp.getString("temp"));
                double fahrenheit = Math.round((1.8*(temperature-273))+32);
                ftemp = (int)fahrenheit;
                date = current.getString("dt_txt").substring(0,10);

                for(int x = 0; x<=4; x++) {
                    JSONObject three = forecast.getJSONObject(x);
                    JSONObject threeTemp = new JSONObject(three.getString("main"));
                    Double minTemp = Double.parseDouble(threeTemp.getString("temp_min"));
                    double fahrenheitmin = Math.round((1.8*(minTemp-273))+32);
                    min[x] = (int)fahrenheitmin;
                    Double maxTemp = Double.parseDouble(threeTemp.getString("temp_max"));
                    double fahrenheitmax = Math.round((1.8*(maxTemp-273))+32);
                    max[x] = (int)fahrenheitmax;
                }

                for(int x = 0; x<5; x++){
                    JSONObject list = forecast.getJSONObject(x);
                    String timestamp = list.getString("dt_txt");
                    String specifictime = timestamp.substring(11,13);
                    Log.d("time",specifictime);
                    int stime = Integer.parseInt(specifictime);

                    if(stime-5 <1){
                        stime = 24+(stime-5);
                    }
                    else stime-=5;

                    int ttime= (stime)%12;
                    if((stime)<13) {
                        time[x] = ttime + ":00 AM";
                    }
                    else time[x] = ttime + ":00 PM";

                    if(stime<6 || stime>16){
                        day[x] = false;}

                    else day[x] = true;
                }

                for(int x = 0; x<5; x++){
                    JSONObject list = forecast.getJSONObject(x);
                    JSONArray weather = list.getJSONArray("weather");
                    JSONObject trueweather = weather.getJSONObject(0);
                    condition[x] = trueweather.getString("main");
                    actualCondition[x] = trueweather.getString("description");
                    conditionids[x]= trueweather.getInt("id");
                }

                String now = Integer.toString(ftemp)+"º";
                temp.setText(now);
                minmax.setText(max[0]+"º/"+min[0]+"º");
                maincondition.setText(actualCondition[0]);
                datetext.setText(date);
                t.setText("("+time[0]+")");
                three.setText(time[1]+"\n"+actualCondition[1]+"\n"+min[1]+"º / "+max[1]+"º  ");
                six.setText(time[2]+"\n"+ actualCondition[2] + "\n"+min[2]+"º / "+max[2]+"º  ");
                nine.setText(time[3]+"\n" + actualCondition[3] + "\n"+min[3]+"º / "+max[3]+"º  ");
                twelve.setText(time[4]+"\n" + actualCondition[4] + "\n"+min[4]+"º / "+max[4]+"º  ");
                Images();
                setQuotes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
