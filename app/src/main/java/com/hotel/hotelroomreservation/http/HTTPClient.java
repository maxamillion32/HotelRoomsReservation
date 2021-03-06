package com.hotel.hotelroomreservation.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hotel.hotelroomreservation.model.Currencies;
import com.hotel.hotelroomreservation.utils.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPClient {

    public static Bitmap getPhoto(String URL) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            URL reqUrl = new URL(URL);
            connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setDoInput(true);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public static Currencies getCurrentRate(String URL) {
        String currencyRate = "";

        try {
            URL url = new URL(URL);
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                try {
                    StringBuilder str = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        str.append(line);
                    }

                    currencyRate = str.toString();

                } finally {
                    inputStream.close();
                    reader.close();
                    connection.disconnect();
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSONParser.parseCurrencyRate(currencyRate);
    }
}