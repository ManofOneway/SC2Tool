package com.andrewhoover.sctool.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andrew on 3/25/2018.
 */
public abstract class DataPopulaterUrl extends DataPopulater
{
    private static String OAuthToken;

    public String readUrl( URL url ) {
        String response = new String();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            response = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return response;
    }

    public String getOAuthToken() {
        return OAuthToken;
    }

    public void setOAuthToken(String OAuthToken) {
        this.OAuthToken = OAuthToken;
    }
}
