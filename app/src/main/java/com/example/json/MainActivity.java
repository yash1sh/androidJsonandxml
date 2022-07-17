package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private Button jsonBtnId, xmlBtnId;  private TextView cityNameId,latitudeValueId,longitudeValueId;
    private TextView temperatureValueId,humidityValueId;
    private TextView cityNameId1,latitudeValueId1,longitudeValueId1;
    private TextView temperatureValueId1,humidityValueId1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsonBtnId = findViewById(R.id.jsonBtn);
        xmlBtnId = findViewById(R.id.xmlBtn);
        cityNameId = findViewById(R.id.cityName);
        latitudeValueId = findViewById(R.id.latitude);
        longitudeValueId = findViewById(R.id.longitude);
        temperatureValueId = findViewById(R.id.temperature);
        humidityValueId = findViewById(R.id.humidity);
        cityNameId1 = findViewById(R.id.cityName1);
        latitudeValueId1 = findViewById(R.id.latitude1);
        longitudeValueId1 = findViewById(R.id.longitude1);
        temperatureValueId1 = findViewById(R.id.temperature1);  humidityValueId1 = findViewById(R.id.humidity1);
        jsonBtnId.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {  try
            {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                String cityNome = obj.get("City Name").toString();
                String latitude = obj.get("Latitude").toString();
                String longitude = obj.get("Longitude").toString();
                String temperature = obj.get("Temperature").toString();  String humidity = obj.get("Humidity").toString();
                cityNameId.setText(cityNome);  latitudeValueId.setText(latitude);  longitudeValueId.setText(longitude);  temperatureValueId.setText(temperature);
                humidityValueId.setText(humidity);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            }
        });
        xmlBtnId.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {  try
            {
                InputStream is = getAssets().open("Sample.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element = doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("data");
                for (int i = 0; i < nList.getLength(); i++)
                {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element element2 = (Element) node;  cityNameId1.setText(getValue("City_Name", element2));  latitudeValueId1.setText( getValue("Latitude", element2));  longitudeValueId1.setText( getValue("Longitude", element2));  temperatureValueId1.setText( getValue("Temperature", element2));  humidityValueId1.setText( getValue("Humidity", element2));
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            }
        });  }
    private static String getValue(String tag, Element element)
    {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
    public String loadJSONFromAsset()
    {
        String json = null;
        try
        {
            InputStream is = getAssets().open("Sample.json");
            int size = is.available();  byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException ex)
        {  ex.printStackTrace();
            return null;
        }
        return json;
    }
}
