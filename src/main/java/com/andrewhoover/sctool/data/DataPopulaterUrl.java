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
    // TODO: This token should not be hard coded. Among other issues, it expires every 30 days.
    // Could be command line arg, or set in a .properties file.
    // Probably should be set in a prop file so I can try out using Spring to read it :D
    protected static String OAuthToken = "6td6f7fvku6wtjvf684twcvg";

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
}
