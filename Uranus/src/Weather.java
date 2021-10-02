import java.io.*;
import org.json.*;
import java.net.*;
import java.nio.charset.Charset;


public class Weather {
    String latitude;
    String longitude;

    Weather(String lat, String longt) {

        try {
            String json = fetchURL("https://api.weather.gov/points/" + lat + "," + longt);

            // Response from weather service
            JSONObject weaData = new JSONObject(json);
            // Gets properties
            JSONObject prop = weaData.getJSONObject("properties");
            // Gets link to weather forecast for the area
            String forecastURL = prop.getString("forecast");


            String json2 = fetchURL(forecastURL);
            JSONObject weaFore = new JSONObject(json2);
            JSONObject prop2 = weaFore.getJSONObject("properties");
            JSONArray periods = new JSONArray(prop2.getJSONArray("periods"));

            for (int i = 0; i < periods.length(); i++) {
                JSONObject day = periods.getJSONObject(i);
                System.out.println(day.getString("name") + ": " + day.getString("shortForecast") + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

        // sample data using Austin
        String lat = "30.2672";
        String longi = "-97.7431";
        Weather wea = new Weather(lat, longi);

    }

    public static String fetchURL(final String aUrl) {
        final StringBuilder sb = new StringBuilder();
        URLConnection conn = null;
        InputStreamReader in = null;
        try {
            final URL url = new URL(aUrl);
            conn = url.openConnection();
            if (conn != null)
                conn.setReadTimeout(10 * 1000); // timeout in 10 seconds
            if (conn != null && conn.getInputStream() != null) {
                in = new InputStreamReader(conn.getInputStream(), Charset.defaultCharset());
                final BufferedReader br = new BufferedReader(in);
                if (br != null) {
                    int ch;
                    // read the next character until end of reader
                    while ((ch = br.read()) != -1) {
                        sb.append((char) ch);
                    }
                    br.close();
                }
            }
            in.close();
        } catch (final Exception ex) {
            System.out.println("Exception in url request:" + ex.getMessage());
        }
        return sb.toString();
    }

}
